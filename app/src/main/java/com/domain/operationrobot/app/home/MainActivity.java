package com.domain.operationrobot.app.home;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.domain.library.GlideApp;
import com.domain.library.base.AbsActivity;
import com.domain.library.base.BasePresenter;
import com.domain.library.http.consumer.BaseObserver;
import com.domain.library.http.exception.BaseException;
import com.domain.library.ui.CommonDialog;
import com.domain.library.ui.SureInterface;
import com.domain.library.utils.App;
import com.domain.library.utils.SpUtils;
import com.domain.operationrobot.BaseApplication;
import com.domain.operationrobot.R;
import com.domain.operationrobot.app.company.ApplyActivity;
import com.domain.operationrobot.app.company.UserApplyActivity;
import com.domain.operationrobot.app.home.server.ServerMonitorActivity;
import com.domain.operationrobot.app.home.server.ServerMonitorFragment;
import com.domain.operationrobot.app.login.ChoiceCompanyDialog;
import com.domain.operationrobot.app.login.LoginActivity;
import com.domain.operationrobot.app.login.LoginContract;
import com.domain.operationrobot.app.login.LoginPresenterImpl;
import com.domain.operationrobot.app.operation.OperationManagerActivity;
import com.domain.operationrobot.app.password.ModifyPwdActivity;
import com.domain.operationrobot.app.setting.UserInfoActivity;
import com.domain.operationrobot.http.bean.ServerMobile;
import com.domain.operationrobot.http.bean.SideInfo;
import com.domain.operationrobot.http.bean.User;
import com.domain.operationrobot.http.data.RemoteMode;
import com.domain.operationrobot.im.chatroom.MainChatRoom;
import com.domain.operationrobot.listener.ThrottleLastClickListener;
import com.domain.operationrobot.util.TimeUtil;
import com.xiaomi.mipush.sdk.MiPushClient;
import java.math.BigDecimal;
import java.util.ArrayList;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;
import static com.domain.library.GlideOptions.bitmapTransform;
import static com.domain.operationrobot.util.Constant.IS_LOGIN;
import static com.domain.operationrobot.util.Constant.SERVER_MOBILE;
import static com.domain.operationrobot.util.Constant.SERVER_PHONE;
import static com.domain.operationrobot.util.Constant.USER_SP_KEY;

public class MainActivity extends AbsActivity implements LoginContract.LoginView<BasePresenter> {
  private static final String TAG = "------------";
  private Fragment mCurrentFragment;
  private ArrayList<Fragment> mFragments = new ArrayList<>();
  private boolean[] mFragmentAdded = new boolean[] {false, false};
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
          if (mRole == 1 || mRole == 3) {//游客
            startActivity(new Intent(MainActivity.this, ApplyActivity.class));
          } else if (mRole == 2) {
            //new CommonDialog.Builder(MainActivity.this).setContent("取消申请").setCancelText("取消").setSureText("确定", new SureInterface() {
            //  @Override
            //  public void onSureClick() {
            //    Log.e(TAG, "onSureClick: " );
            //  }
            //}).build().show();
          } else if (mRole == 0) {

            if ("1".equals(mUser.getCompanyrole())) {
              new ApplyDialog(MainActivity.this).show();
            } else if ("2".equals(mUser.getCompanyrole())) {
              BigDecimal time = new BigDecimal(mUser.getCompanyexpiredate()).subtract(new BigDecimal(System.currentTimeMillis() / 1000));
              new DelayDialog(MainActivity.this, time).show();
            }
            //if (TextUtils.isEmpty(mUser.getCompanyexpiredate())) {
            //  new ApplyDialog(MainActivity.this).show();
            //} else {
            //  try {
            //    BigDecimal time = new BigDecimal(mUser.getCompanyexpiredate()).subtract(new BigDecimal(System.currentTimeMillis() / 1000));
            //    new DelayDialog(MainActivity.this, time).show();
            //  } catch (NumberFormatException n) {
            //    Log.e(TAG, "onViewClick: 服务器返回的过期时间不是时间戳--》" + mUser.getCompanyexpiredate());
            //  }
            //}
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
  private ChoiceCompanyDialog mChoiceCompanyDialog;
  private SideInfo            mSideInfo;
  private TextView            mTv_time;
  private ImageView           mIv_red_yuan;

  public ArrayList<Fragment> getFragments() {
    return mFragments;
  }

  public Fragment getCurrentFragment() {
    return mCurrentFragment;
  }

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
    MainChatRoom.getInstance()
                .initAppSocket();
    BaseApplication.getInstance()
                   .initMiPush();
  }

