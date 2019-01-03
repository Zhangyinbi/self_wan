package com.domain.operationrobot.http.bean;

import com.domain.library.http.entry.BaseEntry;
import java.util.ArrayList;

/**
 * Project Name : OperationRobot
 * description:null
 *
 * @author : yinbi.zhang.o
 * Create at : 2018/12/15 22:24
 */
public class OrderIdBean extends BaseEntry {
  private ArrayList<OrderIdInfo> result;

  public ArrayList<OrderIdInfo> getResult() {
    return result == null ? new ArrayList<>() : result;
  }

  public class OrderIdInfo {
    private String            groupName;
    private ArrayList<Action> orders;

    public ArrayList<Action> getOrders() {
      return orders == null ? new ArrayList<>() : orders;
    }

    public String getGroupName() {
      return groupName == null ? "" : groupName;
    }

    public class Action {
      private String name;
      private String orderId;

      public String getName() {
        return name == null ? "" : name;
      }

      public String getId() {
        return orderId == null ? "" : orderId;
      }
    }
  }
}
