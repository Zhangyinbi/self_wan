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
import com.domain.operationrobot.R;
import com.domain.operationrobot.http.bean.ServerBean;
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
        if (position == getItemCount() - 1) {
          (mContext).startActivity(new Intent(mContext, ServerMonitorActivity.class));
        }
      }
    });
    if (position == getItemCount() - 1) {
      holder.getView(R.id.view)
            .setVisibility(View.VISIBLE);
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
      int v = (int) (monitorInfo.getAvailable() * 100 / monitorInfo.getTotal());
      if (v < 10) {
        ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(Color.TRANSPARENT);
        SpannableString spannableString = new SpannableString("0" + v + "%");
        spannableString.setSpan(foregroundColorSpan, 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvRoate.setText(spannableString);
      } else {
        tvRoate.setText(v + "%");
      }
      pbr.setProgress(v);
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