  @Override
  protected void initView() {
    initFragments();
    mTv_fragment1 = findViewById(R.id.tv_fragment1);
    mTv_fragment2 = findViewById(R.id.tv_fragment2);
    mTv_fragment1.setOnClickListener(listener);
    mTv_fragment2.setOnClickListener(listener);
    mIv_red_yuan = findViewById(R.id.iv_red_yuan);
    mTv_time = findViewById(R.id.tv_time);
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
    findViewById(R.id.left_drawer).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {

      }
    });
    showContent(0);
    checkDefaultCompany();
    getSide();
    getServerMobile();
  }

  /**
   * 获取客服电话
   */
  private void getServerMobile() {
    RemoteMode.getInstance()
              .getServerMobile()
              .subscribe(new BaseObserver<ServerMobile>(compositeDisposable) {

                @Override
                public void onError(BaseException e) {

                  hideProgress();
                  showToast(e.getMsg());
                }

                @Override
                public void onSuss(ServerMobile user) {
                  SpUtils.putString(SERVER_MOBILE, user.getCustommermobile());
                  SERVER_PHONE = user.getCustommermobile();
                  hideProgress();
                }

                @Override
                public void onComplete() {
                  hideProgress();
                }
              });
  }

  private void checkDefaultCompany() {
    RemoteMode.getInstance()
              .checkDefaultCompany()
              .subscribe(new BaseObserver<User>(compositeDisposable) {

                @Override
                public void onError(BaseException e) {

                  hideProgress();
                  showToast(e.getMsg());
                }

                @Override
                public void onSuss(User user) {

                  hideProgress();
                  if (null != user.getChoice() && !user.getChoice()
                                                       .getStatus() && user.getChoice()
                                                                           .getCompanyinfo() != null && user.getChoice()
                                                                                                            .getCompanyinfo()
                                                                                                            .size() > 0) {
                    LoginPresenterImpl loginPresenter = new LoginPresenterImpl(MainActivity.this);
                    mChoiceCompanyDialog = new ChoiceCompanyDialog(MainActivity.this, user.getChoice()
                                                                                          .getCompanyinfo(), loginPresenter, user.getToken());
                    loginPresenter.setChoiceCompanyDialog(mChoiceCompanyDialog);
                    mChoiceCompanyDialog.setCancelable(false);
                    mChoiceCompanyDialog.show();
                  }
                }

                @Override
                public void onComplete() {
                  hideProgress();
                }
              });
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
        new CommonDialog.Builder(MainActivity.this).setContent("确定要退出吗？")
                                                   .setCancelText("取消", null)
                                                   .setSureText("确定", new SureInterface() {
                                                     @Override
                                                     public void onSureClick() {
                                                       outLogin();
                                                     }
                                                   })
                                                   .build()
                                                   .show();
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

  private void outLogin() {
    ChatRoomFragment fragment = (ChatRoomFragment) getSupportFragmentManager().getFragments()
                                                                              .get(0);
    SpUtils.removeData(IS_LOGIN);
    SpUtils.removeData(USER_SP_KEY);
    startActivity(new Intent(MainActivity.this, LoginActivity.class));
    MainChatRoom.getInstance()
                .outSocket();
    MiPushClient.unsetUserAccount(MainActivity.this,BaseApplication.getInstance().getUser().getUserId(),"");
    finish();
  }

  @Override
  public void showEmptyView() {

  }

  @Override
  public void onBackPressed() {
    if (drawer.isDrawerOpen(GravityCompat.START)) {
      drawer.closeDrawer(GravityCompat.START);
    } else {
      Intent intent = new Intent(Intent.ACTION_MAIN);
      intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
      intent.addCategory(Intent.CATEGORY_HOME);
      startActivity(intent);
      //if (!again) {
      //  ToastUtils.showToast("再次点击退出");
      //  again = true;
      //  drawer.postDelayed(new Runnable() {
      //    @Override
      //    public void run() {
      //      again = false;
      //    }
      //  }, 2000);
      //} else {
      //  //super.onBackPressed();
      //
      //}
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
    switch (mRole) {
      case 1://游客
        tv_top.setText("加入/创建公司，享受一站式运维 >");
        tv_company_name.setText("您还未加入公司,点击加入/创建");

        //mTv_fragment1.setVisibility(View.GONE);
        break;
      case 2://申请待同意用户
        tv_top.setText("已申请公司，请等待公司管理员审核");
        tv_company_name.setText("(待审核)" + mUser.getCompany());

        //mTv_fragment1.setVisibility(View.GONE);
        break;
      case 0://普通公司用户
        //已加入公司试用中用户（管理员）
        if ((mUser.getOprole() == 4 && "1".equals(mUser.getCompanyrole())) || (mUser.getOprole() != 4 && "1".equals(mUser.getCompanyrole()))) {
          tv_top.setText("升级成为正式用户，享受一站式运维 >");
        } else if ((mUser.getOprole() == 4 && "2".equals(mUser.getCompanyrole())) || (mUser.getOprole() != 4 && "2".equals(mUser.getCompanyrole()))) {
          try {
            BigDecimal time = new BigDecimal(mUser.getCompanyexpiredate()).subtract(new BigDecimal(System.currentTimeMillis() / 1000));
            BigDecimal timeDay = TimeUtil.getTimeDay(time);
            if (timeDay.compareTo(new BigDecimal("0")) == 1) {
              tv_top.setText("还有" + timeDay + "天就要过期了，请续费 >");
            }else {
              tv_top.setVisibility(View.GONE);
            }
          } catch (Exception e) {
            tv_top.setVisibility(View.GONE);
          }
        }
        tv_company_name.setText(mUser.getCompany());

        //if (TextUtils.isEmpty(mUser.getCompanyexpiredate())) {
        //  tv_top.setText("升级成为正式用户，享受一站式运维 >");
        //} else {
        //  BigDecimal time = new BigDecimal(mUser.getCompanyexpiredate()).subtract(new BigDecimal(System.currentTimeMillis() / 1000));
        //  tv_top.setText("还有" + TimeUtil.getTimeDay(time) + "天就要过期了，请续费 >");
        //}
        mTv_fragment1.setVisibility(View.VISIBLE);
        break;
      case 3://被拒绝
        tv_top.setText("加入/创建公司，享受一站式运维");
        tv_company_name.setText("您还未加入公司,点击加入/创建");
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
                  User user = BaseApplication.getInstance()
                                             .getUser();
                  user.setRole(sideInfo.getRole());
                  BaseApplication.getInstance()
                                 .setUser(user);
                  mUser = user;
                  SpUtils.setObject(USER_SP_KEY, BaseApplication.getInstance()
                                                                .getUser());
                  setSideData();
                }
              });
  }

  private void setSideData() {
    User user = BaseApplication.getInstance()
                               .getUser();

    if (TextUtils.isEmpty(user.getCompanyexpiredate())) {
      mTv_time.setVisibility(View.GONE);
    } else {
      try {
        mTv_time.setVisibility(View.VISIBLE);
        Log.e(TAG, "setSideData: "+ mUser.getCompanyexpiredate());
        BigDecimal time = new BigDecimal(mUser.getCompanyexpiredate()).subtract(new BigDecimal(System.currentTimeMillis() / 1000));
        BigDecimal timeDay = TimeUtil.getTimeDay(time);
        if (timeDay.compareTo(new BigDecimal("0")) == 1) {
          mTv_time.setText("将于" + TimeUtil.strToDate(user.getCompanyexpiredate()) + "到期");
        }else {
          mTv_time.setText("已过期，请续费");
        }
      }catch (Exception e){
        mTv_time.setVisibility(View.GONE);
      }



    }
    if (mSideInfo != null) {
      user.setUsername(mSideInfo.getUsername());
      if (mSideInfo.getRole() == 1) {
        //游客
        tv_user_role.setText("游客");
      } else if (mSideInfo.getRole() == 3) {
        tv_user_role.setText("申请未通过");
      } else {
        tv_user_role.setText(getRoleName(mSideInfo.getOprole()));
      }
      tv_user_indication.setVisibility(View.VISIBLE);
      if ("1".equals(mSideInfo.getCompanyrole())) {
        tv_user_indication.setText("试用中公司");
      } else if ("2".equals(mSideInfo.getCompanyrole())){
        tv_user_indication.setText("正式公司");
      }else {
        tv_user_indication.setVisibility(View.GONE);
      }
      setSideUI(mSideInfo.getRole());
      if (TextUtils.isEmpty(mSideInfo.getCompanyname())) {
      } else {
        user.setCompanyname(mSideInfo.getCompanyname());
        user.setCompanyid(mSideInfo.getCompanyid());
        user.setCompanyrole(mSideInfo.getCompanyrole());
        if (!TextUtils.isEmpty(mSideInfo.getCompanyexpiredate())) {
          user.setCompanyexpiredate(mSideInfo.getCompanyexpiredate());
        }
      }
      tv_company_name.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
          if (mSideInfo.getRole() == 1 || mSideInfo.getRole() == 3) {
            openOrCloseDrawer();
            drawer.postDelayed(new Runnable() {
              @Override
              public void run() {
                startActivity(new Intent(MainActivity.this, ApplyActivity.class));
              }
            }, 300);
          }
        }
      });
      if (mSideInfo.getJoinlist() > 0) {
        tv_join_num.setVisibility(View.VISIBLE);
        tv_join_num.setText(mSideInfo.getJoinlist() + "");
        mIv_red_yuan.setVisibility(View.VISIBLE);
      } else {
        tv_join_num.setVisibility(View.GONE);
        mIv_red_yuan.setVisibility(View.GONE);
      }
      if (mSideInfo.getMemberlist() > 0) {
        tv_admin_num.setVisibility(View.VISIBLE);
        tv_admin_num.setText("(" + mSideInfo.getMemberlist() + ")");
      } else {
        tv_admin_num.setVisibility(View.GONE);
      }
      BaseApplication.getInstance()
                     .setUser(user);
      mUser = user;
      SpUtils.setObject(USER_SP_KEY, BaseApplication.getInstance()
                                                    .getUser());
      return;
    }
    setSideUIUseUserInfo(user.getRole());

    tv_join_num.setVisibility(View.GONE);
    tv_admin_num.setVisibility(View.GONE);
    tv_user_role.setText(getRoleName(mUser.getOprole()));
  }

  private void setSideUIUseUserInfo(int role) {
    if (role == 3) {
      tv_user_indication.setVisibility(View.GONE);
      tv_company_name.setText("您还未加入公司,点击加入/创建");
      tv_top.setText("加入/创建公司，享受一站式运维");
      ll_yunwei.setVisibility(View.GONE);
      ll_sq.setVisibility(View.GONE);
    } else if (role == 2) {
      tv_user_indication.setVisibility(View.GONE);
      tv_company_name.setText("(待审核)" + mUser.getCompanyname());
      ll_yunwei.setVisibility(View.GONE);
      ll_sq.setVisibility(View.GONE);
    } else if (role == 0) {
      ll_yunwei.setVisibility(View.VISIBLE);
      ll_sq.setVisibility(View.VISIBLE);
      tv_company_name.setText(mUser.getCompanyname());
    } else {
      ll_yunwei.setVisibility(View.GONE);
      ll_sq.setVisibility(View.GONE);
      tv_company_name.setText("您还未加入公司,点击加入/创建");
      tv_top.setText("加入/创建公司，享受一站式运维");
    }
    tv_company_name.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        if (mUser.getRole() == 1 || mUser.getRole() == 3) {
          openOrCloseDrawer();
          drawer.postDelayed(new Runnable() {
            @Override
            public void run() {
              startActivity(new Intent(MainActivity.this, ApplyActivity.class));
            }
          }, 300);
        }
      }
    });
    if (mUser.getOprole() != 4) {
      ll_sq.setVisibility(View.GONE);
    }
  }

  private void setSideUI(int role) {
    if (role == 3) {
      tv_user_indication.setVisibility(View.GONE);
      tv_company_name.setText("您还未加入公司,点击加入/创建");
      tv_top.setText("加入/创建公司，享受一站式运维");
      ll_yunwei.setVisibility(View.GONE);
      ll_sq.setVisibility(View.GONE);
    } else if (role == 2) {
      tv_user_indication.setVisibility(View.GONE);
      tv_company_name.setText("(待审核)" + mSideInfo.getCompanyname());
      ll_yunwei.setVisibility(View.GONE);
      ll_sq.setVisibility(View.GONE);
    } else if (role == 0) {
      ll_yunwei.setVisibility(View.VISIBLE);
      ll_sq.setVisibility(View.VISIBLE);
      tv_company_name.setText(mSideInfo.getCompanyname());
    } else {
      ll_yunwei.setVisibility(View.GONE);
      ll_sq.setVisibility(View.GONE);
      tv_company_name.setText("您还未加入公司,点击加入/创建");
      tv_top.setText("加入/创建公司，享受一站式运维");
    }
    tv_company_name.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        if (mSideInfo.getRole() == 1 || mSideInfo.getRole() == 3) {
          openOrCloseDrawer();
          drawer.postDelayed(new Runnable() {
            @Override
            public void run() {
              startActivity(new Intent(MainActivity.this, ApplyActivity.class));
            }
          }, 300);
        }
      }
    });
    if (mUser.getOprole() != 4 || mSideInfo.getOprole() != 4) {
      ll_sq.setVisibility(View.GONE);
    }
  }

  private String getRoleName(int oprRole) {
    String userRoleName = "游客";
//（2为申请待同意用户，3为普通用户，4为管理员，5为机器人，6为审核员）
    switch (oprRole) {
      case 1:

        break;
      case 2:
        userRoleName = "申请待同意";
        break;
      case 3:
        userRoleName = "运维人员";
        break;
      case 4:
        userRoleName = "管理员,审核员";
        break;
      case 5:
        userRoleName = "机器人";
        break;
      case 6:
        userRoleName = "审核员";
        break;
    }
    return userRoleName;
  }

  @Override
  public void loginFail(String msg) {

  }

  @Override
  public void LoginSuss() {

  }
}
