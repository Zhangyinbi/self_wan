package com.domain.operationrobot.http.api;

public interface IApiProvider {
    String getSchema();

    String getDomain();

    String getVersion();

    String getPort();

    String getApiBase();

    String getAppSecret();
}
