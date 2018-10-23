package com.domain.operationrobot.http.bean;

import com.domain.library.http.entry.BaseEntry;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Project Name : OperationRobot
 * description:null
 *
 * @author : yinbi.zhang.o
 * Create at : 2018/10/21 00:54
 */
public class CompanyList extends BaseEntry {
  private ArrayList<Company> companys;

  public ArrayList<Company> getCompanys() {
    return companys == null ? new ArrayList<Company>() : companys;
  }

  @Override
  public String toString() {
    return "CompanyList{" + "companys=" + companys + '}';
  }
}
