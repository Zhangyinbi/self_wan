package com.domain.operationrobot.app.home;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.domain.library.GlideApp;
import com.domain.library.recycleview.holder.ViewHolder;
import com.domain.library.utils.DisplaysUtil;
import com.domain.library.utils.GalleryUtil;
import com.domain.operationrobot.BaseApplication;
import com.domain.operationrobot.R;
import com.domain.operationrobot.app.home.holder.ViewHolder11;
import com.domain.operationrobot.http.bean.ChatBean;
import com.domain.operationrobot.http.bean.ImageBean;
import com.domain.operationrobot.im.bean.RootMessage2;
import com.domain.operationrobot.im.bean.RootMessage34;
import com.domain.operationrobot.im.bean.RootMessage6;
import com.domain.operationrobot.im.bean.RootMessage8;
import com.domain.operationrobot.util.TimeUtil;
//import com.jelly.mango.Mango;
//import com.jelly.mango.MultiplexImage;
import com.google.gson.Gson;
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
public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
  private final Gson mGson;
  private ArrayList<ChatBean> mList = new ArrayList<>();
  private RecyclerView mRecyclerView;
  //private ArrayList<MultiplexImage> images = new ArrayList<>();
  private ArrayList<String> imgs = new ArrayList<>();
  private Activity      mActivity;
  private String        mRegex;
  private HostInterface mHostInterface;
  private OnViewClick   mOnViewClick;

  public ChatAdapter(ArrayList<ChatBean> list, Activity activity) {
    mList.clear();
    mList.addAll(list);
    mActivity = activity;
    mGson = new Gson();
    mRegex = mActivity.getString(R.string.regex);
    notifyDataSetChanged();
  }

  public void setHostMsg(HostInterface hostMsg) {
    mHostInterface = hostMsg;
  }

  @NonNull
  @Override
  public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int type) {
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
    } else if (type == 2 || type == 6 || type == 34 || type == 8) {
      layout = LayoutInflater.from(viewGroup.getContext())
                             .inflate(R.layout.root_item_2, viewGroup, false);
    } else if (type == 11) {
      return ViewHolder.createViewHolder(mActivity, viewGroup, R.layout.root_item_11);
    }
    return new MyViewHolder(layout);
  }

  public void setOnViewClick(OnViewClick onViewClick) {
    mOnViewClick = onViewClick;
  }

  @Override
  public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder1, int position) {
    MyViewHolder holder = null;
    ChatBean chatBean = mList.get(position);
    int type = chatBean.getType();
    long time = chatBean.getTime();

    TextView tvTime = null;

    if (holder1 instanceof MyViewHolder) {
      holder = (MyViewHolder) holder1;
      tvTime = holder.tv_time;
      setTime(tvTime,time,position);
    } else if (type == 11) {
      ViewHolder11.covert((ViewHolder) holder1, chatBean, position);
      tvTime = ((ViewHolder) holder1).getView(R.id.tv_time);
      setTime(tvTime,time,position);
      return;
    }

    holder.tv_name.setText(chatBean.getUserName());

    if (chatBean.getTargetId() != null && chatBean.getTargetId()
                                                  .equals(BaseApplication.getInstance()
                                                                         .getUser()
                                                                         .getUserId())) {
      holder.iv_user_img.setOnLongClickListener(null);
    } else {
      holder.iv_user_img.setOnLongClickListener(new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View view) {
          if (mOnViewClick != null) {
            mOnViewClick.viewClick(view, chatBean, position);
          }
          return true;
        }
      });
    }
    //if (type == -1) {
    GlideApp.with(holder.itemView.getContext())
            .load(chatBean.getUrl())
            .placeholder(R.drawable.root_40)
            .error(R.drawable.root_40)
            .into(holder.iv_user_img);
    //} else {
    //  holder.iv_user_img.setImageResource(R.drawable.root_40);
    //}

    String msg = chatBean.getContent();
    if (holder.iv_image_msg != null) {
      if (msg.startsWith(mRegex) && msg.endsWith(mRegex)) {
        holder.tv_content.setVisibility(View.GONE);
        holder.iv_image_msg.setVisibility(View.VISIBLE);
        setChatImage(msg, holder.iv_image_msg);
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
      holder.rlv_recycler.setAdapter(new RootBean2Adapter(actions, mHostInterface));
    } else if (type == 6) {
      holder.rlv_recycler.setLayoutManager(new LinearLayoutManager(holder.itemView.getContext()) {
        @Override
        public boolean canScrollVertically() {
          return false;
        }
      });
      ArrayList<RootMessage6.Action> actions = chatBean.getActions6();
      holder.rlv_recycler.setAdapter(new RootBean6Adapter(actions));
    } else if (type == 34) {
      holder.rlv_recycler.setLayoutManager(new LinearLayoutManager(holder.itemView.getContext()) {
        @Override
        public boolean canScrollVertically() {
          return false;
        }
      });
      ArrayList<RootMessage34.Action> actions = chatBean.getActions34();
      holder.rlv_recycler.setAdapter(new RootBean34Adapter(actions));
    } else if (type == 8) {
      holder.rlv_recycler.setLayoutManager(new LinearLayoutManager(holder.itemView.getContext()) {
        @Override
        public boolean canScrollVertically() {
          return false;
        }
      });
      ArrayList<RootMessage8.Action> actions = chatBean.getActions8();
      holder.rlv_recycler.setAdapter(new RootBean8Adapter(actions));
    }
  }

  private void setTime(TextView tvTime, long time, int position) {
    if (position == 0) {
      tvTime.setVisibility(View.VISIBLE);
      tvTime.setText(TimeUtil.getTimeStr(time));
    } else {
      ChatBean pre = mList.get(position - 1);
      long preTime = pre.getTime();
      if (time - preTime < 5 * 60 * 1000) {
        tvTime.setVisibility(View.GONE);
      } else {
        tvTime.setVisibility(View.VISIBLE);
        tvTime.setText(TimeUtil.getTimeStr(time));
      }
    }
  }

  /**
   * 设置聊天图片
   */
  private void setChatImage(String msg, ImageView iv_image_msg) {
    int end = msg.lastIndexOf(mRegex);
    String imageInfo = msg.substring(mRegex.length(), end);
    ImageBean imageBean = mGson.fromJson(imageInfo, ImageBean.class);
    imageInfo = imageBean.getUrl();
    //////设置图片圆角角度
    //RoundedCorners roundedCorners = new RoundedCorners(14);
    ////通过RequestOptions扩展功能
    //RequestOptions options = RequestOptions.bitmapTransform(roundedCorners);
    ////获取图片真正的宽高
    //GlideApp.with(mActivity)
    //        .asBitmap()
    //        .placeholder(R.drawable.round_88)
    //        .error(R.drawable.round_88)
    //        .load(imageInfo)
    //        .apply(options)
    //        .into(new SimpleTarget<Bitmap>() {
    //          @Override
    //          public void onResourceReady(Bitmap bitmap, Transition<? super Bitmap> transition) {
    //            int originalWidth = bitmap.getWidth();
    //            int originalHeight = bitmap.getHeight();
    //            int finallyHeight = 0;
    //            int finallyWidth = 0;
    //            if (originalHeight >= originalWidth) {
    //              finallyHeight = DisplaysUtil.dip2px(mActivity, 130);
    //              finallyWidth = (int) (finallyHeight * originalWidth / originalHeight);
    //            } else {
    //              finallyWidth = DisplaysUtil.dip2px(mActivity, 130);
    //              finallyHeight = (int) (finallyWidth * originalHeight / originalWidth);
    //            }
    //            iv_image_msg.setAdjustViewBounds(true);
    //            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) iv_image_msg.getLayoutParams();
    //            layoutParams.height = finallyHeight;
    //            layoutParams.width = finallyWidth;
    //            iv_image_msg.setLayoutParams(layoutParams);
    //            iv_image_msg.setImageBitmap(bitmap);
    //          }
    //        });

    //TODO 实际图片宽高比
    Float originalWidth = Float.valueOf(imageBean.getWidth());
    Float originalHeight = Float.valueOf(imageBean.getHeight());
    int finallyHeight = 0;
    int finallyWidth = 0;
    if (originalHeight >= originalWidth) {
      finallyHeight = DisplaysUtil.dip2px(mActivity, 130);
      finallyWidth = (int) (finallyHeight * originalWidth / originalHeight);
    } else {
      finallyWidth = DisplaysUtil.dip2px(mActivity, 130);
      finallyHeight = (int) (finallyWidth * originalHeight / originalWidth);
    }
    iv_image_msg.setAdjustViewBounds(true);
    LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) iv_image_msg.getLayoutParams();
    layoutParams.height = finallyHeight;
    layoutParams.width = finallyWidth;
    iv_image_msg.setLayoutParams(layoutParams);
    RoundedCorners roundedCorners = new RoundedCorners(14);
    RequestOptions options = RequestOptions.bitmapTransform(roundedCorners);
    GlideApp.with(mActivity)
            .load(imageBean.getUrl())
            .placeholder(R.drawable.round_88)
            .error(R.drawable.round_88)
            .apply(options)
            .into(iv_image_msg);

    iv_image_msg.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        GalleryUtil.with(mActivity)
                   .launchPicturePreview(imgs, imgs.indexOf(imageBean.getUrl()));
      }
    });
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
    setImgs(message.content);
    mList.add(message);
    notifyItemInserted(getItemCount());
    //new android.os.Handler().postDelayed(new Runnable() {
    //  @Override
    //  public void run() {
    if (mRecyclerView != null) {
      mRecyclerView.scrollToPosition(getItemCount() - 1);
    }
    //}
    //}, 200);

    //new android.os.Handler().postDelayed(new Runnable() {
    //  @Override
    //  public void run() {
    //    if (mRecyclerView != null) {
    //      mRecyclerView.scrollToPosition(getItemCount() - 1);
    //    }
    //  }
    //}, 100);
  }

  public void addAll(ArrayList<ChatBean> list) {
    for (ChatBean chatBean : list) {
      setImgs(chatBean.content);
    }
    mList.addAll(list);
    notifyItemRangeInserted(getItemCount(), list.size());
    new android.os.Handler().postDelayed(new Runnable() {
      @Override
      public void run() {
        if (mRecyclerView != null) {
          mRecyclerView.scrollToPosition(getItemCount() - 1);
        }
      }
    }, 100);
  }

  public void setRecycler(RecyclerView recycler) {
    mRecyclerView = recycler;
  }

  private void setImgs(String msg) {
    if (msg.startsWith(mRegex) && msg.endsWith(mRegex)) {
      int end = msg.lastIndexOf(mRegex);
      String imageUrl = msg.substring(mRegex.length(), end);
      try {
        ImageBean imageBean = mGson.fromJson(imageUrl, ImageBean.class);
        imageUrl = imageBean.getUrl();
      } catch (Exception e) {

      }
      if (!imgs.contains(imageUrl)) {
        imgs.add(imageUrl);
      }
      return;
    }
  }

  public interface OnViewClick {
    void viewClick(View view, ChatBean chatBean, int position);
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
