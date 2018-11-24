package com.domain.operationrobot.http.bean;

import com.domain.library.http.entry.BaseEntry;

/**
 * Project Name : OperationRobot
 * description:null
 *
 * @author : yinbi.zhang.o
 * Create at : 2018/10/31 22:37
 */
public class SideInfo extends BaseEntry {
  /*{
  "companyid": "c2YDSc5nIO7u0Bxjy7JHj0VOy",
  "companyname": "aijia",
  "companyrole": "1",
  "joinlist": 0,
  "memberlist": 2,
  "mobile": "18816954595",
  "role": "4",
  "username": "phil"
}
*/
  private String companyid;
  private String companyname;
  private String companyexpiredate;
  private String mobile;
  private String username;
  private String    companyrole;
  private int    memberlist;
  private int    role;
  private int    oprole;
  private int    joinlist;

  public int getOprole() {
    return oprole;
  }

  public String getCompanyexpiredate() {
    return companyexpiredate == null ? "" : companyexpiredate;
  }

  public String getCompanyid() {
    return companyid == null ? "" : companyid;
  }

  public void setCompanyid(String companyid) {
    this.companyid = companyid;
  }

  public String getCompanyname() {
    return companyname == null ? "" : companyname;
  }

  public void setCompanyname(String companyname) {
    this.companyname = companyname;
  }

  public String getMobile() {
    return mobile == null ? "" : mobile;
  }

  public void setMobile(String mobile) {
    this.mobile = mobile;
  }

  public String getUsername() {
    return username == null ? "" : username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getCompanyrole() {
    return companyrole;
  }

  public void setCompanyrole(String companyrole) {
    this.companyrole = companyrole;
  }

  public int getMemberlist() {
    return memberlist;
  }

  public void setMemberlist(int memberlist) {
    this.memberlist = memberlist;
  }

  public int getRole() {
    return role;
  }

  public void setRole(int role) {
    this.role = role;
  }

  public int getJoinlist() {
    return joinlist;
  }

  public void setJoinlist(int joinlist) {
    this.joinlist = joinlist;
  }
}
