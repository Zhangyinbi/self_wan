package com.domain.operationrobot.http.data;

import com.domain.library.base.BaseMode;
import com.domain.library.http.RetrofitHelper;
import com.domain.library.http.entry.BaseEntry;
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

    private static class SingletonHolder {
        private static final RemoteMode INSTANCE = new RemoteMode();
    }
}
