package com.domain.operationrobot.app.home;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.domain.library.base.AbsFragment;
import com.domain.library.ui.CommonDialog;
import com.domain.library.utils.MyPermissionUtils;
import com.domain.library.utils.SoftInputUtil;
import com.domain.library.utils.SpUtils;
import com.domain.library.utils.ToastUtils;
import com.domain.operationrobot.BaseApplication;
import com.domain.operationrobot.R;
import com.domain.operationrobot.app.order.SelectTimeActivity;
import com.domain.operationrobot.http.bean.ChatBean;
import com.domain.operationrobot.http.bean.ImageBean;
import com.domain.operationrobot.http.bean.User;
import com.domain.operationrobot.im.bean.NewMessage;
import com.domain.operationrobot.im.bean.ObserverModel;
import com.domain.operationrobot.im.bean.RootMessage1;
import com.domain.operationrobot.im.bean.RootMessage2;
import com.domain.operationrobot.im.bean.RootMessage34;
import com.domain.operationrobot.im.bean.RootMessage6;
import com.domain.operationrobot.im.bean.RootMessage8;
import com.domain.operationrobot.im.chatroom.BaseChatRoom;
import com.domain.operationrobot.im.chatroom.MainChatRoom;
import com.domain.operationrobot.im.listener.IEventType;
import com.domain.operationrobot.im.socket.AppSocket;
import com.domain.operationrobot.util.FileUpLoadUtils;
import com.domain.operationrobot.util.IUpLoadCallBack;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import static android.app.Activity.RESULT_OK;
import static com.domain.operationrobot.im.listener.IEventType.ROOT_MESSAGE_TYPE_1;
import static com.domain.operationrobot.im.listener.IEventType.ROOT_MESSAGE_TYPE_2;
import static com.domain.operationrobot.im.listener.IEventType.ROOT_MESSAGE_TYPE_34;
import static com.domain.operationrobot.im.listener.IEventType.ROOT_MESSAGE_TYPE_6;
import static com.domain.operationrobot.im.listener.IEventType.ROOT_MESSAGE_TYPE_8;
import static com.domain.operationrobot.util.Constant.USER_SP_KEY;

/**
 * Project Name : OperationRobot
 * description:null
 *
 * @author : yinbi.zhang.o
 * Create at : 2018/10/21 13:51
 */
