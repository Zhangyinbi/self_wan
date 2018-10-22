package com.domain.operationrobot.app.company;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;

import com.domain.library.base.AbsActivity;
import com.domain.library.http.consumer.BaseObserver;
import com.domain.library.http.entry.BaseEntry;
import com.domain.library.http.exception.BaseException;
import com.domain.library.utils.InputUtils;
import com.domain.library.utils.ToastUtils;
import com.domain.library.widgets.DeleteEdit;
import com.domain.operationrobot.R;
import com.domain.operationrobot.http.data.RemoteMode;
import com.domain.operationrobot.listener.ThrottleLastClickListener;

public class CreateCompanyActivity extends AbsActivity {
  private DeleteEdit de_company_name;
  private DeleteEdit de_email;
  private DeleteEdit de_name;
  ThrottleLastClickListener listener = new ThrottleLastClickListener() {
    @Override
    public void onViewClick(View v) {
      switch (v.getId()) {
        case R.id.iv_back:
          finish();
          break;
        case R.id.btn_complete:
          String companyName = de_company_name.getValue();
          if (isEmpty(companyName)) {
            return;
          }

          String email = de_email.getValue();
          if (isEmpty(email)) {
            return;
          }
          if (!InputUtils.isEmail(email)) {
            showToast("邮箱格式不对，请输入正确的邮箱");
            return;
          }
          String name = de_name.getValue();
          if (isEmpty(name)) {
            return;
          }
          createCompany(companyName, email, name);
          break;
      }
    }
  };

  /**
   * 创建公司
   */
  private void createCompany(String companyName, String email, String name) {
    showProgress();
    //RemoteMode.getInstance()
    //          .createCompany(companyName, email, name)
    //          .subscribe(new BaseObserver<String>(compositeDisposable) {
    //            @Override
    //            public void onError(BaseException e) {
    //              hideProgress();
    //              showToast(e.getMsg());
    //            }
    //
    //            @Override
    //            public void onSuss(String baseEntryBaseEntry) {
    //              hideProgress();
    //              //TODO  创建公司成功
    //              finish();
    //            }
    //
    //            @Override
    //            public void onComplete() {
    //              super.onComplete();
    //              hideProgress();
    //            }
    //          });
  }

  @Override
  protected int getLayoutId() {
    return R.layout.activity_create_company;
  }

  @Override
  protected void newInstancePresenter() {

  }

  @Override
  protected void initView() {
    TextView tv_name = findViewById(R.id.tv_name);
    ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(Color.TRANSPARENT);
    SpannableString spannableString = new SpannableString("姓名姓名");
    spannableString.setSpan(foregroundColorSpan, 0, 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
    tv_name.setText(spannableString);
    findViewById(R.id.iv_back).setOnClickListener(listener);
    findViewById(R.id.btn_complete).setOnClickListener(listener);

    de_company_name = findViewById(R.id.de_company_name);
    de_email = findViewById(R.id.de_email);
    de_name = findViewById(R.id.de_name);
  }

  @Override
  protected void initData() {

  }

  @Override
  public void showEmptyView() {

  }
}
