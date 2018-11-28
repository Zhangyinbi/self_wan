package com.domain.operationrobot.app.home;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.domain.library.utils.ToastUtils;
import com.domain.operationrobot.R;
import com.domain.operationrobot.im.bean.RootMessage2;
import com.domain.operationrobot.im.chatroom.MainChatRoom;
import com.domain.operationrobot.im.socket.AppSocket;
import java.util.ArrayList;

/**
 * Project Name : OperationRobot
 * description:null
 *
 * @author : yinbi.zhang.o
 * Create at : 2018/10/23 23:09
 */
public class RootBean2Adapter extends RecyclerView.Adapter<RootBean2Adapter.MyViewHolder> {

  private ArrayList<RootMessage2.Action> mActions;
  private HostInterface                  hostInterface;

  public RootBean2Adapter(ArrayList<RootMessage2.Action> actions, HostInterface hostInterface) {
    mActions = actions;
    this.hostInterface = hostInterface;
  }

  @NonNull
  @Override
  public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int type) {
    View layout = LayoutInflater.from(viewGroup.getContext())
                                .inflate(R.layout.root_adapter_item_2, viewGroup, false);
    return new MyViewHolder(layout);
  }

  @Override
  public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int position) {
    RootMessage2.Action action = mActions.get(position);
    String name = action.getName();
    final String type = action.getType();
    myViewHolder.tv_action_name.setText(name);
    myViewHolder.tv_action_name.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        new EditIpDialog(view.getContext(), Integer.parseInt(type),hostInterface).show();

        //TODO 测试代码
        //switch (Integer.parseInt(type)) {
        //  case 3://cpu
        //    String s3
        //      = "{\"data\":{\"type\":3,\"rootbean\":{\"msg\":\"当前温江机房大数据中心服务器003 (ip:201.20.103.36）CPU运行情况：\",\"actions\":[{\"title\":\"CPU\",\"ratio\":\"35\"}]}}}";
        //    MainChatRoom.getInstance()
        //                .moni(s3);
        //    break;
        //  case 4://内存
        //    String s4
        //      = "{\"data\":{\"type\":4,\"rootbean\":{\"msg\":\"当前温江机房大数据中心服务器003（ip:201.20.103.36）内存运行情况 12G/15G:\",\"actions\":[{\"title\":\"内存\",\"ratio\":\"85\"}]}}}";
        //    MainChatRoom.getInstance()
        //                .moni(s4);
        //    break;
        //  case 5:
        //    ToastUtils.showToast("sorry,暂时还没有功能");
        //    break;
        //  case 6://磁盘
        //    String s6
        //      = "{\"data\":{\"type\":6,\"rootbean\":{\"msg\":\"当前温江机房大数据中心服务器003 (ip:201.20.103.36）磁盘使用情况：\",\"actions\":[{\"title\":\"/dev/sda3\",\"ratio\":\"35\",\"totalSize\":\"100G\",\"usedSize\":\"35G\"},{\"title\":\"10.0.1.200:/Public/ytrz_bak\",\"ratio\":\"80\",\"totalSize\":\"40T\",\"usedSize\":\"10T\"},{\"title\":\"张隐蔽测试\",\"ratio\":\"95\",\"totalSize\":\"40T\",\"usedSize\":\"37T\"}]}}}";
        //    MainChatRoom.getInstance()
        //                .moni(s6);
        //    break;
        //  case 7:
        //    ToastUtils.showToast("sorry,暂时还没有功能");
        //    break;
        //  case 8:
        //String s8 = "{\"data\":{\"type\":\"8\",\"rootbean\":{\"msg\":\"网络状况\",\"actions\":[{\"time\":\"15:00\",\"out\":\"27312\",\"in\":\"7248\"},{\"time\":\"15:46\",\"out\":\"1213\",\"in\":\"277.5\"},{\"time\":\"16:00\",\"out\":\"1213.45\",\"in\":\"432.75\"},{\"time\":\"19:00\",\"out\":\"324234\",\"in\":\"4353.654\"}]}}}";
        //MainChatRoom.getInstance()
        //                .moni(s8);
        //    break;
        //}
      }
    });
  }

  @Override
  public int getItemCount() {
    return mActions != null ? mActions.size() : 0;
  }

  public class MyViewHolder extends RecyclerView.ViewHolder {
    private TextView tv_action_name;

    public MyViewHolder(@NonNull View itemView) {
      super(itemView);
      tv_action_name = itemView.findViewById(R.id.tv_action_name);
    }
  }
}
