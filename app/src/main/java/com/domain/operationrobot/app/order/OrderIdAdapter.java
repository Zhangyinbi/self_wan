package com.domain.operationrobot.app.order;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.domain.library.recycleview.dapter.RecyclerComAdapter;
import com.domain.library.recycleview.holder.ViewHolder;
import com.domain.operationrobot.R;
import com.domain.operationrobot.http.bean.OrderIdBean;
import java.util.ArrayList;
import java.util.List;

/**
 * Project Name : OperationRobot
 * description:null
 *
 * @author : yinbi.zhang.o
 * Create at : 2018/12/15 22:23
 */
public class OrderIdAdapter extends RecyclerComAdapter<OrderIdBean.OrderIdInfo> {
  private OnItemClick mOnItemClick;

  public OrderIdAdapter(Context context, List data) {
    super(context, data, R.layout.order_id_item);
  }

  @Override
  protected void convert(ViewHolder holder, OrderIdBean.OrderIdInfo itemData, int position) {
    holder.setText(R.id.tv_group_name, itemData.getGroupName());
    LinearLayout ll = holder.getView(R.id.ll_content_view);
    ArrayList<OrderIdBean.OrderIdInfo.Action> orders = itemData.getOrders();
    if (orders != null && orders.size() > 0) {
      for (OrderIdBean.OrderIdInfo.Action action : orders) {
        View itemView = LayoutInflater.from(mContext)
                                      .inflate(R.layout.order_id_item_view, null);
        ll.addView(itemView);
        TextView tvItemName = itemView.findViewById(R.id.tv_group_item_name);
        tvItemName.setText(action.getName());
        itemView.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
            if (mOnItemClick != null) {
              mOnItemClick.OnItemClick(action.getId());
            }
          }
        });
      }
    }
  }

  public void setOnItemClick(OnItemClick onItemClick) {
    mOnItemClick = onItemClick;
  }

  public interface OnItemClick {
    void OnItemClick(String orderId);
  }
}
