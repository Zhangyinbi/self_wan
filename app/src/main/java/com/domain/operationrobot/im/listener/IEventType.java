package com.domain.operationrobot.im.listener;

/**
 * @author silencezwm on 2017/8/25 下午12:08
 * @email silencezwm@gmail.com
 * @description
 */
public interface IEventType {
  String NEW_MESSAGE = "newMessage";



  /*** ****************    无效的    ***********************/
  String LOGIN       = "login";
  String ADD_USER    = "addUser";
  String USER_JOINED = "userJoined";
  String USER_LEFT   = "userLeft";
  String TYPING      = "typing";
  String STOP_TYPING = "stopTyping";
}
