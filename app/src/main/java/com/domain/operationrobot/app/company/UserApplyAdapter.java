package com.domain.operationrobot.app.company;

import android.content.Context;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.domain.library.GlideApp;
import com.domain.operationrobot.BaseApplication;
import com.domain.operationrobot.R;
import com.domain.operationrobot.http.bean.ApplyInfo;
import com.domain.operationrobot.http.bean.Company;
import com.domain.operationrobot.listener.ThrottleLastClickListener;

import com.domain.operationrobot.util.TimeUtil;
import java.util.ArrayList;

/**
 * Project Name : OperationRobot
 * description:null
 *
 * @author : yinbi.zhang.o
 * Create at : 2018/10/13 17:34
 */
public class UserApplyAdapter extends RecyclerView.Adapter<UserApplyAdapter.MyViewHolder> {

  private UserApplyActivity context;

  private ArrayList<ApplyInfo.Info> mInfos = new ArrayList<>();

  public UserApplyAdapter(UserApplyActivity context) {
    this.context = context;
  }

  @NonNull
  @Override
  public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
    View inflate = LayoutInflater.from(context)
                                 .inflate(R.layout.user_apply_item, null);
    return new MyViewHolder(inflate);
  }

  @Override
  public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
    final ApplyInfo.Info info = mInfos.get(position);
    if (info.getAdmin_action() == 0) {
      holder.btn_agree.setVisibility(View.GONE);
      holder.btn_refuse.setVisibility(View.GONE);
      holder.btn_result.setVisibility(View.VISIBLE);
      holder.btn_result.setBackgroundResource(R.drawable.yhsq_img_tongyi);
    } else if (info.getAdmin_action() == 1) {
      holder.btn_agree.setVisibility(View.GONE);
      holder.btn_refuse.setVisibility(View.GONE);
      holder.btn_result.setVisibility(View.VISIBLE);
      holder.btn_result.setBackgroundResource(R.drawable.yhsq_img_jujue);
    } else {
      holder.btn_agree.setVisibility(View.VISIBLE);
      holder.btn_refuse.setVisibility(View.VISIBLE);
      holder.btn_result.setVisibility(View.GONE);
    }

    holder.tv_name.setText(info.getRequest_username());
    holder.tv_mobile.setText(info.getRequest_mobile());
    holder.tv_time.setText("申请时间：" + TimeUtil.formatTimeMillis(info.getRequest_createtime()));
    GlideApp.with(context)
            .load(info.getRequest_img())
            .placeholder(R.drawable.yhsq_tx2)
            .error(R.drawable.yhsq_tx2)
            .into(holder.iv_image);

    holder.btn_agree.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        context.dispose(0, info.getRequest_userid());
      }
    });
    holder.btn_refuse.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        context.dispose(1, info.getRequest_userid());
      }
    });
  }

  @Override
  public int getItemCount() {
    return null == mInfos ? 0 : mInfos.size();
  }

  public void setData(ArrayList<ApplyInfo.Info> info) {
    mInfos = info;
    notifyDataSetChanged();
  }

  class MyViewHolder extends RecyclerView.ViewHolder {

    Button    btn_result;
    Button    btn_refuse;
    Button    btn_agree;
    TextView  tv_name;
    TextView  tv_mobile;
    TextView  tv_time;
    ImageView iv_image;

    public MyViewHolder(@NonNull View itemView) {
      super(itemView);
      btn_result = itemView.findViewById(R.id.btn_result);
      btn_refuse = itemView.findViewById(R.id.btn_refuse);
      btn_agree = itemView.findViewById(R.id.btn_agree);
      tv_name = itemView.findViewById(R.id.tv_name);
      tv_mobile = itemView.findViewById(R.id.tv_mobile);
      tv_time = itemView.findViewById(R.id.tv_time);
      iv_image = itemView.findViewById(R.id.iv_image);
    }
  }
}
