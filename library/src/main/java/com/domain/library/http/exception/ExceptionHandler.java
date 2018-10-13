package com.domain.library.http.exception;

import android.accounts.NetworkErrorException;
import android.net.ParseException;

import com.domain.library.BaseApplicationController;
import com.domain.library.R;
import com.google.gson.JsonSyntaxException;

import org.json.JSONException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import retrofit2.HttpException;

/**
 * 异常处理封装
 *
 * @author mars.yu
 * @date 2017/11/13.
 */
public class ExceptionHandler {

    /**
     * 处理异常.
     *
     * @param e the e
     * @return 异常基类
     */
    public static BaseException handleException(Throwable e) {
        BaseException ex;
        if (e instanceof HttpException) {
            //HTTP错误
            HttpException httpExc = (HttpException) e;
            ex = new BaseException(httpExc, String.valueOf(httpExc.code()), BaseApplicationController.getContext().getString(R.string.http_exception));
            return ex;
        } else if (e instanceof JsonSyntaxException) {
            //数据格式错误
            ex = new BaseException(e, String.valueOf(BaseException.JSON_SYNTAX_DATA_ERROR), BaseApplicationController.getContext().getString(R.string.code_1006_json_syntax));
            return ex;
        } else if (e instanceof JSONException || e instanceof ParseException) {
            //解析数据错误
            ex = new BaseException(e, String.valueOf(BaseException.ANALYTIC_SERVER_DATA_ERROR), BaseApplicationController.getContext().getString(R.string.code_1001_json_parse));
            return ex;
        } else if (e instanceof ConnectException) {
            //连接网络错误
            ex = new BaseException(e, String.valueOf(BaseException.NET_CONNECT_ERROR), BaseApplicationController.getContext().getString(R.string.code_1003_connect_fail));
            return ex;
        } else if (e instanceof SocketTimeoutException) {
            //网络超时
            ex = new BaseException(e, String.valueOf(BaseException.NET_TIME_OUT_ERROR), BaseApplicationController.getContext().getString(R.string.code_1004_socket_time_out));
            return ex;
        } else if (e instanceof NetworkErrorException) {
            // 网络错误
            ex = new BaseException(e, String.valueOf(BaseException.NETWORK_ERROR), BaseApplicationController.getContext().getString(R.string.code_1007_error_exception));
            return ex;
        } else if (e instanceof UnknownHostException) {
            // 网络错误
            ex = new BaseException(e, String.valueOf(BaseException.NET_HOST_ERROR), BaseApplicationController.getContext().getString(R.string.code_1005_host_exception));
            return ex;
        } else {
            //未知错误
            ex = new BaseException(e, String.valueOf(BaseException.UN_KNOWN_ERROR), BaseApplicationController.getContext().getString(R.string.unKnow_error));
            return ex;
        }
    }
}
