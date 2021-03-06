package com.domain.operationrobot.app.password;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.domain.library.base.AbsActivity;
import com.domain.library.http.consumer.BaseObserver;
import com.domain.library.http.entry.BaseEntry;
import com.domain.library.http.exception.BaseException;
import com.domain.library.utils.SpUtils;
import com.domain.library.widgets.DeleteEdit;
import com.domain.operationrobot.BaseApplication;
import com.domain.operationrobot.R;
import com.domain.operationrobot.http.data.RemoteMode;
import com.domain.operationrobot.listener.ThrottleLastClickListener;
import com.domain.operationrobot.util.ToastU;

import static com.domain.operationrobot.util.Constant.USER_SP_KEY;

public class ModifyUserNameActivity extends AbsActivity {
  private DeleteEdit de_user_name;
  ThrottleLastClickListener listener = new ThrottleLastClickListener() {
    @Override
    public void onViewClick(View v) {
      switch (v.getId()) {
        case R.id.iv_back:
          finish();
          break;
        case R.id.tv_complete:
          String name = de_user_name.getValue();
          if (isEmpty(name)) {
            return;
          }
          if (name.length() > 20) {
            showToast("名称应在20字以内");
            return;
          }
          modifyUserName(name);
          break;
      }
    }
  };

  private void modifyUserName(final String name) {
    showProgress();
    RemoteMode.getInstance()
              .modifyUserName(name)
              .subscribe(new BaseObserver<BaseEntry>(compositeDisposable) {
                @Override
                public void onError(BaseException e) {
                  hideProgress();
                  showToast(e.getMsg());
                }

                @Override
                public void onSuss(BaseEntry userBaseEntry) {
                  hideProgress();
                  ToastU.ToastLoginSussMessage(ModifyUserNameActivity.this, userBaseEntry.msg);
                  BaseApplication.getInstance()
                                 .getUser()
                                 .setUsername(name);
                  SpUtils.setObject(USER_SP_KEY, BaseApplication.getInstance()
                                                                .getUser());
                  finish();
                }

                @Override
                public void onComplete() {
                  super.onComplete();
                  hideProgress();
                }
              });
  }

  @Override
  protected int getLayoutId() {
    return R.layout.activity_modify_user_name;
  }

  @Override
  protected void newInstancePresenter() {

  }

  @Override
  protected void initView() {
    findViewById(R.id.iv_back).setOnClickListener(listener);
    findViewById(R.id.tv_complete).setOnClickListener(listener);
    de_user_name = findViewById(R.id.de_user_name);
    de_user_name.setValue(BaseApplication.getInstance().getUser().getUsername());
  }

  @Override
  protected void initData() {

  }

  @Override
  public void showEmptyView() {

  }
}
