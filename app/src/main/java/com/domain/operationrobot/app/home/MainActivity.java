package com.domain.operationrobot.app.home;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.domain.library.base.AbsActivity;
import com.domain.library.utils.App;
import com.domain.library.utils.SpUtils;
import com.domain.library.utils.ToastUtils;
import com.domain.operationrobot.BaseApplication;
import com.domain.operationrobot.GlideApp;
import com.domain.operationrobot.MyAppGlideModule;
import com.domain.operationrobot.R;
import com.domain.operationrobot.app.company.ApplyActivity;
import com.domain.operationrobot.app.company.UserApplyActivity;
import com.domain.operationrobot.app.login.LoginActivity;
import com.domain.operationrobot.app.operation.OperationActivity;
import com.domain.operationrobot.app.password.ModifyPwdActivity;
import com.domain.operationrobot.http.bean.User;
import com.domain.operationrobot.im.chatroom.MainChatRoom;
import com.domain.operationrobot.listener.ThrottleLastClickListener;
import java.util.ArrayList;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;
import static com.domain.operationrobot.GlideOptions.bitmapTransform;
import static com.domain.operationrobot.app.operation.OperationActivity.ADD_OPERATION;
import static com.domain.operationrobot.util.Constant.IS_LOGIN;

public class MainActivity extends AbsActivity {
  private static final String TAG = "------------";
  private Fragment mCurrentFragment;
  private ArrayList<Fragment> mFragments     = new ArrayList<>();
  private boolean[]           mFragmentAdded = new boolean[] { false, false };
  private TextView       mTv_fragment1;
  private TextView       mTv_fragment2;
  private DrawerLayout   drawer;
  private RelativeLayout rl_drawer;
  private ImageView      iv_user_header;
  private TextView       mTvUserName;
  private TextView       tv_top;
  private TextView       tv_company_name;
  private TextView       tv_app_version;
  private User           mUser;
  private LinearLayout   ll_sq;
  private LinearLayout   ll_yunwei;
  private LinearLayout   ll_forget;
  private boolean        again;
  private long           time;
  private int            mRole;
  ThrottleLastClickListener listener = new ThrottleLastClickListener() {
    @Override
    public void onViewClick(View v) {
      switch (v.getId()) {
        case R.id.tv_fragment1:
          seclectFragment();
          break;
        case R.id.ll_sq:
          openOrCloseDrawer();
          drawer.postDelayed(new Runnable() {
            @Override
            public void run() {
              startActivity(new Intent(MainActivity.this, UserApplyActivity.class));
            }
          }, 300);
          break;
        case R.id.ll_yunwei:
          openOrCloseDrawer();
          drawer.postDelayed(new Runnable() {
            @Override
            public void run() {
              OperationActivity.start(MainActivity.this, ADD_OPERATION, null);
            }
          }, 300);
          break;
        case R.id.ll_forget:
          openOrCloseDrawer();
          drawer.postDelayed(new Runnable() {
            @Override
            public void run() {
              startActivity(new Intent(MainActivity.this, ModifyPwdActivity.class));
            }
          }, 300);

          break;
        case R.id.rl_drawer:
          openOrCloseDrawer();
          break;
        case R.id.tv_top:
          if (drawer.isDrawerOpen(GravityCompat.START)) {
            return;
          }
          if (mRole == 1) {
            startActivity(new Intent(MainActivity.this, ApplyActivity.class));
          } else if (mRole == 2) {

          } else if (mRole == 3) {
            new ApplyDialog(MainActivity.this).show();
          } else if (mRole == 4) {
            new DelayDialog(MainActivity.this).show();
          }
          break;
      }
    }
  };

  private void openOrCloseDrawer() {
    if (drawer.isDrawerOpen(GravityCompat.START)) {
      drawer.closeDrawer(GravityCompat.START);
    } else {
      drawer.openDrawer(GravityCompat.START);
    }
  }

  private void seclectFragment() {
    if (mTv_fragment1.getText()
                     .equals("服务器监控")) {
      mTv_fragment1.setText("首页");
      mTv_fragment2.setText("服务器监控");
      showContent(1);
    } else {
      mTv_fragment2.setText("首页");
      mTv_fragment1.setText("服务器监控");
      showContent(0);
    }
  }

