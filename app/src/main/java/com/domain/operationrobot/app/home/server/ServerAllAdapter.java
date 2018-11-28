package com.domain.operationrobot.app.home.server;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.domain.operationrobot.R;
import com.domain.operationrobot.app.home.ChatAdapter;
import com.domain.operationrobot.http.bean.ServerMachineBean;
import java.util.ArrayList;

/**
 * Project Name : OperationRobot
 * description:null
 *
 * @author : yinbi.zhang.o
 * Create at : 2018/11/28 19:29
 */
public class ServerAllAdapter extends RecyclerView.Adapter<ServerAllAdapter.MyViewHolder> {
  private ArrayList<ServerMachineBean> data;
  private Context                      mContext;
  private OnItemClick                  mOnItemClick;

  public ServerAllAdapter(Context context) {
    mContext = context;
    data = new ArrayList<>();
  }

  @NonNull
  @Override
  public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
    View view = null;

    view = LayoutInflater.from(mContext)
                         .inflate(R.layout.server_all_item, viewGroup, false);
    return new MyViewHolder(view);
  }

  @Override
  public void onBindViewHolder(@NonNull MyViewHolder holder, int i) {
    ServerMachineBean serverMachineBean = data.get(i);
    holder.tv_host_name.setText(serverMachineBean.getName());
    holder.tv_ip.setText("IP：" + serverMachineBean.getHost());
    if ("in".equals(serverMachineBean.getHoststatus())) {
      holder.iv_selected.setImageResource(R.drawable.icon_chiocse);
    } else {
      holder.iv_selected.setImageResource(R.drawable.icon_nochoose);
    }
    holder.iv_selected.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        if ("in".equals(serverMachineBean.getHoststatus())) {
          serverMachineBean.setHoststatus("out");
          holder.iv_selected.setImageResource(R.drawable.icon_nochoose);
        } else {
          serverMachineBean.setHoststatus("in");
          holder.iv_selected.setImageResource(R.drawable.icon_chiocse);
        }
        if (mOnItemClick != null) {
          mOnItemClick.onItemClick(serverMachineBean);
        }
      }
    });
  }

  public void setItemClickListener(OnItemClick onItemClick) {
    mOnItemClick = onItemClick;
  }

  @Override
  public int getItemCount() {
    return data != null ? data.size() : 0;
  }

  public void upData(ArrayList<ServerMachineBean> data) {
    this.data.clear();
    this.data.addAll(data);
    notifyDataSetChanged();
  }

  public class MyViewHolder extends RecyclerView.ViewHolder {
    private TextView  tv_host_name;
    private TextView  tv_ip;
    private ImageView iv_selected;

    public MyViewHolder(@NonNull View itemView) {
      super(itemView);
      tv_host_name = itemView.findViewById(R.id.tv_host_name);
      tv_ip = itemView.findViewById(R.id.tv_ip);
      iv_selected = itemView.findViewById(R.id.iv_selected);
    }
  }
}
