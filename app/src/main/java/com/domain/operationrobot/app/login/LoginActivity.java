package com.domain.operationrobot.app.login;

import android.content.Intent;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.domain.library.base.AbsActivity;
import com.domain.library.base.BasePresenter;
import com.domain.library.utils.InputUtils;
import com.domain.library.utils.ToastUtils;
import com.domain.library.utils.bar.LightStatusBarCompat;
import com.domain.library.widgets.DeleteEdit;
import com.domain.operationrobot.R;
import com.domain.operationrobot.app.login.LoginContract.LoginView;
import com.domain.operationrobot.app.password.AccountActivity;
import com.domain.operationrobot.listener.ThrottleLastClickListener;

import static com.domain.operationrobot.util.Constant.PHONE_LENGTH;

public class LoginActivity extends AbsActivity implements LoginView<BasePresenter> {

    private DeleteEdit loginPhone;
    private Button btnLogin;
    private TextView tvForget;
    private TextView tvCreateAccount;
    private Button btnToggle;
    private EditText etPwd;
    private LoginContract.LoginPresenter presenter;
    ThrottleLastClickListener listener = new ThrottleLastClickListener() {
        @Override
        public void onViewClick(View v) {
            switch (v.getId()) {
                case R.id.btn_login:
                    String phone = loginPhone.getValue();
                    if (isEmpty(phone)) {
                        return;
                    }
                    if (InputUtils.isMobileNumber(phone)) {
                        String pwd = etPwd.getText().toString().trim();
                        if (isEmpty(pwd)) {
                            ToastUtils.showToast("请输入密码");
                            return;
                        }
                        login(phone, pwd);
                    } else {
                        ToastUtils.showToast("手机号码格式不正确");
                    }
                    break;
                case R.id.tv_forget:
                    //忘记密码
                    ToastUtils.showToast("忘记密码");
                    break;
                case R.id.tv_create_account:
                    //创建账户
                    createAccount();
                    break;
                case R.id.rl_toggle:
                case R.id.btn_toggle:
                    if (etPwd.getInputType() == 129) {
                        btnToggle.setBackgroundResource(R.drawable.img_yj);
                        etPwd.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    } else {
                        etPwd.setInputType(129);
                        btnToggle.setBackgroundResource(R.drawable.img_by);
                    }
                    break;
                default:
                    break;
            }
        }
    };
    private RelativeLayout rlToggle;

    /**
     * 创建用户
     */
    private void createAccount() {
        startActivity(new Intent(this, AccountActivity.class));
    }

    /**
     * 登陆
     *
     * @param phone
     * @param pwd
     */
    private void login(String phone, String pwd) {
        presenter.login(phone, pwd);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void newInstancePresenter() {
        presenter = new LoginPresenterImpl(this);
    }

    @Override
    protected void initView() {
        loginPhone = findViewById(R.id.login_main_phone);
        loginPhone.setInputType(InputType.TYPE_CLASS_NUMBER);
        loginPhone.setKeyListener("0123456789");
        loginPhone.addFilter(new InputFilter.LengthFilter(PHONE_LENGTH));
        btnLogin = findViewById(R.id.btn_login);
        tvForget = findViewById(R.id.tv_forget);
        tvCreateAccount = findViewById(R.id.tv_create_account);
        btnToggle = findViewById(R.id.btn_toggle);
        etPwd = findViewById(R.id.et_pwd);
        rlToggle = findViewById(R.id.rl_toggle);
        loginPhone.setTextAfterChange(new DeleteEdit.TextAfterChange() {
            @Override
            public void afterTextChanged(Editable editable) {
                if (loginPhone.getValue().length() == PHONE_LENGTH) {
                    btnLogin.setBackground(getResources().getDrawable(R.drawable.login_blue_round_click_bg));
                    btnLogin.setClickable(true);
                } else {
                    btnLogin.setBackground(getResources().getDrawable(R.drawable.login_blue_round_not_click_bg));
                    btnLogin.setClickable(false);
                }
            }
        });

        btnLogin.setOnClickListener(listener);
        btnLogin.setClickable(false);
        tvForget.setOnClickListener(listener);
        tvCreateAccount.setOnClickListener(listener);
        btnToggle.setOnClickListener(listener);
        rlToggle.setOnClickListener(listener);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void showEmptyView() {

    }

    @Override
    public void setLightStatusBar() {
        LightStatusBarCompat.setLightStatusBar(getWindow(), true);
    }


    @Override
    public void loginFail(String msg) {

    }

    @Override
    public void LoginSuss() {

    }
}
