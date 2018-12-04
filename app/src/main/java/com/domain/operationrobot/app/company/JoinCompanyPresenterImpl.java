package com.domain.operationrobot.app.company;

import android.app.Activity;
import com.domain.library.base.BasePresenter;
import com.domain.library.http.consumer.BaseObserver;
import com.domain.library.http.entry.BaseEntry;
import com.domain.library.http.exception.BaseException;
import com.domain.library.utils.SpUtils;
import com.domain.library.utils.ToastUtils;
import com.domain.operationrobot.BaseApplication;
import com.domain.operationrobot.http.bean.Company;
import com.domain.operationrobot.http.bean.CompanyList;
import com.domain.operationrobot.http.bean.User;
import com.domain.operationrobot.http.data.RemoteMode;

import com.domain.operationrobot.util.ToastU;
import java.util.ArrayList;

import static com.domain.operationrobot.util.Constant.USER_SP_KEY;

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
    RemoteMode.getInstance()
              .getCompanyList(companyName)
              .subscribe(new BaseObserver<CompanyList>(getCompositeDisposable()) {
                @Override
                public void onError(BaseException e) {
                  if (mView == null) {
                    return;
                  }
                }

                @Override
                public void onSuss(CompanyList companyList) {
                  if (mView == null) {
                    return;
                  }
                  mView.setCompanyList(companyList.getCompanys());
                }

                @Override
                public void onComplete() {
                  super.onComplete();
                  if (mView == null) {
                    return;
                  }
                  hideProgress();
                }
              });
  }

  @Override
  public void getCompanyTargetList(String companyName) {
    mView.setCompanyList(null);
    RemoteMode.getInstance()
              .getCompanyTargetList(companyName)
              .subscribe(new BaseObserver<CompanyList>(getCompositeDisposable()) {
                @Override
                public void onError(BaseException e) {
                  if (mView == null) {
                    return;
                  }
                }

                @Override
                public void onSuss(CompanyList companyList) {
                  if (mView == null) {
                    return;
                  }
                  mView.setCompanyList(companyList.getCompanys());
                }

                @Override
                public void onComplete() {
                  super.onComplete();
                  if (mView == null) {
                    return;
                  }
                  hideProgress();
                }
              });
  }

  /**
   * 加入公司 当前用户id可以本地获取或者传名字出去
   */
  @Override
  public void join(String admin, String companyid) {
    showProgress();
    RemoteMode.getInstance()
              .joinCompany(admin, companyid)
              .subscribe(new BaseObserver<Company>(getCompositeDisposable()) {
                @Override
                public void onError(BaseException e) {
                  if (mView == null) {
                    return;
                  }
                  hideProgress();
                  mView.showToast("服务器繁忙，请重试");
                }

                @Override
                public void onSuss(Company company) {
                  if (mView == null) {
                    return;
                  }
                  mView.joinSuss();
                  ToastU.ToastLoginSussMessage((Activity) mView,"申请成功,请耐心等待");
                  User user = BaseApplication.getInstance()
                                             .getUser();
                  user.setRole(company.getRole());
                  user.setOprole(company.getOprole());
                  BaseApplication.getInstance()
                                 .setUser(user);
                  SpUtils.setObject(USER_SP_KEY, BaseApplication.getInstance()
                                                                .getUser());
                }

                @Override
                public void onComplete() {
                  super.onComplete();
                  if (mView == null) {
                    return;
                  }
                  hideProgress();
                }
              });
  }
}
