package com.domain.operationrobot;

import android.app.Application;

import com.domain.library.BaseApplicationController;
import com.domain.library.exception.AppUncaughtExceptionHandler;
import com.domain.library.exception.SdcardConfig;
import com.domain.library.http.RetrofitHelper;
import com.domain.library.utils.SpUtils;
import com.domain.operationrobot.http.api.ApiProviderImp;
import com.domain.operationrobot.http.bean.User;
import com.domain.operationrobot.http.env.EnvManager;

import static com.domain.operationrobot.http.Constant.BASE_API;
import static com.domain.operationrobot.util.Constant.USER_SP_KEY;


/**
 * Project Name : OperationRobot
 * description:null
 *
 * @author : yinbi.zhang.o
 * Create at : 2018/10/9 23:15
 */
public class BaseApplication extends Application {

    private static BaseApplication mInstance = null;
    private User user;

    public static BaseApplication getInstance() {
        if (mInstance == null) {
            throw new IllegalStateException("Application is not created.");
        }
        return mInstance;
    }

    public User getUser() {
        return user == null ? SpUtils.get(USER_SP_KEY, User.class) : user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        BaseApplicationController.init(this, BuildConfig.DEBUG);
        mInstance = this;
        initRetrofit();
        // 初始化文件目录
        SdcardConfig.getInstance().initSdcard();
        // 捕捉异常
        AppUncaughtExceptionHandler crashHandler = AppUncaughtExceptionHandler.getInstance();
        crashHandler.init(getApplicationContext());
    }

    private void initRetrofit() {
        //TODO 切换环境需要SpUtils存储一下环境在本地
        EnvManager.updateENV(SpUtils.getString(BASE_API));
        RetrofitHelper.getInstance().initUrl(new ApiProviderImp(EnvManager.getENV()).getBaseUrl()).builder();
    }
}
