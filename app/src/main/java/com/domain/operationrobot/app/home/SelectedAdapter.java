package com.domain.operationrobot.app.home;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.domain.library.GlideApp;
import com.domain.library.recycleview.dapter.RecyclerComAdapter;
import com.domain.library.recycleview.holder.ViewHolder;
import com.domain.operationrobot.BaseApplication;
import com.domain.operationrobot.R;
import com.domain.operationrobot.http.bean.OperationList;
import java.util.List;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;
import static com.domain.library.GlideOptions.bitmapTransform;

/**
 * Project Name : OperationRobot
 * description:null
 *
 * @author : yinbi.zhang.o
 * Create at : 2018/12/4 23:29
 */
public class SelectedAdapter extends RecyclerComAdapter<OperationList.OperationInfo> {

  public SelectedAdapter(Context context, List<OperationList.OperationInfo> data) {
    super(context, data, R.layout.adapter_selected_item);
  }

  @Override
  protected void convert(ViewHolder holder, OperationList.OperationInfo itemData, int position) {
    GlideApp.with(mContext)
            .load(itemData.getImageUrl())
            .placeholder(R.drawable.round_88)//图片加载出来前，显示的图片
            .error(R.drawable.round_88)//图片加载失败后，显示的图片
            .transition(withCrossFade())
            .apply(bitmapTransform(new CircleCrop()))
            .into((ImageView) holder.getView(R.id.civ_user_img));
    holder.setText(R.id.tv_name, itemData.getOpusername());
  }
}
