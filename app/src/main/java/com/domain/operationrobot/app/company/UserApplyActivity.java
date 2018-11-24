package com.domain.operationrobot.app.company;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.domain.library.base.AbsActivity;
import com.domain.library.http.consumer.BaseObserver;
import com.domain.library.http.entry.BaseEntry;
import com.domain.library.http.exception.BaseException;
import com.domain.operationrobot.R;
import com.domain.operationrobot.http.bean.ApplyInfo;
import com.domain.operationrobot.http.data.RemoteMode;
import com.google.gson.Gson;

/**
 *
 */
public class UserApplyActivity extends AbsActivity {

  private RecyclerView     rlv_recycler;
  private UserApplyAdapter mUserApplyAdapter;
  private TextView         mTv_no_data;
  private LinearLayout mLl_no_data;
  private ApplyInfo mApplyInfo;

  @Override
  protected int getLayoutId() {
    return R.layout.activity_user_apply;
  }

  @Override
  protected void newInstancePresenter() {

  }

  @Override
  protected void initView() {
    mLl_no_data = findViewById(R.id.ll_no_data);
    mTv_no_data = findViewById(R.id.tv_no_data);
    findViewById(R.id.iv_back).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        finish();
      }
    });
    rlv_recycler = findViewById(R.id.rlv_recycler);
    mUserApplyAdapter = new UserApplyAdapter(this);
    rlv_recycler.setLayoutManager(new LinearLayoutManager(this));
    rlv_recycler.setAdapter(mUserApplyAdapter);
  }

  @Override
  protected void initData() {
    showProgress();
    RemoteMode.getInstance()
              .getJoinInfo()
              .subscribe(new BaseObserver<ApplyInfo>(compositeDisposable) {
                @Override
                public void onError(BaseException e) {
                  hideProgress();
                  showToast(e.getMsg());
                  mLl_no_data.setVisibility(View.VISIBLE);
                }

                @Override
                public void onSuss(ApplyInfo applyInfo) {
                  mApplyInfo = applyInfo;
                  hideProgress();
                  if (null == applyInfo.getInfo() || applyInfo.getInfo()
                                                              .size() == 0) {
                    mLl_no_data.setVisibility(View.VISIBLE);
                    return;
                  }
                  mLl_no_data.setVisibility(View.GONE);
                  mUserApplyAdapter.setData(applyInfo.getInfo());
                }

                @Override
                public void onComplete() {
                  super.onComplete();
                  hideProgress();
                }
              });
  }

  @Override
  public void showEmptyView() {

  }

  /**
   * 处理请求
   * @param action 0代表同意加入公司，1代表拒绝加入公司
   */
  public void dispose(int action, String request_userid) {
    showProgress();
    RemoteMode.getInstance()
              .disposeJoinInfo(action, request_userid)
              .subscribe(new BaseObserver<BaseEntry>(compositeDisposable) {
                @Override
                public void onError(BaseException e) {
                  hideProgress();
                  showToast(e.getMsg());
                }

                @Override
                public void onSuss(BaseEntry applyInfo) {
                  hideProgress();
                  initData();
                }

                @Override
                public void onComplete() {
                  super.onComplete();
                  hideProgress();
                }
              });
  }
}
