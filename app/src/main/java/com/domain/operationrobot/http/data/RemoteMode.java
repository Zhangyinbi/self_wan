package com.domain.operationrobot.http.data;

import android.text.TextUtils;
import com.domain.library.base.BaseMode;
import com.domain.library.http.RetrofitHelper;
import com.domain.library.http.entry.BaseEntry;
import com.domain.operationrobot.BaseApplication;
import com.domain.operationrobot.http.UpLoadFileHelper;
import com.domain.operationrobot.http.bean.ApplyInfo;
import com.domain.operationrobot.http.bean.CommandBean;
import com.domain.operationrobot.http.bean.Company;
import com.domain.operationrobot.http.bean.CompanyList;
import com.domain.operationrobot.http.bean.ImageFileBean;
import com.domain.operationrobot.http.bean.OperationList;
import com.domain.operationrobot.http.bean.ServerBean;
import com.domain.operationrobot.http.bean.ServerInfo;
import com.domain.operationrobot.http.bean.ServerMachineBean;
import com.domain.operationrobot.http.bean.ServerMobile;
import com.domain.operationrobot.http.bean.SideInfo;
import com.domain.operationrobot.http.bean.User;

import com.google.gson.Gson;
import java.io.File;
import java.util.ArrayList;

import io.reactivex.Observable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import org.json.JSONArray;
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
   * 登陆接口
   */
  public Observable<String> getOffLineMsg(String msgid) {
    User user = BaseApplication.getInstance()
                               .getUser();
    String companyId = "";
    if (!TextUtils.isEmpty(user.getCompanyid())) {
      companyId = user.getCompanyid();
    }
    return RetrofitHelper.getInstance()
                         .create(Api.class)
                         .getOffLineMsg(user.getToken(), companyId, msgid);
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
   * 忘记密码发送验证码
   */
  public Observable<BaseEntry> sendCodeForget(String phone) {
    JSONObject root = new JSONObject();
    try {
      root.put("mobile", phone);
    } catch (JSONException e) {
      e.printStackTrace();
    }
    RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), root.toString());

    return RetrofitHelper.getInstance()
                         .create(Api.class)
                         .sendCodeForget(requestBody);
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

    map.put("token", BaseApplication.getInstance()
                                    .getUser()
                                    .getToken());
    return RetrofitHelper.getInstance()
                         .create(Api.class)
                         .getCompanyList(map);
  }

  /**
   * 获取公司列表
   */
  public Observable<CompanyList> getCompanyTargetList(String companyName) {

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
    return RetrofitHelper.getInstance()
                         .create(Api.class)
                         .getCompanyTargetList(companyName, BaseApplication.getInstance()
                                                                           .getUser()
                                                                           .getToken());
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
  public Observable<BaseEntry> editOperation(String phone, String name, String id, String companyId) {
    JSONObject root = new JSONObject();
    try {
      root.put("opuserid", id);
      root.put("opusername", name);
      root.put("opmobile", phone);
      root.put("opcompanyid", companyId);
      root.put("token", BaseApplication.getInstance()
                                       .getUser()
                                       .getToken());
    } catch (JSONException e) {
      e.printStackTrace();
    }
    RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), root.toString());

    return RetrofitHelper.getInstance()
                         .create(Api.class)
                         .editOperation(requestBody);
  }

  /**
   * 删除运维账户
   */
  public Observable<BaseEntry> delete(String phone, String name, String id, String companyId) {
    Map<String, String> map = new HashMap<>();
    map.put("opmobile", phone);
    map.put("companyid", companyId);
    map.put("token", BaseApplication.getInstance()
                                    .getUser()
                                    .getToken());

    return RetrofitHelper.getInstance()
                         .create(Api.class)
                         .delete(map);
  }

  /**
   * 添加运维账户
   */
  public Observable<BaseEntry> addOperation(String phone, String name) {
    JSONObject root = new JSONObject();
    try {
      root.put("opmobile", phone);
      root.put("opusername", name);
      root.put("companyid", BaseApplication.getInstance()
                                           .getUser()
                                           .getCompanyid());
      root.put("token", BaseApplication.getInstance()
                                       .getUser()
                                       .getToken());
    } catch (JSONException e) {
      e.printStackTrace();
    }
    RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), root.toString());
    return RetrofitHelper.getInstance()
                         .create(Api.class)
                         .addOperation(requestBody);
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
                         .getJoinInfo(token, BaseApplication.getInstance()
                                                            .getUser()
                                                            .getCompanyid());
  }

  /**
   * 处理用户请求
   */
  public Observable<BaseEntry> disposeJoinInfo(int action, String request_userid) {
    JSONObject root = new JSONObject();
    try {
      root.put("admin_action", String.valueOf(action));
      root.put("request_userid", request_userid);
      root.put("request_companyid", BaseApplication.getInstance()
                                                   .getUser()
                                                   .getCompanyid());
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

  public Observable<OperationList> getOperationInfo() {
    User user = BaseApplication.getInstance()
                               .getUser();
    String token = user.getToken();
    String companyid = user.getCompanyid();
    return RetrofitHelper.getInstance()
                         .create(Api.class)
                         .getOperationInfo(token, companyid);
  }

  /**
   * 管理员状态设置
   */
  public Observable<OperationList> updateStatus(int lastRole, String userId, String opcompanyid) {
    JSONObject root = new JSONObject();
    try {
      root.put("opuserid", userId);
      root.put("opcompanyid", opcompanyid);
      root.put("oprole", String.valueOf(lastRole));
      root.put("token", BaseApplication.getInstance()
                                       .getUser()
                                       .getToken());
    } catch (JSONException e) {
      e.printStackTrace();
    }
    RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), root.toString());
    return RetrofitHelper.getInstance()
                         .create(Api.class)
                         .updateStatus(requestBody);
  }

  /**
   * 退出公司
   */
  public Observable<User> outOfCompany() {
    return RetrofitHelper.getInstance()
                         .create(Api.class)
                         .outOfCompany(BaseApplication.getInstance()
                                                      .getUser()
                                                      .getToken(), BaseApplication.getInstance()
                                                                                  .getUser()
                                                                                  .getCompanyid());
  }

  /**
   * 上传头像
   *
   * @param type 0-》上传头像   1-》聊天图片上传
   */
  public Observable<ImageFileBean> upLoadImage(String path, int type) {
    //File file = new File(path);
    //RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);

    MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);//表单类型

