package com.domain.library.http;

import org.reactivestreams.Publisher;

import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Project Name : DO-Feidian-Core-Android
 * <p>{线程调度的帮助类}</p>
 *
 * @author : yinbi.zhang.o
 * Create at : 2018/5/15 12:27
 */
public class RxHelper {

    /**
     * 线程调度
     *
     * @param <T>
     * @return
     */
    public static <T> ObservableTransformer<T, T> rxObservableSchedulerHelper() {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                return upstream.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    /**
     * 线程调度
     *
     * @param <T>
     * @return
     */
    public static <T> FlowableTransformer<T, T> rxFlowSchedulerHelper() {
        return new FlowableTransformer<T, T>() {
            @Override
            public Publisher<T> apply(Flowable<T> upstream) {
                return upstream.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
            }


        };
    }
}