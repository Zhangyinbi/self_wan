package com.domain.operationrobot.app.home.holder;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.domain.library.GlideApp;
import com.domain.library.recycleview.holder.ViewHolder;
import com.domain.library.utils.App;
import com.domain.operationrobot.BaseApplication;
import com.domain.operationrobot.R;
import com.domain.operationrobot.http.bean.ChatBean;
import com.domain.operationrobot.im.socket.AppSocket;
import com.domain.operationrobot.util.TimeUtil;

/**
 * Project Name : OperationRobot
 * description:null
 *
 * @author : yinbi.zhang.o
 * Create at : 2018/12/3 22:43
 */
public class ViewHolder11 {

  /**
   * {@link **R.layout.root_item_11}
   */
  public static void covert(ViewHolder holder, ChatBean chatBean, int position) {

    holder.setText(R.id.tv_name, chatBean.getUserName());
    holder.setText(R.id.tv_content, chatBean.getContent());
    GlideApp.with(holder.itemView.getContext())
            .load(chatBean.getUrl())
            .placeholder(R.drawable.root_40)
            .error(R.drawable.root_40)
            .into((ImageView) holder.getView(R.id.iv_user_img));

    ImageView refuse = holder.getView(R.id.iv_jujue);
    ImageView agree = holder.getView(R.id.iv_tongyi);
    LinearLayout llOrder = holder.getView(R.id.ll_order);
    int oprole = BaseApplication.getInstance()
                                .getUser()
                                .getOprole();
    if (oprole == 4 || oprole == 6) {
      String action = chatBean.getAction();
      String result = "请求错误";
      if (!TextUtils.isEmpty(action)) {
        holder.setVisible(R.id.tv_result, true);
        if ("denied".equals(action)) {
          result = "已拒绝执行命令";
        } else if ("agree".equals(action)) {
          result = "已同意执行命令";
        }
        holder.setText(R.id.tv_result, result);
        llOrder.setVisibility(View.GONE);
        return;
      }
      holder.setVisible(R.id.tv_result, false);
      llOrder.setVisibility(View.VISIBLE);
    } else {
      holder.setVisible(R.id.tv_result, false);
      llOrder.setVisibility(View.GONE);
    }
    refuse.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        AppSocket.getInstance()
                 .sendRobotMessage12(chatBean.getIp(), "denied", chatBean.getMsgid());
      }
    });

    agree.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        AppSocket.getInstance()
                 .sendRobotMessage12(chatBean.getIp(), "agree", chatBean.getMsgid());
      }
    });
  }
}
