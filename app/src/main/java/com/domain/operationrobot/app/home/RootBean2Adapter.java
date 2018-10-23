package com.domain.operationrobot.app.home;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.domain.library.utils.ToastUtils;
import com.domain.operationrobot.R;
import com.domain.operationrobot.im.bean.RootMessage2;
import java.util.ArrayList;

/**
 * Project Name : OperationRobot
 * description:null
 *
 * @author : yinbi.zhang.o
 * Create at : 2018/10/23 23:09
 */
public class RootBean2Adapter extends RecyclerView.Adapter<RootBean2Adapter.MyViewHolder> {

  private ArrayList<RootMessage2.Action> mActions;

  public RootBean2Adapter(ArrayList<RootMessage2.Action> actions) {
    mActions = actions;
  }

  @NonNull
  @Override
  public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int type) {
    View layout = LayoutInflater.from(viewGroup.getContext())
                                .inflate(R.layout.root_adapter_item_2, viewGroup, false);
    return new MyViewHolder(layout);
  }

  @Override
  public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int position) {
    RootMessage2.Action action = mActions.get(position);
    String name = action.getName();
    final String id = action.getId();
    myViewHolder.tv_action_name.setText(name);
    myViewHolder.tv_action_name.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        ToastUtils.showToast(id);
      }
    });
  }

  @Override
  public int getItemCount() {
    return mActions != null ? mActions.size() : 0;
  }

  public class MyViewHolder extends RecyclerView.ViewHolder {
    private TextView tv_action_name;

    public MyViewHolder(@NonNull View itemView) {
      super(itemView);
      tv_action_name = itemView.findViewById(R.id.tv_action_name);
    }
  }
}
