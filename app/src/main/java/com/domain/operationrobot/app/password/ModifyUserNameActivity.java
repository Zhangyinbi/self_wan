package com.domain.operationrobot.app.password;

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
                    if (isEmpty(name)) return;
                    modifyUserName(name);
                    break;

            }
        }
    };

    private void modifyUserName(String name) {
        showProgress();
        //RemoteMode.getInstance().modifyUserName(name).subscribe(new BaseObserver<String>(compositeDisposable) {
        //    @Override
        //    public void onError(BaseException e) {
        //        hideProgress();
        //        showToast(e.getMsg());
        //    }
        //
        //    @Override
        //    public void onSuss(String userBaseEntry) {
        //        hideProgress();
        //        showToast("用户名修改成功");
        //        finish();
        //    } @Override
        //    public void onComplete() {
        //        super.onComplete();
        //        hideProgress();
        //    }
        //});
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
    }

    @Override
    protected void initData() {

    }

    @Override
    public void showEmptyView() {

    }
}
