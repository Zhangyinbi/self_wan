package com.domain.operationrobot.http.bean;

/**
 * Project Name : OperationRobot
 * description:null
 *
 * @author : yinbi.zhang.o
 * Create at : 2018/10/13 05:21
 */
public class Company {
    private String company;
    private String admin;
    private long companyId = -1;

    public Company(String companyName, String adminName, long companyId) {
        this.company = companyName;
        this.admin = adminName;
        this.companyId = companyId;
    }

    public long getCompanyId() {
        return companyId;
    }

    public String getAdmin() {
        return admin == null ? "" : admin;
    }

    public String getCompany() {
        return company == null ? "" : company;
    }
}
