package com.domain.library.http;


import android.text.TextUtils;

import com.domain.library.BaseApplicationController;
import com.domain.library.http.exception.ExceptionFunction;
import com.domain.library.utils.SpUtils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.domain.library.utils.Constant.BASE_URL_KEY;
import static com.domain.library.utils.Constant.CONNECT_TIMEOUT;
import static com.domain.library.utils.Constant.READ_TIMEOUT;

/**
 * Project Name : DO-Feidian-Core-Android
 * <p>{}</p>
 *
 * @author : yinbi.zhang.o
 * Create at : 2018/5/16 09:46
 */
public class RetrofitHelper {
    /**
     * 默认超时30s
     */

    private static RetrofitHelper instance;
    private String baseUrl;
    private Retrofit retrofit;
    private ArrayList<Interceptor> interceptors;

    private RetrofitHelper() {
        interceptors = new ArrayList<>();
    }

    public static RetrofitHelper getInstance() {
        synchronized (RetrofitHelper.class) {
            if (null == instance) {
                instance = new RetrofitHelper();
            }
        }
        return instance;
    }

    public void builder() {
        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder()
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .sslSocketFactory(TrustManager.getUnsafeOkHttpClient());
        if (null != interceptors && interceptors.size() > 0) {
            for (int i = 0; i < interceptors.size(); i++) {
                okHttpClientBuilder.addInterceptor(interceptors.get(i));
            }
        }
        if (BaseApplicationController.getDebug()) {
            okHttpClientBuilder
                    .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor
                            .Level.BODY));
        }

        if (TextUtils.isEmpty(baseUrl)) {
            baseUrl = SpUtils.getString(BASE_URL_KEY);
        }
        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttpClientBuilder.build())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public RetrofitHelper addInterceptor(Interceptor interceptor) {
        interceptors.add(interceptor);
        return this;
    }

    public RetrofitHelper initUrl(String baseUrl) {
        this.baseUrl = baseUrl;
        SpUtils.putString(BASE_URL_KEY, baseUrl);
        return this;
    }

    /**
     * @param service 具体的请求接口类
     * @param <T>
     * @return 返回service的代理对象出去
     */
    public <T> T create(Class<T> service) {
        if (!service.isInterface()) {
            throw new IllegalArgumentException("API declarations must be interfaces.");
        }
        if (service.getInterfaces().length > 0) {
            throw new IllegalArgumentException("API interfaces must not extend other interfaces.");
        }
        final T t = retrofit.create(service);
        Object o = Proxy.newProxyInstance(service.getClassLoader()
                , new Class<?>[]{service}, new InvocationHandler() {
                    @Override
                    public Object invoke(Object o, Method method, Object[] args) throws Throwable {
                        Object invoke = null;
                        try {
                            invoke = method.invoke(t, args);
                        } catch (InvocationTargetException e) {
                            e.printStackTrace();
                        }
                        if (invoke instanceof Observable) {
                            return ((Observable) invoke).onErrorResumeNext(new ExceptionFunction()).compose(RxHelper
                                    .rxObservableSchedulerHelper());
                        }
                        if (invoke instanceof Flowable) {
                            return ((Flowable) invoke).onErrorResumeNext(new ExceptionFunction()).compose(RxHelper
                                    .rxFlowSchedulerHelper());
                        }
                        return invoke;
                    }
                });
        return (T) o;
    }
}
