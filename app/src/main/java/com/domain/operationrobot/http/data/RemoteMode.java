package com.domain.operationrobot.http.data;

import com.domain.library.base.BaseMode;
import com.domain.library.http.RetrofitHelper;
import com.domain.library.http.entry.BaseEntry;
import com.domain.operationrobot.BaseApplication;
import com.domain.operationrobot.http.bean.Company;
import com.domain.operationrobot.http.bean.CompanyList;
import com.domain.operationrobot.http.bean.User;

import java.util.ArrayList;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import org.json.JSONException;
import org.json.JSONObject;
import retrofit2.http.Field;

/**
 * Project Name : OperationRobot
 * description:null
 *
 * @author : yinbi.zhang.o
 * Create at : 2018/10/12 22:41
 */
public class RemoteMode implements BaseMode {
  private RemoteMode() {
  }

  public static RemoteMode getInstance() {
    return SingletonHolder.INSTANCE;
  }

  /**
   * 登陆接口
   */
  public Observable<User> login(String phone, String pwd) {
    JSONObject root = new JSONObject();
    try {
      root.put("password", pwd);
      root.put("mobile", phone);
    } catch (JSONException e) {
      e.printStackTrace();
    }
    RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), root.toString());
    return RetrofitHelper.getInstance()
                         .create(Api.class)
                         .login(requestBody);
  }

  /**
   * 创建用户
   */
  public Observable<User> createAccount(String phone, String pwd, String code) {
    JSONObject root = new JSONObject();
    try {
      root.put("password", pwd);
      root.put("mobile", phone);
      root.put("smsvc", code);
    } catch (JSONException e) {
      e.printStackTrace();
    }
    RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), root.toString());
    return RetrofitHelper.getInstance()
                         .create(Api.class)
                         .createAccount(requestBody);
  }

  /**
   * 发送验证码
   */
  public Observable<String> sendCode(String phone) {
    return RetrofitHelper.getInstance()
                         .create(Api.class)
                         .sendCode(phone);
  }

  /**
   * 获取公司列表
   */
  public Observable<CompanyList> getCompanyList(String companyName) {
    return RetrofitHelper.getInstance()
                         .create(Api.class)
                         .getCompanyList(companyName, BaseApplication.getInstance()
                                                                     .getUser()
                                                                     .getToken());
  }

  /**
   * 加入公司
   */
  public Observable<Company> joinCompany(String admin, String companyName) {

    JSONObject root = new JSONObject();
    try {
      root.put("username", admin);
      root.put("companyname", companyName);
      root.put("token", BaseApplication.getInstance()
                                       .getUser()
                                       .getToken());
    } catch (JSONException e) {
      e.printStackTrace();
    }
    RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), root.toString());

    return RetrofitHelper.getInstance()
                         .create(Api.class)
                         .joinCompany(requestBody);
  }

  /**
   * 忘记密码
   */
  public Observable<BaseEntry> forgetPwd(String phone, String pwd, String code) {

    JSONObject root = new JSONObject();
    try {
      root.put("smsvc", code);
      root.put("newpassword", pwd);
      root.put("mobile", phone);
    } catch (JSONException e) {
      e.printStackTrace();
    }
    RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), root.toString());

    return RetrofitHelper.getInstance()
                         .create(Api.class)
                         .forgetPwd(requestBody);
  }

  /**
   * 修改密码
   */
  public Observable<String> modifyPwd(String old, String newP, String again) {
    return RetrofitHelper.getInstance()
                         .create(Api.class)
                         .modifyPwd(old, newP, again);
  }

  /**
   * 修改手机号码
   */
  public Observable<String> modifyPhone(String phone, String code) {
    return RetrofitHelper.getInstance()
                         .create(Api.class)
                         .modifyPhone(phone, code);
  }

  /**
   * 验证密码
   */
  public Observable<String> verifyPwd(String pwd) {
    String userId = BaseApplication.getInstance()
                                   .getUser()
                                   .getUserId();
    return RetrofitHelper.getInstance()
                         .create(Api.class)
                         .verifyPwd(pwd, userId);
  }

  /**
   * 修改用户名称
   */
  public Observable<String> modifyUserName(String name) {
    String userId = BaseApplication.getInstance()
                                   .getUser()
                                   .getUserId();
    return RetrofitHelper.getInstance()
                         .create(Api.class)
                         .modifyUserName(name, userId);
  }

  /**
   * 创建公司
   */
  public Observable<Company> createCompany(String companyName, String email, String name) {
    JSONObject root = new JSONObject();
    try {
      root.put("email", email);
      root.put("username", name);
      root.put("companyname", companyName);
      root.put("token", BaseApplication.getInstance()
                                       .getUser()
                                       .getToken());
    } catch (JSONException e) {
      e.printStackTrace();
    }
    RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), root.toString());
    return RetrofitHelper.getInstance()
                         .create(Api.class)
                         .createCompany(requestBody);
  }

  /**
   * 编辑运维账户
   */
  public Observable<String> editOperation(String phone, String name, String id) {
    return RetrofitHelper.getInstance()
                         .create(Api.class)
                         .editOperation(phone, name, id);
  }

  /**
   * 添加运维账户
   */
  public Observable<String> addOperation(String phone, String name) {
    return RetrofitHelper.getInstance()
                         .create(Api.class)
                         .addOperation(phone, name);
  }

  private static class SingletonHolder {
    private static final RemoteMode INSTANCE = new RemoteMode();
  }
}
