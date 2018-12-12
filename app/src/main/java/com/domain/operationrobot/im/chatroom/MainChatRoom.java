package com.domain.operationrobot.im.chatroom;

import com.domain.operationrobot.http.api.ApiProviderImp;
import com.domain.operationrobot.http.env.EnvManager;
import com.domain.operationrobot.im.listener.IConstants;
import com.domain.operationrobot.im.socket.AppSocket;

/**
 * @author silencezwm on 2017/8/25 上午11:27
 * @email silencezwm@gmail.com
 * @description 主聊天室
 */
public class MainChatRoom extends BaseChatRoom {

  private static volatile MainChatRoom INSTANCE = null;

  //static {
  //  new MainChatRoom();
  //}

  private MainChatRoom() {
    super();
    INSTANCE = this;
    initAppSocket();
  }

  //public static MainChatRoom getInstance() {
  //  if (INSTANCE == null) {
  //    throw new NullPointerException("must first call the init() method");
  //  }
  //  return INSTANCE;
  //}
  public static MainChatRoom getInstance() {
    synchronized (MainChatRoom.class) {
      if (null == INSTANCE) {
        INSTANCE = new MainChatRoom();
      }
    }
    return INSTANCE;
  }

  /**
   * 初始化Socket
   */
  public void initAppSocket() {
    AppSocket.Builder builder = new AppSocket.Builder(new ApiProviderImp(EnvManager.getENV()).getBaseUrl()).setEmitterListener(this);
    AppSocket.init(builder)
             .connect();
  }

  public void outSocket() {
    AppSocket.getInstance().close();
  }
}
