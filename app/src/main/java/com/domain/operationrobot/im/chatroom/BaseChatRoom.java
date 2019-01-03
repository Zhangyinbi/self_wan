package com.domain.operationrobot.im.chatroom;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import com.domain.library.utils.SpUtils;
import com.domain.library.utils.ToastUtils;
import com.domain.operationrobot.BaseApplication;
import com.domain.operationrobot.http.data.RemoteMode;
import com.domain.operationrobot.im.bean.NewMessage;
import com.domain.operationrobot.im.bean.ObserverModel;
import com.domain.operationrobot.im.bean.RootMessage1;
import com.domain.operationrobot.im.bean.RootMessage11;
import com.domain.operationrobot.im.bean.RootMessage12;
import com.domain.operationrobot.im.bean.RootMessage2;
import com.domain.operationrobot.im.bean.RootMessage34;
import com.domain.operationrobot.im.bean.RootMessage6;
import com.domain.operationrobot.im.bean.RootMessage8;
import com.domain.operationrobot.im.bean.UpDataMsg;
import com.domain.operationrobot.im.listener.IChatRoom;
import com.domain.operationrobot.im.listener.IConstants;
import com.domain.operationrobot.im.listener.IEventType;
import com.domain.operationrobot.im.socket.AppSocket;
import com.google.gson.Gson;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.socket.client.Manager;
import io.socket.client.Socket;
import java.util.Observable;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author silencezwm on 2017/8/25 上午11:18
 * @email silencezwm@gmail.com
 * @description 基类聊天室
 */
public class BaseChatRoom extends Observable implements IChatRoom {

  private final Gson mGson;
  boolean flag = true;
  private String TAG = "---------";
  private long tempTime;
  private int  errCount;

  BaseChatRoom() {
    mGson = new Gson();
  }

  /**
   * 更新普通信息
   */
  private void upData(JSONObject data, boolean flag) {
    setChanged();
    ObserverModel model = new ObserverModel();
    model.setFlag(flag);
    model.setEventType(IEventType.NEW_MESSAGE);
    NewMessage newMessage = mGson.fromJson(data.toString(), NewMessage.class);
    newMessage.setTime(System.currentTimeMillis());
    model.setNewMessage(newMessage);
    notifyObservers(model);
  }

  private void handlerMsg(JSONObject content) {
    this.handlerMsg(content, false);
  }

  private void handlerMsg(JSONObject content, boolean flag) {
    setChanged();
    //存本地
    SpUtils.putString("msgid" + BaseApplication.getInstance()
                                               .getUser()
                                               .getUserId(), content.optString("msgid"));
    int type = content.optInt("type", -1);
    if (type == -1) {
      upData(content, flag);
    } else {
      upDataRoot(content, flag);
    }
  }

  /**
   * 更新机器人消息
   */
  private void upDataRoot(JSONObject jsonObject, boolean flag) {
    setChanged();
    int type = jsonObject.optInt("type");
    String imageUrl = jsonObject.optString("imageUrl");
    String username = jsonObject.optString("username");
    String userid = jsonObject.optString("userid");
    JSONObject rootBean = jsonObject.optJSONObject("rootbean");
    if (rootBean == null) {
      upData(jsonObject, flag);
      return;
    } else {
      try {
        rootBean.put("imageUrl", imageUrl);
        rootBean.put("username", username);
        rootBean.put("userid", userid);
      } catch (JSONException e) {
        e.printStackTrace();
      }
    }
    switch (type) {
      case 1:
        parse_type_1(rootBean, flag);
        break;
      case 2:
        parse_type_2(rootBean, flag);
        break;
      case 3://cpu
        parse_type_34(rootBean, flag);
        break;
      case 4:
        parse_type_34(rootBean, flag);
        break;
      case 5:
        parse_type_2(rootBean, flag);
        break;
      case 6://磁盘
        parse_type_6(rootBean, flag);
        break;
      case 7:
        parse_type_2(rootBean, flag);
        break;
      case 8:
        parse_type_8(rootBean, flag);
        break;
      case 10:
        parse_type_10(rootBean, flag);
        break;
      case 11:
        parse_type_11(rootBean, flag);
        break;
      case 12:
        parse_type_12(rootBean, flag);
        break;
    }
  }



  private void parse_type_10(JSONObject rootBean, boolean flag) {
    setChanged();
    ObserverModel model = new ObserverModel();
    model.setEventType(IEventType.NEW_MESSAGE);
    NewMessage newMessage = mGson.fromJson(rootBean.toString(), NewMessage.class);
    newMessage.setTime(System.currentTimeMillis());
    model.setFlag(flag);
    model.setNewMessage(newMessage);
    notifyObservers(model);
    if (!TextUtils.isEmpty(rootBean.optString("action"))) {
      upDataChatBean(rootBean.optString("action"), rootBean.optString("msgid"));
    }
  }

