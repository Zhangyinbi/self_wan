package com.domain.operationrobot.app.login;


import com.domain.library.base.BasePresenter;
import com.domain.library.base.BaseView;

/**
 * Project Name : Invoice
 * description:null
 *
 * @author : yinbi.zhang.o
 * Create at : 2018/10/6 13:36
 */
public interface LoginContract {
    interface LoginView<L extends BasePresenter> extends BaseView {
        void loginFail(String msg);

        void LoginSuss();
    }

    public abstract class LoginPresenter<T extends LoginView> extends BasePresenter<T> {

        public abstract void login(String phone, String pwd);
        public abstract void setDefaultCompany(String companyId, String token);
    }
}
