package com.domain.operationrobot.http.api;

import com.domain.operationrobot.http.env.EnvManager;

import static com.domain.operationrobot.http.Constant.BASE_API_QA;

public class ApiProviderImp implements IApiProvider {

    private EnvManager.ENV env;

    public ApiProviderImp(EnvManager.ENV env) {
        this.env = env;
    }

    public String getSchema() {
        switch (this.env) {
            case QA:
                return "https";
            default:
                return "https";
        }
    }

    public String getDomain() {
        switch (this.env) {
            case QA:
                return BASE_API_QA;
            default:
                return BASE_API_QA;
        }
    }

    public String getVersion() {
        return "v1";
    }

    public String getPort() {
        return null;
    }

    public String getApiBase() {
        return "api";
    }

    @Override
    public String getAppSecret() {
        return null;
    }

    public String getBaseUrl() {
        return getSchema() + "://" + getDomain() + "/";
    }

}
