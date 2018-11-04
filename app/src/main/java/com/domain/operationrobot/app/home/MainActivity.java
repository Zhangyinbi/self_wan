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
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.domain.library.GlideApp;
import com.domain.library.base.AbsActivity;
import com.domain.library.http.consumer.BaseObserver;
import com.domain.library.http.exception.BaseException;
import com.domain.library.utils.App;
import com.domain.library.utils.SpUtils;
import com.domain.library.utils.ToastUtils;
import com.domain.operationrobot.BaseApplication;
import com.domain.operationrobot.R;
import com.domain.operationrobot.app.company.ApplyActivity;
import com.domain.operationrobot.app.company.UserApplyActivity;
import com.domain.operationrobot.app.login.LoginActivity;
import com.domain.operationrobot.app.operation.OperationActivity;
import com.domain.operationrobot.app.operation.OperationManagerActivity;
import com.domain.operationrobot.app.password.ModifyPwdActivity;
import com.domain.operationrobot.app.setting.UserInfoActivity;
import com.domain.operationrobot.http.bean.SideInfo;
import com.domain.operationrobot.http.bean.User;
import com.domain.operationrobot.http.data.RemoteMode;
import com.domain.operationrobot.im.chatroom.MainChatRoom;
import com.domain.operationrobot.listener.ThrottleLastClickListener;
import java.util.ArrayList;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;
import static com.domain.library.GlideOptions.bitmapTransform;
import static com.domain.operationrobot.app.operation.OperationActivity.ADD_OPERATION;
import static com.domain.operationrobot.util.Constant.IS_LOGIN;
import static com.domain.operationrobot.util.Constant.USER_SP_KEY;

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
  private TextView       tv_user_role;
  private TextView       tv_app_version;
  private TextView       tv_join_num;
  private TextView       tv_admin_num;
  private TextView       tv_user_indication;
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
              OperationManagerActivity.start(MainActivity.this);
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
        case R.id.iv_user_header:
          openOrCloseDrawer();
          drawer.postDelayed(new Runnable() {
            @Override
            public void run() {
              startActivity(new Intent(MainActivity.this, UserInfoActivity.class));
            }
          }, 300);
          break;
      }
    }
  };
  private SideInfo mSideInfo;

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
    tv_user_role = findViewById(R.id.tv_user_role);
    tv_join_num = findViewById(R.id.tv_join_num);
    tv_admin_num = findViewById(R.id.tv_admin_num);
    tv_app_version = findViewById(R.id.tv_app_version);
    tv_user_indication = findViewById(R.id.tv_user_indication);
    drawer = findViewById(R.id.main_layout);
    rl_drawer = findViewById(R.id.rl_drawer);
    tv_top = findViewById(R.id.tv_top);
    rl_drawer.setOnClickListener(listener);
    iv_user_header = findViewById(R.id.iv_user_header);
    iv_user_header.setOnClickListener(listener);
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
        SpUtils.removeData(USER_SP_KEY);
        startActivity(new Intent(MainActivity.this, LoginActivity.class));
        finish();
      }
    });
    tv_top.setOnClickListener(listener);

    tv_app_version.setText("版本：V" + App.getVersionName(MainActivity.this));
    drawer.addDrawerListener(new DrawerLayout.DrawerListener() {
      @Override
      public void onDrawerSlide(View drawerView, float slideOffset) {
      }

      @Override
      public void onDrawerOpened(View drawerView) {
        setSideData();
        getSide();
      }

      @Override
      public void onDrawerClosed(View drawerView) {
      }

      @Override
      public void onDrawerStateChanged(int newState) {
      }
    });
    getSide();
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

  @Override
  protected void onResume() {
    super.onResume();
    mUser = BaseApplication.getInstance()
                           .getUser();
    mRole = mUser.getRole();
    mTvUserName.setText(mUser.getUsername());
    GlideApp.with(MainActivity.this)
            .load(BaseApplication.getInstance()
                                 .getUser()
                                 .getImage())
            .placeholder(R.drawable.round_88)//图片加载出来前，显示的图片
            .error(R.drawable.round_88)//图片加载失败后，显示的图片
            .transition(withCrossFade())
            .apply(bitmapTransform(new CircleCrop()))
            .into(iv_user_header);
    tv_company_name.setText(TextUtils.isEmpty(mUser.getCompany()) ? "你还未加入公司，点击加入/创建" : mUser.getCompany());
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
  }

  private void getSide() {
    RemoteMode.getInstance()
              .getSide()
              .subscribe(new BaseObserver<SideInfo>(compositeDisposable) {
                @Override
                public void onError(BaseException e) {
                  hideProgress();
                }

                @Override
                public void onSuss(SideInfo sideInfo) {
                  mSideInfo = sideInfo;
                  setSideData();
                }
              });
  }

  private void setSideData() {
    if (mSideInfo != null) {
      tv_user_role.setText(getRoleName(mSideInfo.getRole()));
      tv_user_indication.setText(mSideInfo.getCompanyrole() == 1 ? "试用中" : "正式用户");
      tv_company_name.setText(TextUtils.isEmpty(mSideInfo.getCompanyname()) ? "你还未加入公司，点击加入/创建" : mUser.getCompany());
      if (mSideInfo.getJoinlist() > 0) {
        tv_join_num.setVisibility(View.VISIBLE);
        tv_join_num.setText(mSideInfo.getJoinlist() + "");
      }
      if (mSideInfo.getMemberlist() > 0) {
        tv_admin_num.setVisibility(View.VISIBLE);
        tv_admin_num.setText("(" + mSideInfo.getMemberlist() + ")");
      }
      return;
    }
    tv_join_num.setVisibility(View.GONE);
    tv_admin_num.setVisibility(View.GONE);
    tv_user_role.setText(getRoleName(mUser.getRole()));
    tv_company_name.setText(TextUtils.isEmpty(mUser.getCompany()) ? "你还未加入公司，点击加入/创建" : mUser.getCompany());
    tv_company_name.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        if (TextUtils.isEmpty(mUser.getCompany())) {
          startActivity(new Intent(MainActivity.this, ApplyActivity.class));
        }
      }
    });
  }

  private String getRoleName(int role) {
    String userRoleName = "游客";
    switch (role) {
      case 1:
        userRoleName = "游客";
        break;
      case 2:
        userRoleName = "待同意";
        break;
      case 3:
        userRoleName = "普通用户";
        break;
      case 4:
        userRoleName = "管理员/审核员";
        break;
    }
    return userRoleName;
  }
}
