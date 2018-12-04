package com.domain.operationrobot.im.bean;

/**
 * Project Name : OperationRobot
 * description:null
 *
 * @author : yinbi.zhang.o
 * Create at : 2018/12/4 22:42
 */
public class UpDataMsg {
  private String action;
  private String msgid;

  public String getMsgid() {
    return msgid == null ? "" : msgid;
  }

  public UpDataMsg(String action, String msgid) {
    this.action = action;
    this.msgid = msgid;
  }

  public void setMsgid(String msgid) {
    this.msgid = msgid;
  }

  public void setAction(String action) {
    this.action = action;
  }

  public String getAction() {
    return action == null ? "" : action;
  }
}
