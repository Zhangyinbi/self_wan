package com.domain.operationrobot.http.data;

import com.domain.library.http.entry.BaseEntry;
import com.domain.operationrobot.http.bean.ApplyInfo;
import com.domain.operationrobot.http.bean.Company;
import com.domain.operationrobot.http.bean.CompanyList;
import com.domain.operationrobot.http.bean.ImageFileBean;
import com.domain.operationrobot.http.bean.OperationList;
import com.domain.operationrobot.http.bean.SideInfo;
import com.domain.operationrobot.http.bean.User;

import java.util.ArrayList;

import io.reactivex.Observable;
import java.util.List;
import java.util.Map;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
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

  /**
   * 验证码
   */
  @Headers({ "Content-Type: application/json;charset=UTF-8" })
  @POST("/api/v1/sms")
  Observable<BaseEntry> sendCode(@Body RequestBody requestBody);

  @Headers({ "Content-Type: application/json;charset=UTF-8" })
  @GET("/api/v1/companys")
  Observable<CompanyList> getCompanyList(@QueryMap Map<String, String> params);

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

  /**
   * 编辑运维用户
   */
  @Headers({ "Content-Type: application/json;charset=UTF-8" })
  @PATCH("/api/v1/opuser")
  Observable<BaseEntry> editOperation(@Body RequestBody requestBody);

  /**
   * 添加运维用户
   */
  @Headers({ "Content-Type: application/json;charset=UTF-8" })
  @POST("/api/v1/opuser")
  Observable<BaseEntry> addOperation(@Body RequestBody requestBody);

  @GET("/api/v1/joininfo")
  Observable<ApplyInfo> getJoinInfo(@Query("token") String token);

  /**
   * 处理用户申请
   */
  @Headers({ "Content-Type: application/json;charset=UTF-8" })
  @POST("/api/v1/joininfo")
  Observable<BaseEntry> disposeJoinInfo(@Body RequestBody requestBody);

  @GET("/api/v1/sidebar")
  Observable<SideInfo> getSide(@Query("token") String token);

  @GET("/api/v1/opusers")
  Observable<OperationList> getOperationInfo(@Query("token") String token, @Query("companyid") String companyid);

  /**
   * 设置管理员
   */
  @Headers({ "Content-Type: application/json;charset=UTF-8" })
  @PATCH("/api/v1/opuser")
  Observable<OperationList> updateStatus(@Body RequestBody requestBody);

  /**
   * 退出公司
   */
  @Headers({ "Content-Type: application/json;charset=UTF-8" })
  @DELETE("/api/v1/member")
  Observable<User> outOfCompany(@Query("token") String token, @Query("companyid") String companyid);

  /**
   * 上传头像
   */
  @Multipart
  @POST("/api/v1/headp")
  Observable<ImageFileBean> upLoadImage(@Part List<MultipartBody.Part> partLis);

  /**
   * 上传聊天图片
   */
  @Multipart
  @POST("/api/v1/uploads")
  Observable<ImageFileBean> upLoadImageMsg(@Part List<MultipartBody.Part> parts);

  /**
   * 设置默认公司
   */
  @Headers({ "Content-Type: application/json;charset=UTF-8" })
  @PATCH("/api/v1/company")
  Observable<User> setDefaultCompany(@Body RequestBody requestBody);


  @Headers({ "Content-Type: application/json;charset=UTF-8" })
  @GET("/api/v1/default")
  Observable<User> checkDefaultCompany(@Query("token")  String token);
}