public class ChatRoomFragment extends AbsFragment implements Observer {
  boolean clear = false;
  private TextView     mBtnSend;
  private EditText     mEtMsg;
  private RecyclerView mRecycler;
  private ChatAdapter  mAdapter;
  IUpLoadCallBack mIUpLoadCallBack = (url, outWidth, outHeight) -> setImageMsg(url, outWidth, outHeight);
  private ImageView mIvRobot;
  private ImageView mImageView;
  private boolean   mSingle;

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
    if (mRecycler != null) {
      mRecycler.scrollToPosition(mAdapter.getItemCount() - 1);
    }
  }

  @Override
  protected void initView(View view) {
    int height = getActivity().getWindowManager()
                              .getDefaultDisplay()
                              .getHeight();
    view.findViewById(R.id.root_view)
        .setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
            SoftInputUtil.hideSoftInput(view);
          }
        });

    mEtMsg = view.findViewById(R.id.et_msg);
    mBtnSend = view.findViewById(R.id.btn_send);
    mRecycler = view.findViewById(R.id.rlv_recycler);
    mIvRobot = view.findViewById(R.id.iv_robot);
    mImageView = view.findViewById(R.id.iv_image);
    mImageView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        MyPermissionUtils.getInstance()
                         .requestPermissions(getActivity(), 1133, MyPermissionUtils.STORAGE_PERMISSION,
                           new MyPermissionUtils.OnRequestPermissionListener() {
                             @Override
                             public void onRequest(boolean isGranted, String[] permissions) {
                               if (isGranted) {
                                 openImageSelect();
                               }
                             }
                           }, new CommonDialog.Builder(getActivity()).setContent("请在手机设置中打开权限")
                                                                     .setSureText("知道了", null)
                                                                     .build());
      }
    });

    view.findViewById(R.id.tv_demo)
        .setOnClickListener((v) -> {
          startActivity(new Intent(getActivity(), SelectTimeActivity.class));
        });

    mIvRobot.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        String s = mEtMsg.getText()
                         .toString();
        s += "@机器人";
        clear = true;
        mEtMsg.setText(s);
        mEtMsg.setSelection(s.length());
      }
    });
    mEtMsg.setOnKeyListener(new View.OnKeyListener() {
      @Override
      public boolean onKey(View view, int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_DEL && event.getAction() == KeyEvent.ACTION_DOWN) {
          if ("@机器人".contains(mEtMsg.getText()
                                    .toString()) && clear) {
            mEtMsg.setText("");
            clear = false;
            return true;
          }
        }
        return false;
      }
    });
    mEtMsg.addTextChangedListener(new TextWatcher() {
      @Override
      public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if (charSequence.toString()
                        .equals("@机器人")) {
          clear = true;
        }
      }

      @Override
      public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

      }

      @Override
      public void afterTextChanged(Editable editable) {
        if (mEtMsg.getText()
                  .toString()
                  .isEmpty()) {
          mBtnSend.setVisibility(View.INVISIBLE);
        } else {
          mBtnSend.setVisibility(View.VISIBLE);
        }
      }
    });
    mEtMsg.setOnFocusChangeListener(new View.OnFocusChangeListener() {
      @Override
      public void onFocusChange(View view, boolean b) {
        if (b) {
          mRecycler.scrollToPosition(mAdapter.getItemCount() - 1);
        }
      }
    });
    mAdapter = new ChatAdapter(new ArrayList<ChatBean>(), getActivity());
    mAdapter.setHostMsg(msg -> ChatRoomFragment.this.sendMsg(msg));
    mAdapter.setRecycler(mRecycler);
    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false);
    //linearLayoutManager.setStackFromEnd(true);
    new Handler().postDelayed(new Runnable() {
      @Override
      public void run() {
        linearLayoutManager.setStackFromEnd(true);
      }
    }, 300);
    mRecycler.setLayoutManager(linearLayoutManager);
    mRecycler.setAdapter(mAdapter);
    String list = SpUtils.getDataList("chat_data" + BaseApplication.getInstance()
                                                                   .getUser()
                                                                   .getMobile());
    if (!TextUtils.isEmpty(list)) {
      Gson gson = new Gson();
      ArrayList<ChatBean> chatBeans = gson.fromJson(list, new TypeToken<ArrayList<ChatBean>>() {}.getType());
      mAdapter.addAll(chatBeans);
    }

    mBtnSend.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        sendMsg(mEtMsg.getText()
                      .toString()
                      .trim());
      }
    });
    mRecycler.setOnTouchListener(new View.OnTouchListener() {
      @Override
      public boolean onTouch(View view, MotionEvent motionEvent) {
        SoftInputUtil.hideSoftInput(view);
        view.requestFocus();
        return false;
      }
    });
  }

  private void openImageSelect() {
    PictureSelector.create(ChatRoomFragment.this)
                   .openGallery(PictureMimeType.ofImage())
                   .compress(true)
                   .forResult(PictureConfig.CHOOSE_REQUEST);
  }

  /**
   * 发送消息
   */
  private void sendMsg(String msg) {
    //String msg = mEtMsg.getText()
    //                   .toString()
    //                   .trim();
    User user = BaseApplication.getInstance()
                               .getUser();
    String mUsername = user.getUsername();
    String url = user.getImage();
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

    if (msg.contains("@机器人")) {
      AppSocket.getInstance()
               .sendMessage(2, msg);
      //if (user.getRole() == 1 || user.getRole() == 2) {
      //  ToastUtils.showToast("只有加入了公司才可以使用机器人功能");
      //  return;
      //}
    } else {
      AppSocket.getInstance()
               .sendMessage(msg);
    }
    addBeanToRecycler(mUsername, url, msg, System.currentTimeMillis(), user.getUserId());
    mEtMsg.setText("");
  }

  /**
   * 往列表的adapter中添加数据
   */
  private void addBeanToRecycler(String username, String url, String content, long l, String targetId) {
    ChatBean message = new ChatBean();
    message.setUserName(username);
    message.setContent(content);
    message.setUrl(url);
    message.setTime(l);
    message.setTargetId(targetId);
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

  /**
   * 接收消息
   */
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
              if (isSelfMsg(newMessage.getTargetId())) {
                return;
              }
              String username = newMessage.getUsername()
                                          .isEmpty() ? newMessage.getMobile()
                                                                 .isEmpty() ? "未知" : newMessage.getMobile() : newMessage.getUsername();
              String url = newMessage.getImageUrl();
              String msg = newMessage.getMsg();
              long time = newMessage.getTime();
              String targetId = newMessage.getTargetId();
              addBeanToRecycler(username, url, msg, time, targetId);
              break;
            case ROOT_MESSAGE_TYPE_1:
              rootMsg1(model);
              break;
            case ROOT_MESSAGE_TYPE_2:
              rootMsg2(model);
              break;
            case ROOT_MESSAGE_TYPE_6:
              rootMsg6(model);
              break;
            case ROOT_MESSAGE_TYPE_34:
              rootMsg34(model);
              break;
            case ROOT_MESSAGE_TYPE_8:
              rootMsg8(model);
              break;
          }
        }
      }
    });
  }

  private void rootMsg8(ObserverModel model) {
    ChatBean chatBean = new ChatBean();
    chatBean.setType(8);
    RootMessage8 rootMessage = model.getRootMessage8();
    chatBean.setUserName("机器人");
    chatBean.setTime(rootMessage.getTime());
    chatBean.setContent(rootMessage.getMsg());
    ArrayList<RootMessage8.Action> actions = rootMessage.getActions();
    chatBean.setActions8(actions);
    mAdapter.addBeanToEnd(chatBean);
  }

  /**
   * cpu 和 内存
   */
  private void rootMsg34(ObserverModel model) {
    ChatBean chatBean = new ChatBean();
    chatBean.setType(34);
    RootMessage34 rootMessage = model.getRootMessage34();
    chatBean.setUserName("机器人");
    chatBean.setTime(rootMessage.getTime());
    chatBean.setContent(rootMessage.getMsg());
    ArrayList<RootMessage34.Action> actions = rootMessage.getActions();
    chatBean.setActions34(actions);
    mAdapter.addBeanToEnd(chatBean);
  }

  /**
   * 磁盘使用情狂
   */
  private void rootMsg6(ObserverModel model) {
    ChatBean chatBean = new ChatBean();
    chatBean.setType(6);
    RootMessage6 rootMessage = model.getRootMessage6();
    chatBean.setUserName("机器人");
    chatBean.setTime(rootMessage.getTime());
    chatBean.setContent(rootMessage.getMsg());
    ArrayList<RootMessage6.Action> actions = rootMessage.getActions();
    chatBean.setActions6(actions);
    mAdapter.addBeanToEnd(chatBean);
  }

  /**
   * @机器人的消息
   */
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
   * 是不是自己发出的消息
   */
  private boolean isSelfMsg(String userId) {
    return BaseApplication.getInstance()
                          .getUser()
                          .getUserId()
                          .equals(userId);
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    setData();
    MainChatRoom.getInstance()
                .deleteObserver(this);
    AppSocket.getInstance()
             .disConnnect();
  }

  @Override
  public void onPause() {
    super.onPause();
  }

  public void setData() {
    if (SpUtils.getObject(USER_SP_KEY, User.class) != null) {
      ArrayList<ChatBean> list = mAdapter.getList();
      if (list.size() > 190) {
        for (int i = 0; i < list.size() - 189; i++) {
          list.remove(i);
        }
      }
      SpUtils.setDataList("chat_data" + BaseApplication.getInstance()
                                                       .getUser()
                                                       .getMobile(), list);
    }
  }

  /**
   * 接收选择图片回收的结果
   */
  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (resultCode == RESULT_OK) {
      switch (requestCode) {
        case PictureConfig.CHOOSE_REQUEST:
          List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
          for (LocalMedia localMedia : selectList) {
            String filePath = localMedia.getPath();
            if (localMedia.isCompressed()) {
              filePath = localMedia.getCompressPath();
            }
            uploadImage(filePath);
          }
          break;
      }
    }
  }

  /**
   * 上传图片
   */
  private void uploadImage(String filePath) {
    BitmapFactory.Options options = new BitmapFactory.Options();
    options.inJustDecodeBounds = true;
    Bitmap bitmap = BitmapFactory.decodeFile(filePath, options); // 此时返回的bitmap为null
    FileUpLoadUtils.getInstance()
                   .upLoadFile(filePath, mIUpLoadCallBack, options.outWidth, options.outHeight);
  }

  /**
   * 发送图片消息
   */
  private void setImageMsg(String path, int outWidth, int outHeight) {
    User user = BaseApplication.getInstance()
                               .getUser();
    String regex = getContext().getString(R.string.regex);
    String realMsg = "{\"url\":\"" + path + "\",\"width\":" + outWidth + ",\"height\":" + outHeight + "}";
    String imgMsg = regex + realMsg + regex;
    addBeanToRecycler(user.getUsername(), user.getImage(), imgMsg, System.currentTimeMillis(), user.getUserId());
    AppSocket.getInstance()
             .sendMessage(imgMsg);
  }
}
