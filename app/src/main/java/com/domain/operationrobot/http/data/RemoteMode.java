package com.domain.operationrobot.http.data;

import com.domain.library.base.BaseMode;
import com.domain.library.http.RetrofitHelper;
import com.domain.library.http.entry.BaseEntry;
import com.domain.operationrobot.BaseApplication;
import com.domain.operationrobot.http.bean.ApplyInfo;
import com.domain.operationrobot.http.bean.Company;
import com.domain.operationrobot.http.bean.CompanyList;
import com.domain.operationrobot.http.bean.SideInfo;
import com.domain.operationrobot.http.bean.User;

import java.util.ArrayList;

import io.reactivex.Observable;
import java.util.HashMap;
import java.util.Map;
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
  public Observable<BaseEntry> sendCode(String phone) {
    JSONObject root = new JSONObject();
    try {
      root.put("mobile", phone);
    } catch (JSONException e) {
      e.printStackTrace();
    }
    RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), root.toString());

    return RetrofitHelper.getInstance()
                         .create(Api.class)
                         .sendCode(requestBody);
  }

  /**
   * 获取公司列表
   */
  public Observable<CompanyList> getCompanyList(String companyName) {

    //JSONObject root = new JSONObject();
    //try {
    //  root.put("companyname", companyName);
    //  root.put("token", BaseApplication.getInstance()
    //                                   .getUser()
    //                                   .getToken());
    //} catch (JSONException e) {
    //  e.printStackTrace();
    //}
    //RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), root.toString());
    Map<String, String> map = new HashMap<>();
    map.put("companyname", companyName);
    map.put("token", BaseApplication.getInstance()
                                    .getUser()
                                    .getToken());
    return RetrofitHelper.getInstance()
                         .create(Api.class)
                         .getCompanyList(map);
  }

  /**
   * 加入公司
   */
  public Observable<Company> joinCompany(String admin, String companyid) {

    JSONObject root = new JSONObject();
    try {
      root.put("username", admin);
      root.put("companyid", companyid);
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
      root.put("action", "password");
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
  public Observable<BaseEntry> modifyPwd(String old, String newP) {

    JSONObject root = new JSONObject();
    User user = BaseApplication.getInstance()
                               .getUser();
    String token = user.getToken();
    try {
      root.put("newpassword", newP);
      root.put("oldpassword", old);
      root.put("token", token);
    } catch (JSONException e) {
      e.printStackTrace();
    }
    RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), root.toString());

    return RetrofitHelper.getInstance()
                         .create(Api.class)
                         .modifyPwd(requestBody);
  }

  /**
   * 修改手机号码
   */
  public Observable<BaseEntry> modifyPhone(String phone, String code) {
    /*{"action": "mobile", "token":"uQiewOeF8JPPo0AXY6CdfEqGf-11111", "newmobile":"18816954595", "smsvc":"11111"}*/
    User user = BaseApplication.getInstance()
                               .getUser();
    String token = user.getToken();
    JSONObject root = new JSONObject();
    try {
      root.put("newmobile", phone);
      root.put("smsvc", code);
      root.put("token", token);
      root.put("action", "mobile");
    } catch (JSONException e) {
      e.printStackTrace();
    }
    RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), root.toString());
    return RetrofitHelper.getInstance()
                         .create(Api.class)
                         .modifyPhone(requestBody);
  }

  /**
   * 验证密码
   */
  public Observable<BaseEntry> verifyPwd(String pwd) {
    User user = BaseApplication.getInstance()
                               .getUser();
    String token = user.getToken();
    JSONObject root = new JSONObject();
    try {
      root.put("password", pwd);
      root.put("token", token);
    } catch (JSONException e) {
      e.printStackTrace();
    }
    RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), root.toString());
    return RetrofitHelper.getInstance()
                         .create(Api.class)
                         .verifyPwd(requestBody);
  }

  /**
   * 修改用户名称
   */
  public Observable<BaseEntry> modifyUserName(String name) {
    User user = BaseApplication.getInstance()
                               .getUser();
    String token = user.getToken();
    JSONObject root = new JSONObject();
    try {
      root.put("newusername", name);
      root.put("token", token);
      root.put("action", "username");
    } catch (JSONException e) {
      e.printStackTrace();
    }
    RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), root.toString());

    return RetrofitHelper.getInstance()
                         .create(Api.class)
                         .modifyUserName(requestBody);
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

  /**
   * 获取申请用户列表
   */
  public Observable<ApplyInfo> getJoinInfo() {
    String token = BaseApplication.getInstance()
                                  .getUser()
                                  .getToken();
    return RetrofitHelper.getInstance()
                         .create(Api.class)
                         .getJoinInfo(token);
  }

  /**
   * 处理用户请求
   */
  public Observable<BaseEntry> disposeJoinInfo(int action, String request_userid) {
    JSONObject root = new JSONObject();
    try {
      root.put("admin_action", action);
      root.put("request_userid", request_userid);
      root.put("token", BaseApplication.getInstance()
                                       .getUser()
                                       .getToken());
    } catch (JSONException e) {
      e.printStackTrace();
    }
    RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), root.toString());
    return RetrofitHelper.getInstance()
                         .create(Api.class)
                         .disposeJoinInfo(requestBody);
  }

  public Observable<SideInfo> getSide() {
    String token = BaseApplication.getInstance()
                                  .getUser()
                                  .getToken();
    return RetrofitHelper.getInstance()
                         .create(Api.class)
                         .getSide(token);
  }

  private static class SingletonHolder {
    private static final RemoteMode INSTANCE = new RemoteMode();
  }
}
