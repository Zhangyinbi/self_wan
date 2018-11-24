package com.domain.operationrobot.app.home;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatDialog;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import com.domain.operationrobot.R;
import com.domain.operationrobot.util.TimeUtil;
import java.math.BigDecimal;

import static com.domain.operationrobot.util.Constant.SERVER_PHONE;

/**
 * Project Name : OperationRobot
 * description:null
 *
 * @author : yinbi.zhang.o
 * Create at : 2018/10/13 19:08
 */
public class DelayDialog extends AppCompatDialog {

  private TextView mTvTime;
  private BigDecimal     timeStamp;
  private TextView mTv_delay_time;

  public DelayDialog(@NonNull Context context, BigDecimal timeStamp) {
    super(context, R.style.ROBOT_Dialog);
    this.timeStamp = timeStamp;
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
    mTv_delay_time = findViewById(R.id.tv_delay_time);
    mTvTime = findViewById(R.id.tv_time);
    setCanceledOnTouchOutside(true);
    mTv_delay_time.setText(TimeUtil.getTime(timeStamp));
  }

  public void callPhone() {
    Intent intent = new Intent(Intent.ACTION_DIAL);
    Uri data = Uri.parse("tel:"+SERVER_PHONE);
    intent.setData(data);
    getContext().startActivity(intent);
  }
}
