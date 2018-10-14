package com.domain.operationrobot.http.bean;

/**
 * Project Name : OperationRobot
 * description:null
 *
 * @author : yinbi.zhang.o
 * Create at : 2018/10/13 01:37
 */
public class User {
    private String name;
    private String userId;

    public String getUserId() {
        return userId == null ? "" : userId;
    }

    public String getName() {
        return name == null ? "" : name;
    }

}
