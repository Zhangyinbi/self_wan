package com.domain.library.http.entry;

/**
 * Project Name : DO-Feidian-Core-Android
 * <p>{response基类}</p>
 *
 * @author : yinbi.zhang.o
 * Create at : 2018/5/17 11:42
 */

public class BaseEntry<T> {

    /**
     * requestId : null
     * resultCode : 0000
     * resultDesc : success
     * message : null
     * serverTime : 2018-04-18 05:24:35
     * resultData : {"code":"0000","message":null,"data":{"title":"车商城后台",
     * "bannerImg":"http://o9gmvrh89.bkt.clouddn
     * .com/testing/2018/4/17/1d390c39-b561-4eaa-b020-a1ef52c75a64.jpg",
     * "tabs":[{"tabName":"服务包后台","isActive":"1","list":[]},{"tabName":"配件后台","isActive":"1",
     * "list":[]}]}}
     */

    private String requestId;
    private String resultCode;
    private String resultDesc;
    private String message;
    private String serverTime;
    private T resultData;

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultDesc() {
        return resultDesc;
    }

    public void setResultDesc(String resultDesc) {
        this.resultDesc = resultDesc;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getServerTime() {
        return serverTime;
    }

    public void setServerTime(String serverTime) {
        this.serverTime = serverTime;
    }

    public T getResultData() {
        return resultData;
    }

    public void setResultData(T resultData) {
        this.resultData = resultData;
    }

}
