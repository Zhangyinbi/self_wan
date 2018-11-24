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
    private String userstatus;
    private int    role;

    private String opcompanyid;
    private String opusername;
    private String opmobile;
    //（2为申请待同意用户，3为普通用户，4为管理员，5为机器人，6为审核员）
    private int oprole;

    public String getOpusername() {
      return opusername == null ? "" : opusername;
    }

    public String getOpmobile() {
      return opmobile == null ? "" : opmobile;
    }

    public int getOprole() {
      return  oprole;
    }

    public String getOpcompanyid() {
      return opcompanyid == null ? "" : opcompanyid;
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
