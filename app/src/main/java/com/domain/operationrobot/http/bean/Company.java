package com.domain.operationrobot.http.bean;

import com.domain.library.http.entry.BaseEntry;
import java.security.PublicKey;
import java.util.ArrayList;

/**
 * Project Name : OperationRobot
 * description:null
 *
 * @author : yinbi.zhang.o
 * Create at : 2018/10/13 05:21
 */
public class Company extends BaseEntry {

  private String            companyname;
  private String            companyid;
  private String            companyexpiredate;
  //（1代表试用公司，2代表正式公司）
  private String            companyrole;
  private String            username;
  private String            mobile;
  private String            userid;
  private String            email;
  private int               role;
  private int               oprole;
  private ChatBotInfo       chatBotInfo;
  private String admin;

  public int getRole() {
    return role;
  }

  public String getEmail() {
    return email == null ? "" : email;
  }

  public int getOprole() {
    return oprole;
  }

  public String getMobile() {
    return mobile == null ? "" : mobile;
  }

  public String getUserid() {
    return userid == null ? "" : userid;
  }

  public String getUsername() {
    return username == null ? "" : username;
  }

  public String getCompanyname() {
    return companyname == null ? "" : companyname;
  }

  public void setCompanyname(String companyname) {
    this.companyname = companyname;
  }

  public String getCompanyid() {
    return companyid == null ? "" : companyid;
  }

  public void setCompanyid(String companyid) {
    this.companyid = companyid;
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

  public String getAdmin() {
    return admin == null ? "" : admin;
  }

  public void setAdmin(String admin) {
    this.admin = admin;
  }

  public class ChatBotInfo {
    private String chatbotid;
    private String chatbotpassword;
    private String chatbotusername;
  }
}