  private void upDataChatBean(String action, String msgId) {
    setChanged();
    ObserverModel model = new ObserverModel();
    model.setEventType(IEventType.UP_DATE_MESSAGE);
    model.setFlag(flag);
    UpDataMsg upDataMsg = new UpDataMsg(action, msgId);
    model.setUpDataMsg(upDataMsg);
    notifyObservers(model);
  }

  private void parse_type_12(JSONObject rootBean, boolean flag) {
    ObserverModel model = new ObserverModel();
    model.setEventType(IEventType.ROOT_MESSAGE_TYPE_12);
    model.setFlag(flag);
    RootMessage12 newMessage = mGson.fromJson(rootBean.toString(), RootMessage12.class);
    newMessage.setTime(System.currentTimeMillis());
    model.setRootMessage12(newMessage);
    notifyObservers(model);
  }

  private void parse_type_11(JSONObject rootBean, boolean flag) {
    setChanged();
    ObserverModel model = new ObserverModel();

    if (rootBean.opt("userid")
                .equals(BaseApplication.getInstance()
                                       .getUser()
                                       .getUserId())) {
      model.setEventType(IEventType.NEW_MESSAGE);
      model.setFlag(flag);
      NewMessage newMessage = mGson.fromJson(rootBean.toString(), NewMessage.class);
      newMessage.setTime(System.currentTimeMillis());
      model.setNewMessage(newMessage);
      notifyObservers(model);
      return;
    }
    model.setEventType(IEventType.ROOT_MESSAGE_TYPE_11);
    model.setFlag(flag);
    RootMessage11 newMessage = mGson.fromJson(rootBean.toString(), RootMessage11.class);
    newMessage.setTime(System.currentTimeMillis());
    model.setRootMessage11(newMessage);
    notifyObservers(model);
  }

  /**
   * 查看网络状况
   */
  private void parse_type_8(JSONObject rootBean, boolean flag) {
    ObserverModel model = new ObserverModel();
    model.setEventType(IEventType.ROOT_MESSAGE_TYPE_8);
    model.setFlag(flag);
    RootMessage8 newMessage = mGson.fromJson(rootBean.toString(), RootMessage8.class);
    newMessage.setTime(System.currentTimeMillis());
    model.setRootMessage8(newMessage);
    notifyObservers(model);
  }

  /**
   * 解析第二种数据类型
   */
  private void parse_type_2(JSONObject rootBean, boolean flag) {
    ObserverModel model = new ObserverModel();
    model.setEventType(IEventType.ROOT_MESSAGE_TYPE_2);
    model.setFlag(flag);
    RootMessage2 newMessage = mGson.fromJson(rootBean.toString(), RootMessage2.class);
    newMessage.setTime(System.currentTimeMillis());
    model.setRootMessage2(newMessage);
    notifyObservers(model);
  }

  /**
   * cpu  内存
   */
  private void parse_type_34(JSONObject rootBean, boolean flag) {
    ObserverModel model = new ObserverModel();
    model.setEventType(IEventType.ROOT_MESSAGE_TYPE_34);
    model.setFlag(flag);
    RootMessage34 newMessage = mGson.fromJson(rootBean.toString(), RootMessage34.class);
    newMessage.setTime(System.currentTimeMillis());
    model.setRootMessage34(newMessage);
    notifyObservers(model);
  }

  /**
   * 磁盘使用状况
   */
  private void parse_type_6(JSONObject rootBean, boolean flag) {
    ObserverModel model = new ObserverModel();
    model.setEventType(IEventType.ROOT_MESSAGE_TYPE_6);
    model.setFlag(flag);
    RootMessage6 newMessage = mGson.fromJson(rootBean.toString(), RootMessage6.class);
    newMessage.setTime(System.currentTimeMillis());
    model.setRootMessage6(newMessage);
    notifyObservers(model);
  }

  /**
   * 机器人普通消息
   */
  private void parse_type_1(JSONObject rootBean, boolean flag) {
    ObserverModel model = new ObserverModel();
    model.setFlag(flag);
    model.setEventType(IEventType.ROOT_MESSAGE_TYPE_1);
    RootMessage1 newMessage = mGson.fromJson(rootBean.toString(), RootMessage1.class);
    newMessage.setTime(System.currentTimeMillis());
    model.setRootMessage1(newMessage);
    notifyObservers(model);
  }

