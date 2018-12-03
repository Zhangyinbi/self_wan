package com.domain.operationrobot.app.order;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import com.domain.library.base.AbsActivity;
import com.domain.operationrobot.R;

import static com.domain.operationrobot.util.Constant.GET_TIME;

public class CommandOperationActivity extends AbsActivity {

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
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (requestCode == GET_TIME && resultCode == RESULT_OK) {
      String startTime = data.getStringExtra("startTime");
      String endTime = data.getStringExtra("endTime");

      Log.e("======", "onActivityResult: startTime-->" + startTime);
      Log.e("======", "onActivityResult: endTime-->" + endTime);
    }
  }

  @Override
  protected void initData() {

  }

  @Override
  public void showEmptyView() {

  }
}
