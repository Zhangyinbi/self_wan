package com.domain.operationrobot;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import android.support.multidex.MultiDex;
import com.domain.library.BaseApplicationController;
import com.domain.library.exception.AppUncaughtExceptionHandler;
import com.domain.library.exception.SdcardConfig;
import com.domain.library.http.RetrofitHelper;
import com.domain.library.utils.SpUtils;
import com.domain.operationrobot.http.bean.User;

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
    galleryInit();
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

  private void galleryInit() {


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
