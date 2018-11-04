package com.domain.operationrobot.app.operation;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
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
import com.domain.library.widgets.DeleteEdit;
import com.domain.operationrobot.R;
import com.domain.operationrobot.http.bean.OperationBean;
import com.domain.operationrobot.http.data.RemoteMode;
import com.domain.operationrobot.listener.ThrottleLastClickListener;

import static com.domain.operationrobot.util.Constant.OPERATION_REQUEST_CODE;
import static com.domain.operationrobot.util.Constant.PHONE_LENGTH;

public class OperationActivity extends AbsActivity {
  public static final int    ADD_OPERATION  = 0x132;
  public static final int    EDIT_OPERATION = ADD_OPERATION + 1;
  private static      String TYPE           = "type";
  private static      String EXTRA_BEAN     = "operationBean";
  private             int    type           = 0;
  private DeleteEdit    de_name;
  private DeleteEdit    de_phone;
  private OperationBean operationBean;
  ThrottleLastClickListener listener = new ThrottleLastClickListener() {
    @Override
    public void onViewClick(View v) {
      switch (v.getId()) {
        case R.id.iv_back:
          finish();
          break;
        case R.id.btn_commit:
          String phone = de_phone.getValue();
          if (isEmpty(phone)) {
            return;
          }
          if (!InputUtils.isMobileNumber(phone)) {
            showToast("手机号码格式不正确");
            return;
          }
          String name = de_name.getValue();
          if (isEmpty(name)) {
            return;
          }
          if (type == ADD_OPERATION) {
            addOperation(phone, name);
          } else {
            editOperation(phone, name, operationBean.getId());
          }
          break;
      }
    }
  };

  public static void start(Activity activity, int type, OperationBean operationBean) {
    Intent intent = new Intent(activity, OperationActivity.class);
    intent.putExtra(TYPE, type);
    if (operationBean != null) {
      intent.putExtra(EXTRA_BEAN, operationBean);
    }
    activity.startActivityForResult(intent, OPERATION_REQUEST_CODE);
  }

  /**
   * 编辑运维用户
   */
  private void editOperation(String phone, String name, String id) {
    showProgress();
    RemoteMode.getInstance()
              .editOperation(phone, name, id)
              .subscribe(new BaseObserver<BaseEntry>(compositeDisposable) {
                @Override
                public void onError(BaseException e) {
                  hideProgress();
                  showToast(e.getMsg());
                }

                @Override
                public void onSuss(BaseEntry baseEntry) {
                  hideProgress();
                  setResult(RESULT_OK);
                  finish();
                }

                @Override
                public void onComplete() {
                  super.onComplete();
                  hideProgress();
                }
              });
  }

  /**
   * 添加运维用户
   */
  private void addOperation(String phone, String name) {
    showProgress();
    RemoteMode.getInstance()
              .addOperation(phone, name)
              .subscribe(new BaseObserver<BaseEntry>(compositeDisposable) {
                @Override
                public void onError(BaseException e) {
                  hideProgress();
                  showToast(e.getMsg());
                }

                @Override
                public void onSuss(BaseEntry baseEntry) {
                  hideProgress();
                  setResult(RESULT_OK);
                  finish();
                }

                @Override
                public void onComplete() {
                  super.onComplete();
                  hideProgress();
                }
              });
  }

  @Override
  protected int getLayoutId() {
    return R.layout.activity_operation;
  }

  @Override
  protected void newInstancePresenter() {

  }

  @Override
  protected void initView() {
    Intent intent = getIntent();
    type = intent.getIntExtra(TYPE, -1);
    if (type == -1) {
      finish();
      return;
    }
    findViewById(R.id.iv_back).setOnClickListener(listener);
    findViewById(R.id.btn_commit).setOnClickListener(listener);
    TextView tv_name = findViewById(R.id.tv_name);
    ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(Color.TRANSPARENT);
    SpannableString spannableString = new SpannableString("姓姓名");
    spannableString.setSpan(foregroundColorSpan, 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
    tv_name.setText(spannableString);
    TextView tvTitle = findViewById(R.id.tv_title);
    de_name = findViewById(R.id.de_name);
    de_phone = findViewById(R.id.de_phone);
    de_phone.setInputType(InputType.TYPE_CLASS_NUMBER);
    de_phone.setKeyListener("0123456789");
    de_phone.addFilter(new InputFilter.LengthFilter(PHONE_LENGTH));
    if (type == ADD_OPERATION) {
      tvTitle.setText("添加运维用户");
    } else {
      tvTitle.setText("编辑运维用户");
      operationBean = intent.getParcelableExtra(EXTRA_BEAN);
      de_name.setValue(operationBean.getName());
      de_phone.setValue(operationBean.getPhone());
      de_name.setSelection(operationBean.getName()
                                        .length());
      de_phone.setSelection(operationBean.getPhone()
                                         .length());
    }
  }

  @Override
  protected void initData() {

  }

  @Override
  public void showEmptyView() {

  }
}
