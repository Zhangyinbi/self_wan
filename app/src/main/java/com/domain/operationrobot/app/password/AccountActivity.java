package com.domain.operationrobot.app.password;

import android.content.Intent;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.domain.library.base.AbsActivity;
import com.domain.library.base.BasePresenter;
import com.domain.library.utils.InputUtils;
import com.domain.library.utils.ToastUtils;
import com.domain.library.utils.bar.LightStatusBarCompat;
import com.domain.library.widgets.DeleteEdit;
import com.domain.operationrobot.R;
import com.domain.operationrobot.app.UserServiceProtocolActivity;
import com.domain.operationrobot.http.bean.User;
import com.domain.operationrobot.listener.ThrottleLastClickListener;

import static com.domain.operationrobot.util.Constant.PHONE_LENGTH;
import static com.domain.operationrobot.util.Constant.VERITY_CODE_LENGTH;

/**
 * Project Name : OperationRobot
 * description:null
 *
 * @author : yinbi.zhang.o
 * Create at : 2018/10/13 00:48
 */
public class AccountActivity extends AbsActivity implements AccountContract.AccountView<BasePresenter> {
    private DeleteEdit accountPhone;
    private Button btnCommit;
    private Button btnToggle;
    private EditText etPwd;
    private EditText etCode;
    private Button btnCreate;
    private RelativeLayout rlToggle;
    private ImageView ivIcon;
    private boolean check;
    private TextView tvUserServiceProtocol;
    private TextView tvSendCode;
    private AccountContract.AccountPresenter presenter;
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
                        presenter.sendCode(phoneCode);
                    } else {
                        ToastUtils.showToast("手机号码格式不正确");
                    }

                    break;
                case R.id.tv_user_service_protocol:
                    startUserServiceProtocol();
                    break;
                case R.id.btn_create:
                    String phone = accountPhone.getValue();
                    if (isEmpty(phone)) {
                        return;
                    }
                    if (InputUtils.isMobileNumber(phone)) {
                        String code = etCode.getText().toString().trim();
                        if (isEmpty(code)) {
                            ToastUtils.showToast("请输入短信验证码");
                            return;
                        } else if (code.length() < 6) {
                            ToastUtils.showToast("请输入正确的的短信验证码");
                            return;
                        }
                        String pwd = etPwd.getText().toString().trim();
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

    /**
     * 前往用户协议页面
     */
    private void startUserServiceProtocol() {
        startActivity(new Intent(this, UserServiceProtocolActivity.class));
    }

    /**
     * 创建用户
     *
     * @param phone
     * @param code
     * @param pwd
     */
    private void commit(String phone, String pwd, String code) {
        presenter.commit(phone, pwd, code);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_account;
    }

    @Override
    protected void newInstancePresenter() {
        presenter = new AccountPresenterImpl(this);
    }

    @Override
    protected void initView() {
        accountPhone = findViewById(R.id.account_phone);
        accountPhone.setInputType(InputType.TYPE_CLASS_NUMBER);
        accountPhone.setKeyListener("0123456789");
        accountPhone.addFilter(new InputFilter.LengthFilter(PHONE_LENGTH));
        btnToggle = findViewById(R.id.btn_toggle);
        btnCommit = findViewById(R.id.btn_create);
        etPwd = findViewById(R.id.et_pwd);
        etCode = findViewById(R.id.et_code);
        etCode.setFilters(new InputFilter[]{new InputFilter.LengthFilter(VERITY_CODE_LENGTH)});
        rlToggle = findViewById(R.id.rl_toggle);
        btnCreate = findViewById(R.id.btn_create);
        tvUserServiceProtocol = findViewById(R.id.tv_user_service_protocol);
        tvSendCode = findViewById(R.id.tv_send_code);
        ivIcon = findViewById(R.id.iv_icon);


        btnCommit.setOnClickListener(listener);
        btnCommit.setClickable(false);
        tvUserServiceProtocol.setOnClickListener(listener);
        btnToggle.setOnClickListener(listener);
        rlToggle.setOnClickListener(listener);
        btnCreate.setOnClickListener(listener);
        tvSendCode.setOnClickListener(listener);
        btnCreate.setClickable(false);
        ivIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (check) {
                    upCheck(false);
                } else {
                    upCheck(true);
                }
            }
        });
        accountPhone.setTextAfterChange(new DeleteEdit.TextAfterChange() {
            @Override
            public void afterTextChanged(Editable editable) {
                if (accountPhone.getValue().length() == PHONE_LENGTH) {
                    if (check) {
                        btnCommit.setBackground(getResources().getDrawable(R.drawable.login_blue_round_click_bg));
                        btnCommit.setClickable(true);
                    }
                } else {
                    btnCommit.setBackground(getResources().getDrawable(R.drawable.login_blue_round_not_click_bg));
                    btnCommit.setClickable(false);
                }
            }
        });
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

    /**
     * 创建用户成功
     *
     * @param user
     */
    @Override
    public void createSuss(User user) {

    }

    /**
     * 更新验证码状态
     *
     * @param s
     * @param b
     * @param color
     */
    @Override
    public void updateText(String s, boolean b, int color) {
        tvSendCode.setText(s);
        tvSendCode.setTextColor(getResources().getColor(color));
        tvSendCode.setClickable(b);
    }

    /**
     * 更新协议状态
     *
     * @param b
     */
    private void upCheck(boolean b) {
        check = b;
        if (check) {
            ivIcon.setImageResource(R.drawable.icon_choose);
            if (accountPhone.getValue().length() == PHONE_LENGTH) {
                btnCommit.setBackground(getResources().getDrawable(R.drawable.login_blue_round_click_bg));
                btnCommit.setClickable(true);
            }
        } else {
            ivIcon.setImageResource(R.drawable.icon_no_choose);
            btnCommit.setBackground(getResources().getDrawable(R.drawable.login_blue_round_not_click_bg));
            btnCommit.setClickable(false);
        }
    }
}
