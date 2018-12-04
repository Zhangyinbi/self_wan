package com.domain.operationrobot.app.company;


import com.domain.library.base.BasePresenter;
import com.domain.library.base.BaseView;
import com.domain.operationrobot.http.bean.Company;

import java.util.ArrayList;

/**
 * Project Name : Invoice
 * description:null
 *
 * @author : yinbi.zhang.o
 * Create at : 2018/10/6 13:36
 */
public interface JoinCompanyContract {
    interface JoinCompanyView<L extends BasePresenter> extends BaseView {
        void setCompanyList(ArrayList<Company> companyList);

        void joinSuss();
    }

    public abstract class JoinCompanyPresenter<T extends JoinCompanyView> extends BasePresenter<T> {

        public abstract void getCompanyList(String value);
        public abstract void getCompanyTargetList(String value);

        public abstract void join(String admin,String companyName);
    }
}
