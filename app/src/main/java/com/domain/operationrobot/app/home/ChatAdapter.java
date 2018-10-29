package com.domain.operationrobot.app.home;

import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
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
import com.domain.operationrobot.im.bean.RootMessage2;
import com.domain.operationrobot.im.bean.RootMessage34;
import com.domain.operationrobot.im.bean.RootMessage6;
import com.domain.operationrobot.util.TimeUtil;
import java.util.ArrayList;

import static com.domain.operationrobot.util.Constant.MESSAGE_OTHER;
import static com.domain.operationrobot.util.Constant.MESSAGE_SELF;

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
    if (type == MESSAGE_SELF) {//自己
      layout = LayoutInflater.from(viewGroup.getContext())
                             .inflate(R.layout.chat_item_self, viewGroup, false);
    } else if (type == MESSAGE_OTHER) {//别人
      layout = LayoutInflater.from(viewGroup.getContext())
                             .inflate(R.layout.chat_item, viewGroup, false);
    } else if (type == 1) {
      layout = LayoutInflater.from(viewGroup.getContext())
                             .inflate(R.layout.root_item_1, viewGroup, false);
    } else if (type == 2 || type == 6 || type == 34) {
      layout = LayoutInflater.from(viewGroup.getContext())
                             .inflate(R.layout.root_item_2, viewGroup, false);
    }
    return new MyViewHolder(layout);
  }

  @Override
  public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
    ChatBean chatBean = mList.get(position);
    int type = chatBean.getType();
    long time = chatBean.getTime();
    if (position == 0) {
      holder.tv_time.setVisibility(View.VISIBLE);
      holder.tv_time.setText(TimeUtil.getTimeStr(time));
    } else {
      ChatBean pre = mList.get(position - 1);
      long preTime = pre.getTime();
      if (time - preTime < 5 * 60 * 1000) {
        holder.tv_time.setVisibility(View.GONE);
      } else {
        holder.tv_time.setVisibility(View.VISIBLE);
        holder.tv_time.setText(TimeUtil.getTimeStr(time));
      }
    }
    holder.tv_name.setText(chatBean.getUserName());

    if (type == -1) {
      Glide.with(holder.itemView.getContext())
           .load(chatBean.getUrl())
           .into(holder.iv_user_img);
    } else {
      holder.iv_user_img.setImageResource(R.drawable.root_40);
    }
    if (holder.tv_content != null) {
      holder.tv_content.setText(chatBean.getContent());
    }
    //以上是公共处理部分
    if (type == 2) {
      holder.rlv_recycler.setLayoutManager(new GridLayoutManager(holder.itemView.getContext(), 2) {
        @Override
        public boolean canScrollVertically() {
          return false;
        }
      });
      ArrayList<RootMessage2.Action> actions = chatBean.getActions();
      holder.rlv_recycler.setAdapter(new RootBean2Adapter(actions));
    } else if (type == 6) {
      holder.rlv_recycler.setLayoutManager(new LinearLayoutManager(holder.itemView.getContext()) {
        @Override
        public boolean canScrollVertically() {
          return false;
        }
      });
      ArrayList<RootMessage6.Action> actions = chatBean.getActions6();
      holder.rlv_recycler.setAdapter(new RootBean6Adapter(actions));
    }
    if (type == 34) {
      holder.rlv_recycler.setLayoutManager(new LinearLayoutManager(holder.itemView.getContext()) {
        @Override
        public boolean canScrollVertically() {
          return false;
        }
      });
      ArrayList<RootMessage34.Action> actions = chatBean.getActions34();
      holder.rlv_recycler.setAdapter(new RootBean34Adapter(actions));
    }
  }

  @Override
  public int getItemViewType(int position) {
    if (mList.get(position)
             .getType() != -1) {
      return mList.get(position)
                  .getType();
    }
    return mList.get(position)
                .getTargetId()
                .equals(BaseApplication.getInstance()
                                       .getUser()
                                       .getUserId()) ? MESSAGE_SELF : MESSAGE_OTHER;
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
    private TextView     tv_name;
    private TextView     tv_content;
    private TextView     tv_time;
    private ImageView    iv_user_img;
    private RecyclerView rlv_recycler;

    public MyViewHolder(@NonNull View itemView) {
      super(itemView);
      tv_name = itemView.findViewById(R.id.tv_name);
      tv_content = itemView.findViewById(R.id.tv_content);
      tv_time = itemView.findViewById(R.id.tv_time);
      iv_user_img = itemView.findViewById(R.id.iv_user_img);
      rlv_recycler = itemView.findViewById(R.id.rlv_recycler);
    }
  }
}
