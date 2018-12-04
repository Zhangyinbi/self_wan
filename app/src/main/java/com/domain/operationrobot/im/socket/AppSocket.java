package com.domain.operationrobot.im.socket;

import android.text.TextUtils;
import com.domain.operationrobot.BaseApplication;
import com.domain.operationrobot.im.listener.IConstants;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author silencezwm on 2017/8/25 上午11:12
 * @email silencezwm@gmail.com
 * @description AppSocket
 */
public class AppSocket extends BaseSocket {

  private static volatile AppSocket INSTANCE = null;

  private AppSocket(Builder builder) {
    super(builder);
    INSTANCE = this;
  }

  public static AppSocket getInstance() {
    if (INSTANCE == null) {
      throw new NullPointerException("must first call the build() method");
    }
    return INSTANCE;
  }

  public static AppSocket init(Builder builder) {
    return new AppSocket(builder);
  }

  /**
   * fasong机器人消息
   */
  public void sendMessage(int type, String content) {
    try {
      JSONObject jsonObject = new JSONObject();
      JSONObject jsonObject1 = new JSONObject();
      jsonObject.put("token", BaseApplication.getInstance()
                                             .getUser()
                                             .getToken());
      jsonObject.put("type", type);
      jsonObject.put("msg", content);
      String companyid = BaseApplication.getInstance()
                                        .getUser()
                                        .getCompanyid();
      jsonObject.putOpt("companyid", companyid);
      jsonObject1.putOpt("data", jsonObject);
      mSocket.emit(IConstants.CHAT_BOT, jsonObject1);
    } catch (JSONException e) {
      e.printStackTrace();
    }
  }

  /**
   * fasong机器人消息,重启服务器
   */
  public void sendRobotMessage(String hosdip, String action, String msgId) {
    try {
      JSONObject jsonObject = new JSONObject();
      JSONObject jsonObject1 = new JSONObject();
      jsonObject.put("token", BaseApplication.getInstance()
                                             .getUser()
                                             .getToken());
      jsonObject.put("type", 10);
      JSONObject jsonObject2 = new JSONObject();
      jsonObject2.put("msg", hosdip);
      jsonObject2.put("action", action);
      if (!TextUtils.isEmpty(msgId)) {
        jsonObject2.put("msgid", msgId);
      }
      String companyid = BaseApplication.getInstance()
                                        .getUser()
                                        .getCompanyid();
      jsonObject.putOpt("companyid", companyid);
      jsonObject.put("rootbean", jsonObject2);
      jsonObject1.putOpt("data", jsonObject);
      mSocket.emit(IConstants.CHAT_BOT, jsonObject1);
    } catch (JSONException e) {
      e.printStackTrace();
    }
  }

  /**
   * 发送消息
   */
  public void sendMessage(String content) {
    try {
      JSONObject jsonObject = new JSONObject();
      jsonObject.put("token", BaseApplication.getInstance()
                                             .getUser()
                                             .getToken());
      String companyid = BaseApplication.getInstance()
                                        .getUser()
                                        .getCompanyid();
      jsonObject.putOpt("companyid", companyid);
      jsonObject.put("msg", content);
      mSocket.emit(IConstants.TALK, jsonObject);
    } catch (JSONException e) {
      e.printStackTrace();
    }
  }

  /**
   * 发送给机器人消息
   */
  public void connTest() {
    mSocket.emit(IConstants.CONN, "...");
  }

  /**
   * 加入房间
   */
  public void setConnSure() {
    try {
      JSONObject jsonObject = new JSONObject();
      jsonObject.put("token", BaseApplication.getInstance()
                                             .getUser()
                                             .getToken());
      String companyid = BaseApplication.getInstance()
                                        .getUser()
                                        .getCompanyid();
      jsonObject.put("msg", "joinroom");
      jsonObject.putOpt("companyid", companyid);
      mSocket.emit(IConstants.TALK, jsonObject);
    } catch (JSONException e) {
      e.printStackTrace();
    }
  }

  /**
   * 输入中
   */
  public void typing() {
    mSocket.emit(IConstants.TYPING);
  }

  /**
   * 停止输入
   */
  public void stopTyping() {
    mSocket.emit(IConstants.STOP_TYPING);
  }
}
