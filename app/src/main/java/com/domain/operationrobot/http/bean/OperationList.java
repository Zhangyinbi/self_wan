package com.domain.operationrobot.http.bean;

import com.domain.library.http.entry.BaseEntry;
import java.util.ArrayList;

/**
 * Project Name : OperationRobot
 * description:null
 *
 * @author : yinbi.zhang.o
 * Create at : 2018/11/3 23:49
 */
public class OperationList extends BaseEntry {
  private ArrayList<OperationInfo> info;

  public ArrayList<OperationInfo> getInfo() {
    return info == null ? new ArrayList<>() : info;
  }

  public void setInfo(ArrayList<OperationInfo> info) {
    this.info = info;
  }

  /*{
            "imageUrl": "https://www.baidu.com/img/bd_logo1.png",
            "role": "4",
            "userid": "2019012831121",
            "username": "\u5218\u9614\u6960",
            "userstatus": "register"
          }*/
  public class OperationInfo {
    private String imageUrl;
    private String userid;
    private String username;
    private String userstatus;
    private int    role;
    private String mobile;

    public String getMobile() {
      return mobile == null ? "" : mobile;
    }

    public void setMobile(String mobile) {
      this.mobile = mobile;
    }

    public String getImageUrl() {
      return imageUrl == null ? "" : imageUrl;
    }

    public void setImageUrl(String imageUrl) {
      this.imageUrl = imageUrl;
    }

    public String getUserid() {
      return userid == null ? "" : userid;
    }

    public void setUserid(String userid) {
      this.userid = userid;
    }

    public String getUsername() {
      return username == null ? "" : username;
    }

    public void setUsername(String username) {
      this.username = username;
    }

    public String getUserstatus() {
      return userstatus == null ? "" : userstatus;
    }

    public void setUserstatus(String userstatus) {
      this.userstatus = userstatus;
    }

    public int getRole() {
      return role;
    }

    public void setRole(int role) {
      this.role = role;
    }
  }
}
