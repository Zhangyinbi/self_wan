package com.domain.operationrobot.http.bean;

import com.domain.library.http.entry.BaseEntry;
import java.util.ArrayList;

/**
 * Project Name : OperationRobot
 * description:null
 *
 * @author : yinbi.zhang.o
 * Create at : 2018/12/15 22:04
 */
public class OrderOperationBean extends BaseEntry {
  private ArrayList<Info> result;

  public ArrayList<Info> getResult() {
    return result == null ? new ArrayList<>() : result;
  }

  public class Info {
    private String operationId;
    private String operationImage;
    private String operationName;

    public String getOperationId() {
      return operationId == null ? "" : operationId;
    }

    public String getOperationImage() {
      return operationImage == null ? "" : operationImage;
    }

    public String getOperationName() {
      return operationName == null ? "" : operationName;
    }
  }
}
