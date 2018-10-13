package com.domain.operationrobot.app.password;


import com.domain.library.base.BasePresenter;
import com.domain.library.base.BaseView;
import com.domain.operationrobot.http.bean.User;

/**
 * Project Name : Invoice
 * description:null
 *
 * @author : yinbi.zhang.o
 * Create at : 2018/10/6 13:36
 */
public interface AccountContract {
    interface AccountView<L extends BasePresenter> extends BaseView {
        void createSuss(User user);

        void updateText(String s, boolean b, int color);
    }

    public abstract class AccountPresenter<T extends AccountView> extends BasePresenter<T> {

        public abstract void commit(String phone, String code, String pwd);

        public abstract void sendCode(String accountPhone);
    }
}
