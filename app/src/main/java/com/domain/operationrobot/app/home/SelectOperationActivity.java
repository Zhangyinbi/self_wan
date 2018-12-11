package com.domain.operationrobot.app.home;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.domain.library.base.AbsActivity;
import com.domain.library.http.consumer.BaseObserver;
import com.domain.library.http.exception.BaseException;
import com.domain.library.recycleview.decoration.CategoryItemDecoration;
import com.domain.library.recycleview.holder.ViewHolder;
import com.domain.library.recycleview.interfaces.ItemClickListener;
import com.domain.operationrobot.BaseApplication;
import com.domain.operationrobot.R;
import com.domain.operationrobot.http.bean.OperationList;
import com.domain.operationrobot.http.data.RemoteMode;
import java.util.ArrayList;

import static com.domain.library.recycleview.decoration.CategoryItemDecoration.VERTICAL_LIST;

public class SelectOperationActivity extends AbsActivity {

  private RecyclerView                           mRecyclerView;
  private SelectedAdapter                        mSelectedAdapter;
  private ArrayList<OperationList.OperationInfo> info;
  private int                                    mType;//1不是艾特进来的

  @Override
  protected int getLayoutId() {
    return R.layout.activity_select_operation;
  }

  @Override
  protected void newInstancePresenter() {

  }

  @Override
  protected void initView() {
    Intent intent1 = getIntent();
    if (intent1 != null) {
      mType = intent1.getIntExtra("type", 0);
    }
    findViewById(R.id.iv_back).setOnClickListener(view -> finish());
    mRecyclerView = findViewById(R.id.recyclerView);
    findViewById(R.id.include_robot).setOnClickListener(view -> {
      Intent intent = new Intent();
      intent.putExtra("name", "机器人");
      setResult(RESULT_OK, intent);
      finish();
    });
    if (mType == 1) {
      findViewById(R.id.include_robot).setVisibility(View.GONE);
    }
    info = new ArrayList<>();
    mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    mSelectedAdapter = new SelectedAdapter(this, info);
    mRecyclerView.addItemDecoration(new CategoryItemDecoration(this, VERTICAL_LIST));
    mRecyclerView.setAdapter(mSelectedAdapter);
    mSelectedAdapter.setItemOnClickListener(new ItemClickListener<OperationList.OperationInfo>() {
      @Override
      public void onItemClick(ViewHolder viewHolder, OperationList.OperationInfo itemData, int position) {
        Intent intent = new Intent();
        intent.putExtra("name", itemData.getOpusername());
        intent.putExtra("id", itemData.getUserid());
        setResult(RESULT_OK, intent);
        finish();
      }
    });
  }

  @Override
  protected void initData() {
    getOperationInfo();
  }

  @Override
  public void showEmptyView() {

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
                  ArrayList<OperationList.OperationInfo> bean = operationList.getInfo();
                  if (mType != 1) {
                    for (OperationList.OperationInfo operationInfo : bean) {
                      if (operationInfo.getUserid()
                                       .equals(BaseApplication.getInstance()
                                                              .getUser()
                                                              .getUserId())) {
                        bean.remove(operationInfo);
                        break;
                      }
                    }
                  }
                  if (bean != null && bean.size() > 0) {
                    info.clear();
                    info.addAll(bean);
                    mSelectedAdapter.notifyDataSetChanged();
                  }
                }

                @Override
                public void onComplete() {
                  super.onComplete();
                  hideProgress();
                }
              });
  }
}
