package com.domain.operationrobot.http.bean;

import com.domain.library.http.entry.BaseEntry;
import java.util.ArrayList;

/**
 * Project Name : OperationRobot
 * description:null
 *
 * @author : yinbi.zhang.o
 * Create at : 2018/10/31 21:41
 */
public class ApplyInfo extends BaseEntry {
  private ArrayList<Info> info;

  public ArrayList<Info> getInfo() {
    return info == null ? new ArrayList<Info>() : info;
  }

  public void setInfo(ArrayList<Info> info) {
    this.info = info;
  }

  public class Info {
    private String    admin_action;
    private String request_mobile;
    private String request_userid;
    private long   request_createtime;
    private String request_username;
    private String request_img;

    public int getAdmin_action() {
      return Integer.parseInt(admin_action);
    }

    public String getRequest_img() {
      return request_img == null ? "" : request_img;
    }

    public void setAdmin_action(int admin_action) {
      this.admin_action = String.valueOf(admin_action);
    }

    public String getRequest_mobile() {
      return request_mobile == null ? "" : request_mobile;
    }

    public void setRequest_mobile(String request_mobile) {
      this.request_mobile = request_mobile;
    }

    public String getRequest_userid() {
      return request_userid == null ? "" : request_userid;
    }

    public void setRequest_userid(String request_userid) {
      this.request_userid = request_userid;
    }

    public long getRequest_createtime() {
      return  request_createtime;
    }

    public void setRequest_createtime(long request_createtime) {
      this.request_createtime = request_createtime;
    }

    public String getRequest_username() {
      return request_username == null ? "" : request_username;
    }

    public void setRequest_username(String request_username) {
      this.request_username = request_username;
    }
  }
}
