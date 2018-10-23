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
  private long time;
  private String msg;
  private ArrayList<Action> actions;

  public long getTime() {
    return  time;
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
    return  actions;
  }

  public void setActions(ArrayList<Action> actions) {
    this.actions = actions;
  }

  @Override
  public String toString() {
    return "RootMessage2{" + "time=" + time + ", msg='" + msg + '\'' + ", actions=" + actions + '}';
  }

  public class Action{
    private String name;
    private String id;

    public String getName() {
      return name == null ? "" : name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public String getId() {
      return id == null ? "" : id;
    }

    public void setId(String id) {
      this.id = id;
    }

    @Override
    public String toString() {
      return "Action{" + "name='" + name + '\'' + ", id='" + id + '\'' + '}';
    }
  }
}
