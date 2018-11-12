package com.domain.operationrobot.app.setting;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import android.widget.LinearLayout;
import android.widget.TextView;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.domain.library.GlideApp;
import com.domain.library.base.AbsActivity;
import com.domain.library.http.consumer.BaseObserver;
import com.domain.library.http.entry.BaseEntry;
import com.domain.library.http.exception.BaseException;
import com.domain.library.ui.CommonDialog;
import com.domain.library.ui.SureInterface;
import com.domain.library.utils.InputUtils;
import com.domain.library.utils.MyPermissionUtils;
import com.domain.library.utils.SpUtils;
import com.domain.operationrobot.BaseApplication;
import com.domain.operationrobot.R;
import com.domain.operationrobot.app.home.ChatRoomFragment;
import com.domain.operationrobot.app.home.MainActivity;
import com.domain.operationrobot.app.password.VerifyPwdActivity;
import com.domain.operationrobot.glide.CircleImageView;
import com.domain.operationrobot.http.bean.ImageFileBean;
import com.domain.operationrobot.http.bean.User;
import com.domain.operationrobot.http.data.RemoteMode;
import com.domain.operationrobot.listener.ThrottleLastClickListener;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import java.util.List;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;
import static com.domain.library.GlideOptions.bitmapTransform;
import static com.domain.operationrobot.app.password.VerifyPwdActivity.FROM_USER_NAME_MODIFY;
import static com.domain.operationrobot.app.password.VerifyPwdActivity.FROM_USER_PHONE_MODIFY;
import static com.domain.operationrobot.util.Constant.USER_SP_KEY;

public class UserInfoActivity extends AbsActivity {

  private TextView        mTvUserName;
  private CircleImageView mCivUserImg;
  private TextView        mTvMobile;
  private LinearLayout    mLlModifyMobile;
  private TextView        mTv_company_name;
  private LinearLayout    mLl_company;
  ThrottleLastClickListener listener = new ThrottleLastClickListener() {
    @Override
    public void onViewClick(View v) {
      switch (v.getId()) {
        case R.id.iv_back:
          finish();
          break;
        case R.id.ll_modify_user_name:
          modifyUserName();
          break;
        case R.id.ll_modify_mobile:
          modifyMobile();
          break;
        case R.id.btn_out_company:
          new CommonDialog.Builder(UserInfoActivity.this).setContent("确定要退出此公司吗？")
                                                     .setCancelText("取消", null)
                                                     .setSureText("确定", new SureInterface() {
                                                       @Override
                                                       public void onSureClick() {
                                                         outOfCompany();
                                                       }
                                                     })
                                                     .build()
                                                     .show();
          break;
        case R.id.ll_modify_header_image:
          openImage();
          break;
      }
    }
  };

  /**
   * 退出公司
   */
  private void outOfCompany() {
    RemoteMode.getInstance()
              .outOfCompany()
              .subscribe(new BaseObserver<User>(compositeDisposable) {
                @Override
                public void onError(BaseException e) {
                  hideProgress();
                  showToast(e.getMsg());
                }

                @Override
                public void onSuss(User user) {
                  hideProgress();
                  showToast("退出公司成功");
                  User realUser = BaseApplication.getInstance()
                                                 .getUser();
                  realUser.setRole(user.getRole());
                  realUser.setCompanyexpiredate("");
                  realUser.setCompanyrole("");
                  realUser.setCompanyname("");
                  realUser.setCompanyid("");
                  mLl_company.setVisibility(View.GONE);
                  SpUtils.setObject(USER_SP_KEY, realUser);
                }

                @Override
                public void onComplete() {
                  super.onComplete();
                  hideProgress();
                }
              });
  }

  /**
   * 修改手机号
   */
  private void modifyMobile() {
    VerifyPwdActivity.start(this, FROM_USER_PHONE_MODIFY);
  }

  /**
   * 修改用户名
   */
  private void modifyUserName() {
    VerifyPwdActivity.start(this, FROM_USER_NAME_MODIFY);
  }

