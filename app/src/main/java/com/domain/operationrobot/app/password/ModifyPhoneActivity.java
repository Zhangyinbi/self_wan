package com.domain.operationrobot.app.password;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.domain.library.base.AbsActivity;
import com.domain.library.http.consumer.BaseObserver;
import com.domain.library.http.entry.BaseEntry;
import com.domain.library.http.exception.BaseException;
import com.domain.library.utils.InputUtils;
import com.domain.library.utils.SpUtils;
import com.domain.library.utils.ToastUtils;
import com.domain.library.widgets.DeleteEdit;
import com.domain.operationrobot.BaseApplication;
import com.domain.operationrobot.R;
import com.domain.operationrobot.http.bean.User;
import com.domain.operationrobot.http.data.RemoteMode;
import com.domain.operationrobot.listener.ThrottleLastClickListener;

import static com.domain.operationrobot.util.Constant.PHONE_LENGTH;
import static com.domain.operationrobot.util.Constant.USER_SP_KEY;
import static com.domain.operationrobot.util.Constant.VERITY_CODE_LENGTH;

public class ModifyPhoneActivity extends AbsActivity {

  private DeleteEdit deNewPhone;
  private TextView   tvSendCode;
  private CountDownTimer timer = new CountDownTimer(60000, 1000) {

    @Override
    public void onTick(long millisUntilFinished) {
      updateText((millisUntilFinished / 1000) + "S", false, R.color.code_gray);
    }

    @Override
    public void onFinish() {
      updateText("发送验证码", true, R.color.code_blue);
    }
  };
  private Button   btnComplete;
  private EditText etCode;
  ThrottleLastClickListener listener = new ThrottleLastClickListener() {
    @Override
    public void onViewClick(View v) {
      switch (v.getId()) {
        case R.id.iv_back:
          finish();
          break;
        case R.id.tv_send_code:
          String phone = deNewPhone.getValue();
          if (isEmpty(phone)) {
            return;
          }
          if (InputUtils.isMobileNumber(phone)) {
            sendCode(phone);
          } else {
            ToastUtils.showToast("手机号码格式不正确");
          }
          break;
        case R.id.btn_sure:
          String phone1 = deNewPhone.getValue();
          if (isEmpty(phone1)) {
            return;
          }
          if (InputUtils.isMobileNumber(phone1)) {
            String code = etCode.getText()
                                .toString()
                                .trim();
            if (isEmpty(code)) {
              ToastUtils.showToast("请输入短信验证码");
              return;
            } else if (code.length() != 5) {
              ToastUtils.showToast("请输入正确的的短信验证码");
              return;
            }
            complete(phone1, code);
          } else {
            ToastUtils.showToast("手机号码格式不正确");
          }
          break;
      }
    }
  };
  private TextView mTvUserMobile;

  /**
   * 修改手机号码
   */
  private void complete(final String phone, String code) {
    showProgress();
    RemoteMode.getInstance()
              .modifyPhone(phone, code)
              .subscribe(new BaseObserver<BaseEntry>(compositeDisposable) {
                @Override
                public void onError(BaseException e) {
                  hideProgress();
                  showToast(e.getMsg());
                }

                @Override
                public void onSuss(BaseEntry baseEntry) {
                  hideProgress();
                  showToast(baseEntry.msg);
                  BaseApplication.getInstance()
                                 .getUser()
                                 .setMobile(phone);
                  SpUtils.setObject(USER_SP_KEY, BaseApplication.getInstance()
                                                                .getUser());
                  finish();
                } @Override
                public void onComplete() {
                  super.onComplete();
                  hideProgress();
                }
              });
  }

  public void sendCode(String accountPhone) {
    showProgress();
    //timer.start();
    //RemoteMode.getInstance()
    //          .sendCode(accountPhone)
    //          .subscribe(new BaseObserver<String>(compositeDisposable) {
    //            @Override
    //            public void onError(BaseException e) {
    //              hideProgress();
    //              showToast(e.getMsg());
    //              if (timer != null) {
    //                timer.cancel();
    //              }
    //              updateText("发送验证码", true, R.color.code_blue);
    //            }
    //
    //            @Override
    //            public void onSuss(String userBaseEntry) {
    //              hideProgress();
    //              showToast("验证码已发送至您手机，请注意查收");
    //            } @Override
    //            public void onComplete() {
    //              super.onComplete();
    //              hideProgress();
    //            }
    //          });
  }

  @Override
  protected int getLayoutId() {
    return R.layout.activity_movify_phone;
  }

  @Override
  protected void newInstancePresenter() {

  }

  @Override
  protected void initView() {
    deNewPhone = findViewById(R.id.de_new_phone);
    deNewPhone.setInputType(InputType.TYPE_CLASS_NUMBER);
    deNewPhone.setKeyListener("0123456789");
    deNewPhone.addFilter(new InputFilter.LengthFilter(PHONE_LENGTH));
    etCode = findViewById(R.id.et_code);
    etCode.setFilters(new InputFilter[] { new InputFilter.LengthFilter(VERITY_CODE_LENGTH) });
    tvSendCode = findViewById(R.id.tv_send_code);
    tvSendCode.setOnClickListener(listener);
    btnComplete = findViewById(R.id.btn_sure);
    btnComplete.setOnClickListener(listener);
    findViewById(R.id.iv_back).setOnClickListener(listener);
    mTvUserMobile = findViewById(R.id.tv_user_mobile);
    User user = BaseApplication.getInstance().getUser();
    mTvUserMobile.setText(user.getMobile());
    deNewPhone.setTextAfterChange(new DeleteEdit.TextAfterChange() {
      @Override
      public void afterTextChanged(Editable editable) {
        if (deNewPhone.getValue()
                      .length() == PHONE_LENGTH) {
          btnComplete.setBackground(getResources().getDrawable(R.drawable.login_blue_round_click_bg));
          btnComplete.setClickable(true);
        } else {
          btnComplete.setBackground(getResources().getDrawable(R.drawable.login_blue_round_not_click_bg));
          btnComplete.setClickable(false);
        }
      }
    });
  }

  public void updateText(String s, boolean b, int color) {
    tvSendCode.setText(s);
    tvSendCode.setTextColor(getResources().getColor(color));
    tvSendCode.setClickable(b);
  }

  @Override
  protected void initData() {

  }

  @Override
  public void showEmptyView() {

  }
}
