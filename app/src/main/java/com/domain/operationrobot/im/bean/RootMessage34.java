package com.domain.operationrobot.im.bean;

import java.util.ArrayList;

/**
 * Project Name : OperationRobot
 * description:cpu he 内存
 *
 * @author : yinbi.zhang.o
 * Create at : 2018/10/23 22:34
 */
public class RootMessage34 {
  private long              time;
  private String            msg;
  private ArrayList<Action> actions;

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
    return actions == null ? new ArrayList<Action>() : actions;
  }

  public void setActions(ArrayList<Action> actions) {
    this.actions = actions;
  }

  public class Action {
    private String title;
    private float ratio;

    public String getTitle() {
      return title == null ? "" : title;
    }

    public void setTitle(String title) {
      this.title = title;
    }

    public float getRatio() {
      return  ratio;
    }

    public void setRatio(float ratio) {
      this.ratio = ratio;
    }
  }
}
