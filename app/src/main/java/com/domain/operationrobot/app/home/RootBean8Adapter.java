package com.domain.operationrobot.app.home;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.domain.operationrobot.R;
import com.domain.operationrobot.im.bean.RootMessage8;
import java.util.ArrayList;
import org.w3c.dom.Text;

/**
 * Project Name : OperationRobot
 * description:null
 *
 * @author : yinbi.zhang.o
 * Create at : 2018/10/23 23:09
 */
public class RootBean8Adapter extends RecyclerView.Adapter<RootBean8Adapter.MyViewHolder> {

  private ArrayList<RootMessage8.Action> mActions;

  public RootBean8Adapter(ArrayList<RootMessage8.Action> actions) {
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
      return;
    }
    int realPosition = position - 1;
    RootMessage8.Action action = mActions.get(realPosition);
    holder.tv_in.setText(action.getIn());
    holder.tv_time.setText(action.getNetDrive());
    holder.tv_out.setText(action.getOut());
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

    public MyViewHolder(@NonNull View itemView) {
      super(itemView);
      tv_time = itemView.findViewById(R.id.tv_time);
      tv_in = itemView.findViewById(R.id.tv_in);
      tv_out = itemView.findViewById(R.id.tv_out);
    }
  }
}
