package com.domain.operationrobot.app.order;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import com.domain.library.base.AbsActivity;
import com.domain.library.recycleview.decoration.CategoryItemDecoration;
import com.domain.operationrobot.R;
import com.domain.operationrobot.app.home.SelectOperationActivity;
import com.luck.picture.lib.decoration.RecycleViewDivider;
import java.util.ArrayList;

import static com.domain.library.recycleview.decoration.CategoryItemDecoration.VERTICAL_LIST;
import static com.domain.operationrobot.util.Constant.GET_TIME;

public class CommandOperationActivity extends AbsActivity {
  private static final int SELECTED_USER = 10293;
  private RecyclerView   mRecyclerView;
  private CommandAdapter mCommandAdapter;

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
        startActivityForResult(new Intent(CommandOperationActivity.this, SelectTimeActivity.class), GET_TIME);
      }
    });
    findViewById(R.id.tv_name).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Intent intent = new Intent(CommandOperationActivity.this, SelectOperationActivity.class);
        intent.putExtra("type", 1);
        startActivityForResult(intent, SELECTED_USER);
      }
    });

    mRecyclerView = findViewById(R.id.rlv_recycler_view);
    mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    ArrayList arrayList = new ArrayList();
    arrayList.add(new Object());
    arrayList.add(new Object());
    arrayList.add(new Object());
    arrayList.add(new Object());
    arrayList.add(new Object());
    arrayList.add(new Object());
    arrayList.add(new Object());
    arrayList.add(new Object());
    mCommandAdapter = new CommandAdapter(this, arrayList);
    mRecyclerView.addItemDecoration(new CategoryItemDecoration(this,VERTICAL_LIST));
    mRecyclerView.setAdapter(mCommandAdapter);
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (resultCode == RESULT_OK) {
      switch (requestCode) {
        case GET_TIME:
          String startTime = data.getStringExtra("startTime");
          String endTime = data.getStringExtra("endTime");

          Log.e("======", "onActivityResult: startTime-->" + startTime);
          Log.e("======", "onActivityResult: endTime-->" + endTime);
          break;
        case SELECTED_USER:
          String id = data.getStringExtra("id");
          Log.e("======", "onActivityResult: id-->" + id);
          break;
      }
    }
  }

  @Override
  protected void initData() {

  }

  @Override
  public void showEmptyView() {

  }
}
