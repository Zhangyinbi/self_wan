package com.domain.operationrobot.http.bean;

import com.domain.library.http.entry.BaseEntry;
import java.util.ArrayList;

/**
 * Project Name : OperationRobot
 * description:null
 *
 * @author : yinbi.zhang.o
 * Create at : 2018/11/28 17:54
 */
public class ServerInfo extends BaseEntry {
  private int inamount;
  private int totalamount;

  private ArrayList<ServerMachineBean> inhosts;
  private ArrayList<ServerMachineBean> totalhosts;

  public ArrayList<ServerMachineBean> getInhosts() {
    return inhosts == null ? new ArrayList<>() : inhosts;
  }

  public ArrayList<ServerMachineBean> getTotalhosts() {
    return totalhosts == null ? new ArrayList<>() : totalhosts;
  }

  public int getInamount() {
    return inamount;
  }

  public int getTotalamount() {
    return totalamount;
  }
}
