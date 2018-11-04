package com.domain.operationrobot.app.home;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatDialog;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.domain.library.utils.SoftInputUtil;
import com.domain.library.utils.ToastUtils;
import com.domain.operationrobot.R;
import com.domain.operationrobot.im.socket.AppSocket;

/**
 * Project Name : OperationRobot
 * description:null
 *
 * @author : yinbi.zhang.o
 * Create at : 2018/11/4 15:26
 */
public class EditIpDialog extends AppCompatDialog {

  private EditText mEditText;
  private Button   mBtnSend;
  private int      type;

  public EditIpDialog(@NonNull Context context, int type) {
    super(context, R.style.ROBOT_Dialog);
    this.type = type;
    init();
  }

  @Override
  public void show() {
    super.show();
  }

  private void init() {
    setContentView(R.layout.edit_ip_dialog);
    getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
    setCanceledOnTouchOutside(true);
    mEditText = findViewById(R.id.et_host);
    mBtnSend = findViewById(R.id.btn_send);
    mBtnSend.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        String host = mEditText.getText()
                               .toString()
                               .trim();
        if (TextUtils.isEmpty(host)) {
          ToastUtils.showToast("请输入机器查询所需信息");
          return;
        }
        AppSocket.getInstance()
                 .sendMessage(type, host);
        dismiss();
      }
    });
    SoftInputUtil.showSoftInput(mEditText);
  }
}

