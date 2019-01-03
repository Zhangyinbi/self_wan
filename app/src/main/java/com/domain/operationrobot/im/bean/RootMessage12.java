package com.domain.operationrobot.im.bean;

import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;

/**
 * Project Name : OperationRobot
 * description:null
 *
 * @author : yinbi.zhang.o
 * Create at : 2018/12/3 22:20
 */
public class RootMessage12 {

  private long                           time;
  private String                         msg;
  private ArrayList<RootMessage12.Action> actions;
  private String                         username;
  private String                         imageUrl;
  private String                         userid;

  public String getUserid() {
    return userid == null ? "" : userid;
  }

  public String getImageUrl() {
    return imageUrl == null ? "" : imageUrl;
  }

  public String getUsername() {
    return username == null ? "" : username;
  }

  public ArrayList<RootMessage12.Action> getActions() {
    return actions;
  }

  public void setActions(ArrayList<RootMessage12.Action> actions) {
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
    private String Blk_read;
    private String Blk_wrtn;
    private String Device;

    public String getBlk_read() {
      return Blk_read == null ? "" : Blk_read;
    }

    public String getBlk_wrtn() {
      return Blk_wrtn == null ? "" : Blk_wrtn;
    }

    public String getDevice() {
      return Device == null ? "" : Device;
    }
  }
}