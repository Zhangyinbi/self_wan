package com.domain.operationrobot.app.company;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatDialog;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.domain.library.utils.ToastUtils;
import com.domain.library.widgets.DeleteEdit;
import com.domain.operationrobot.R;
import com.domain.operationrobot.http.bean.Company;
import com.domain.operationrobot.listener.ThrottleLastClickListener;

import static com.domain.library.utils.ToastUtils.showToast;

/**
 * Project Name : OperationRobot
 * description:null
 *
 * @author : yinbi.zhang.o
 * Create at : 2018/10/13 19:08
 */
public class JoinCompanyDialog extends AppCompatDialog {
  private Company                                  company;
  private String                                   accountName;
  private DeleteEdit                               deAccountName;
  private TextView                                 tvAdminName;
  private JoinCompanyContract.JoinCompanyPresenter presenter;
  ThrottleLastClickListener listener = new ThrottleLastClickListener() {
    @Override
    public void onViewClick(View v) {
      switch (v.getId()) {
        case R.id.btn_cancel:
          dismiss();
          break;
        case R.id.btn_join:
          String inputValue = deAccountName.getInputValue();
          if (TextUtils.isEmpty(inputValue)) {
            showToast("请输入申请人姓名");
            return;
          }
          if (inputValue.length() > 20) {
            showToast("名称应在20字以内");
            return;
          }
          dismiss();
          presenter.join(inputValue, company.getCompanyid());
          break;
        default:
          break;
      }
    }
  };
  private TextView tvCompanyName;

  public JoinCompanyDialog(@NonNull Context context, Company company, String accountName, JoinCompanyContract.JoinCompanyPresenter presenter) {
    super(context, R.style.ROBOT_Dialog);
    this.accountName = accountName;
    this.presenter = presenter;
    this.company = company;
    init();
  }

  private void init() {
    setContentView(R.layout.default_hint_join);
    getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
    tvAdminName = findViewById(R.id.tv_admin_name);
    tvCompanyName = findViewById(R.id.tv_company_name);
    deAccountName = findViewById(R.id.de_account_name);
    findViewById(R.id.btn_cancel).setOnClickListener(listener);
    findViewById(R.id.btn_join).setOnClickListener(listener);
    tvCompanyName.setText(company != null ? company.getCompanyname() : "数据有误");
    String adminName = company != null ? company.getAdmin() : "";
    if (!TextUtils.isEmpty(adminName)) {
      int length = adminName.length();
      String pref = "";
      if (length == 1) {

      } else if (length == 2) {
        pref = "*";
      } else {
        pref = "**";
      }
      String temp = pref + adminName.substring(length - 1, length);
      tvAdminName.setText("管理员：" + temp);
    } else {
      tvAdminName.setText("管理员：***");
    }
    if (!TextUtils.isEmpty(accountName)) {
      deAccountName.setValue(accountName);
      deAccountName.setSelection(accountName.length());
    }
    setCanceledOnTouchOutside(false);
  }
}
