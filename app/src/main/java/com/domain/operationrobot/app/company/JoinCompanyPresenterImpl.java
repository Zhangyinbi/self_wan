package com.domain.operationrobot.app.company;

import com.domain.library.base.BasePresenter;
import com.domain.library.http.consumer.BaseObserver;
import com.domain.library.http.entry.BaseEntry;
import com.domain.library.http.exception.BaseException;
import com.domain.library.utils.ToastUtils;
import com.domain.operationrobot.http.bean.Company;
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
        mView.setCompanyList(null);
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

    /**
     * 加入公司 当前用户id可以本地获取或者传名字出去
     *
     * @param companyId
     */
    @Override
    public void join(long companyId) {
        showProgress();
        RemoteMode.getInstance().joinCompany(companyId).subscribe(new BaseObserver<String>(getCompositeDisposable()) {
            @Override
            public void onError(BaseException e) {
                if (mView == null) return;
                hideProgress();
                mView.showToast(e.getMsg());

            }

            @Override
            public void onSuss(BaseEntry<String> companyList) {
                if (mView == null) return;
            }
        });
    }
}
