package com.domain.operationrobot.app.home;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatDialog;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import com.domain.operationrobot.R;

/**
 * Project Name : OperationRobot
 * description:null
 *
 * @author : yinbi.zhang.o
 * Create at : 2018/10/13 19:08
 */
public class DelayDialog extends AppCompatDialog {

  private TextView mTvTime;

  public DelayDialog(@NonNull Context context) {
    super(context, R.style.ROBOT_Dialog);
    init();
  }

  private void init() {
    setContentView(R.layout.delay_dialog);
    getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
    findViewById(R.id.iv_close).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        dismiss();
      }
    });
    findViewById(R.id.ll_tel).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        dismiss();
        callPhone();
      }
    });
    mTvTime = findViewById(R.id.tv_time);
    setCanceledOnTouchOutside(false);
  }

  public void callPhone() {
    Intent intent = new Intent(Intent.ACTION_DIAL);
    Uri data = Uri.parse("tel:028-23658965");
    intent.setData(data);
    getContext().startActivity(intent);
  }
}
