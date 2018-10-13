package com.domain.operationrobot.app.company;

import android.os.CountDownTimer;

import com.domain.library.base.BasePresenter;
import com.domain.library.http.consumer.BaseObserver;
import com.domain.library.http.entry.BaseEntry;
import com.domain.library.http.exception.BaseException;
import com.domain.operationrobot.R;
import com.domain.operationrobot.app.password.AccountContract;
import com.domain.operationrobot.http.bean.Company;
import com.domain.operationrobot.http.bean.User;
import com.domain.operationrobot.http.data.RemoteMode;

import java.util.ArrayList;

/**
 * Project Name : OperationRobot
 * description:null
 *
 * @author : yinbi.zhang.o
 * Create at : 2018/10/12 22:38
 */
public class JoinCompanyPresenterImpl extends JoinCompanyContract.JoinCompanyPresenter<JoinCompanyContract.JoinCompanyView> {

    public JoinCompanyPresenterImpl(JoinCompanyContract.JoinCompanyView<BasePresenter> mView) {
        this.mView = mView;
        mView.setPresenter(this);
    }

    @Override
    public void create() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void getCompanyList(String companyName) {
        RemoteMode.getInstance().getCompanyList(companyName).subscribe(new BaseObserver<ArrayList<Company>>(getCompositeDisposable()) {
            @Override
            public void onError(BaseException e) {
                if (mView == null) return;
            }

            @Override
            public void onSuss(BaseEntry<ArrayList<Company>> companyList) {
                if (mView == null) return;
                mView.setCompanyList(companyList.getResultData());
            }
        });
    }
}
