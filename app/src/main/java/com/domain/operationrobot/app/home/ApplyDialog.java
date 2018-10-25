package com.domain.operationrobot.app.home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatDialog;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import com.domain.library.widgets.DeleteEdit;
import com.domain.operationrobot.R;
import com.domain.operationrobot.app.company.JoinCompanyContract;
import com.domain.operationrobot.http.bean.Company;
import com.domain.operationrobot.listener.ThrottleLastClickListener;

/**
 * Project Name : OperationRobot
 * description:null
 *
 * @author : yinbi.zhang.o
 * Create at : 2018/10/13 19:08
 */
public class ApplyDialog extends AppCompatDialog {

  public ApplyDialog(@NonNull Context context) {
    super(context, R.style.ROBOT_Dialog);
    init();
  }

  private void init() {
    setContentView(R.layout.apply_dialog);
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
    setCanceledOnTouchOutside(false);
  }

  public void callPhone() {
    Intent intent = new Intent(Intent.ACTION_DIAL);
    Uri data = Uri.parse("tel:028-23658965");
    intent.setData(data);
    getContext().startActivity(intent);
  }
}
