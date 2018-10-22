package com.domain.operationrobot.im.chatroom;

import com.domain.operationrobot.im.listener.IConstants;
import com.domain.operationrobot.im.socket.AppSocket;

/**
 * @author silencezwm on 2017/8/25 上午11:27
 * @email silencezwm@gmail.com
 * @description 主聊天室
 */
public class MainChatRoom extends BaseChatRoom {

  private static volatile MainChatRoom INSTANCE = null;

  private MainChatRoom() {
    super();
    INSTANCE = this;
    initAppSocket();
  }

  public static MainChatRoom getInstance() {
    if (INSTANCE == null) {
      throw new NullPointerException("must first call the init() method");
    }
    return INSTANCE;
  }

  public static void init() {
    new MainChatRoom();
  }

  /**
   * 初始化Socket
   */
  public void initAppSocket() {
    AppSocket.Builder builder = new AppSocket.Builder(IConstants.CHAT_SERVER_URL).setEmitterListener(this);
    AppSocket.init(builder)
             .connect();
  }
}
