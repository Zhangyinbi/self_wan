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
  private String msgid;
  private String name;
  private String result;
  private String username;
  private String imageUrl;
  private String userid;

  public String getUserid() {
    return userid == null ? "" : userid;
  }
  public String getMsgid() {
    return msgid == null ? "" : msgid;
  }

  public String getHostip() {
    return hostip == null ? "" : hostip;
  }

  public String getImageUrl() {
    return imageUrl == null ? "" : imageUrl;
  }

  public String getUsername() {
    return username == null ? "" : username;
  }

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
