package com.domain.operationrobot.im.bean;

import java.util.ArrayList;

/**
 * Project Name : OperationRobot
 * description:磁盘使用情况
 *
 * @author : yinbi.zhang.o
 * Create at : 2018/10/23 22:34
 */
public class RootMessage6 {
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
    private int ratio;
    private String totalSize;
    private String usedSize;

    public String getTitle() {
      return title == null ? "" : title;
    }

    public void setTitle(String title) {
      this.title = title;
    }

    public int getRatio() {
      return  ratio;
    }

    public void setRatio(int ratio) {
      this.ratio = ratio;
    }

    public String getTotalSize() {
      return totalSize == null ? "" : totalSize;
    }

    public void setTotalSize(String totalSize) {
      this.totalSize = totalSize;
    }

    public String getUsedSize() {
      return usedSize == null ? "" : usedSize;
    }

    public void setUsedSize(String usedSize) {
      this.usedSize = usedSize;
    }
  }
}
