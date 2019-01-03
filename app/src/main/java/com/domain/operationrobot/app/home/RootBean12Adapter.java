package com.domain.operationrobot.app.home;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.domain.operationrobot.R;
import com.domain.operationrobot.im.bean.RootMessage12;
import com.domain.operationrobot.im.bean.RootMessage8;
import java.util.ArrayList;

/**
 * Project Name : OperationRobot
 * description:null
 *
 * @author : yinbi.zhang.o
 * Create at : 2018/10/23 23:09
 */
public class RootBean12Adapter extends RecyclerView.Adapter<RootBean12Adapter.MyViewHolder> {

  private ArrayList<RootMessage12.Action> mActions;

  public RootBean12Adapter(ArrayList<RootMessage12.Action> actions) {
    mActions = actions;
  }

  @NonNull
  @Override
  public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int type) {
    View layout = null;
    if (type == 0) {
      layout = LayoutInflater.from(viewGroup.getContext())
                             .inflate(R.layout.root_adapter_item_8_position_0, viewGroup, false);
    } else {
      layout = LayoutInflater.from(viewGroup.getContext())
                             .inflate(R.layout.root_adapter_item_8_position_1, viewGroup, false);
    }
    return new MyViewHolder(layout);
  }

  @Override
  public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
    if (position == 0) {
      holder.tv_name.setText("Device");
      holder.tv_name1.setText("KB_read/s");
      holder.tv_name2.setText("KB_write/s");
      return;
    }
    int realPosition = position - 1;
    RootMessage12.Action action = mActions.get(realPosition);
    holder.tv_in.setText(action.getBlk_read());
    holder.tv_time.setText(action.getDevice());
    holder.tv_out.setText(action.getBlk_wrtn());
  }

  @Override
  public int getItemViewType(int position) {
    return position == 0 ? 0 : 1;
  }

  @Override
  public int getItemCount() {
    return mActions != null && mActions.size() > 0 ? mActions.size() + 1 : 0;
  }

  public class MyViewHolder extends RecyclerView.ViewHolder {
    private TextView tv_time;
    private TextView tv_in;
    private TextView tv_out;
    private TextView tv_name;
    private TextView tv_name1;
    private TextView tv_name2;



    public MyViewHolder(@NonNull View itemView) {
      super(itemView);
      tv_time = itemView.findViewById(R.id.tv_time);
      tv_in = itemView.findViewById(R.id.tv_in);
      tv_out = itemView.findViewById(R.id.tv_out);

      tv_name = itemView.findViewById(R.id.tv_name);
      tv_name1 = itemView.findViewById(R.id.tv_name1);
      tv_name2 = itemView.findViewById(R.id.tv_name2);
    }
  }
}
