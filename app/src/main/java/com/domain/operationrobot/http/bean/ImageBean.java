package com.domain.operationrobot.http.bean;

/**
 * Project Name : OperationRobot
 * description:null
 *
 * @author : yinbi.zhang.o
 * Create at : 2018/11/5 19:35
 */
public class ImageBean {
  private String url;
  private String width;
  private String height;

  public String getUrl() {
    return url == null ? "" : url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getWidth() {
    return width == null ? "" : width;
  }

  public void setWidth(String width) {
    this.width = width;
  }

  public String getHeight() {
    return height == null ? "" : height;
  }

  public void setHeight(String height) {
    this.height = height;
  }
}
