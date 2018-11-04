package com.domain.operationrobot.app.operation;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.domain.library.GlideApp;
import com.domain.library.utils.ToastUtils;
import com.domain.operationrobot.BaseApplication;
import com.domain.operationrobot.R;
import com.domain.operationrobot.http.bean.Company;
import com.domain.operationrobot.http.bean.OperationBean;
import com.domain.operationrobot.http.bean.OperationList;
import java.util.ArrayList;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;
import static com.domain.library.GlideOptions.bitmapTransform;
import static com.domain.operationrobot.app.operation.OperationActivity.ADD_OPERATION;
import static com.domain.operationrobot.app.operation.OperationActivity.EDIT_OPERATION;

/**
 * Project Name : OperationRobot
 * description:null
 *
 * @author : yinbi.zhang.o
 * Create at : 2018/11/3 22:58
 */
public class OperationAdapter extends RecyclerView.Adapter<OperationAdapter.MyViewHolder> {

  private ArrayList<OperationList.OperationInfo> data;
  private ArrayList<OperationList.OperationInfo> target = new ArrayList<>();
  private Context mContext;
  OperationManagerActivity.IUpdate update;

  public OperationAdapter(Context context) {
    this.mContext = context;
  }

  @NonNull
  @Override
  public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int type) {
    View view = LayoutInflater.from(viewGroup.getContext())
                              .inflate(R.layout.operation_item, viewGroup, false);
    return new MyViewHolder(view);
  }

  @Override
  public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
    OperationList.OperationInfo operationInfo = data.get(position);
    if (operationInfo.getUserstatus()
                     .equals("register")) {
      holder.iv_user_status.setVisibility(View.GONE);
    } else {
      holder.iv_user_status.setVisibility(View.VISIBLE);
    }
    String url = operationInfo.getImageUrl();
    GlideApp.with(mContext)
            .load(url)
            .placeholder(R.drawable.round_88)//图片加载出来前，显示的图片
            .error(R.drawable.round_88)//图片加载失败后，显示的图片
            .transition(withCrossFade())
            .apply(bitmapTransform(new CircleCrop()))
            .into(holder.iv_admin_image);

    holder.tv_admin_name.setText(operationInfo.getUsername());
    holder.iv_edit_operation.setOnClickListener(view -> {
      OperationActivity.start((Activity) mContext, EDIT_OPERATION,
        new OperationBean(operationInfo.getUsername(), operationInfo.getMobile(), operationInfo.getUserid()));
    });
    if (operationInfo.getRole() == 3) {
      holder.iv_yw.setVisibility(View.VISIBLE);
      holder.iv_shy.setVisibility(View.GONE);
      holder.iv_gly.setVisibility(View.GONE);
    } else if (operationInfo.getRole() == 4) {
      holder.iv_yw.setVisibility(View.GONE);
      holder.iv_shy.setVisibility(View.GONE);
      holder.iv_gly.setVisibility(View.VISIBLE);
    }
    holder.btn_manger.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
          new OperationDialog(mContext,operationInfo,update).show();
      }
    });
  }

  @Override
  public int getItemCount() {
    return data != null ? data.size() : 0;
  }

  /**
   * 更新数据
   */
  public void updateData(ArrayList<OperationList.OperationInfo> data, String targetName,OperationManagerActivity.IUpdate update) {
    this.update = update;
    if (!TextUtils.isEmpty(targetName)) {
      target.clear();
      for (OperationList.OperationInfo operationInfo : data) {
        if (operationInfo.getUsername()
                         .contains(targetName)) {
          target.add(operationInfo);
        }
      }
      if (target != null && target.size() > 0) {
        this.data = target;
        this.notifyDataSetChanged();
        return;
      } else {
        ToastUtils.showToast("未找到此运维用户，请重新输入");
      }
    }
    this.data = data;
    this.notifyDataSetChanged();
  }

  class MyViewHolder extends RecyclerView.ViewHolder {
    private ImageView iv_edit_operation;
    private ImageView iv_user_status;
    private ImageView iv_admin_image;
    private ImageView iv_gly;
    private ImageView iv_shy;
    private ImageView iv_yw;
    private TextView  tv_admin_name;
    private Button    btn_manger;

    public MyViewHolder(@NonNull View itemView) {
      super(itemView);
      iv_edit_operation = itemView.findViewById(R.id.iv_edit_operation);
      iv_user_status = itemView.findViewById(R.id.iv_user_status);
      iv_admin_image = itemView.findViewById(R.id.iv_admin_image);
      tv_admin_name = itemView.findViewById(R.id.tv_admin_name);
      btn_manger = itemView.findViewById(R.id.btn_manger);
      iv_gly = itemView.findViewById(R.id.iv_gly);
      iv_shy = itemView.findViewById(R.id.iv_shy);
      iv_yw = itemView.findViewById(R.id.iv_yw);
    }
  }
}
