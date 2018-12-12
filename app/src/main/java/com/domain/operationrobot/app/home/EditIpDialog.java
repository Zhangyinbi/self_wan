package com.domain.operationrobot.app.home;

import android.content.Context;
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
import com.domain.operationrobot.BaseApplication;
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

  private EditText      mEditText;
  private Button        mBtnSend;
  private int           type;
  private HostInterface hostInterface;
  private String        actionName;

  public EditIpDialog(@NonNull Context context, int type, HostInterface hostInterface, String actionName) {
    super(context, R.style.ROBOT_Dialog);
    this.type = type;
    this.hostInterface = hostInterface;
    this.actionName = actionName;
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
    TextView textView = findViewById(R.id.tv_action);
    textView.setText("申请 " + actionName);
    if (type==10){
      mEditText.setHint("请输入服务器IP(目前只支持IP进行重启)");
    }
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
        //hostInterface.sendMsg(host);
        if (type == 10) {
          int oprole = BaseApplication.getInstance()
                                      .getUser()
                                      .getOprole();
          if (oprole == 4 || oprole == 6) {
            AppSocket.getInstance()
                     .sendRobotMessage11(host, "agree", "");
          } else {
            AppSocket.getInstance()
                     .sendRobotMessage(host);
          }
        } else {
          AppSocket.getInstance()
                   .sendMessage(type, host);
        }
        dismiss();
      }
    });
    SoftInputUtil.showSoftInput(mEditText);
  }
}

