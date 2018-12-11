package com.domain.operationrobot.im.listener;

/**
 * @author silencezwm on 2017/8/25 上午11:03
 * @email silencezwm@gmail.com  接受消息的 eventId
 * @description 常量
 */
public interface IConstants {


  String CHAT_SERVER_URL = "http://139.196.107.14:5001/";
  //--------------------发送的type----------------
  String CONN            = "conn";
  String TALK            = "talk";
  String CHAT_BOT = "chatbot";

  //--------------------接受的type----------------
  String TALK_STATUS    = "talkstatus";
  String CONNECT_STATUS = "connstatus";
  String CHAT_BOT_STATUS = "chatbotstatus";

  /*** ****************    无效的    ***********************/
  // 登录
  String LOGIN       = "login";
  // 新消息
  String NEW_MESSAGE = "new message";
  // 用户加入
  String USER_JOINED = "user joined";
  // 用户退出
  String USER_LEFT   = "user left";
  // 输入中
  String TYPING      = "typing";
  // 停止输入
  String STOP_TYPING = "stop typing";

  // 增加用户
  String ADD_USER = "conn";

  String USERNAME = "username";
  String NUMUSERS = "numUsers";
}
