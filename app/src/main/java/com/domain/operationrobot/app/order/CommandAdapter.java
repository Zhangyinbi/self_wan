package com.domain.operationrobot.app.order;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spanned;
import com.domain.library.recycleview.dapter.RecyclerComAdapter;
import com.domain.library.recycleview.holder.ViewHolder;
import com.domain.operationrobot.R;
import java.util.List;

/**
 * Project Name : OperationRobot
 * description:null
 *
 * @author : yinbi.zhang.o
 * Create at : 2018/12/9 23:13
 */
public class CommandAdapter extends RecyclerComAdapter {
  public CommandAdapter(Context context, List data) {
    super(context, data, R.layout.adapter_command_item);
  }

  @Override
  protected void convert(ViewHolder holder, Object itemData, int position) {
    Spanned spanned = Html.fromHtml("执行命令  <font color='red'>"+"重启主机"+"</font>   温江机房大数据中心服务器（ip:201.20.103.36）");
    holder.setText(R.id.tv_msg,spanned);
  }
}
