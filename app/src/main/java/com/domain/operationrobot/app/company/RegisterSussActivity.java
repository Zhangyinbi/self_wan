package com.domain.operationrobot.app.company;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.domain.library.base.AbsActivity;
import com.domain.library.base.BasePresenter;
import com.domain.operationrobot.BaseApplication;
import com.domain.operationrobot.R;
import com.domain.operationrobot.app.home.MainActivity;
import com.domain.operationrobot.http.bean.Company;
import com.domain.operationrobot.listener.ThrottleLastClickListener;

import java.util.ArrayList;

public class RegisterSussActivity extends AbsActivity implements JoinCompanyContract.JoinCompanyView<BasePresenter> {

  ThrottleLastClickListener listener = new ThrottleLastClickListener() {
    @Override
    public void onViewClick(View v) {
      switch (v.getId()) {
        case R.id.iv_back:
          onBackPressed();
          break;
        case R.id.btn_join_company:
          startJoinCompanyActivity();
          break;
        case R.id.btn_create_company:
          createCompany();
          break;
        case R.id.btn_look_around:
          break;
        default:
          break;
      }
    }
  };
  private JoinCompanyPresenterImpl presenter;

  private void createCompany() {
    startActivity(new Intent(this, CreateCompanyActivity.class));
  }

  private void startJoinCompanyActivity() {
    startActivity(new Intent(this, JoinCompanyActivity.class));
  }

  @Override
  protected int getLayoutId() {
    return R.layout.activity_register_suss;
  }

  @Override
  protected void newInstancePresenter() {
    presenter = new JoinCompanyPresenterImpl(this);
  }

  @Override
  protected void initView() {
    findViewById(R.id.iv_back).setOnClickListener(listener);
    findViewById(R.id.btn_join_company).setOnClickListener(listener);
    findViewById(R.id.btn_create_company).setOnClickListener(listener);
    findViewById(R.id.btn_look_around).setOnClickListener(listener);
    //String name = BaseApplication.getInstance()
    //                             .getUser() != null ? BaseApplication.getInstance()
    //                                                                 .getUser()
    //                                                                 .getName() : "请输入姓名";
    //new JoinCompanyDialog(this, new Company("成都云图锐展科技有限公司", "占隐蔽", 89825), name, presenter).show();
  }

  @Override
  protected void initData() {

  }

  @Override
  public void showEmptyView() {

  }

  @Override
  public void setCompanyList(ArrayList<Company> companyList) {

  }

  @Override
  public void onBackPressed() {
    startActivity(new Intent(this, MainActivity.class));
    finish();
  }
}