  /**
   * 开启相册
   */
  private void openImage() {
    MyPermissionUtils.getInstance()
                     .requestPermissions(this, 1133, MyPermissionUtils.STORAGE_PERMISSION, new MyPermissionUtils.OnRequestPermissionListener() {
                       @Override
                       public void onRequest(boolean isGranted, String[] permissions) {
                         if (isGranted) {
                           openImageSelect();
                         }
                       }
                     }, null);
  }

  private void openImageSelect() {
    PictureSelector.create(this)
                   .openGallery(PictureMimeType.ofImage())
                   .maxSelectNum(1)
                   .compress(true)
                   .forResult(PictureConfig.CHOOSE_REQUEST);
  }

  @Override
  protected int getLayoutId() {
    return R.layout.activity_user_info;
  }

  @Override
  protected void newInstancePresenter() {

  }

  @Override
  protected void initView() {
    findViewById(R.id.iv_back).setOnClickListener(listener);
    findViewById(R.id.ll_modify_user_name).setOnClickListener(listener);
    mTvUserName = findViewById(R.id.tv_user_name);
    mCivUserImg = findViewById(R.id.civ_user_img);
    mTvMobile = findViewById(R.id.tv_mobile);
    mLlModifyMobile = findViewById(R.id.ll_modify_mobile);
    mLlModifyMobile.setOnClickListener(listener);
    mTv_company_name = findViewById(R.id.tv_company_name);
    mLl_company = findViewById(R.id.ll_company);
    findViewById(R.id.btn_out_company).setOnClickListener(listener);
    findViewById(R.id.ll_modify_header_image).setOnClickListener(listener);
  }

  @Override
  protected void onResume() {
    super.onResume();
    User user = BaseApplication.getInstance()
                               .getUser();
    String path = user.getImage();
    setHeader(path);

    String userName = user.getUsername();
    mTvUserName.setText(userName);
    mTvMobile.setText(user.getMobile());
    if (TextUtils.isEmpty(user.getCompany())) {
      mLl_company.setVisibility(View.GONE);
    } else {
      mLl_company.setVisibility(View.VISIBLE);
      mTv_company_name.setText(user.getCompany());
    }
  }

  @Override
  protected void initData() {

  }

  @Override
  public void showEmptyView() {

  }

  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (resultCode == RESULT_OK) {
      switch (requestCode) {
        case PictureConfig.CHOOSE_REQUEST:
          // 图片、视频、音频选择结果回调
          List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
          LocalMedia media = null;
          String path = "";
          if (selectList != null) {
            media = selectList.get(0);
            path = media.getPath();
            if (media.isCompressed()) {
              path = media.getCompressPath();
            }
          }
          if (!TextUtils.isEmpty(path)) {
            upLoadImage(path);
          }

          // 例如 LocalMedia 里面返回三种path
          // 1.media.getPath(); 为原图path
          // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true  注意：音视频除外
          // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true  注意：音视频除外
          // 如果裁剪并压缩了，以取压缩路径为准，因为是先裁剪后压缩的
      }
    }
  }

  /**
   * 上传头像
   */
  private void upLoadImage(String path) {
    RemoteMode.getInstance()
              .upLoadImage(path,0)
              .subscribe(new BaseObserver<ImageFileBean>(compositeDisposable) {
                @Override
                public void onError(BaseException e) {
                  hideProgress();
                  showToast(e.getMsg());
                }

                @Override
                public void onSuss(ImageFileBean imageFileBean) {
                  BaseApplication.getInstance()
                                 .getUser()
                                 .setImage(imageFileBean.getImageUrl());
                  setHeader(imageFileBean.getImageUrl());
                  SpUtils.setObject(USER_SP_KEY, BaseApplication.getInstance()
                                                                .getUser());
                }

                @Override
                public void onComplete() {
                  super.onComplete();
                  hideProgress();
                }
              });
  }

  private void setHeader(String path) {
    GlideApp.with(this)
            .load(BaseApplication.getInstance()
                                 .getUser()
                                 .getImage())
            .placeholder(R.drawable.round_88)//图片加载出来前，显示的图片
            .error(R.drawable.round_88)//图片加载失败后，显示的图片
            .transition(withCrossFade())
            .apply(bitmapTransform(new CircleCrop()))
            .into(mCivUserImg);
  }
}
