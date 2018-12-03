package com.domain.operationrobot.im.bean;

/**
 * Project Name : OperationRobot
 * description:null
 *
 * @author : yinbi.zhang.o
 * Create at : 2018/12/3 22:20
 */
public class RootMessage11 {

  private long   time;
  private String msg;
  private String hostip;
  private String name;
  private String result;

  public String getName() {
    return name == null ? "" : name;
  }

  public String getResult() {
    return result == null ? "" : result;
  }

  public void setResult(String result) {
    this.result = result;
  }

  public String getMsg() {
    return msg == null ? "" : msg;
  }

  public String getIp() {
    return hostip == null ? "" : hostip;
  }

  public long getTime() {
    return  time;
  }

  public void setTime(long time) {
    this.time = time;
  }
}
