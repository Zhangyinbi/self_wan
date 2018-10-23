package com.domain.operationrobot.http.bean;

import com.domain.operationrobot.im.bean.RootMessage2;
import java.util.ArrayList;

/**
 * Project Name : OperationRobot
 * description:null
 *
 * @author : yinbi.zhang.o
 * Create at : 2018/10/21 16:40
 */
public class ChatBean {
  public long   time;
  public String userName;
  public String content;
  public String url;
  private int type = -1;//默认正常聊天信息  1-> 机器人消息 2
  private ArrayList<RootMessage2.Action> actions;

  public ArrayList<RootMessage2.Action> getActions() {
    return actions;
  }

  public void setActions(ArrayList<RootMessage2.Action> actions) {
    this.actions = actions;
  }

  public int getType() {
    return type;
  }

  public void setType(int type) {
    this.type = type;
  }

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
