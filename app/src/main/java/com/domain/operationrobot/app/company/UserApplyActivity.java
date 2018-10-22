package com.domain.operationrobot.app.company;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.domain.library.base.AbsActivity;
import com.domain.operationrobot.R;

/**
 *
 */
public class UserApplyActivity extends AbsActivity {

    private RecyclerView rlv_recycler;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_user_apply;
    }

    @Override
    protected void newInstancePresenter() {

    }

    @Override
    protected void initView() {
        rlv_recycler = findViewById(R.id.rlv_recycler);
        UserApplyAdapter userApplyAdapter = new UserApplyAdapter(this);
        rlv_recycler.setLayoutManager(new LinearLayoutManager(this));
        rlv_recycler.setAdapter(userApplyAdapter);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void showEmptyView() {

    }
}
