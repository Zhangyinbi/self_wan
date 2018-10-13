package com.domain.operationrobot.app.company;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.domain.library.base.AbsActivity;
import com.domain.operationrobot.R;
import com.domain.operationrobot.listener.ThrottleLastClickListener;

public class RegisterSussActivity extends AbsActivity {

    ThrottleLastClickListener listener = new ThrottleLastClickListener() {
        @Override
        public void onViewClick(View v) {
            switch (v.getId()) {
                case R.id.iv_back:
                    finish();
                    break;
                case R.id.btn_join_company:
                    startJoinCompanyActivity();
                    break;
                case R.id.btn_create_company:
                    break;
                case R.id.btn_look_around:
                    break;
                default:
                    break;
            }
        }
    };

    private void startJoinCompanyActivity() {
        startActivity(new Intent(this, JoinCompanyActivity.class));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_register_suss;
    }

    @Override
    protected void newInstancePresenter() {

    }

    @Override
    protected void initView() {
        findViewById(R.id.iv_back).setOnClickListener(listener);
        findViewById(R.id.btn_join_company).setOnClickListener(listener);
        findViewById(R.id.btn_create_company).setOnClickListener(listener);
        findViewById(R.id.btn_look_around).setOnClickListener(listener);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void showEmptyView() {

    }
}
