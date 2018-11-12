package com.domain.operationrobot.im.bean;

import java.util.ArrayList;

/**
 * Project Name : OperationRobot
 * description:null
 *
 * @author : yinbi.zhang.o
 * Create at : 2018/11/11 18:13
 */
public class RootMessage8 {
  private long                           time;
  private String                         msg;
  private ArrayList<RootMessage8.Action> actions;

  public ArrayList<Action> getActions() {
    return actions;
  }

  public void setActions(ArrayList<Action> actions) {
    this.actions = actions;
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

  public class Action {
    private String time;
    private String inNet;
    private String outNet;

    public String getIn() {
      return inNet == null ? "" : inNet;
    }

    public String getOut() {
      return outNet == null ? "" : outNet;
    }

    public String getTime() {
      return time == null ? "" : time;
    }
  }
}
