package com.domain.operationrobot.http.bean;

import com.domain.library.http.entry.BaseEntry;

/**
 * Project Name : OperationRobot
 * description:null
 *
 * @author : yinbi.zhang.o
 * Create at : 2018/10/13 05:21
 */
public class Company extends BaseEntry {

  /*{
  "companyid": "cYQ0xRLj35AX4c6GXzVBEFBQz",
  "companyname": "aijia",
  "email": "359594776@qq.com",
  "msg": "Create success",
  "role": 4,
  "status": 0,
  "username": "phil"
}
*/

  private String companyname;
  private String companyid;
  private String username;
  private String email;
  private int    role;

  public String getUsername() {
    return username == null ? "" : username;
  }

  private String admin;
  private String company;

  public Company(String companyName, String adminName) {
    this.companyname = companyName;
    this.username = adminName;
  }

  public String getCompanyid() {
    return companyid == null ? "" : companyid;
  }

  public void setCompanyid(String companyid) {
    this.companyid = companyid;
  }

  public String getEmail() {
    return email == null ? "" : email;
  }

  public int getRole() {
    return role;
  }

  public String getAdmin() {
    return username == null ? admin : username;
  }

  public String getCompany() {
    return companyname == null ? company : companyname;
  }
}
