package com.domain.operationrobot.app.password;

import android.os.CountDownTimer;
import android.text.InputFilter;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
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

import io.reactivex.disposables.CompositeDisposable;

import static com.domain.operationrobot.util.Constant.PHONE_LENGTH;
import static com.domain.operationrobot.util.Constant.VERITY_CODE_LENGTH;

/**
 * Project Name : OperationRobot
 * description:null
 *
 * @author : yinbi.zhang.o
 * Create at : 2018/10/14 08:35
 */
public class ForgetPwdActivity extends AbsActivity {
  private DeleteEdit     accountPhone;
  private Button         btnCommit;
  private Button         btnToggle;
  private EditText       etPwd;
  private EditText       etCode;
  private RelativeLayout rlToggle;
  private TextView       tvSendCode;
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
  private CompositeDisposable compositeDisposable;
  ThrottleLastClickListener listener = new ThrottleLastClickListener() {
    @Override
    public void onViewClick(View v) {
      switch (v.getId()) {
        case R.id.tv_send_code:
          String phoneCode = accountPhone.getValue();
          if (isEmpty(phoneCode)) {
            return;
          }
          if (InputUtils.isMobileNumber(phoneCode)) {
            sendCode(phoneCode);
          } else {
            ToastUtils.showToast("手机号码格式不正确");
          }

          break;
        case R.id.btn_commit:
          String phone = accountPhone.getValue();
          if (isEmpty(phone)) {
            return;
          }
          if (InputUtils.isMobileNumber(phone)) {
            String code = etCode.getText()
                                .toString()
                                .trim();
            if (isEmpty(code)) {
              ToastUtils.showToast("请输入短信验证码");
              return;
            } else if (code.length() != VERITY_CODE_LENGTH) {
              ToastUtils.showToast("请输入正确的的短信验证码");
              return;
            }
            String pwd = etPwd.getText()
                              .toString()
                              .trim();
            if (isEmpty(pwd)) {
              ToastUtils.showToast("请输入密码");
              return;
            }
            commit(phone, pwd, code);
          } else {
            ToastUtils.showToast("手机号码格式不正确");
          }
          break;
        case R.id.iv_back:
          finish();
          break;
        case R.id.rl_toggle:
        case R.id.btn_toggle:
          if (etPwd.getInputType() == 129) {
            btnToggle.setBackgroundResource(R.drawable.img_yj);
            etPwd.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            etPwd.setSelection(etPwd.getText().toString().trim().length());
          } else {
            etPwd.setInputType(129);
            btnToggle.setBackgroundResource(R.drawable.img_by);
            etPwd.setSelection(etPwd.getText().toString().trim().length());
          }
          break;
        default:
          break;
      }
    }
  };
  private View viewById;

  @Override
  protected int getLayoutId() {
    return R.layout.activity_forget;
  }

  @Override
  protected void newInstancePresenter() {

  }

  @Override
  protected void initView() {
    compositeDisposable = new CompositeDisposable();
    accountPhone = findViewById(R.id.account_phone);
    accountPhone.setInputType(InputType.TYPE_CLASS_NUMBER);
    accountPhone.setKeyListener("0123456789");
    accountPhone.addFilter(new InputFilter.LengthFilter(PHONE_LENGTH));
    btnToggle = findViewById(R.id.btn_toggle);
    etPwd = findViewById(R.id.et_pwd);
    etCode = findViewById(R.id.et_code);
    etCode.setFilters(new InputFilter[] { new InputFilter.LengthFilter(VERITY_CODE_LENGTH) });
    rlToggle = findViewById(R.id.rl_toggle);
    btnCommit = findViewById(R.id.btn_commit);
    tvSendCode = findViewById(R.id.tv_send_code);
    findViewById(R.id.iv_back).setOnClickListener(listener);

    btnCommit.setOnClickListener(listener);
    btnToggle.setOnClickListener(listener);
    rlToggle.setOnClickListener(listener);
    tvSendCode.setOnClickListener(listener);
  }

  @Override
  protected void initData() {

  }

  @Override
  public void showEmptyView() {

  }

  /**
   * 创建用户
   */
  private void commit(String phone, String pwd, String code) {
    showProgress();
    RemoteMode.getInstance()
              .forgetPwd(phone, pwd, code)
              .subscribe(new BaseObserver<BaseEntry>(compositeDisposable) {
                @Override
                public void onError(BaseException e) {
                  hideProgress();
                  showToast(e.getMsg());
                }

                @Override
                public void onSuss(BaseEntry baseEntry) {
                  hideProgress();
                  resetPwdSuss();
                  showToast(baseEntry.getMsg());
                }

                @Override
                public void onComplete() {
                  super.onComplete();
                  hideProgress();
                }
              });
  }

  public void sendCode(String accountPhone) {
    showProgress();
    timer.start();
    RemoteMode.getInstance()
              .sendCode(accountPhone)
              .subscribe(new BaseObserver<BaseEntry>(compositeDisposable) {
                @Override
                public void onError(BaseException e) {
                  hideProgress();
                  showToast(e.getMsg());
                  if (timer != null) {
                    timer.cancel();
                  }
                  updateText("发送验证码", true, R.color.code_blue);
                }

                @Override
                public void onSuss(BaseEntry userBaseEntry) {
                  hideProgress();
                  showToast("验证码已发送至您手机，请注意查收");
                }

                @Override
                public void onComplete() {
                  super.onComplete();
                  hideProgress();
                }
              });
  }

  public void updateText(String s, boolean b, int color) {
    tvSendCode.setText(s);
    tvSendCode.setTextColor(getResources().getColor(color));
    tvSendCode.setClickable(b);
  }

  /**
   * 修改密码成功
   */
  private void resetPwdSuss() {
    finish();
  }

  @Override
  protected void onDestroy() {
    if (timer != null) {
      timer.cancel();
      timer = null;
    }
    if (compositeDisposable != null) {
      compositeDisposable.dispose();
      compositeDisposable = null;
    }
    super.onDestroy();
  }
}
