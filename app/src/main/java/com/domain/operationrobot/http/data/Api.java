package com.domain.operationrobot.http.data;

import com.domain.library.http.entry.BaseEntry;
import com.domain.operationrobot.http.bean.Company;
import com.domain.operationrobot.http.bean.User;

import java.util.ArrayList;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Project Name : OperationRobot
 * description:null
 *
 * @author : yinbi.zhang.o
 * Create at : 2018/10/12 23:10
 */
public interface Api {

    @FormUrlEncoded
    @POST("/login")
    Observable<BaseEntry<String>> login(@Field("phone") String phone, @Field("password") String pwd);

    @FormUrlEncoded
    @POST("/createAccount")
    Observable<BaseEntry<User>> createAccount(@Field("phone") String phone, @Field("password") String pwd, @Field("code") String code);

    @FormUrlEncoded
    @POST("/sendCode")
    Observable<BaseEntry<String>> sendCode(@Field("phone") String phone);

    @FormUrlEncoded
    @POST("/getCompanyList")
    Observable<BaseEntry<ArrayList<Company>>> getCompanyList(@Field("companyName") String companyName);

    @FormUrlEncoded
    @POST("/joinCompany")
    Observable<BaseEntry<String>> joinCompany(@Field("companyId") long companyId);
}
