package com.domain.operationrobot.http.data;

import com.domain.library.http.entry.BaseEntry;
import com.domain.operationrobot.http.bean.Company;
import com.domain.operationrobot.http.bean.CompanyList;
import com.domain.operationrobot.http.bean.User;

import java.util.ArrayList;

import io.reactivex.Observable;
import java.util.Map;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

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
  Observable<CompanyList> getCompanyList(@QueryMap Map<String,String> params);

  @Headers({ "Content-Type: application/json;charset=UTF-8" })
  @POST("/api/v1/member")
  Observable<Company> joinCompany(@Body RequestBody requestBody);

  @Headers({ "Content-Type: application/json;charset=UTF-8" })
  @PATCH("/api/v1/user")
  Observable<BaseEntry> forgetPwd(@Body RequestBody requestBody);

  @Headers({ "Content-Type: application/json;charset=UTF-8" })
  @PATCH("/api/v1/password")
  Observable<BaseEntry> modifyPwd(@Body RequestBody requestBody);

  @Headers({ "Content-Type: application/json;charset=UTF-8" })
  @PATCH("/api/v1/user")
  Observable<BaseEntry> modifyPhone(@Body RequestBody requestBody);

  @Headers({ "Content-Type: application/json;charset=UTF-8" })
  @POST("/api/v1/password")
  Observable<BaseEntry> verifyPwd(@Body RequestBody requestBody);

  @Headers({ "Content-Type: application/json;charset=UTF-8" })
  @PATCH("/api/v1/user")
  Observable<BaseEntry> modifyUserName(@Body RequestBody requestBody);

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
