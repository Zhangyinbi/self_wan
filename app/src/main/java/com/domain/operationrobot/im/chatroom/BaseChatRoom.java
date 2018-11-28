package com.domain.operationrobot.im.chatroom;

import android.util.Log;
import android.widget.Toast;
import com.domain.library.utils.ToastUtils;
import com.domain.operationrobot.im.bean.NewMessage;
import com.domain.operationrobot.im.bean.ObserverModel;
import com.domain.operationrobot.im.bean.RootMessage1;
import com.domain.operationrobot.im.bean.RootMessage2;
import com.domain.operationrobot.im.bean.RootMessage34;
import com.domain.operationrobot.im.bean.RootMessage6;
import com.domain.operationrobot.im.bean.RootMessage8;
import com.domain.operationrobot.im.listener.IChatRoom;
import com.domain.operationrobot.im.listener.IConstants;
import com.domain.operationrobot.im.listener.IEventType;
import com.domain.operationrobot.im.socket.AppSocket;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import io.socket.client.Manager;
import io.socket.client.Socket;
import java.util.Observable;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author silencezwm on 2017/8/25 上午11:18
 * @email silencezwm@gmail.com
 * @description 基类聊天室
 */
public class BaseChatRoom extends Observable implements IChatRoom {

  private final Gson mGson;
  private String TAG = "---------";
  private long tempTime;
  private int  errCount;

  BaseChatRoom() {
    mGson = new Gson();
  }

  private void login(int numUsers) {
    setChanged();
    ObserverModel model = new ObserverModel();
    model.setEventType(IEventType.LOGIN);
    ObserverModel.Login login = new ObserverModel.Login();
    login.setNumUsers(numUsers);
    model.setLogin(login);
    notifyObservers(model);
  }

  private void userJoined(String username, int numUsers) {
    setChanged();
    ObserverModel model = new ObserverModel();
    model.setEventType(IEventType.USER_JOINED);
    ObserverModel.UserJoined userJoined = new ObserverModel.UserJoined();
    userJoined.setUsername(username);
    userJoined.setNumUsers(numUsers);
    model.setUserJoined(userJoined);
    notifyObservers(model);
  }

  private void userLeft(String username, int numUsers) {
    setChanged();
    ObserverModel model = new ObserverModel();
    model.setEventType(IEventType.USER_LEFT);
    ObserverModel.UserLeft userLeft = new ObserverModel.UserLeft();
    userLeft.setUsername(username);
    userLeft.setNumUsers(numUsers);
    model.setUserLeft(userLeft);
    notifyObservers(model);
  }

  private void typing(String username) {
    setChanged();
    ObserverModel model = new ObserverModel();
    model.setEventType(IEventType.TYPING);
    ObserverModel.Typing typing = new ObserverModel.Typing();
    typing.setUsername(username);
    model.setTyping(typing);
    notifyObservers(model);
  }

  private void stopTyping(String username) {
    setChanged();
    ObserverModel model = new ObserverModel();
    model.setEventType(IEventType.STOP_TYPING);
    ObserverModel.StopTyping stopTyping = new ObserverModel.StopTyping();
    stopTyping.setUsername(username);
    model.setStopTyping(stopTyping);
    notifyObservers(model);
  }

  /**
   * 更新普通信息
   */
  private void upData(JSONObject content) {
    setChanged();
    ObserverModel model = new ObserverModel();
    model.setEventType(IEventType.NEW_MESSAGE);
    NewMessage newMessage = mGson.fromJson(content.toString(), NewMessage.class);
    newMessage.setTime(System.currentTimeMillis());
    model.setNewMessage(newMessage);
    notifyObservers(model);
  }

  /**
   * 更新机器人消息
   */
  private void upDataRoot(JSONObject jsonObject) {
    setChanged();
    int type = jsonObject.optInt("type");
    JSONObject rootBean = jsonObject.optJSONObject("rootbean");
    if (rootBean == null) {
      upData(jsonObject);
      return;
    }
    switch (type) {
      case 1:
        parse_type_1(rootBean);
        break;
      case 2:
        parse_type_2(rootBean);
        break;
      case 3://cpu
        parse_type_34(rootBean);
        break;
      case 4:
        parse_type_34(rootBean);
        break;
      case 5:
        parse_type_2(rootBean);
        break;
      case 6://磁盘
        parse_type_6(rootBean);
        break;
      case 7:
        parse_type_2(rootBean);
        break;
      case 8:
        parse_type_8(rootBean);
        break;
    }
  }

  /**
   * 查看网络状况
   */
  private void parse_type_8(JSONObject rootBean) {
    ObserverModel model = new ObserverModel();
    model.setEventType(IEventType.ROOT_MESSAGE_TYPE_8);
    RootMessage8 newMessage = mGson.fromJson(rootBean.toString(), RootMessage8.class);
    newMessage.setTime(System.currentTimeMillis());
    model.setRootMessage8(newMessage);
    notifyObservers(model);
  }

  /**
   * 模拟机器人发送的消息
   */
  public void moni(String json) {
    JSONObject rootData = null;
    try {
      rootData = new JSONObject(json);
      JSONObject jsonObject = rootData.optJSONObject("data");
      upDataRoot(jsonObject);
    } catch (JSONException e) {
      e.printStackTrace();
    }
  }

  /**
   * 解析第二种数据类型
   */
  private void parse_type_2(JSONObject rootBean) {
    ObserverModel model = new ObserverModel();
    model.setEventType(IEventType.ROOT_MESSAGE_TYPE_2);
    RootMessage2 newMessage = mGson.fromJson(rootBean.toString(), RootMessage2.class);
    newMessage.setTime(System.currentTimeMillis());
    model.setRootMessage2(newMessage);
    notifyObservers(model);
  }

  /**
   * cpu  内存
   */
  private void parse_type_34(JSONObject rootBean) {
    ObserverModel model = new ObserverModel();
    model.setEventType(IEventType.ROOT_MESSAGE_TYPE_34);
    RootMessage34 newMessage = mGson.fromJson(rootBean.toString(), RootMessage34.class);
    newMessage.setTime(System.currentTimeMillis());
    model.setRootMessage34(newMessage);
    notifyObservers(model);
  }

  /**
   * 磁盘使用状况
   */
  private void parse_type_6(JSONObject rootBean) {
    ObserverModel model = new ObserverModel();
    model.setEventType(IEventType.ROOT_MESSAGE_TYPE_6);
    RootMessage6 newMessage = mGson.fromJson(rootBean.toString(), RootMessage6.class);
    newMessage.setTime(System.currentTimeMillis());
    model.setRootMessage6(newMessage);
    notifyObservers(model);
  }

  /**
   * 机器人普通消息
   */
  private void parse_type_1(JSONObject rootBean) {
    ObserverModel model = new ObserverModel();
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
        JSONObject jsonObject1 = content.optJSONObject("data");
        upData(jsonObject1);
        break;
      case IConstants.CHAT_BOT_STATUS:
        JSONObject rootData = (JSONObject) args[0];
        Log.e(TAG, "接收到机器人消息: " + rootData.toString());
        JSONObject jsonObject = rootData.optJSONObject("data");
        upDataRoot(jsonObject);
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
        break;
      // Socket断开连接
      case Socket.EVENT_DISCONNECT:
        Log.e(TAG, "EVENT_DISCONNECT");
        break;
      // Socket连接错误
      case Socket.EVENT_ERROR:

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

  @Override
  public void requestSocketResult(String key, Object... args) {

    switch (key) {

    }
  }
}
