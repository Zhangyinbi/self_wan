package com.domain.operationrobot.app.home.server;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.domain.library.base.AbsActivity;
import com.domain.library.recycleview.dapter.RecyclerComAdapter;
import com.domain.library.recycleview.holder.ViewHolder;
import com.domain.library.recycleview.interfaces.MultiTypeSupport;
import com.domain.operationrobot.BaseApplication;
import com.domain.operationrobot.R;
import com.domain.operationrobot.http.bean.ServerBean;
import java.text.DecimalFormat;
import java.util.List;

import static com.domain.operationrobot.app.home.server.ServerMonitorFragment.REFRESH;

/**
 * Project Name : OperationRobot
 * description:null
 *
 * @author : yinbi.zhang.o
 * Create at : 2018/12/3 23:44
 */
public class ServerAdapter extends RecyclerComAdapter<ServerBean.ServerList> {
  public ServerAdapter(Context context, List<ServerBean.ServerList> data) {
    super(context, data, new MultiTypeSupport<ServerBean.ServerList>() {
      @Override
      public int getLayoutId(ServerBean.ServerList data) {
        if (data != null && data.getItem() != null) {
          return R.layout.server_monitor_item;
        }
        return R.layout.server_monitor_last_item;
      }
    });
  }

  @Override
  protected void convert(ViewHolder holder, ServerBean.ServerList itemData, int position) {

    holder.itemView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        if (itemData != null && itemData.getItem() != null) {

        }else {
          (mContext).startActivity(new Intent(mContext, ServerMonitorActivity.class));
        }
      }
    });
    if (position == 0||position==1) {
      holder.getView(R.id.view)
            .setVisibility(View.VISIBLE);
    } else {
      holder.getView(R.id.view)
            .setVisibility(View.GONE);
    }
  if (getItemViewType(position)==R.layout.server_monitor_last_item){
      return;
  }
    LinearLayout ll = holder.getView(R.id.ll_content);
    holder.setText(R.id.tv_host_name, itemData.getHost());
    ll.removeAllViews();
    for (ServerBean.ServerList.MonitorInfo monitorInfo : itemData.getItem()) {
      View inflate = LayoutInflater.from(mContext)
                                   .inflate(R.layout.server_item_monitor, null);
      TextView tvKey = inflate.findViewById(R.id.tv_key);
      TextView tvRoate = inflate.findViewById(R.id.tv_roate);
      ProgressBar pbr = inflate.findViewById(R.id.pbr);
      tvKey.setText(monitorInfo.getKey());
      float v = ((monitorInfo.getTotal()-monitorInfo.getAvailable()) * 100 / monitorInfo.getTotal());
      DecimalFormat decimalFormat=new DecimalFormat("0.00");//构造方法的字符格式这里如果小数不足2位,会以0补足.
      String p=decimalFormat.format(v);
      if (v < 10) {
        ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(Color.TRANSPARENT);
        SpannableString spannableString = new SpannableString("00" + p + "%");
        spannableString.setSpan(foregroundColorSpan, 0, 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvRoate.setText(spannableString);
      } else if (v < 100){
        ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(Color.TRANSPARENT);
        SpannableString spannableString = new SpannableString("0" + p + "%");
        spannableString.setSpan(foregroundColorSpan, 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvRoate.setText(spannableString);
        //tvRoate.setText(p + "%");
      }else {
        tvRoate.setText(p + "%");
      }
      pbr.setProgress((int) v);
      if (v >= 90) {
        pbr.setProgressDrawable(mContext.getResources()
                                        .getDrawable(R.drawable.red));
      } else {
        pbr.setProgressDrawable(mContext.getResources()
                                        .getDrawable(R.drawable.green));
      }
      ll.addView(inflate);
    }
  }
}
