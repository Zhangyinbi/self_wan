package com.domain.operationrobot.app.order;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.domain.library.base.AbsActivity;
import com.domain.library.http.consumer.BaseObserver;
import com.domain.library.http.exception.BaseException;
import com.domain.operationrobot.R;
import com.domain.operationrobot.http.bean.OrderIdBean;
import com.domain.operationrobot.http.bean.OrderOperationBean;
import com.domain.operationrobot.http.data.RemoteMode;
import java.util.ArrayList;

public class SelectOrderIdActivity extends AbsActivity {

  private RecyclerView                       mRecyclerView;
  private ArrayList<OrderIdBean.OrderIdInfo> result;
  private OrderIdAdapter                     mOrderIdAdapter;

  @Override
  protected int getLayoutId() {
    return R.layout.activity_select_order_id;
  }

  @Override
  protected void newInstancePresenter() {

  }

  @Override
  protected void initView() {
    findViewById(R.id.iv_back).setOnClickListener(view -> finish());
    mRecyclerView = findViewById(R.id.recyclerView);
    mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    result = new ArrayList<>();
    mOrderIdAdapter = new OrderIdAdapter(this, result);
    mRecyclerView.setAdapter(mOrderIdAdapter);
    mOrderIdAdapter.setOnItemClick(new OrderIdAdapter.OnItemClick() {
      @Override
      public void OnItemClick(String orderId) {
        Intent intent = new Intent();
        intent.putExtra("id", orderId);
        setResult(RESULT_OK, intent);
        finish();
      }
    });
  }

  @Override
  protected void initData() {
    showProgress();
    RemoteMode.getInstance()
              .getOrderOperationAction()
              .subscribe(new BaseObserver<OrderIdBean>(compositeDisposable) {
                @Override
                public void onError(BaseException e) {
                  hideProgress();
                  showToast(e.getMsg());
                }

                @Override
                public void onSuss(OrderIdBean orderIdBean) {
                  hideProgress();
                  if (orderIdBean != null && orderIdBean.getResult()
                                                        .size() > 0) {
                    result.addAll(orderIdBean.getResult());
                    mOrderIdAdapter.notifyDataSetChanged();
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
