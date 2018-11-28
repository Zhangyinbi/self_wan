package com.domain.operationrobot.app.home.server;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.domain.library.base.AbsFragment;
import com.domain.operationrobot.R;

/**
 * Project Name : OperationRobot
 * description:null
 *
 * @author : yinbi.zhang.o
 * Create at : 2018/10/18 00:26
 */
public class ServerMonitorFragment extends AbsFragment {

    public static ServerMonitorFragment newInstance() {
        ServerMonitorFragment fragment = new ServerMonitorFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_server_monitor;
    }

    @Override
    protected void newInstancePresenter() {

    }

    @Override
    protected void initView(View view) {
        RecyclerView rlv_recycler = view.findViewById(R.id.rlv_recycler);
        rlv_recycler.setLayoutManager(new GridLayoutManager(getContext(), 2));
        rlv_recycler.setAdapter(new ServerMonitorAdapter(getContext()));
    }

    @Override
    protected void initData() {

    }

    @Override
    public void back() {

    }

    @Override
    public void showEmptyView() {

    }
}
