package com.domain.operationrobot.app.order;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spanned;
import android.widget.ImageView;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.domain.library.GlideApp;
import com.domain.library.recycleview.dapter.RecyclerComAdapter;
import com.domain.library.recycleview.holder.ViewHolder;
import com.domain.operationrobot.R;
import com.domain.operationrobot.http.bean.CommandBean;
import com.domain.operationrobot.util.TimeUtil;
import java.util.List;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;
import static com.domain.library.GlideOptions.bitmapTransform;

/**
 * Project Name : OperationRobot
 * description:null
 *
 * @author : yinbi.zhang.o
 * Create at : 2018/12/9 23:13
 */
public class CommandAdapter extends RecyclerComAdapter<CommandBean.CommandInfo> {
  public CommandAdapter(Context context, List<CommandBean.CommandInfo> data) {
    super(context, data, R.layout.adapter_command_item);
  }

  @Override
  protected void convert(ViewHolder holder, CommandBean.CommandInfo itemData, int position) {

    Spanned spanned = Html.fromHtml("执行命令  <font color='red'>" + itemData.getOperating_command() + "</font>   "+itemData.getMsg());

    holder.setText(R.id.tv_msg, spanned);
    holder.setText(R.id.tv_name, itemData.getUsername());
    holder.setText(R.id.tv_time, "操作时间：" + itemData.getExec_time());
    GlideApp.with(mContext)
            .load(itemData.getUser_image())
            .placeholder(R.drawable.round_88)//图片加载出来前，显示的图片
            .error(R.drawable.round_88)//图片加载失败后，显示的图片
            .transition(withCrossFade())
            .apply(bitmapTransform(new CircleCrop()))
            .into((ImageView) holder.getView(R.id.civ_user_img));
  }
}
