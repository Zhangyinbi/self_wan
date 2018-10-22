package com.domain.operationrobot.http.bean;

/**
 * Project Name : OperationRobot
 * description:null
 *
 * @author : yinbi.zhang.o
 * Create at : 2018/10/21 16:40
 */
public class ChatBean {
  public  long   time;
  public String userName;
  public String content;
  public String url;

  public long getTime() {
    return time;
  }

  public void setTime(long time) {
    this.time = time;
  }

  public String getUrl() {
    return url == null ? "" : url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getContent() {
    return content == null ? "" : content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public String getUserName() {
    return userName == null ? "" : userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  @Override
  public String toString() {
    return "ChatBean{" + "userName='" + userName + '\'' + ", content='" + content + '\'' + '}';
  }
}
