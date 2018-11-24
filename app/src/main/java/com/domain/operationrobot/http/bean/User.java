package com.domain.operationrobot.http.bean;

import android.text.TextUtils;
import com.domain.library.http.entry.BaseEntry;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Project Name : OperationRobot
 * description:null
 *
 * @author : yinbi.zhang.o
 * Create at : 2018/10/13 01:37
 */
public class User extends BaseEntry implements Serializable {
  private String userid;
  private String token;
  private String companyname;
  private String email;

  //（0为正式使用者#1为游客，2为申请待同意用户，）
  private int    role;
  private String username;
  private String imageUrl;
  private String companyid;
  private String mobile;
  private String userstatus;
  //公司角色（1代表试用公司，2代表正式公司）
  private String companyrole;
  //时间戳 毫秒值
  private String companyexpiredate;

  //opmobile:运维用户手机号
  private String opmobile;
  //运维用户角色（2为申请待同意用户，3为普通用户，4为管理员，5为机器人，6为审核员）
  private int    oprole;
  //运维用户姓名
  private String opusername;
  private Choice choice;

  public int getOprole() {
    return  oprole;
  }

  public String getImageUrl() {
    return imageUrl == null ? "" : imageUrl;
  }

  public String getOpmobile() {
    return opmobile == null ? "" : opmobile;
  }

  public String getOpusername() {
    return opusername == null ? "" : opusername;
  }

  public void setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
  }

  public void setOpmobile(String opmobile) {
    this.opmobile = opmobile;
  }

  public void setOprole(int oprole) {
    this.oprole = oprole;
  }

  public void setOpusername(String opusername) {
    this.opusername = opusername;
  }

  public void setUserid(String userid) {
    this.userid = userid;
  }

  @Override
  protected synchronized void setChanged() {
    super.setChanged();
  }

  public String getCompanyname() {
    return companyname == null ? "" : companyname;
  }

  public void setCompanyname(String companyname) {
    this.companyname = companyname;
  }

  public String getCompanyexpiredate() {
    return companyexpiredate == null ? "" : companyexpiredate;
  }

  public void setCompanyexpiredate(String companyexpiredate) {
    this.companyexpiredate = companyexpiredate;
  }

  public String getCompanyrole() {
    return companyrole == null ? "" : companyrole;
  }

  public void setCompanyrole(String companyrole) {
    this.companyrole = companyrole;
  }

  public String getUserstatus() {
    return userstatus == null ? "" : userstatus;
  }

  public void setUserstatus(String userstatus) {
    this.userstatus = userstatus;
  }

  public String getMobile() {
    return mobile == null ? "" : mobile;
  }

  public void setMobile(String mobile) {
    this.mobile = mobile;
  }

  public String getImage() {
    return imageUrl == null ? "" : imageUrl;
  }

  public void setImage(String image) {
    this.imageUrl = image;
  }

  public String getUserId() {
    return userid;
  }

  public String getToken() {
    return token == null ? "" : token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public String getCompany() {
    return companyname == null ? "" : companyname;
  }

  public String getEmail() {
    return email == null ? "" : email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public int getRole() {
    return role;
  }

  public void setRole(int role) {
    this.role = role;
  }

  public String getUsername() {
    return TextUtils.isEmpty(username) ? mobile : username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getCompanyid() {
    return companyid == null ? "" : companyid;
  }

  public void setCompanyid(String companyid) {
    this.companyid = companyid;
  }

  //如果没有默认公司
  /*"choice": {
    "companyinfo": [
      {
        "companyexpiredate": null,
        "companyid": "cO4AeI7NdGiK5CWyYUczakeaQ",
        "companyname": "aijia",
        "companyrole": "1",
        "mobile": "18816954596",
        "userid": "uBgKY7slo7vyQZmxO8gL6S6ne",
        "username": "user1"
      },
      {
        "companyexpiredate": null,
        "companyid": "cERZiTP8ufjfMLVvhtNR9gdKR",
        "companyname": "buaijia",
        "companyrole": "1",
        "mobile": "18816954596",
        "userid": "uBgKY7slo7vyQZmxO8gL6S6ne",
        "username": "user1"
      }
    ],
    "status": "false"
  }*/

  public Choice getChoice() {
    return choice;
  }

  public void setChoice(Choice choice) {
    this.choice = choice;
  }

  public class Choice {
    //有没有默认公司
    private boolean            status;
    private ArrayList<Company> companyinfo;

    public ArrayList<Company> getCompanyinfo() {
      return companyinfo;
    }

    public boolean getStatus() {
      return status;
    }
  }

  //public void setUpUser(User user) {
  //
  //}
}
