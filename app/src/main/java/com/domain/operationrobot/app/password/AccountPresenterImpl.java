package com.domain.operationrobot.app.password;

import android.os.CountDownTimer;

import com.domain.library.base.BasePresenter;
import com.domain.library.http.consumer.BaseObserver;
import com.domain.library.http.entry.BaseEntry;
import com.domain.library.http.exception.BaseException;
import com.domain.operationrobot.R;
import com.domain.operationrobot.http.bean.User;
import com.domain.operationrobot.http.data.RemoteMode;

/**
 * Project Name : OperationRobot
 * description:null
 *
 * @author : yinbi.zhang.o
 * Create at : 2018/10/12 22:38
 */
public class AccountPresenterImpl extends AccountContract.AccountPresenter<AccountContract.AccountView> {

  private CountDownTimer timer = new CountDownTimer(60000, 1000) {

    @Override
    public void onTick(long millisUntilFinished) {
      if (mView == null) {
        timer.cancel();
        timer = null;
        return;
      }
      mView.updateText((millisUntilFinished / 1000) + "S", false, R.color.code_gray);
    }

    @Override
    public void onFinish() {
      if (mView == null) {
        timer.cancel();
        timer = null;
        return;
      }
      mView.updateText("发送验证码", true, R.color.code_blue);
    }
  };

  public AccountPresenterImpl(AccountContract.AccountView<BasePresenter> mView) {
    this.mView = mView;
    mView.setPresenter(this);
  }

  /**
   * 发送验证码 TODO  暂无api
   */
  @Override
  public void sendCode(String accountPhone) {
    showProgress();
    //timer.start();
    //RemoteMode.getInstance()
    //          .sendCode(accountPhone)
    //          .subscribe(new BaseObserver<String>(getCompositeDisposable()) {
    //            @Override
    //            public void onError(BaseException e) {
    //              if (mView == null) {
    //                return;
    //              }
    //              hideProgress();
    //              mView.showToast(e.getMsg());
    //              if (timer != null) {
    //                timer.cancel();
    //              }
    //              mView.updateText("发送验证码", true, R.color.code_blue);
    //            }
    //
    //            @Override
    //            public void onSuss(String userBaseEntry) {
    //              if (mView == null) {
    //                return;
    //              }
    //              hideProgress();
    //              mView.showToast("验证码已发送至您手机，请注意查收");
    //            }
    //
    //            @Override
    //            public void onComplete() {
    //              super.onComplete();
    //              if (mView == null) {
    //                return;
    //              }
    //              hideProgress();
    //            }
    //          });
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

  /**
   * 创建用户
   */
  @Override
  public void commit(String phone, String pwd, String code) {
    showProgress();
    RemoteMode.getInstance()
              .createAccount(phone, pwd, code)
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
                  mView.createSuss(user);
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
  public void destroy() {
    if (timer != null) {
      timer.cancel();
      timer = null;
    }

    super.destroy();
  }
}
