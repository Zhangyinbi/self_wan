package com.domain.operationrobot.im.listener;

/**
 * @author silencezwm on 2017/8/25 下午12:08
 * @email silencezwm@gmail.com
 * @description
 */
public interface IEventType {
  String NEW_MESSAGE         = "newMessage";
  //普通模式
  String ROOT_MESSAGE_TYPE_1 = "root_message_type_1";

  String ROOT_MESSAGE_TYPE_2 = "root_message_type_2";

  //磁盘
  String ROOT_MESSAGE_TYPE_6 = "root_message_type_6";

  //内存  和 cpu
  String ROOT_MESSAGE_TYPE_34 = "root_message_type_34";

  //网络状况
  String ROOT_MESSAGE_TYPE_8 = "root_message_type_8";

  //重启服务器
  String ROOT_MESSAGE_TYPE_11 = "root_message_type_11";

  /*** ****************    无效的    ***********************/
  String LOGIN       = "login";
  String ADD_USER    = "connTest";
  String USER_JOINED = "userJoined";
  String USER_LEFT   = "userLeft";
  String TYPING      = "typing";
  String STOP_TYPING = "stopTyping";
}
