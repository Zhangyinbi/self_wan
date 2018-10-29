package com.domain.library.http.entry;

import java.io.Serializable;
import java.util.Observable;

/**
 * Project Name : DO-Feidian-Core-Android
 * <p>{response基类}</p>
 *
 * @author : yinbi.zhang.o
 * Create at : 2018/5/17 11:42
 */

public class BaseEntry extends Observable{
  public String msg;
  public int status = -1;

  public String getMsg() {
    return msg == null ? "" : msg;
  }

  public int getStatus() {
    return status;
  }
}
