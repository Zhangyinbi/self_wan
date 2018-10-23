package com.domain.operationrobot.app.home;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import com.domain.library.base.AbsFragment;
import com.domain.library.utils.App;
import com.domain.library.utils.SpUtils;
import com.domain.operationrobot.BaseApplication;
import com.domain.operationrobot.R;
import com.domain.operationrobot.http.bean.ChatBean;
import com.domain.operationrobot.http.bean.RootBean;
import com.domain.operationrobot.im.bean.NewMessage;
import com.domain.operationrobot.im.bean.ObserverModel;
import com.domain.operationrobot.im.bean.RootMessage1;
import com.domain.operationrobot.im.bean.RootMessage2;
import com.domain.operationrobot.im.chatroom.BaseChatRoom;
import com.domain.operationrobot.im.chatroom.MainChatRoom;
import com.domain.operationrobot.im.listener.IEventType;
import com.domain.operationrobot.im.socket.AppSocket;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import org.json.JSONException;
import org.json.JSONObject;

import static com.domain.operationrobot.im.listener.IEventType.ROOT_MESSAGE_TYPE_1;
import static com.domain.operationrobot.im.listener.IEventType.ROOT_MESSAGE_TYPE_2;

/**
 * Project Name : OperationRobot
 * description:null
 *
 * @author : yinbi.zhang.o
 * Create at : 2018/10/21 13:51
 */
public class ChatRoomFragment extends AbsFragment implements Observer {
  private ImageButton  mBtnSend;
  private EditText     mEtMsg;
  private RecyclerView mRecycler;
  private ChatAdapter  mAdapter;

  public static ChatRoomFragment newInstance() {
    ChatRoomFragment fragment = new ChatRoomFragment();
    Bundle args = new Bundle();
    fragment.setArguments(args);
    return fragment;
  }

  @Override
  protected int getLayoutId() {
    return R.layout.fragment_chat_room;
  }

  @Override
  protected void newInstancePresenter() {
    MainChatRoom.getInstance()
                .addObserver(this);
  }

  @Override
  public void onResume() {
    super.onResume();
  }

  @Override
  protected void initView(View view) {
    mEtMsg = view.findViewById(R.id.et_msg);
    mBtnSend = view.findViewById(R.id.btn_send);
    mRecycler = view.findViewById(R.id.rlv_recycler);

    mAdapter = new ChatAdapter(new ArrayList<ChatBean>());
    mAdapter.setRecycler(mRecycler);
    mRecycler.setLayoutManager(new LinearLayoutManager(this.getContext()));
    mRecycler.setAdapter(mAdapter);
    String list = SpUtils.getDataList("chat_data");
    if (!TextUtils.isEmpty(list)) {
      Gson gson = new Gson();
      ArrayList<ChatBean> chatBeans = gson.fromJson(list, new TypeToken<ArrayList<ChatBean>>() {}.getType());
      mAdapter.addAll(chatBeans);
    }

    mBtnSend.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        String msg = mEtMsg.getText()
                           .toString()
                           .trim();
        String mUsername = BaseApplication.getInstance()
                                          .getUser()
                                          .getUsername();
        String url = BaseApplication.getInstance()
                                    .getUser()
                                    .getImage();
        if (null == mUsername) {
          return;
        }

        if (TextUtils.isEmpty(msg)) {
          return;
        }
        if (!AppSocket.getInstance()
                      .isConnected()) {
          MainChatRoom.init();
          return;
        }

        addBeanToRecycler(mUsername, url, msg, System.currentTimeMillis());
        mEtMsg.setText("");
        AppSocket.getInstance()
                 .sendMessage(msg);
      }
    });
  }

  private void addBeanToRecycler(String username, String url, String content, long l) {
    ChatBean message = new ChatBean();
    message.setUserName(username);
    message.setContent(content);
    message.setUrl(url);
    message.setTime(l);
    mAdapter.addBeanToEnd(message);
  }

  @Override
  protected void initData() {

  }

  @Override
  public void back() {

  }

  @Override
  public void showEmptyView() {

  }

  @Override
  public void update(final Observable observable, final Object o) {
    getActivity().runOnUiThread(new Runnable() {
      @Override
      public void run() {
        if (observable instanceof BaseChatRoom) {
          final ObserverModel model = (ObserverModel) o;
          switch (model.getEventType()) {
            case IEventType.NEW_MESSAGE:
              NewMessage newMessage = model.getNewMessage();
              if (isSelfMsg(newMessage.getToken())) {
                return;
              }
              String username = newMessage.getUsername()
                                          .isEmpty() ? newMessage.getMobile()
                                                                 .isEmpty() ? "未知" : newMessage.getMobile() : newMessage.getUsername();
              String url = newMessage.getImageUrl();
              String msg = newMessage.getMsg();
              long time = newMessage.getTime();
              addBeanToRecycler(username, url, msg, time);
              break;
            case ROOT_MESSAGE_TYPE_1:
              rootMsg1(model);
              break;
            case ROOT_MESSAGE_TYPE_2:
              rootMsg2(model);
              break;
          }
        }
      }
    });
  }

  private void rootMsg2(ObserverModel model) {
    ChatBean chatBean = new ChatBean();
    chatBean.setType(2);
    RootMessage2 rootMessage = model.getRootMessage2();
    chatBean.setUserName("机器人");
    chatBean.setTime(rootMessage.getTime());
    chatBean.setContent(rootMessage.getMsg());
    ArrayList<RootMessage2.Action> actions = rootMessage.getActions();
    chatBean.setActions(actions);
    mAdapter.addBeanToEnd(chatBean);
  }

  /**
   * 更新机器人普通消息
   */
  private void rootMsg1(ObserverModel model) {
    ChatBean chatBean = new ChatBean();
    chatBean.setType(1);
    RootMessage1 rootMessage = model.getRootMessage1();
    chatBean.setUserName("机器人");
    chatBean.setTime(rootMessage.getTime());
    chatBean.setContent(rootMessage.getMsg());
    mAdapter.addBeanToEnd(chatBean);
  }

  /**
   * 是不是自己消息
   */
  private boolean isSelfMsg(String token) {
    return BaseApplication.getInstance()
                          .getUser()
                          .getToken()
                          .equals(token);
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    ArrayList<ChatBean> list = mAdapter.getList();
    if (list.size() > 20) {
      for (int i = 0; i < list.size() - 19; i++) {
        list.remove(i);
      }
    }
    SpUtils.setDataList("chat_data", list);
    MainChatRoom.getInstance()
                .deleteObserver(this);
    AppSocket.getInstance()
             .disConnnect();
  }
}
