package com.domain.operationrobot.http.bean;

import com.domain.library.http.entry.BaseEntry;

/**
 * Project Name : OperationRobot
 * description:null
 *
 * @author : yinbi.zhang.o
 * Create at : 2018/11/26 21:50
 */
public class ServerMobile extends BaseEntry {
  private String customermobile;

  public String getCustommermobile() {
    return customermobile == null ? "" : customermobile;
  }
}
