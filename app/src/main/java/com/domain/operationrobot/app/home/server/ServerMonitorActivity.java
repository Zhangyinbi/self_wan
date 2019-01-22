package com.domain.operationrobot.app.home.server;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.domain.library.base.AbsActivity;
import com.domain.library.http.consumer.BaseObserver;
import com.domain.library.http.entry.BaseEntry;
import com.domain.library.http.exception.BaseException;
import com.domain.library.utils.DisplaysUtil;
import com.domain.library.utils.SoftInputUtil;
import com.domain.operationrobot.R;
import com.domain.operationrobot.http.bean.ServerInfo;
import com.domain.operationrobot.http.bean.ServerMachineBean;
import com.domain.operationrobot.http.data.RemoteMode;
import com.domain.operationrobot.listener.ThrottleLastClickListener;
import com.domain.operationrobot.util.ToastU;
import java.util.ArrayList;

public class ServerMonitorActivity extends AbsActivity {

  private RecyclerView                 mRlv_in;
  private RecyclerView                 mRlv_all;
  private ServerAllAdapter             mAllAdapter;
  private ServerInAdapter              mInAdapter;
  private TextView                     mTv_title;
  private TextView                     tv_no_data;
  private View                         view;
  private FrameLayout                  mFl_view;
  private ArrayList<ServerMachineBean> mInhosts;
  ThrottleLastClickListener listener = new ThrottleLastClickListener() {
    @Override
    public void onViewClick(View v) {
      switch (v.getId()) {
        case R.id.iv_back:
          finish();
          break;
        case R.id.tv_save:
          save();
          break;
      }
    }
  };
  private ArrayList<ServerMachineBean> mAllHosts;
  private ArrayList<ServerMachineBean> mTempAllHosts;
  private EditText                     mEt_host;
  private View                         tv_save;

  private void save() {
    showProgress();
    RemoteMode.getInstance()
              .save(mInhosts)
              .subscribe(new BaseObserver<BaseEntry>(compositeDisposable) {

                @Override
                public void onError(BaseException e) {
                  hideProgress();
                }

                @Override
                public void onSuss(BaseEntry baseEntry) {
                  ToastU.ToastLoginSussMessage(ServerMonitorActivity.this, "保存成功");
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
    return R.layout.activity_server_monitor;
  }

  @Override
  protected void newInstancePresenter() {

  }

  @Override
  protected void initView() {
    findViewById(R.id.iv_back).setOnClickListener(listener);
    mRlv_in = findViewById(R.id.rlv_in);
    mTempAllHosts = new ArrayList<>();
    mRlv_all = findViewById(R.id.rlv_all);
    mTv_title = findViewById(R.id.tv_title);
    tv_save = findViewById(R.id.tv_save);
    tv_save.setOnClickListener(listener);
    tv_no_data = findViewById(R.id.tv_no_data);
    mEt_host = findViewById(R.id.et_host);
    mRlv_in.setLayoutManager(new GridLayoutManager(this, 2));
    mRlv_all.setLayoutManager(new LinearLayoutManager(this));
    mAllAdapter = new ServerAllAdapter(this);
    mRlv_all.setAdapter(mAllAdapter);
    mInAdapter = new ServerInAdapter(this);
    mRlv_in.setAdapter(mInAdapter);
    view = LayoutInflater.from(this)
                         .inflate(R.layout.no_data, null);
    mFl_view = findViewById(R.id.fl_view);
    mFl_view.addView(view);
    mAllAdapter.setItemClickListener(new OnItemClick() {
      @Override
      public void onItemClick(ServerMachineBean serverMachineBean) {
        for (ServerMachineBean bean : mInhosts) {
          if (bean.getHostid()
                  .equals(serverMachineBean.getHostid())) {
            if ("out".equals(serverMachineBean.getHoststatus())) {
              mInhosts.remove(bean);
              upDataIn(mInhosts);
              return;
            }
          }
        }
        if ("in".equals(serverMachineBean.getHoststatus())) {
          mInhosts.add(serverMachineBean);
          upDataIn(mInhosts);
        }
      }
    });
    mInAdapter.setItemClickListener(new OnItemClick() {
      @Override
      public void onItemClick(ServerMachineBean serverMachineBean) {
        for (ServerMachineBean bean : mAllHosts) {
          if (bean.getHostid()
                  .equals(serverMachineBean.getHostid())) {
            bean.setHoststatus("out");
            upDataAll(mAllHosts);
            mEt_host.setText("");
            SoftInputUtil.hideSoftInput(mFl_view);
            for (ServerMachineBean inbean : mInhosts) {
              if (inbean.getHostid()
                        .equals(serverMachineBean.getHostid())) {
                mInhosts.remove(inbean);
                upDataIn(mInhosts);
                return;
              }
            }
          }
        }
      }
    });

    mEt_host.addTextChangedListener(new TextWatcher() {
      @Override
      public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

      }

      @Override
      public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

      }

      @Override
      public void afterTextChanged(Editable editable) {
        String host = editable.toString()
                              .trim();
        mTempAllHosts.clear();
        for (ServerMachineBean bean : mAllHosts) {
          if (bean.getHost()
                  .contains(host) || bean.getName()
                                         .contains(host)) {
            mTempAllHosts.add(bean);
          }
        }
        upDataAll(mTempAllHosts);
      }
    });
  }

  @Override
  protected void initData() {
    showProgress();
    RemoteMode.getInstance()
              .getServerMachine()
              .subscribe(new BaseObserver<ServerInfo>(compositeDisposable) {

                @Override
                public void onError(BaseException e) {
                  hideProgress();
                }

                @Override
                public void onSuss(ServerInfo serverInfo) {
                  mTv_title.setText("监控服务器（" + serverInfo.getInamount() + "/" + serverInfo.getTotalamount() + "）");
                  mInhosts = serverInfo.getInhosts();
                  mAllHosts = serverInfo.getTotalhosts();
                  upDataIn(mInhosts);
                  upDataAll(mAllHosts);
                }

                @Override
                public void onComplete() {
                  super.onComplete();
                  hideProgress();
                }
              });
  }

  private void upDataAll(ArrayList<ServerMachineBean> totalhosts) {
    mAllAdapter.upData(totalhosts);
  }

  private void upDataIn(ArrayList<ServerMachineBean> inhosts) {
    mFl_view.removeView(view);
    mTv_title.setText("监控服务器（" + inhosts.size() + "/" + mAllHosts.size() + "）");
    if (inhosts != null && inhosts.size() > 0) {
      mRlv_in.setVisibility(View.VISIBLE);
      mInAdapter.upData(inhosts);
    } else {
      mFl_view.addView(view);
      mRlv_in.setVisibility(View.GONE);
    }
    ViewGroup.LayoutParams lp = mRlv_in.getLayoutParams();
    if (inhosts.size() > 4) {
      lp.height = DisplaysUtil.dip2px(this,102 * 2);
    } else {
      lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
    }
    mRlv_in.setLayoutParams(lp);
  }

  @Override
  public void showEmptyView() {

  }
}