//2.获取图片，创建请求体
    File file = new File(path);
    RequestBody body = RequestBody.create(MediaType.parse("multipart/form-data"), file);//表单类型

//3.调用MultipartBody.Builder的addFormDataPart()方法添加表单数据
    builder.addFormDataPart("token", BaseApplication.getInstance()
                                                    .getUser()
                                                    .getToken());//传入服务器需要的key，和相应value值
    builder.addFormDataPart("image", file.getName(), body); //添加图片数据，body创建的请求体

//4.创建List<MultipartBody.Part> 集合，
//  调用MultipartBody.Builder的build()方法会返回一个新创建的MultipartBody
//  再调用MultipartBody的parts()方法返回MultipartBody.Part集合
    List<MultipartBody.Part> parts = builder.build()
                                            .parts();

    if (type == 0) {
      return UpLoadFileHelper.getInstance()
                             .create(Api.class)
                             .upLoadImage(parts);
    }
    return UpLoadFileHelper.getInstance()
                           .create(Api.class)
                           .upLoadImageMsg(parts);
  }

  public Observable<User> setDefaultCompany(String companyId, String token) {
    JSONObject root = new JSONObject();
    try {
      root.put("companyid", companyId);
      root.put("token", token);
    } catch (JSONException e) {
      e.printStackTrace();
    }
    RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), root.toString());
    return RetrofitHelper.getInstance()
                         .create(Api.class)
                         .setDefaultCompany(requestBody);
  }

  /**
   * 检查默认公司
   */
  public Observable<User> checkDefaultCompany() {
    return RetrofitHelper.getInstance()
                         .create(Api.class)
                         .checkDefaultCompany(BaseApplication.getInstance()
                                                             .getUser()
                                                             .getToken());
  }

  /**
   * 检查默认公司
   */
  public Observable<ServerMobile> getServerMobile() {
    return RetrofitHelper.getInstance()
                         .create(Api.class)
                         .getServerMobile(BaseApplication.getInstance()
                                                         .getUser()
                                                         .getToken());
  }

  public Observable<ServerInfo> getServerMachine() {
    return RetrofitHelper.getInstance()
                         .create(Api.class)
                         .getServerMachine(BaseApplication.getInstance()
                                                          .getUser()
                                                          .getToken(), BaseApplication.getInstance()
                                                                                      .getUser()
                                                                                      .getCompanyid());
  }

  public Observable<BaseEntry> save(ArrayList<ServerMachineBean> inhosts) {

    JSONObject root = new JSONObject();
    try {
      root.put("companyid", BaseApplication.getInstance()
                                           .getUser()
                                           .getCompanyid());
      root.put("token", BaseApplication.getInstance()
                                       .getUser()
                                       .getToken());
      JSONArray jsonArray = new JSONArray(new Gson().toJson(inhosts));
      root.put("hostinfo", jsonArray);
    } catch (JSONException e) {
      e.printStackTrace();
    }
    RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), root.toString());
    return RetrofitHelper.getInstance()
                         .create(Api.class)
                         .save(requestBody);
  }

  public Observable<ServerBean> getDataMonitorInfo() {

    JSONObject root = new JSONObject();
    try {
      root.put("companyid", BaseApplication.getInstance()
                                           .getUser()
                                           .getCompanyid());
      root.put("usertoken", BaseApplication.getInstance()
                                           .getUser()
                                           .getToken());
      //root.put("oprole", BaseApplication.getInstance().getUser().getOprole());
      //root.put("companyrole", BaseApplication.getInstance().getUser().getCompanyrole());
    } catch (JSONException e) {
      e.printStackTrace();
    }
    RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), root.toString());
    return RetrofitHelper.getInstance()
                         .create(Api.class)
                         .getDataMonitorInfo(requestBody);
  }

  public Observable<CommandBean> getOrderLog(String id, String startTime, String endTime, String orderId, int page) {
    JSONObject root = new JSONObject();
    User user = BaseApplication.getInstance()
                               .getUser();
    try {
      root.put("companyid", user.getCompanyid());
      root.put("usertoken", user.getToken());
      root.put("role", String.valueOf(user.getRole()));
      root.put("oprole", String.valueOf(user.getOprole()));
      root.put("page_num", String.valueOf(page));
      root.put("operatorid", id);
      root.put("starttime", startTime);
      root.put("endtime", endTime);
      root.put("orderid", orderId);
    } catch (JSONException e) {
      e.printStackTrace();
    }
    RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), root.toString());
    return RetrofitHelper.getInstance()
                         .create(Api.class)
                         .getOrderLog(requestBody);
  }

  private static class SingletonHolder {
    private static final RemoteMode INSTANCE = new RemoteMode();
  }
}
