package com.domain.operationrobot.http.bean;

import com.domain.library.http.entry.BaseEntry;
import java.util.ArrayList;

/**
 * Project Name : OperationRobot
 * description:null
 *
 * @author : yinbi.zhang.o
 * Create at : 2018/12/12 19:37
 */
public class CommandBean extends BaseEntry{
  private int                    all_page;
  private ArrayList<CommandInfo> result;

  public ArrayList<CommandInfo> getResult() {
    return result == null ? new ArrayList<>() : result;
  }

  public int getAll_page() {
    return all_page;
  }

  public static class CommandInfo {
    private String exec_time;
    private String msg;
    private String operating_command;
    private String username;
    private String user_image;

    public String getOperating_command() {
      return operating_command == null ? "" : operating_command;
    }

    public String getUser_image() {
      return user_image == null ? "" : user_image;
    }

    public String getUsername() {
      return username == null ? "" : username;
    }

    public String getMsg() {
      return msg == null ? "" : msg;
    }

    public String getExec_time() {
      return exec_time == null ? "" : exec_time;
    }
  }
}
