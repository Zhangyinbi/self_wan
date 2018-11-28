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
import com.domain.operationrobot.http.bean.ServerMachineBean;
import java.util.ArrayList;

/**
 * Project Name : OperationRobot
 * description:null
 *
 * @author : yinbi.zhang.o
 * Create at : 2018/11/28 19:29
 */
public class ServerInAdapter extends RecyclerView.Adapter<ServerInAdapter.MyViewHolder> {
  private ArrayList<ServerMachineBean> data;
  private Context                      mContext;
  private OnItemClick mOnItemClick;

  public ServerInAdapter(Context context) {
    mContext = context;
    data = new ArrayList<>();
  }

  @NonNull
  @Override
  public ServerInAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int type) {
    View view = null;

    view = LayoutInflater.from(mContext)
                         .inflate(R.layout.server_in_item, viewGroup, false);
    return new MyViewHolder(view);
  }

  public void setItemClickListener(OnItemClick onItemClick) {
    mOnItemClick = onItemClick;
  }

  @Override
  public void onBindViewHolder(@NonNull ServerInAdapter.MyViewHolder holder, int i) {
    ServerMachineBean serverMachineBean = data.get(i);
    holder.tv_host_name.setText(serverMachineBean.getName());
    holder.iv_close.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        if (mOnItemClick!=null){
          mOnItemClick.onItemClick(serverMachineBean);
        }
      }
    });
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

  public ArrayList<ServerMachineBean> getData() {
    return data;
  }

  public class MyViewHolder extends RecyclerView.ViewHolder {
    private TextView  tv_host_name;
    private ImageView iv_close;

    public MyViewHolder(@NonNull View itemView) {
      super(itemView);
      tv_host_name = itemView.findViewById(R.id.tv_host_name);
      iv_close = itemView.findViewById(R.id.iv_close);
    }
  }
}
