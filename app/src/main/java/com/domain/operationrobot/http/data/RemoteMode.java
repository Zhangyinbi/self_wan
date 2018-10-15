package com.domain.operationrobot.http.data;

import com.domain.library.base.BaseMode;
import com.domain.library.http.RetrofitHelper;
import com.domain.library.http.entry.BaseEntry;
import com.domain.operationrobot.BaseApplication;
import com.domain.operationrobot.http.bean.Company;
import com.domain.operationrobot.http.bean.User;

import java.util.ArrayList;

import io.reactivex.Observable;

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
     *
     * @param phone
     * @param pwd
     * @return
     */
    public Observable<BaseEntry<String>> login(String phone, String pwd) {
        return RetrofitHelper.getInstance().create(Api.class).login(phone, pwd);
    }

    /**
     * 创建用户
     *
     * @param phone
     * @param pwd
     * @param code
     * @return
     */
    public Observable<BaseEntry<User>> createAccount(String phone, String pwd, String code) {
        return RetrofitHelper.getInstance().create(Api.class).createAccount(phone, pwd, code);

    }

    /**
     * 发送验证码
     *
     * @param phone
     * @return
     */
    public Observable<BaseEntry<String>> sendCode(String phone) {
        return RetrofitHelper.getInstance().create(Api.class).sendCode(phone);
    }

    /**
     * 获取公司列表
     *
     * @param companyName
     * @return
     */
    public Observable<BaseEntry<ArrayList<Company>>> getCompanyList(String companyName) {
        return RetrofitHelper.getInstance().create(Api.class).getCompanyList(companyName);
    }

    /**
     * 加入公司
     *
     * @param companyId
     * @return
     */
    public Observable<BaseEntry<String>> joinCompany(long companyId) {
        return RetrofitHelper.getInstance().create(Api.class).joinCompany(companyId);
    }

    /**
     * 忘记密码
     *
     * @param phone
     * @param pwd
     * @param code
     * @return
     */
    public Observable<BaseEntry<String>> foegetPwd(String phone, String pwd, String code) {
        return RetrofitHelper.getInstance().create(Api.class).forgetPwd(phone, pwd, code);
    }

    /**
     * 修改密码
     *
     * @param old
     * @param newP
     * @param again
     * @return
     */
    public Observable<BaseEntry<String>> modifyPwd(String old, String newP, String again) {
        return RetrofitHelper.getInstance().create(Api.class).modifyPwd(old, newP, again);
    }

    /**
     * 修改手机号码
     *
     * @param phone
     * @param code
     * @return
     */
    public Observable<BaseEntry<String>> modifyPhone(String phone, String code) {
        return RetrofitHelper.getInstance().create(Api.class).modifyPhone(phone, code);
    }

    /**
     * 验证密码
     *
     * @param pwd
     * @return
     */
    public Observable<BaseEntry<String>> verifyPwd(String pwd) {
        String userId = BaseApplication.getInstance().getUser().getUserId();
        return RetrofitHelper.getInstance().create(Api.class).verifyPwd(pwd, userId);
    }

    /**
     * 修改用户名称
     *
     * @param name
     * @return
     */
    public Observable<BaseEntry<String>> modifyUserName(String name) {
        String userId = BaseApplication.getInstance().getUser().getUserId();
        return RetrofitHelper.getInstance().create(Api.class).modifyUserName(name, userId);
    }

    /**
     * 创建公司
     *
     * @param companyName
     * @param email
     * @param name
     * @return
     */
    public Observable<BaseEntry<String>> createCompany(String companyName, String email, String name) {
        return RetrofitHelper.getInstance().create(Api.class).createCompany(companyName, email, name);
    }

    /**
     * 编辑运维账户
     *
     * @param phone
     * @param name
     * @param id
     * @return
     */
    public Observable<BaseEntry<String>> editOperation(String phone, String name, String id) {
        return RetrofitHelper.getInstance().create(Api.class).editOperation(phone, name, id);
    }

    /**
     * 添加运维账户
     *
     * @param phone
     * @param name
     * @return
     */
    public Observable<BaseEntry<String>> addOperation(String phone, String name) {
        return RetrofitHelper.getInstance().create(Api.class).addOperation(phone, name);
    }


    private static class SingletonHolder {
        private static final RemoteMode INSTANCE = new RemoteMode();
    }
}
