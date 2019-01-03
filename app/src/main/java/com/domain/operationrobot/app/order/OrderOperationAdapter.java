package com.domain.operationrobot.app.order;

import android.content.Context;
import android.widget.ImageView;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.domain.library.GlideApp;
import com.domain.library.recycleview.dapter.RecyclerComAdapter;
import com.domain.library.recycleview.holder.ViewHolder;
import com.domain.operationrobot.R;
import com.domain.operationrobot.http.bean.OperationList;
import com.domain.operationrobot.http.bean.OrderOperationBean;
import java.util.List;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;
import static com.domain.library.GlideOptions.bitmapTransform;

/**
 * Project Name : OperationRobot
 * description:null
 *
 * @author : yinbi.zhang.o
 * Create at : 2018/12/15 22:09
 */
public class OrderOperationAdapter extends RecyclerComAdapter<OrderOperationBean.Info> {

  public OrderOperationAdapter(Context context, List<OrderOperationBean.Info> data) {
    super(context, data, R.layout.adapter_selected_item);
  }

  @Override
  protected void convert(ViewHolder holder, OrderOperationBean.Info itemData, int position) {
    GlideApp.with(mContext)
            .load(itemData.getOperationImage())
            .placeholder(R.drawable.round_88)//图片加载出来前，显示的图片
            .error(R.drawable.round_88)//图片加载失败后，显示的图片
            .transition(withCrossFade())
            .apply(bitmapTransform(new CircleCrop()))
            .into((ImageView) holder.getView(R.id.civ_user_img));
    holder.setText(R.id.tv_name, itemData.getOperationName());
  }
}