  @Override
  public void emitterListenerResut(String key, Object... args) {
    switch (key) {
      case Manager.EVENT_TRANSPORT:
        Log.e(TAG, "EVENT_CONNECT_ERROR");
        break;

      case Socket.EVENT_CONNECT_ERROR:
        if (System.currentTimeMillis() - tempTime > 7000 && errCount < 5) {
          ToastUtils.showToast("登陆聊天服务器失败");
          tempTime = System.currentTimeMillis();
          errCount++;
        }
        Log.e(TAG, "EVENT_CONNECT_ERROR");

        break;

      case Socket.EVENT_CONNECT_TIMEOUT:
        Log.e(TAG, "EVENT_CONNECT_TIMEOUT");
        break;

      case IConstants.TALK_STATUS:
        JSONObject content = (JSONObject) args[0];
        Log.e(TAG, "接收到其他人消息: " + content.toString());
        JSONObject contentTalk = content.optJSONObject("data");
        handlerMsg(contentTalk);
        //upData(content);
        break;
      case IConstants.CHAT_BOT_STATUS:
        JSONObject rootData = (JSONObject) args[0];
        Log.e(TAG, "接收到机器人消息: " + rootData.toString());
        //upDataRoot(rootData);
        JSONObject msg = rootData.optJSONObject("data");
        handlerMsg(msg);
        break;

      case IConstants.CONNECT_STATUS:
        JSONObject data = (JSONObject) args[0];
        try {
          String data1 = data.getString("data");
          Log.e(TAG, "emitterListenerResut: " + data1);
        } catch (JSONException e) {
          e.printStackTrace();
        }
        break;
      // Socket连接成功
      case Socket.EVENT_CONNECT:
        errCount = 0;
        tempTime = 0;
        Log.e(TAG, "链接成功，发送一条消息 确认加入房间");
        AppSocket.getInstance()
                 .setConnSure();
        if (flag) {
          flag = false;
          getOffLinMsg();
        }
        break;
      // Socket断开连接
      case Socket.EVENT_DISCONNECT:
        flag = true;
        Log.e(TAG, "EVENT_DISCONNECT");
        break;
      // Socket连接错误
      case Socket.EVENT_ERROR:
        flag = true;
        Log.e(TAG, "EVENT_ERROR");
        break;

      // Socket重新连接
      case Socket.EVENT_RECONNECT:
        Log.d(TAG, "EVENT_RECONNECT");
        errCount = 0;
        tempTime = 0;
        Log.e(TAG, "链接成功，发送一条消息 确认加入房间");
        AppSocket.getInstance()
                 .setConnSure();
        if (flag) {
          flag = false;
          getOffLinMsg();
        }
        break;

      case Socket.EVENT_RECONNECT_ATTEMPT:
        Log.e(TAG, "EVENT_RECONNECT_ATTEMPT");
        break;

      case Socket.EVENT_RECONNECT_ERROR:
        Log.e(TAG, "EVENT_RECONNECT_ERROR");
        break;

      case Socket.EVENT_RECONNECT_FAILED:
        Log.e(TAG, "EVENT_RECONNECT_FAILED");
        break;

      case Socket.EVENT_RECONNECTING:
        Log.e(TAG, "EVENT_RECONNECTING");
        break;
    }
  }

  private void getOffLinMsg() {
    String msgid = SpUtils.getString("msgid" + BaseApplication.getInstance()
                                                              .getUser()
                                                              .getUserId());
    if (TextUtils.isEmpty(msgid)) {
      return;
    }
    RemoteMode.getInstance()
              .getOffLineMsg(msgid)
              .subscribe(new Observer<String>() {
                @Override
                public void onSubscribe(Disposable d) {

                }

                @Override
                public void onNext(String s) {
                  try {
                    JSONObject jsonObject = new JSONObject(s);
                    int status = jsonObject.optInt("status", -1);
                    if (status == 0) {
                      JSONArray jsonArray = jsonObject.getJSONArray("data");
                      int length = jsonArray.length();
                      if (length == 0) {
                        return;
                      }
                      notifyObserversOffMsg();
                      for (int i = 0; i < length; i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        if (!jsonObject1.isNull("data")) {
                          jsonObject1 = jsonObject1.getJSONObject("data");
                        }
                        if (i == length - 1) {
                          handlerMsg(jsonObject1, false);
                        } else {
                          handlerMsg(jsonObject1, true);
                        }
                      }
                    } else {
                      ToastUtils.showToast(jsonObject.optString("msg"));
                    }
                  } catch (JSONException e) {
                    e.printStackTrace();
                  }
                }

                @Override
                public void onError(Throwable e) {

                }

                @Override
                public void onComplete() {

                }
              });
  }

  private void notifyObserversOffMsg() {
    setChanged();
    notifyObservers(1);
  }

  @Override
  public void requestSocketResult(String key, Object... args) {

    switch (key) {

    }
  }
}
