package com.domain.operationrobot;

import android.app.ActivityManager;
import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import android.support.multidex.MultiDex;
import android.util.Log;

import com.domain.library.BaseApplicationController;
import com.domain.library.exception.AppUncaughtExceptionHandler;
import com.domain.library.exception.SdcardConfig;
import com.domain.library.http.RetrofitHelper;
import com.domain.library.utils.SpUtils;
import com.domain.operationrobot.http.UpLoadFileHelper;
import com.domain.operationrobot.http.bean.User;
import com.xiaomi.channel.commonutils.logger.LoggerInterface;
import com.xiaomi.mipush.sdk.Logger;
import com.xiaomi.mipush.sdk.MiPushClient;

import java.util.List;

import static com.domain.operationrobot.util.Constant.IS_LOGIN;
import static com.domain.operationrobot.util.Constant.USER_SP_KEY;

/**
 * Project Name : OperationRobot
 * description:null
 *
 * @author : yinbi.zhang.o
 * Create at : 2018/10/9 23:15
 */
public class BaseApplication extends Application {

    public static final String APP_ID = "2882303761517701997";
    public static final String APP_KEY = "UBqfw8+eyet7eoH3WQOsdQ==";
    public static final String TAG = "mi====push";
    private static BaseApplication mInstance = null;
    private User user;

    public static BaseApplication getInstance() {
        if (mInstance == null) {
            throw new IllegalStateException("Application is not created.");
        }
        return mInstance;
    }

    public User getUser() {
        if (user == null) {
            user = SpUtils.getObject(USER_SP_KEY, User.class);
        }

        if (user == null) {
            SpUtils.putBoolean(IS_LOGIN, false);
            //  重启
            Intent i = getBaseContext().getPackageManager()
                    .getLaunchIntentForPackage(getBaseContext().getPackageName());
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
            return null;
        }
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        BaseApplicationController.init(this, BuildConfig.DEBUG);
        SpUtils.init(this);
        initMiPush();
        mInstance = this;
        initRetrofit();
        // 初始化文件目录
        SdcardConfig.getInstance()
                .initSdcard();
        // 捕捉异常
        AppUncaughtExceptionHandler crashHandler = AppUncaughtExceptionHandler.getInstance();
        crashHandler.init(getApplicationContext());
        createNotificationChannel();
    }

    /**
     * 注册小米推送
     */
    private void initMiPush() {
        if (shouldInit()) {
            MiPushClient.registerPush(this, APP_ID, APP_KEY);
        }
        //打开Log
        LoggerInterface newLogger = new LoggerInterface() {

            @Override
            public void setTag(String tag) {
                // ignore
            }

            @Override
            public void log(String content, Throwable t) {
                Log.d(TAG, content, t);
            }

            @Override
            public void log(String content) {
                Log.d(TAG, content);
            }
        };
        Logger.setLogger(this, newLogger);
    }

    private boolean shouldInit() {
        ActivityManager am = ((ActivityManager) getSystemService(Context.ACTIVITY_SERVICE));
        List<ActivityManager.RunningAppProcessInfo> processInfos = am.getRunningAppProcesses();
        String mainProcessName = getPackageName();
        int myPid = android.os.Process.myPid();
        for (ActivityManager.RunningAppProcessInfo info : processInfos) {
            if (info.pid == myPid && mainProcessName.equals(info.processName)) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    private void initRetrofit() {
        //TODO 切换环境需要SpUtils存储一下环境在本地
        //EnvManager.updateENV(SpUtils.getObject(BASE_API, String.class));
        //RetrofitHelper.getInstance().initUrl(new ApiProviderImp(EnvManager.getENV()).getBaseUrl()).builder();
        RetrofitHelper.getInstance()
                .initUrl("http://139.196.107.14:5000/")
                .builder();
        UpLoadFileHelper.getInstance()
                .initUrl("http://139.196.107.14:6000/")
                .builder();
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel on API 26+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("new_message", getString(R.string.notification_channel_name),
                    NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription(getString(R.string.notification_channel_description));
            NotificationManager nm = getSystemService(NotificationManager.class);
            if (nm != null) {
                nm.createNotificationChannel(channel);
            }
        }
    }
}
