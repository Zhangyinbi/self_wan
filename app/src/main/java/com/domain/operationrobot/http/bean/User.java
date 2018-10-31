package com.domain.operationrobot.http.bean;

import com.domain.library.http.entry.BaseEntry;
import java.io.Serializable;

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
  private int    role;
  private String username;
  private String imageUrl;
  private String companyid;
  private String mobile;
  private String userstatus;
  private String companyrole;
  private String companyexpiredate;

  public void setCompanyrole(String companyrole) {
    this.companyrole = companyrole;
  }

  public String getCompanyname() {
    return companyname == null ? "" : companyname;
  }

  public void setCompanyexpiredate(String companyexpiredate) {
    this.companyexpiredate = companyexpiredate;
  }

  public String getCompanyexpiredate() {
    return companyexpiredate == null ? "" : companyexpiredate;
  }

  public String getCompanyrole() {
    return companyrole == null ? "" : companyrole;
  }

  public void setCompanyid(String companyid) {
    this.companyid = companyid;
  }

  public void setCompanyname(String companyname) {
    this.companyname = companyname;
  }

  public String getUserstatus() {
    return userstatus == null ? "" : userstatus;
  }

  public void setUserstatus(String userstatus) {
    this.userstatus = userstatus;
  }

  public void setRole(int role) {
    this.role = role;
  }

  public void setMobile(String mobile) {
    this.mobile = mobile;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getMobile() {
    return mobile == null ? "" : mobile;
  }

  public String getImage() {
    return imageUrl == null ? "" : imageUrl;
  }

  public void setImage(String image) {
    this.imageUrl = image;
  }

  public String getUserId() {
    return  userid;
  }

  public String getToken() {
    return token == null ? "" : token;
  }

  public String getCompany() {
    return companyname == null ? "" : companyname;
  }

  public String getEmail() {
    return email == null ? "" : email;
  }

  public int getRole() {
    return role;
  }

  public String getUsername() {
    return username == null ? mobile : username;
  }

  public String getCompanyid() {
    return companyid == null ? "" : companyid;
  }

  //public void setUpUser(User user) {
  //
  //}
}
