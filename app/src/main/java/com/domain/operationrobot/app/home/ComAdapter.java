package com.domain.operationrobot.app.home;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import com.domain.library.recycleview.dapter.RecyclerComAdapter;
import com.domain.library.recycleview.holder.ViewHolder;
import com.domain.library.recycleview.interfaces.MultiTypeSupport;
import java.util.List;

/**
 * Project Name : OperationRobot
 * description:null
 *
 * @author : yinbi.zhang.o
 * Create at : 2018/12/3 22:35
 */
public class ComAdapter extends RecyclerComAdapter {
  public ComAdapter(Context context, List data, MultiTypeSupport typeSupport) {
    super(context, data, typeSupport);
  }

  @Override
  protected void convert(ViewHolder viewHolder, Object itemData, int position) {

  }
}
