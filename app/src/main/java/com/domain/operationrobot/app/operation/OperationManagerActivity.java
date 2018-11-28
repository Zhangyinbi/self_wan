package com.domain.operationrobot.app.operation;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.domain.library.base.AbsActivity;
import com.domain.library.http.consumer.BaseObserver;
import com.domain.library.http.entry.BaseEntry;
import com.domain.library.http.exception.BaseException;
import com.domain.operationrobot.BaseApplication;
import com.domain.operationrobot.R;
import com.domain.operationrobot.app.home.MainActivity;
import com.domain.operationrobot.http.bean.OperationBean;
import com.domain.operationrobot.http.bean.OperationList;
import com.domain.operationrobot.http.data.RemoteMode;
import com.domain.operationrobot.listener.ThrottleLastClickListener;
import java.util.ArrayList;

import static com.domain.operationrobot.app.operation.OperationActivity.ADD_OPERATION;
import static com.domain.operationrobot.util.Constant.OPERATION_REQUEST_CODE;

public class OperationManagerActivity extends AbsActivity {

  ThrottleLastClickListener listener = new ThrottleLastClickListener() {
    @Override
    public void onViewClick(View v) {
      switch (v.getId()) {
        case R.id.tv_add_admin:
          OperationActivity.start(OperationManagerActivity.this, ADD_OPERATION, null);
          break;
        case R.id.iv_back:
          finish();
          break;
      }
    }
  };
  private TextView                               mTvAddAdmin;
  private ImageView                              ivBack;
  private RecyclerView                           mRecycler;
  private OperationAdapter                       mOperationAdapter;
  private EditText                               mEtAdminName;
  private ArrayList<OperationList.OperationInfo> mInfo;
  private IUpdate                                mIUpdate;

  public static void start(Activity activity) {
    Intent intent = new Intent(activity, OperationManagerActivity.class);
    activity.startActivity(intent);
  }

  @Override
  protected int getLayoutId() {
    return R.layout.activity_operation_manager;
  }

  @Override
  protected void newInstancePresenter() {

  }

  @Override
  protected void initView() {
    mTvAddAdmin = findViewById(R.id.tv_add_admin);
    mTvAddAdmin.setOnClickListener(listener);
    if (BaseApplication.getInstance().getUser().getOprole()!=4){
      mTvAddAdmin.setVisibility(View.INVISIBLE);
    }
    ivBack = findViewById(R.id.iv_back);
    ivBack.setOnClickListener(listener);
    mEtAdminName = findViewById(R.id.et_admin_name);
    mEtAdminName.addTextChangedListener(new TextWatcher() {
      @Override
      public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

      }

      @Override
      public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

      }

      @Override
      public void afterTextChanged(Editable editable) {
        String targetName = editable.toString()
                                    .trim();
        if (mInfo != null) {
          mOperationAdapter.updateData(mInfo, targetName, mIUpdate);
        }
      }
    });
    mIUpdate = new IUpdate() {
      @Override
      public void updateData() {
        getOperationInfo();
      }
    };

    mRecycler = findViewById(R.id.rlv_recycler_view);
    mRecycler.setLayoutManager(new GridLayoutManager(this, 2) {
      @Override
      public boolean canScrollVertically() {
        return false;
      }
    });
    mOperationAdapter = new OperationAdapter(this);
    mRecycler.setAdapter(mOperationAdapter);
  }

  @Override
  protected void initData() {
    showProgress();
    getOperationInfo();
  }

  private void getOperationInfo() {
    RemoteMode.getInstance()
              .getOperationInfo()
              .subscribe(new BaseObserver<OperationList>(compositeDisposable) {
                @Override
                public void onError(BaseException e) {
                  hideProgress();
                  showToast(e.getMsg());
                }

                @Override
                public void onSuss(OperationList operationList) {
                  hideProgress();
                  mInfo = operationList.getInfo();
                  mOperationAdapter.updateData(mInfo, null, mIUpdate);
                }

                @Override
                public void onComplete() {
                  super.onComplete();
                  hideProgress();
                }
              });
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (requestCode == OPERATION_REQUEST_CODE && resultCode == RESULT_OK) {
      getOperationInfo();
    }
  }

  @Override
  public void showEmptyView() {

  }

  @Override
  protected void onPause() {
    super.onPause();
    mTvAddAdmin.setFocusable(true);
    mTvAddAdmin.setFocusableInTouchMode(true);
    mTvAddAdmin.requestFocus();
  }

  public interface IUpdate {
    void updateData();
  }
}
