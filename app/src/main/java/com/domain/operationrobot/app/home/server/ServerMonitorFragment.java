package com.domain.operationrobot.app.home.server;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;

import com.domain.library.base.AbsActivity;
import com.domain.library.base.AbsFragment;
import com.domain.library.http.consumer.BaseObserver;
import com.domain.library.http.entry.BaseEntry;
import com.domain.library.http.exception.BaseException;
import com.domain.operationrobot.R;
import com.domain.operationrobot.http.bean.ServerBean;
import com.domain.operationrobot.http.data.RemoteMode;
import com.domain.operationrobot.util.ToastU;
import com.google.gson.Gson;
import com.luck.picture.lib.immersive.RomUtils;
import java.util.ArrayList;
import java.util.List;

/**
 * Project Name : OperationRobot
 * description:null
 *
 * @author : yinbi.zhang.o
 * Create at : 2018/10/18 00:26
 */
public class ServerMonitorFragment extends AbsFragment {

  public static final int REFRESH = 1000021;
  private ArrayList<ServerBean.ServerList> mData;
  private ServerAdapter                    mServerAdapter;

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
    StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
    rlv_recycler.setLayoutManager(layoutManager);
    //rlv_recycler.setAdapter(new ServerMonitorAdapter(getContext()));

    mData = new ArrayList<>();
    mServerAdapter = new ServerAdapter(getActivity(), mData);
    rlv_recycler.setAdapter(mServerAdapter);
    ArrayList<ServerBean.ServerList> result = new ArrayList<>();
    result.add(new ServerBean.ServerList());
    mData.addAll(result);
  }

  @Override
  protected void initData() {

  }

  @Override
  public void onResume() {
    super.onResume();
    getDataMonitorInfo();
  }

  private void getDataMonitorInfo() {
    RemoteMode.getInstance()
              .getDataMonitorInfo()
              .subscribe(new BaseObserver<ServerBean>(((AbsActivity) getActivity()).compositeDisposable) {

                @Override
                public void onError(BaseException e) {
                  hideProgress();
                }

                @Override
                public void onSuss(ServerBean serverBean) {
                  mData.clear();
                  ArrayList<ServerBean.ServerList> result = serverBean.getResult();
                  if (result == null) {
                    result = new ArrayList<>();
                  }
                  result.add(new ServerBean.ServerList());
                  mData.addAll(result);
                  mServerAdapter.notifyDataSetChanged();
                }

                @Override
                public void onComplete() {
                  super.onComplete();
                  hideProgress();
                }
              });
  }

  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    Log.e("-----", "onActivityResult: " + requestCode + "---" + resultCode);
  }

  @Override
  public void back() {

  }

  @Override
  public void showEmptyView() {

  }
}
