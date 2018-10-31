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

  private String companyname;
  private String companyid;
  private String companyexpiredate;
  private String companyrole;
  private String username;
  private int role;

  public String getUsername() {
    return username == null ? "" : username;
  }

  public int getRole() {
    return  role;
  }

  private ArrayList<String> admin;

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

  public ArrayList<String> getAdmin() {
    return admin == null ? new ArrayList<String>() : admin;
  }

  public void setAdmin(ArrayList<String> admin) {
    this.admin = admin;
  }
}
