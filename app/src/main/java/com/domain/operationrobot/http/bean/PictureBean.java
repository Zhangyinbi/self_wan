package com.domain.operationrobot.http.bean;

/**
 * Project Name : OperationRobot
 * description:null
 *
 * @author : yinbi.zhang.o
 * Create at : 2018/11/3 08:10
 */
public class PictureBean extends ChatBean{
  private int    index;
  private String path;

  public PictureBean(int index, String path) {
    this.index = index;
    this.path = path;
  }

  public int getIndex() {
    return index;
  }

  public void setIndex(int index) {
    this.index = index;
  }

  public String getPath() {
    return path == null ? "" : path;
  }

  public void setPath(String path) {
    this.path = path;
  }
}
