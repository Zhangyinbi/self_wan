package com.domain.operationrobot.http.bean;

/**
 * Project Name : OperationRobot
 * description:null
 *
 * @author : yinbi.zhang.o
 * Create at : 2018/11/28 17:51
 */
public class ServerMachineBean {
  /*Hoststatus:主机状态，in代表已经加入了运维机器人，out代表未加入运维机器人
host：代表zabbix IP
hostid：代表主机id
Name : 代表zabbix主机名称
*/
  private String host;
  private String hostid;
  private String hoststatus;
  private String name;

  public String getName() {
    return name == null ? "" : name;
  }

  public String getHost() {
    return host == null ? "" : host;
  }

  public String getHostid() {
    return hostid == null ? "" : hostid;
  }

  public String getHoststatus() {
    return hoststatus == null ? "" : hoststatus;
  }

  public void setHost(String host) {
    this.host = host;
  }

  public void setHostid(String hostid) {
    this.hostid = hostid;
  }

  public void setHoststatus(String hoststatus) {
    this.hoststatus = hoststatus;
  }

  public void setName(String name) {
    this.name = name;
  }
}

