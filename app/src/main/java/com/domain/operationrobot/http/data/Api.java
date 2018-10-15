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

    @FormUrlEncoded
    @POST("/forgetPwd")
    Observable<BaseEntry<String>> forgetPwd(@Field("phone") String phone, @Field("password") String pwd, @Field("code") String code);

    @FormUrlEncoded
    @POST("/modifyPwd")
    Observable<BaseEntry<String>> modifyPwd(@Field("oldPwd") String old, @Field("newPwd") String newP, @Field("newPwdAgain") String again);

    @FormUrlEncoded
    @POST("/modifyPhone")
    Observable<BaseEntry<String>> modifyPhone(@Field("phone") String phone, @Field("code") String code);

    @FormUrlEncoded
    @POST("/verifyPwd")
    Observable<BaseEntry<String>> verifyPwd(@Field("pwd") String pwd, @Field("userId") String userId);

    @FormUrlEncoded
    @POST("/modifyUserName")
    Observable<BaseEntry<String>> modifyUserName(@Field("name") String name, @Field("userId") String userId);

    @FormUrlEncoded
    @POST("/createCompany")
    Observable<BaseEntry<String>> createCompany(@Field("companyName") String companyName, @Field("email") String email, @Field("name") String name);

    @FormUrlEncoded
    @POST("/editOperation")
    Observable<BaseEntry<String>> editOperation(@Field("phone") String phone, @Field("name") String name, @Field("id") String id);

    @FormUrlEncoded
    @POST("/addOperation")
    Observable<BaseEntry<String>> addOperation(@Field("phone") String phone, @Field("name") String name);
}
