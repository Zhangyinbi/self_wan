package com.domain.operationrobot.app.home;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.domain.library.GlideApp;
import com.domain.library.utils.DisplaysUtil;
import com.domain.library.utils.GalleryUtil;
import com.domain.operationrobot.BaseApplication;
import com.domain.operationrobot.R;
import com.domain.operationrobot.http.bean.ChatBean;
import com.domain.operationrobot.im.bean.RootMessage2;
import com.domain.operationrobot.im.bean.RootMessage34;
import com.domain.operationrobot.im.bean.RootMessage6;
import com.domain.operationrobot.util.TimeUtil;
//import com.jelly.mango.Mango;
//import com.jelly.mango.MultiplexImage;
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
  //private ArrayList<MultiplexImage> images = new ArrayList<>();
  private ArrayList<String> imgs = new ArrayList<>();
  private Activity mActivity;

  public ChatAdapter(ArrayList<ChatBean> list, Activity activity) {
    mList.clear();
    mList.addAll(list);
    mActivity = activity;
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
  public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
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

    String msg = chatBean.getContent();
    if (holder.iv_image_msg != null) {
      String regex = mActivity.getString(R.string.regex);
      if (msg.startsWith(regex) && msg.endsWith(regex)) {
        holder.tv_content.setVisibility(View.GONE);
        holder.iv_image_msg.setVisibility(View.VISIBLE);
        int end = msg.lastIndexOf(regex);
        String imageUrl = msg.substring(regex.length(), end);
        //TODO 实际图片宽高比
        float ratio = 1080 / 1920F;
        int height = 0;
        int width = 0;
        if (height >= width) {
          height = DisplaysUtil.dip2px(mActivity, 130);
          width = (int) (height * ratio);
        } else {
          width = DisplaysUtil.dip2px(mActivity, 130);
          height = (int) (width * ratio);
        }
        holder.iv_image_msg.setAdjustViewBounds(true);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) holder.iv_image_msg.getLayoutParams();
        layoutParams.height = height;
        layoutParams.width = width;
        holder.iv_image_msg.setLayoutParams(layoutParams);
        ////设置图片圆角角度
        RoundedCorners roundedCorners = new RoundedCorners(14);
        //通过RequestOptions扩展功能
        RequestOptions options = RequestOptions.bitmapTransform(roundedCorners);
        //
        Glide.with(mActivity)
             .load(imageUrl)
             .apply(options)
             .into(holder.iv_image_msg);

        holder.iv_image_msg.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
            GalleryUtil.with(mActivity)
                       .launchPicturePreview(imgs, imgs.indexOf(imageUrl));
          }
        });
        return;
      }
    }

    if (holder.tv_content != null) {
      if (holder.iv_image_msg != null) {
        holder.iv_image_msg.setVisibility(View.GONE);
      }
      holder.tv_content.setVisibility(View.VISIBLE);
      holder.tv_content.setOnClickListener(null);
      holder.tv_content.setText(msg);
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
    setImgs(message);
    mList.add(message);
    notifyItemInserted(getItemCount());
    if (mRecyclerView != null) {
      mRecyclerView.scrollToPosition(getItemCount() - 1);
    }
  }

  public void addAll(ArrayList<ChatBean> list) {
    for (ChatBean chatBean : list) {
      setImgs(chatBean);
    }
    mList.addAll(list);
    notifyItemRangeInserted(getItemCount(), list.size());
    if (mRecyclerView != null) {
      mRecyclerView.scrollToPosition(getItemCount() - 1);
    }
  }

  public void setRecycler(RecyclerView recycler) {
    mRecyclerView = recycler;
  }

  private void setImgs(ChatBean message) {

    String msg = message.content;
    String regex = mActivity.getString(R.string.regex);
    if (msg.startsWith(regex) && msg.endsWith(regex)) {
      int end = msg.lastIndexOf(regex);
      final String imageUrl = msg.substring(regex.length(), end);
      if (!imgs.contains(imageUrl)) {
        //images.add(new MultiplexImage(imageUrl, null, MultiplexImage.ImageType.NORMAL));
        imgs.add(imageUrl);
      }
      return;
    }
  }

  public class MyViewHolder extends RecyclerView.ViewHolder {
    private TextView     tv_name;
    private TextView     tv_content;
    private TextView     tv_time;
    private ImageView    iv_user_img;
    private ImageView    iv_image_msg;
    private RecyclerView rlv_recycler;

    public MyViewHolder(@NonNull View itemView) {
      super(itemView);
      tv_name = itemView.findViewById(R.id.tv_name);
      tv_content = itemView.findViewById(R.id.tv_content);
      tv_time = itemView.findViewById(R.id.tv_time);
      iv_user_img = itemView.findViewById(R.id.iv_user_img);
      iv_image_msg = itemView.findViewById(R.id.iv_image_msg);
      rlv_recycler = itemView.findViewById(R.id.rlv_recycler);
    }
  }
}
