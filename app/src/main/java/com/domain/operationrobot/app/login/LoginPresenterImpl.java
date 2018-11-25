package com.domain.operationrobot.app.login;

import com.domain.library.base.BasePresenter;
import com.domain.library.http.consumer.BaseObserver;
import com.domain.library.http.exception.BaseException;
import com.domain.library.utils.SpUtils;
import com.domain.operationrobot.BaseApplication;
import com.domain.operationrobot.http.bean.Company;
import com.domain.operationrobot.http.bean.User;
import com.domain.operationrobot.http.data.RemoteMode;
import java.util.ArrayList;

import static com.domain.operationrobot.util.Constant.IS_LOGIN;
import static com.domain.operationrobot.util.Constant.USER_SP_KEY;

/**
 * Project Name : OperationRobot
 * description:null
 *
 * @author : yinbi.zhang.o
 * Create at : 2018/10/12 22:38
 */
public class LoginPresenterImpl extends LoginContract.LoginPresenter<LoginContract.LoginView> {

  //    private AccountContract.LoginView<BasePresenter> mView;
  private ChoiceCompanyDialog mChoiceCompanyDialog;

  public LoginPresenterImpl(LoginContract.LoginView<BasePresenter> mView) {
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
  public void login(String phone, String pwd) {
    showProgress();
    RemoteMode.getInstance()
              .login(phone, pwd)
              .subscribe(new BaseObserver<User>(getCompositeDisposable()) {

                @Override
                public void onError(BaseException e) {
                  if (mView == null) {
                    return;
                  }
                  hideProgress();
                  mView.showToast(e.getMsg());
                }

                @Override
                public void onSuss(User user) {
                  if (mView == null) {
                    return;
                  }
                  hideProgress();
                  if (null != user.getChoice() && !user.getChoice()
                                                       .getStatus() && user.getChoice()
                                                                           .getCompanyinfo() != null && user.getChoice()
                                                                                                            .getCompanyinfo()
                                                                                                            .size() > 0) {
                    mChoiceCompanyDialog = new ChoiceCompanyDialog(((LoginActivity) mView), user.getChoice()
                                                                                                                        .getCompanyinfo(),
                      LoginPresenterImpl.this, user.getToken());
                    mChoiceCompanyDialog.show();
                  } else {
                    BaseApplication.getInstance()
                                   .setUser(user);
                    SpUtils.setObject(USER_SP_KEY, user);
                    SpUtils.putBoolean(IS_LOGIN, true);
                    mView.LoginSuss();
                  }
                }

                @Override
                public void onComplete() {
                  hideProgress();
                }
              });
  }

  @Override
  public void setDefaultCompany(String companyId, String token) {
    showProgress();
    mChoiceCompanyDialog.dismiss();
    RemoteMode.getInstance()
              .setDefaultCompany(companyId, token)
              .subscribe(new BaseObserver<User>(getCompositeDisposable()) {

                @Override
                public void onError(BaseException e) {
                  if (mView == null) {
                    return;
                  }
                  hideProgress();
                  mChoiceCompanyDialog.show();
                  mView.showToast(e.getMsg());
                }

                @Override
                public void onSuss(User user) {
                  if (mView == null) {
                    return;
                  }
                  hideProgress();
                  BaseApplication.getInstance()
                                 .setUser(user);
                  SpUtils.setObject(USER_SP_KEY, user);
                  SpUtils.putBoolean(IS_LOGIN, true);
                  mView.LoginSuss();
                }

                @Override
                public void onComplete() {
                  hideProgress();
                }
              });
  }
}