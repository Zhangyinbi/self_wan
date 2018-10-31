package com.domain.operationrobot.app.login;

import com.domain.library.base.BasePresenter;
import com.domain.library.http.consumer.BaseObserver;
import com.domain.library.http.entry.BaseEntry;
import com.domain.library.http.exception.BaseException;
import com.domain.library.utils.SpUtils;
import com.domain.operationrobot.BaseApplication;
import com.domain.operationrobot.http.bean.User;
import com.domain.operationrobot.http.data.RemoteMode;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

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
                public void onSuss(User tBaseEntry) {
                  if (mView == null) {
                    return;
                  }
                  hideProgress();
                  BaseApplication.getInstance()
                                 .setUser(tBaseEntry);
                  SpUtils.setObject(USER_SP_KEY, tBaseEntry);
                  mView.LoginSuss();
                }

                @Override
                public void onComplete() {
                  hideProgress();
                }
              });
  }
}