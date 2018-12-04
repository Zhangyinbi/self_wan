package com.domain.operationrobot.im.bean;

import java.util.ArrayList;

/**
 * Project Name : OperationRobot
 * description:null
 *
 * @author : yinbi.zhang.o
 * Create at : 2018/10/23 22:34
 */
public class RootMessage2 {
  private long              time;
  private String            msg;
  private ArrayList<Action> actions;
  private String username;
  private String imageUrl;
  private String userid;

  public String getUserid() {
    return userid == null ? "" : userid;
  }
  public String getImageUrl() {
    return imageUrl == null ? "" : imageUrl;
  }

  public String getUsername() {
    return username == null ? "" : username;
  }

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

  public ArrayList<Action> getActions() {
    return actions;
  }

  public void setActions(ArrayList<Action> actions) {
    this.actions = actions;
  }

  @Override
  public String toString() {
    return "RootMessage2{" + "time=" + time + ", msg='" + msg + '\'' + ", actions=" + actions + '}';
  }

  public class Action {
    private String name;
    private String type;

    public String getName() {
      return name == null ? "" : name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public String getType() {
      return type == null ? "-1" : type;
    }

    public void setId(String id) {
      this.type = id;
    }

    @Override
    public String toString() {
      return "Action{" + "name='" + name + '\'' + ", id='" + type + '\'' + '}';
    }
  }
}
