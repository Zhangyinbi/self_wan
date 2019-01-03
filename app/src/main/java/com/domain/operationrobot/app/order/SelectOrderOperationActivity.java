package com.domain.operationrobot.app.order;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.domain.library.base.AbsActivity;
import com.domain.library.http.consumer.BaseObserver;
import com.domain.library.http.exception.BaseException;
import com.domain.library.recycleview.decoration.CategoryItemDecoration;
import com.domain.library.recycleview.holder.ViewHolder;
import com.domain.library.recycleview.interfaces.ItemClickListener;
import com.domain.operationrobot.R;
import com.domain.operationrobot.app.home.SelectedAdapter;
import com.domain.operationrobot.http.bean.OperationList;
import com.domain.operationrobot.http.bean.OrderOperationBean;
import com.domain.operationrobot.http.data.RemoteMode;
import java.util.ArrayList;

import static com.domain.library.recycleview.decoration.CategoryItemDecoration.VERTICAL_LIST;

public class SelectOrderOperationActivity extends AbsActivity {

  private RecyclerView                       mRecyclerView;
  private OrderOperationAdapter              mSelectedAdapter;
  private ArrayList<OrderOperationBean.Info> info;

  @Override
  protected int getLayoutId() {
    return R.layout.activity_select_operation2;
  }

  @Override
  protected void newInstancePresenter() {

  }

  @Override
  protected void initView() {
    findViewById(R.id.iv_back).setOnClickListener(view -> finish());
    mRecyclerView = findViewById(R.id.recyclerView);
    mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    info = new ArrayList<>();
    mSelectedAdapter = new OrderOperationAdapter(this, info);
    mRecyclerView.addItemDecoration(new CategoryItemDecoration(this, VERTICAL_LIST));
    mRecyclerView.setAdapter(mSelectedAdapter);
    mSelectedAdapter.setItemOnClickListener(new ItemClickListener<OrderOperationBean.Info>() {
      @Override
      public void onItemClick(ViewHolder viewHolder, OrderOperationBean.Info itemData, int position) {
        Intent intent = new Intent();
        intent.putExtra("id", itemData.getOperationId());
        setResult(RESULT_OK, intent);
        finish();
      }
    });
  }

  @Override
  protected void initData() {
    getData();
  }

  private void getData() {
    showProgress();
    RemoteMode.getInstance()
              .getOrderOperationUser()
              .subscribe(new BaseObserver<OrderOperationBean>(compositeDisposable) {
                @Override
                public void onError(BaseException e) {
                  hideProgress();
                  showToast(e.getMsg());
                }

                @Override
                public void onSuss(OrderOperationBean orderOperationBean) {
                  hideProgress();
                  if (orderOperationBean != null && orderOperationBean.getResult()
                                                                      .size() > 0) {
                    info.addAll(orderOperationBean.getResult());
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

  @Override
  public void showEmptyView() {

  }
}
