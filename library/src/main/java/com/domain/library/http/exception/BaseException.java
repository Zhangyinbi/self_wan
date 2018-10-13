package com.domain.library.http.exception;

/**
 * 异常基类
 */
public class BaseException extends Exception {
    /**
     * 未知错误.
     */
    public static final int UN_KNOWN_ERROR = 1000;
    /**
     * 解析(服务器)数据错误.
     */
    public static final int ANALYTIC_SERVER_DATA_ERROR = 1001;

    /**
     * 数据格式不对
     */
    public static final int JSON_SYNTAX_DATA_ERROR = 1006;

    /**
     * 包含网络连接错误。网络连接超时。网络错误
     */
    public static final int NETWORK_ERROR = 1007;

    /**
     * 网络链接超时
     */
    public static final int NET_TIME_OUT_ERROR = 1004;

    /**
     * 网路链接失败
     */
    public static final int NET_CONNECT_ERROR = 1003;

    /**
     * host异常
     */
    public static final int NET_HOST_ERROR = 1005;

    private String msg;
    private String code;

    /**
     * @param cause 异常
     * @param code  错误码
     * @param msg   错误信息
     */
    public BaseException(Throwable cause, String code, String msg) {
        super(cause);
        this.code = code;
        this.msg = msg;
    }

    /**
     * @param code 错误码
     * @param msg  错误信息
     */
    public BaseException(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    /**
     * @param cause 异常
     * @param code  错误码
     */
    public BaseException(Throwable cause, String code) {
        super(cause);
        this.code = code;
    }

    /**
     * 获取错误信息
     *
     * @return 错误信息
     */
    public String getMsg() {
        return msg;
    }

    /**
     * 设置错误信息
     *
     * @param msg 错误信息
     */
    public void setMsg(String msg) {
        this.msg = msg;
    }

    /**
     * 返回错误信息
     *
     * @return 错误码
     */
    public String getCode() {
        return code;
    }

    /**
     * 设置错误码
     *
     * @param code 错误码
     */
    public void setCode(String code) {
        this.code = code;
    }
}
