package com.domain.operationrobot.im.bean;

public class NewMessage {
  /*{
    "data": {
      "token": "17621791680-11111",
      "msg": "图像",
      "imageUrl": "https:\/\/www.baidu.com\/img\/bd_logo1.png",
      "mobile": "17621791680",
      "username": null
    }
  }*/
  private long   time;
  private String token;
  private String msg;
  private String imageUrl;
  private String mobile;
  private String username;
  private String    userid;

  public String getTargetId() {
    return  userid;
  }

  @Override
  public String toString() {
    return "NewMessage{" + "time=" + time + ", token='" + token + '\'' + ", msg='" + msg + '\'' + ", imageUrl='" + imageUrl + '\'' + ", mobile='"
      + mobile + '\'' + ", username='" + username + '\'' + '}';
  }

  public long getTime() {
    return time;
  }

  public void setTime(long time) {
    this.time = time;
  }

  public String getToken() {
    return token == null ? "" : token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public String getMsg() {
    return msg == null ? "" : msg;
  }

  public void setMsg(String msg) {
    this.msg = msg;
  }

  public String getImageUrl() {
    return imageUrl == null ? "" : imageUrl;
  }

  public void setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
  }

  public String getMobile() {
    return mobile == null ? "" : mobile;
  }

  public void setMobile(String mobile) {
    this.mobile = mobile;
  }

  public String getUsername() {
    return username == null ? "" : username;
  }

  public void setUsername(String username) {
    this.username = username;
  }
}