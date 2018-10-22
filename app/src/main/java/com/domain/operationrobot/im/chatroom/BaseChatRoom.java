package com.domain.operationrobot.im.chatroom;

import android.util.Log;
import com.domain.operationrobot.im.bean.ObserverModel;
import com.domain.operationrobot.im.listener.IChatRoom;
import com.domain.operationrobot.im.listener.IConstants;
import com.domain.operationrobot.im.listener.IEventType;
import com.domain.operationrobot.im.socket.AppSocket;
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

  private String TAG = "---------";

  BaseChatRoom() {

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

  private void upData(JSONObject content) {
    setChanged();
    ObserverModel model = new ObserverModel();
    model.setEventType(IEventType.NEW_MESSAGE);
    ObserverModel.NewMessage newMessage = new ObserverModel.NewMessage();
    newMessage.setContent(content);
    newMessage.setTime(System.currentTimeMillis());
    model.setNewMessage(newMessage);
    notifyObservers(model);
  }

  @Override
  public void emitterListenerResut(String key, Object... args) {
    switch (key) {
      case Manager.EVENT_TRANSPORT:
        Log.e(TAG, "EVENT_CONNECT_ERROR");
        break;

      case Socket.EVENT_CONNECT_ERROR:
        Log.e(TAG, "EVENT_CONNECT_ERROR");

        break;

      case Socket.EVENT_CONNECT_TIMEOUT:
        Log.e(TAG, "EVENT_CONNECT_TIMEOUT");
        break;

      case IConstants.TALK_STATUS:
        JSONObject content = (JSONObject) args[0];
        upData(content);
        break;
      case IConstants.CONNECT_STATUS:
        JSONObject data = (JSONObject) args[0];
        try {
          String data1 = data.getString("data");
          Log.e(TAG, "emitterListenerResut: "+data1);
        } catch (JSONException e) {
          e.printStackTrace();
        } break;

      // Socket连接成功
      case Socket.EVENT_CONNECT:
        Log.e(TAG, "链接成功");
        AppSocket.getInstance()
                 .addUser("nihao");
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
