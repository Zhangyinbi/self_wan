package com.domain.operationrobot.http.bean;

import com.domain.library.http.entry.BaseEntry;

/**
 * Project Name : OperationRobot
 * description:null
 *
 * @author : yinbi.zhang.o
 * Create at : 2018/11/11 12:44
 */
public class ImageFileBean extends BaseEntry {
  private String imageUrl;
  private String uploadUrl;

  public String getImageUrl() {
    return imageUrl == null ? "" : imageUrl;
  }

  public String getUploadUrl() {
    return uploadUrl == null ? "" : uploadUrl;
  }
}
