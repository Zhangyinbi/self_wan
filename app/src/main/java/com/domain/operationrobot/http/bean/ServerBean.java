package com.domain.operationrobot.http.bean;

import com.domain.library.http.entry.BaseEntry;
import java.util.ArrayList;

/**
 * Project Name : OperationRobot
 * description:null
 *
 * @author : yinbi.zhang.o
 * Create at : 2018/12/3 23:45
 */
public class ServerBean extends BaseEntry{
  private ArrayList<ServerList> result;

  public ArrayList<ServerList> getResult() {
    return result;
  }

  public void setResult(ArrayList<ServerList> result) {
    this.result = result;
  }

  public static class ServerList {
    private String                 host;
    private ArrayList<MonitorInfo> item;

    public String getHost() {
      return host == null ? "" : host;
    }

    public void setHost(String host) {
      this.host = host;
    }

    public ArrayList<MonitorInfo> getItem() {
      return item;
    }

    public void setItem(ArrayList<MonitorInfo> item) {
      this.item = item;
    }

    public class MonitorInfo {
      private String key;
      private float  available;
      private float  total;

      public String getKey() {
        return key == null ? "" : key;
      }

      public void setKey(String key) {
        this.key = key;
      }

      public float getAvailable() {
        return  available;
      }

      public void setAvailable(float available) {
        this.available = available;
      }

      public float getTotal() {
        return  total;
      }

      public void setTotal(float total) {
        this.total = total;
      }
    }
  }
}
