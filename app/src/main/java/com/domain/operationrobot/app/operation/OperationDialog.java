package com.domain.operationrobot.app.operation;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatDialog;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.domain.library.GlideApp;
import com.domain.library.http.consumer.BaseObserver;
import com.domain.library.http.entry.BaseEntry;
import com.domain.library.http.exception.BaseException;
import com.domain.library.ui.CommonDialog;
import com.domain.library.ui.SureInterface;
import com.domain.library.utils.ToastUtils;
import com.domain.operationrobot.R;
import com.domain.operationrobot.http.bean.OperationList;
import com.domain.operationrobot.http.data.RemoteMode;
import io.reactivex.disposables.CompositeDisposable;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;
import static com.domain.library.GlideOptions.bitmapTransform;
import static com.domain.library.utils.ToastUtils.showToast;

/**
 * Project Name : OperationRobot
 * description:null
 *
 * @author : yinbi.zhang.o
 * Create at : 2018/11/4 12:42
 */
public class OperationDialog extends AppCompatDialog {

  private OperationList.OperationInfo      operationInfo;
  private ImageView                        mCiv_user_img;
  private TextView                         mTv_admin_name;
  private Button                           mBtn_update_status;
  private Button                           mBtn_shy_status;
  private Button                           mBtn_cancel;
  private Button                           mBtn_sure;
  private Context                          mContext;
  private int                              role;
  private OperationManagerActivity.IUpdate update;

  public OperationDialog(@NonNull Context context, OperationList.OperationInfo operationInfo, OperationManagerActivity.IUpdate update) {
    super(context, R.style.ROBOT_Dialog);
    mContext = context;
    this.operationInfo = operationInfo;
    role = operationInfo.getOprole();
    this.update = update;
    init();
  }

  private void init() {
    setContentView(R.layout.operation_dialog);
    initView();
    getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
    setCanceledOnTouchOutside(true);

    GlideApp.with(mContext)
            .load(operationInfo.getImageUrl())
            .placeholder(R.drawable.round_88)//图片加载出来前，显示的图片
            .error(R.drawable.round_88)//图片加载失败后，显示的图片
            .transition(withCrossFade())
            .apply(bitmapTransform(new CircleCrop()))
            .into(mCiv_user_img);
    mTv_admin_name.setText(operationInfo.getOpusername());

    mBtn_cancel.setOnClickListener(view -> dismiss());
    mBtn_sure.setOnClickListener(view -> {
      //TODO
      dismiss();
    });
    updateUI();
    mBtn_update_status.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        if (role == 4) {
          ToastUtils.showToast("你需要指定一个用户为管理员");
          return;
        }
        new CommonDialog.Builder(mContext).setContent("此操作将会撤销本人的管理员权限，确定设置此用户为管理员吗？")
                                          .setSureText("确定", new SureInterface() {
                                            @Override
                                            public void onSureClick() {
                                              upDateStatus();
                                            }
                                          })
                                          .setCancelText("取消", null)
                                          .build()
                                          .show();
      }
    });

    mBtn_shy_status.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        int lastRole = 0;
        if (role == 3) {
          lastRole = 6;
        } else {
          lastRole = 3;
        }
        RemoteMode.getInstance()
                  .updateStatus(lastRole, operationInfo.getUserid(), operationInfo.getOpcompanyid())
                  .subscribe(new BaseObserver<BaseEntry>(new CompositeDisposable()) {
                    @Override
                    public void onError(BaseException e) {
                      showToast(e.getMsg());
                    }

                    @Override
                    public void onSuss(BaseEntry baseEntry) {
                      update.updateData();
                      if (role == 3) {
                        role = 6;
                      } else {
                        role = 3;
                      }
                      updateUI();
                    }

                    @Override
                    public void onComplete() {
                      super.onComplete();
                    }
                  });
      }
    });
  }

  private void updateUI() {
    if (role == 4) {
      mBtn_update_status.setBackgroundResource(R.drawable.tc_btn_kq);
      mBtn_shy_status.setBackgroundResource(R.drawable.tc_btn_kq);
    } else if (role == 3) {
      mBtn_update_status.setBackgroundResource(R.drawable.tc_btn_gb);
      mBtn_shy_status.setBackgroundResource(R.drawable.tc_btn_gb);
    } else if (role == 6) {
      mBtn_update_status.setBackgroundResource(R.drawable.tc_btn_gb);
      mBtn_shy_status.setBackgroundResource(R.drawable.tc_btn_kq);
    }
  }

  private void upDateStatus() {
    //int lastRole = 0;
    //if (role == 6) {
    //  lastRole = 4;
    //} else {
    //  lastRole = 6;
    //}
    RemoteMode.getInstance()
              .updateStatus(4, operationInfo.getUserid(), operationInfo.getOpcompanyid())
              .subscribe(new BaseObserver<BaseEntry>(new CompositeDisposable()) {
                @Override
                public void onError(BaseException e) {
                  showToast(e.getMsg());
                }

                @Override
                public void onSuss(BaseEntry baseEntry) {
                  update.updateData();
                  role = 4;
                  updateUI();
                }

                @Override
                public void onComplete() {
                  super.onComplete();
                }
              });
  }

  private void initView() {
    mCiv_user_img = findViewById(R.id.civ_user_img);
    mTv_admin_name = findViewById(R.id.tv_admin_name);
    mBtn_update_status = findViewById(R.id.btn_update_status);
    mBtn_shy_status = findViewById(R.id.btn_shy_status);
    mBtn_cancel = findViewById(R.id.btn_cancel);
    mBtn_sure = findViewById(R.id.btn_sure);
  }
}
