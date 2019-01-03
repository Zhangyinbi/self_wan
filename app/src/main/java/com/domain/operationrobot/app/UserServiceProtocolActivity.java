package com.domain.operationrobot.app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.webkit.WebView;
import com.domain.library.base.AbsActivity;
import com.domain.operationrobot.R;

public class UserServiceProtocolActivity extends AbsActivity {

  @Override
  protected int getLayoutId() {
    return R.layout.activity_user_service_protocol;
  }

  @Override
  protected void newInstancePresenter() {

  }

  @Override
  protected void initView() {
    findViewById(R.id.iv_back).setOnClickListener(v -> finish());
    WebView webView = findViewById(R.id.wv_web);
    webView.loadUrl("file:///android_asset/userAgreement.html");
  }

  @Override
  protected void initData() {

  }

  @Override
  public void showEmptyView() {

  }
}
