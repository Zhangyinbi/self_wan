package com.domain.operationrobot.app.home;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.domain.operationrobot.R;
import com.domain.operationrobot.im.bean.RootMessage2;
import com.domain.operationrobot.im.bean.RootMessage6;
import com.domain.operationrobot.im.socket.AppSocket;
import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * Project Name : OperationRobot
 * description:null
 *
 * @author : yinbi.zhang.o
 * Create at : 2018/10/23 23:09
 */
public class RootBean6Adapter extends RecyclerView.Adapter<RootBean6Adapter.MyViewHolder> {

  private ArrayList<RootMessage6.Action> mActions;

  public RootBean6Adapter(ArrayList<RootMessage6.Action> actions) {
    mActions = actions;
  }

  @NonNull
  @Override
  public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int type) {
    View layout = LayoutInflater.from(viewGroup.getContext())
                                .inflate(R.layout.root_adapter_item_6, viewGroup, false);
    return new MyViewHolder(layout);
  }

  @Override
  public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
    RootMessage6.Action action = mActions.get(position);
    holder.tv_title.setText(action.getTitle());
    holder.tv_size.setText(action.getUsedSize() + "/" + action.getTotalSize());
    holder.tv_ratio.setText(action.getRatio()+"%");
    holder.progress_bar.setProgress(action.getRatio());
    if (action.getRatio() >= 85) {
      holder.progress_bar.setProgressDrawable(holder.itemView.getContext()
                                                             .getResources()
                                                             .getDrawable(R.drawable.red));
    } else {
      holder.progress_bar.setProgressDrawable(holder.itemView.getContext()
                                                             .getResources()
                                                             .getDrawable(R.drawable.green));
    }
  }

  @Override
  public int getItemCount() {
    return mActions != null ? mActions.size() : 0;
  }

  public class MyViewHolder extends RecyclerView.ViewHolder {
    private TextView    tv_title;
    private TextView    tv_size;
    private TextView    tv_ratio;
    private ProgressBar progress_bar;

    public MyViewHolder(@NonNull View itemView) {
      super(itemView);
      tv_title = itemView.findViewById(R.id.tv_title);
      tv_size = itemView.findViewById(R.id.tv_size);
      tv_ratio = itemView.findViewById(R.id.tv_ratio);
      progress_bar = itemView.findViewById(R.id.progress_bar);
    }
  }
}
