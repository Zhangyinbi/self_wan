package com.domain.operationrobot.mipush;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import android.util.Log;
import com.domain.library.utils.App;
import com.domain.operationrobot.BaseApplication;
import com.domain.operationrobot.app.login.LoginActivity;
import com.xiaomi.mipush.sdk.ErrorCode;
import com.xiaomi.mipush.sdk.MiPushClient;
import com.xiaomi.mipush.sdk.MiPushCommandMessage;
import com.xiaomi.mipush.sdk.MiPushMessage;
import com.xiaomi.mipush.sdk.PushMessageReceiver;

import java.util.List;

import static android.content.Context.ACTIVITY_SERVICE;
import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class MiMessageReceiver extends PushMessageReceiver {
  private String mRegId;
  private long mResultCode = -1;
  private String mReason;
  private String mCommand;
  private String mMessage;
  private String mTopic;
  private String mAlias;
  private String mUserAccount;
  private String mStartTime;
  private String mEndTime;

  @Override
  public void onReceivePassThroughMessage(Context context, MiPushMessage message) {
    mMessage = message.getContent();
    if (!TextUtils.isEmpty(message.getTopic())) {
      mTopic = message.getTopic();
    } else if (!TextUtils.isEmpty(message.getAlias())) {
      mAlias = message.getAlias();
    } else if (!TextUtils.isEmpty(message.getUserAccount())) {
      mUserAccount = message.getUserAccount();
    }
  }

  @Override
  public void onNotificationMessageClicked(Context context, MiPushMessage message) {
    mMessage = message.getContent();
    if (!TextUtils.isEmpty(message.getTopic())) {
      mTopic = message.getTopic();
    } else if (!TextUtils.isEmpty(message.getAlias())) {
      mAlias = message.getAlias();
    } else if (!TextUtils.isEmpty(message.getUserAccount())) {
      mUserAccount = message.getUserAccount();
    }
    if (App.isRunningForeground(context)) {//前台运行
      return;
    } else {
      //获取ActivityManager
      ActivityManager mAm = (ActivityManager) context.getSystemService(ACTIVITY_SERVICE);
      //获得当前运行的task
      List<ActivityManager.RunningTaskInfo> taskList = mAm.getRunningTasks(100);
      for (ActivityManager.RunningTaskInfo rti : taskList) {
        //找到当前应用的task，并启动task的栈顶activity，达到程序切换到前台
        if (rti.topActivity.getPackageName()
                           .equals(App.getPackageName(context))) {
          mAm.moveTaskToFront(rti.id, 0);
          return;
        }
      }
      //若没有找到运行的task，用户结束了task或被系统释放，LoginActivity
      Intent resultIntent = new Intent(context, LoginActivity.class);
      resultIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
      context.startActivity(resultIntent);
    }
  }

  @Override
  public void onNotificationMessageArrived(Context context, MiPushMessage message) {
    mMessage = message.getContent();
    if (!TextUtils.isEmpty(message.getTopic())) {
      mTopic = message.getTopic();
    } else if (!TextUtils.isEmpty(message.getAlias())) {
      mAlias = message.getAlias();
    } else if (!TextUtils.isEmpty(message.getUserAccount())) {
      mUserAccount = message.getUserAccount();
    }
  }

  @Override
  public void onCommandResult(Context context, MiPushCommandMessage message) {
    String command = message.getCommand();
    List<String> arguments = message.getCommandArguments();
    String cmdArg1 = ((arguments != null && arguments.size() > 0) ? arguments.get(0) : null);
    String cmdArg2 = ((arguments != null && arguments.size() > 1) ? arguments.get(1) : null);
    if (MiPushClient.COMMAND_REGISTER.equals(command)) {
      if (message.getResultCode() == ErrorCode.SUCCESS) {
        mRegId = cmdArg1;
        Log.e("-----1111", "onCommandResult: " + mRegId);
      }
    } else if (MiPushClient.COMMAND_SET_ALIAS.equals(command)) {
      if (message.getResultCode() == ErrorCode.SUCCESS) {
        mAlias = cmdArg1;
      }
    } else if (MiPushClient.COMMAND_UNSET_ALIAS.equals(command)) {
      if (message.getResultCode() == ErrorCode.SUCCESS) {
        mAlias = cmdArg1;
      }
    } else if (MiPushClient.COMMAND_SUBSCRIBE_TOPIC.equals(command)) {
      if (message.getResultCode() == ErrorCode.SUCCESS) {
        mTopic = cmdArg1;
      }
    } else if (MiPushClient.COMMAND_UNSUBSCRIBE_TOPIC.equals(command)) {
      if (message.getResultCode() == ErrorCode.SUCCESS) {
        mTopic = cmdArg1;
      }
    } else if (MiPushClient.COMMAND_SET_ACCEPT_TIME.equals(command)) {
      if (message.getResultCode() == ErrorCode.SUCCESS) {
        mStartTime = cmdArg1;
        mEndTime = cmdArg2;
      }
    }
  }

  @Override
  public void onReceiveRegisterResult(Context context, MiPushCommandMessage message) {
    String command = message.getCommand();
    List<String> arguments = message.getCommandArguments();
    String cmdArg1 = ((arguments != null && arguments.size() > 0) ? arguments.get(0) : null);
    String cmdArg2 = ((arguments != null && arguments.size() > 1) ? arguments.get(1) : null);
    if (MiPushClient.COMMAND_REGISTER.equals(command)) {
      if (message.getResultCode() == ErrorCode.SUCCESS) {
        mRegId = cmdArg1;
        if (BaseApplication.getInstance()
                           .getUser() != null) {
          MiPushClient.setUserAccount(context, BaseApplication.getInstance()
                                                              .getUser()
                                                              .getUserId(), null);
        }
        Log.e("-----22222", "onCommandResult: " + mRegId);
      }
    }
  }
}