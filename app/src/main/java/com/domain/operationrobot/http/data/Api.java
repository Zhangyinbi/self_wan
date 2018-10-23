package com.domain.operationrobot.http.data;

import com.domain.library.http.entry.BaseEntry;
import com.domain.operationrobot.http.bean.Company;
import com.domain.operationrobot.http.bean.CompanyList;
import com.domain.operationrobot.http.bean.User;

import java.util.ArrayList;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Project Name : OperationRobot
 * description:null
 *
 * @author : yinbi.zhang.o
 * Create at : 2018/10/12 23:10
 */
public interface Api {

  /**
   * 用户登陆
   */
  @Headers({ "Content-Type: application/json;charset=UTF-8" })
  @POST("/api/v1/login")
  Observable<User> login(@Body RequestBody requestBody);

  /**
   * 创建用户
   */
  @Headers({ "Content-Type: application/json;charset=UTF-8" })
  @POST("/api/v1/user")
  Observable<User> createAccount(@Body RequestBody requestBody);

  @FormUrlEncoded
  @POST("/sendCode")
  Observable<String> sendCode(@Field("phone") String phone);

  @Headers({ "Content-Type: application/json;charset=UTF-8" })
  @GET("/api/v1/companys")
  Observable<CompanyList> getCompanyList(@Query("companyname") String companyName, @Query("token") String token);

  @Headers({ "Content-Type: application/json;charset=UTF-8" })
  @POST("/api/v1/member")
  Observable<Company> joinCompany(@Body RequestBody requestBody);

  @Headers({ "Content-Type: application/json;charset=UTF-8" })
  @POST("/api/v1/user")
  Observable<BaseEntry> forgetPwd(@Body RequestBody requestBody);

  @FormUrlEncoded
  @POST("/modifyPwd")
  Observable<String> modifyPwd(@Field("oldPwd") String old, @Field("newPwd") String newP, @Field("newPwdAgain") String again);

  @FormUrlEncoded
  @POST("/modifyPhone")
  Observable<String> modifyPhone(@Field("phone") String phone, @Field("code") String code);

  @FormUrlEncoded
  @POST("/verifyPwd")
  Observable<String> verifyPwd(@Field("pwd") String pwd, @Field("userId") String userId);

  @FormUrlEncoded
  @POST("/modifyUserName")
  Observable<String> modifyUserName(@Field("name") String name, @Field("userId") String userId);

  /**
   * 创建公司
   */
  @Headers({ "Content-Type: application/json;charset=UTF-8" })
  @POST("/api/v1/company")
  Observable<Company> createCompany(@Body RequestBody requestBody);

  @FormUrlEncoded
  @POST("/editOperation")
  Observable<String> editOperation(@Field("phone") String phone, @Field("name") String name, @Field("id") String id);

  @FormUrlEncoded
  @POST("/addOperation")
  Observable<String> addOperation(@Field("phone") String phone, @Field("name") String name);
}
