package com.domain.operationrobot.app.order;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import com.domain.library.base.AbsActivity;
import com.domain.library.http.consumer.BaseObserver;
import com.domain.library.http.exception.BaseException;
import com.domain.library.recycleview.RefreshRecyclerView;
import com.domain.library.recycleview.creator.DefaultLoadMoreCreator;
import com.domain.library.recycleview.creator.DefaultRefreshCreator;
import com.domain.library.recycleview.decoration.CategoryItemDecoration;
import com.domain.library.recycleview.decoration.DividerItemDecoration;
import com.domain.library.utils.ToastUtils;
import com.domain.operationrobot.R;
import com.domain.operationrobot.app.home.SelectOperationActivity;
import com.domain.operationrobot.http.bean.CommandBean;
import com.domain.operationrobot.http.data.RemoteMode;
import com.domain.operationrobot.util.ToastU;
import com.luck.picture.lib.decoration.RecycleViewDivider;
import java.util.ArrayList;

import static com.domain.library.recycleview.decoration.CategoryItemDecoration.VERTICAL_LIST;
import static com.domain.library.recycleview.decoration.DividerItemDecoration.HORIZONTAL_LIST;
import static com.domain.operationrobot.util.Constant.GET_TIME;

public class CommandOperationActivity extends AbsActivity {
  private static final int SELECTED_USER     = 10293;
  private static final int SELECTED_ORDER_ID = 10294;
  private RefreshRecyclerView                mRecyclerView;
  private CommandAdapter                     mCommandAdapter;
  private String                             mStartTime;
  private String                             mEndTime;
  private String                             mId;
  private String                             mOrderId;
  private ArrayList<CommandBean.CommandInfo> mArrayList;
  private int page = 1;
  private int allPageNum;

  @Override
  protected int getLayoutId() {
    return R.layout.activity_command_operation;
  }

  @Override
  protected void newInstancePresenter() {

  }

  @Override
  protected void initView() {
    findViewById(R.id.iv_back).setOnClickListener(view -> finish());
    findViewById(R.id.tv_order_time).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        mId = "";
        mOrderId = "";
        startActivityForResult(new Intent(CommandOperationActivity.this, SelectTimeActivity.class), GET_TIME);
      }
    });
    findViewById(R.id.tv_name).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        mStartTime = "";
        mEndTime = "";
        mOrderId = "";
        Intent intent = new Intent(CommandOperationActivity.this, SelectOrderOperationActivity.class);
        startActivityForResult(intent, SELECTED_USER);
      }
    });

    findViewById(R.id.tv_order_id).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        mStartTime = "";
        mEndTime = "";
        mId = "";
        Intent intent = new Intent(CommandOperationActivity.this, SelectOrderIdActivity.class);
        startActivityForResult(intent, SELECTED_ORDER_ID);
      }
    });

    mRecyclerView = findViewById(R.id.rlv_recycler_view);
    mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    mArrayList = new ArrayList();
    mCommandAdapter = new CommandAdapter(this, mArrayList);
    mRecyclerView.setAdapter(mCommandAdapter);
    mRecyclerView.addLoadViewCreator(new DefaultLoadMoreCreator());
    mRecyclerView.addRefreshViewCreator(new DefaultRefreshCreator());
    mRecyclerView.setOnRefreshListener(new RefreshRecyclerView.OnRefreshListener() {
      @Override
      public void onRefresh() {
        page = 1;
        getData(true);
      }
    });
    mRecyclerView.setmLoadMoreListener(new RefreshRecyclerView.OnLoadMoreListener() {
      @Override
      public void onLoadMore() {
        if (page < allPageNum) {
          page += 1;
          getData(true);
        } else {
          mRecyclerView.onComplete();
          ToastUtils.showToast("没有更多了");
        }
      }
    });
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (resultCode == RESULT_OK) {
      switch (requestCode) {
        case GET_TIME:
          mStartTime = data.getStringExtra("startTime");
          mEndTime = data.getStringExtra("endTime");
          getData(false);
          break;
        case SELECTED_USER:
          mId = data.getStringExtra("id");
          getData(false);
          break;
        case SELECTED_ORDER_ID:
          mOrderId = data.getStringExtra("id");
          getData(false);
      }
    }
  }

  @Override
  protected void initData() {
    getData(false);
  }

  private void getData(boolean isRefresh) {
    if (!isRefresh) {
      showProgress();
    }
    RemoteMode.getInstance()
              .getOrderLog(mId, mStartTime, mEndTime, mOrderId, page)
              .subscribe(new BaseObserver<CommandBean>(compositeDisposable) {
                @Override
                public void onError(BaseException e) {
                  hideProgress();
                  showToast(e.getMsg());
                  if (isRefresh) {
                    mRecyclerView.onComplete();
                  }
                }

                @Override
                public void onSuss(CommandBean commandBean) {
                  hideProgress();
                  if (isRefresh) {
                    mRecyclerView.onComplete();
                  }
                  allPageNum = commandBean.getAll_page();
                  if (commandBean.getResult()
                                 .size() == 0) {
                    return;
                  } else {
                    mRecyclerView.setVisibility(View.VISIBLE);
                  }
                  if (page == 1) {
                    mArrayList.clear();
                    mArrayList.addAll(commandBean.getResult());
                    mCommandAdapter.notifyDataSetChanged();
                  } else {
                    mArrayList.addAll(commandBean.getResult());
                    mCommandAdapter.notifyDataSetChanged();
                  }
                }

                @Override
                public void onComplete() {
                  super.onComplete();
                  hideProgress();
                  if (isRefresh) {
                    mRecyclerView.onComplete();
                  }
                }
              });
  }

  @Override
  public void showEmptyView() {

  }
}