  @Override
  protected int getLayoutId() {
    return R.layout.activity_main;
  }

  @Override
  protected void newInstancePresenter() {
    MainChatRoom.init();
  }

  @Override
  protected void initView() {
    initFragments();
    mTv_fragment1 = findViewById(R.id.tv_fragment1);
    mTv_fragment2 = findViewById(R.id.tv_fragment2);
    mTv_fragment1.setOnClickListener(listener);
    mTv_fragment2.setOnClickListener(listener);

    mTvUserName = findViewById(R.id.tv_user_name);
    tv_company_name = findViewById(R.id.tv_company_name);
    tv_app_version = findViewById(R.id.tv_app_version);
    drawer = findViewById(R.id.main_layout);
    rl_drawer = findViewById(R.id.rl_drawer);
    tv_top = findViewById(R.id.tv_top);
    rl_drawer.setOnClickListener(listener);
    iv_user_header = findViewById(R.id.iv_user_header);
    ll_sq = findViewById(R.id.ll_sq);
    ll_yunwei = findViewById(R.id.ll_yunwei);
    ll_forget = findViewById(R.id.ll_forget);
    ll_forget.setOnClickListener(listener);
    ll_yunwei.setOnClickListener(listener);
    ll_sq.setOnClickListener(listener);
    showContent(0);
  }

  private void showContent(int position) {
    try {
      if (position >= mFragments.size()) {
        return;
      }

      Fragment targetFragment = mFragments.get(position);
      if (targetFragment == mCurrentFragment) {
        return;
      }

      FragmentManager fm = getSupportFragmentManager();
      FragmentTransaction transaction = fm.beginTransaction();
      if (mCurrentFragment != null) {
        transaction.hide(mCurrentFragment);
      }

      if (!targetFragment.isAdded() && !mFragmentAdded[position]) {
        transaction.add(R.id.fl_content, targetFragment)
                   .show(targetFragment);
        mFragmentAdded[position] = true;
      } else {
        transaction.show(targetFragment);
      }
      transaction.commit();
      mCurrentFragment = targetFragment;
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void initFragments() {
    mFragments.add(ChatRoomFragment.newInstance());
    mFragments.add(ServerMonitorFragment.newInstance());
  }

  @Override
  protected void initData() {
    findViewById(R.id.btn_out).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        SpUtils.putBoolean(IS_LOGIN, false);
        startActivity(new Intent(MainActivity.this, LoginActivity.class));
        finish();
      }
    });
    mUser = BaseApplication.getInstance()
                           .getUser();
    mRole = mUser.getRole();
    switch (mRole) {
      case 1://游客
        tv_top.setText("加入/创建公司，享受一站式运维 >");
        break;
      case 2://申请待同意用户
        tv_top.setText("已申请公司，请等待公司管理员审核");
        break;
      case 3://普通公司用户
        tv_top.setText("升级成为正式用户，请等待公司管理员审核 >");
        break;
      case 4://公司管理员
        tv_top.setText("还有18天就要过期了，请续费 >");
        break;
    }
    tv_top.setOnClickListener(listener);
    mTvUserName.setText(mUser.getUsername());
    if (!TextUtils.isEmpty(mUser.getCompany())) {
      tv_company_name.setVisibility(View.VISIBLE);
      tv_company_name.setText(mUser.getCompany());
    }
    tv_app_version.setText("版本：V" + App.getVersionName(this));
    GlideApp.with(this)
            .load(BaseApplication.getInstance()
                                 .getUser()
                                 .getImage())
            .placeholder(R.drawable.round_88)//图片加载出来前，显示的图片
            .error(R.drawable.round_88)//图片加载失败后，显示的图片
            .transition(withCrossFade())
            .apply(bitmapTransform(new CircleCrop()))
            .into(iv_user_header);
  }

  @Override
  public void showEmptyView() {

  }

  @Override
  public void onBackPressed() {
    if (drawer.isDrawerOpen(GravityCompat.START)) {
      drawer.closeDrawer(GravityCompat.START);
    } else {
      if (!again) {
        ToastUtils.showToast("再次点击退出");
        again = true;
        drawer.postDelayed(new Runnable() {
          @Override
          public void run() {
            again = false;
          }
        }, 2000);
      } else {
        super.onBackPressed();
      }
    }
  }
}
