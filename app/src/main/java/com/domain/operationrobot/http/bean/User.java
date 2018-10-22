package com.domain.operationrobot.http.bean;

import com.domain.library.http.entry.BaseEntry;
import java.io.Serializable;

/**
 * Project Name : OperationRobot
 * description:null
 *
 * @author : yinbi.zhang.o
 * Create at : 2018/10/13 01:37
 */
public class User extends BaseEntry implements Serializable {
  private String userId;
  private String token;
  private String company;
  private String email;
  private String role;
  private String username;
  private String image;

  public String getImage() {
    return image == null ? "https://www.baidu.com/img/bd_logo1.png" : image;
  }

  public void setImage(String image) {
    this.image = image;
  }

  public String getUserId() {
    return userId == null ? "" : userId;
  }

  public String getToken() {
    return token == null ? "" : token;
  }

  public String getCompany() {
    return company == null ? "" : company;
  }

  public String getEmail() {
    return email == null ? "" : email;
  }

  public String getRole() {
    return role == null ? "" : role;
  }

  public String getUsername() {
    return username == null ? "张隐蔽" : username;
  }

  @Override
  public String toString() {
    return "User{" + "userId='" + userId + '\'' + ", token='" + token + '\'' + ", company='" + company + '\'' + ", email='" + email + '\''
      + ", role='" + role + '\'' + ", username='" + username + '\'' + ", msg='" + msg + '\'' + ", status=" + status + '}';
  }
}
