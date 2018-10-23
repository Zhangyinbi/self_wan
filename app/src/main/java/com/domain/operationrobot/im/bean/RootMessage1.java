package com.domain.operationrobot.im.bean;

/**
 * Project Name : OperationRobot
 * description:null
 *
 * @author : yinbi.zhang.o
 * Create at : 2018/10/23 22:12
 */
public class RootMessage1 {
  private String msg;
  private long   time;

  public long getTime() {
    return time;
  }

  public void setTime(long time) {
    this.time = time;
  }

  public String getMsg() {
    return msg == null ? "" : msg;
  }

  public void setMsg(String msg) {
    this.msg = msg;
  }

  @Override
  public String toString() {
    return "RootMessage1{" + "msg='" + msg + '\'' + '}';
  }
}
