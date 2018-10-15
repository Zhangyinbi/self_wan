package com.domain.operationrobot.app.setting;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.domain.library.base.AbsActivity;
import com.domain.library.utils.InputUtils;
import com.domain.operationrobot.R;
import com.domain.operationrobot.listener.ThrottleLastClickListener;

public class UserInfoActivity extends AbsActivity {

    ThrottleLastClickListener listener = new ThrottleLastClickListener() {
        @Override
        public void onViewClick(View v) {
            switch (v.getId()) {
                case R.id.iv_back:
                    finish();
                    break;
            }
        }
    };


    @Override
    protected int getLayoutId() {
        return R.layout.activity_user_info;
    }

    @Override
    protected void newInstancePresenter() {

    }

    @Override
    protected void initView() {
        findViewById(R.id.iv_back).setOnClickListener(listener);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void showEmptyView() {

    }
}
