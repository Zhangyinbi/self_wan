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
  private String companyname;
  private String username;
  private String email;
  private int    role;

  private String admin;
  private String company;

  public Company(String companyName, String adminName) {
    this.companyname = companyName;
    this.username = adminName;
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
