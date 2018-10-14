package com.domain.operationrobot.app.password;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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

public class VerifyPwdActivity extends AbsActivity {

    public static final int FROM_USER_NAME_MODIFY = 0x111;
    public static final int FROM_USER_PHONE_MODIFY = FROM_USER_NAME_MODIFY + 1;
    private static final String SOURCE = "source";
    private int source;
    private DeleteEdit de_pwd;
    ThrottleLastClickListener listener = new ThrottleLastClickListener() {
        @Override
        public void onViewClick(View v) {
            switch (v.getId()) {
                case R.id.iv_back:
                    finish();
                    break;
                case R.id.btn_next:
                    String pwd = de_pwd.getValue();
                    if (isEmpty(pwd)) return;
                    verifyPwd(pwd);
                    break;
            }
        }
    };

    public static void start(Activity activity, int source) {
        Intent intent = new Intent(activity, VerifyPwdActivity.class);
        intent.putExtra(SOURCE, source);
        activity.startActivity(intent);
    }

    private void verifyPwd(String pwd) {
        showProgress();
        RemoteMode.getInstance().verifyPwd(pwd).subscribe(new BaseObserver<String>(compositeDisposable) {
            @Override
            public void onError(BaseException e) {
                hideProgress();
                showToast(e.getMsg());
            }

            @Override
            public void onSuss(BaseEntry<String> userBaseEntry) {
                hideProgress();
                next();
            }
        });
    }

    private void next() {
        if (source == FROM_USER_NAME_MODIFY) {
            startActivity(new Intent(this, ModifyUserNameActivity.class));
        } else if (source == FROM_USER_PHONE_MODIFY) {
            startActivity(new Intent(this, ModifyPhoneActivity.class));
        }
        finish();
    }

//    public static void start(Activity activity, String name) {
//        Intent intent = new Intent(activity, VerifyPwdActivity.class);
//        intent.putExtra("user_name", name);
//        activity.startActivity(intent);
//    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_verify_pwd;
    }

    @Override
    protected void newInstancePresenter() {
        Intent intent = getIntent();
        int intExtra = intent.getIntExtra(SOURCE, -1);
        if (intExtra == -1) {
            finish();
            return;
        }
        source = intExtra;
    }

    @Override
    protected void initView() {
        findViewById(R.id.iv_back).setOnClickListener(listener);
        findViewById(R.id.btn_next).setOnClickListener(listener);
        de_pwd = findViewById(R.id.de_pwd);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void showEmptyView() {

    }
}
