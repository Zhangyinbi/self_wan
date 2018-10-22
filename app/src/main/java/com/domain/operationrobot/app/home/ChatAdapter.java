package com.domain.operationrobot.app.home;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.domain.operationrobot.BaseApplication;
import com.domain.operationrobot.R;
import com.domain.operationrobot.http.bean.ChatBean;
import com.domain.operationrobot.im.bean.MessageRecord;
import com.domain.operationrobot.util.TimeUtil;
import java.util.ArrayList;

/**
 * Project Name : OperationRobot
 * description:null
 *
 * @author : yinbi.zhang.o
 * Create at : 2018/10/21 16:34
 */
public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.MyViewHolder> {
  private ArrayList<ChatBean> mList = new ArrayList<>();
  private RecyclerView mRecyclerView;

  public ChatAdapter(ArrayList<ChatBean> list) {
    mList.clear();
    mList.addAll(list);
    notifyDataSetChanged();
  }

  @NonNull
  @Override
  public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int type) {
    View layout = null;
    if (type == 0) {//自己
      layout = LayoutInflater.from(viewGroup.getContext())
                             .inflate(R.layout.chat_item_self, viewGroup, false);
    } else if (type == 1) {//别人
      layout = LayoutInflater.from(viewGroup.getContext())
                             .inflate(R.layout.chat_item, viewGroup, false);
    } /*else if (type == 2) {
      layout = LayoutInflater.from(viewGroup.getContext())
                             .inflate(R.layout.time_item, viewGroup, false);
    }*/

    return new MyViewHolder(layout);
  }

  @Override
  public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

    ChatBean chatBean = mList.get(position);
    holder.tv_content.setText(chatBean.getContent());
    holder.tv_name.setText(chatBean.getUserName() );

    Glide.with(holder.itemView.getContext())
         .load(chatBean.getUrl())
         .into(holder.iv_user_img);
    long time = chatBean.getTime() / 1000;
    if (position == 0) {
      holder.tv_time.setVisibility(View.VISIBLE);
      holder.tv_time.setText(TimeUtil.getTimeStr(time));
    } else {
      ChatBean pre = mList.get(position - 1);
      long preTime = pre.getTime() / 1000;
      if (time - preTime < 5*60) {
        holder.tv_time.setVisibility(View.GONE);
      } else {
        holder.tv_time.setVisibility(View.VISIBLE);
        holder.tv_time.setText(TimeUtil.getTimeStr(time));
      }
    }
  }

  @Override
  public int getItemViewType(int position) {

    return mList.get(position)
                .getUserName()
                .equals(BaseApplication.getInstance()
                                       .getUser()
                                       .getUsername()) ? 0 : 1;
  }

  public ArrayList<ChatBean> getList() {
    return mList;
  }

  @Override
  public int getItemCount() {
    return mList != null ? mList.size() : 0;
  }

  public void addBeanToEnd(ChatBean message) {
    mList.add(message);
    notifyItemInserted(getItemCount());
    if (mRecyclerView != null) {
      mRecyclerView.scrollToPosition(getItemCount() - 1);
    }
  }

  public void addAll(ArrayList<ChatBean> list) {
    mList.addAll(list);
    notifyItemRangeInserted(getItemCount(), list.size());
    if (mRecyclerView != null) {
      mRecyclerView.scrollToPosition(getItemCount() - 1);
    }
  }

  public void setRecycler(RecyclerView recycler) {
    mRecyclerView = recycler;
  }

  public class MyViewHolder extends RecyclerView.ViewHolder {
    private TextView  tv_name;
    private TextView  tv_content;
    private TextView  tv_time;
    private ImageView iv_user_img;

    public MyViewHolder(@NonNull View itemView) {
      super(itemView);

      tv_name = itemView.findViewById(R.id.tv_name);
      tv_content = itemView.findViewById(R.id.tv_content);
      tv_time = itemView.findViewById(R.id.tv_time);
      iv_user_img = itemView.findViewById(R.id.iv_user_img);
    }
  }
}
