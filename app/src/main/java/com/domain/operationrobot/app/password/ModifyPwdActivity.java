package com.domain.operationrobot.app.password;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.domain.library.base.AbsActivity;
import com.domain.library.http.consumer.BaseObserver;
import com.domain.library.http.entry.BaseEntry;
import com.domain.library.http.exception.BaseException;
import com.domain.library.widgets.DeleteEdit;
import com.domain.operationrobot.R;
import com.domain.operationrobot.http.data.RemoteMode;
import com.domain.operationrobot.listener.ThrottleLastClickListener;

public class ModifyPwdActivity extends AbsActivity {

    private DeleteEdit de_old_pwd;
    private DeleteEdit de_new_pwd;
    private DeleteEdit de_new_pwd_again;
    ThrottleLastClickListener listener = new ThrottleLastClickListener() {
        @Override
        public void onViewClick(View v) {
            switch (v.getId()) {
                case R.id.iv_back:
                    finish();
                    break;
                case R.id.tv_complete:
                    String old = de_old_pwd.getValue();
                    String newP = de_new_pwd.getValue();
                    String again = de_new_pwd_again.getValue();

                    if (!(isEmpty(old) || isEmpty(newP) || isEmpty(again))) {
                        if (!newP.equals(again)) {
                            showToast("两次密码输入不一致");
                            return;
                        }
                        complete(old, newP);
                    }
                    break;

            }
        }
    };

    /**
     * 完成修改提交
     *
     * @param old
     * @param newP
     */
    public void complete(String old, String newP) {
        showProgress();
        RemoteMode.getInstance().modifyPwd(old, newP).subscribe(new BaseObserver<BaseEntry>(compositeDisposable) {
            @Override
            public void onError(BaseException e) {
                hideProgress();
                showToast(e.getMsg());
            }

            @Override
            public void onSuss(BaseEntry userBaseEntry) {
                hideProgress();
                showToast("修改密码成功");
                finish();
            } @Override
            public void onComplete() {
                super.onComplete();
                hideProgress();
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_modify_pwd;
    }

    @Override
    protected void newInstancePresenter() {

    }

    @Override
    protected void initView() {
        findViewById(R.id.iv_back).setOnClickListener(listener);
        findViewById(R.id.tv_complete).setOnClickListener(listener);

        de_old_pwd = findViewById(R.id.de_old_pwd);
        de_new_pwd = findViewById(R.id.de_new_pwd);
        de_new_pwd_again = findViewById(R.id.de_new_pwd_again);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void showEmptyView() {

    }
}
