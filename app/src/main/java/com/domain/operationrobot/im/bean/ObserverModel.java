package com.domain.operationrobot.im.bean;

/**
 * @author silencezwm on 2017/8/25 下午12:06
 * @email silencezwm@gmail.com
 * @description 观察者实体类
 */
public class ObserverModel {
  private RootMessage11 mRootMessage11;
  //有效的
  private String        mEventType;
  private NewMessage    mNewMessage;
  private RootMessage1  mRootMessage1;
  private RootMessage2  mRootMessage2;
  //磁盘使用
  private RootMessage6  mRootMessage6;
  //cpu 和 内存
  private RootMessage34 mRootMessage34;
  private RootMessage8  mRootMessage8;
  //      无效的
  private Login         mLogin;
  private UserJoined    mUserJoined;
  private UserLeft      mUserLeft;
  private Typing        mTyping;
  private StopTyping    mStopTyping;

  private boolean flag;

  public boolean getFlag(){
    return flag;
  }

  public void setFlag(boolean flag) {
    this.flag = flag;
  }

  private UpDataMsg upDataMsg;

  public UpDataMsg getUpDataMsg() {
    return  upDataMsg;
  }

  public void setUpDataMsg(UpDataMsg upDataMsg) {
    this.upDataMsg = upDataMsg;
  }

  public RootMessage11 getRootMessage11() {
    return mRootMessage11;
  }

  public void setRootMessage11(RootMessage11 mRootMessage11) {
    this.mRootMessage11 = mRootMessage11;
  }

  public RootMessage8 getRootMessage8() {
    return mRootMessage8;
  }

  public void setRootMessage8(RootMessage8 rootMessage8) {
    mRootMessage8 = rootMessage8;
  }

  public RootMessage34 getRootMessage34() {
    return mRootMessage34;
  }

  public void setRootMessage34(RootMessage34 rootMessage34) {
    mRootMessage34 = rootMessage34;
  }

  public RootMessage2 getRootMessage2() {
    return mRootMessage2;
  }

  public void setRootMessage2(RootMessage2 rootMessage2) {
    mRootMessage2 = rootMessage2;
  }

  public RootMessage1 getRootMessage1() {
    return mRootMessage1;
  }

  public void setRootMessage1(RootMessage1 rootMessage1) {
    mRootMessage1 = rootMessage1;
  }

  public String getEventType() {
    return mEventType;
  }

  public void setEventType(String eventType) {
    mEventType = eventType;
  }

  public Login getLogin() {
    return mLogin;
  }

  public void setLogin(Login login) {
    mLogin = login;
  }

  public NewMessage getNewMessage() {
    return mNewMessage;
  }

  public void setNewMessage(NewMessage newMessage) {
    mNewMessage = newMessage;
  }

  public UserJoined getUserJoined() {
    return mUserJoined;
  }

  public void setUserJoined(UserJoined userJoined) {
    mUserJoined = userJoined;
  }

  public UserLeft getUserLeft() {
    return mUserLeft;
  }

  public void setUserLeft(UserLeft userLeft) {
    mUserLeft = userLeft;
  }

  public Typing getTyping() {
    return mTyping;
  }

  public void setTyping(Typing typing) {
    mTyping = typing;
  }

  public StopTyping getStopTyping() {
    return mStopTyping;
  }

  public void setStopTyping(StopTyping stopTyping) {
    mStopTyping = stopTyping;
  }

  public RootMessage6 getRootMessage6() {
    return mRootMessage6;
  }

  public void setRootMessage6(RootMessage6 rootMessage6) {
    mRootMessage6 = rootMessage6;
  }

  public static class Login {
    private int numUsers;

    public int getNumUsers() {
      return numUsers;
    }

    public void setNumUsers(int numUsers) {
      this.numUsers = numUsers;
    }
  }

  public static class UserJoined {
    private String username;
    private int    numUsers;

    public String getUsername() {
      return username;
    }

    public void setUsername(String username) {
      this.username = username;
    }

    public int getNumUsers() {
      return numUsers;
    }

    public void setNumUsers(int numUsers) {
      this.numUsers = numUsers;
    }
  }

  public static class UserLeft {
    private String username;
    private int    numUsers;

    public String getUsername() {
      return username;
    }

    public void setUsername(String username) {
      this.username = username;
    }

    public int getNumUsers() {
      return numUsers;
    }

    public void setNumUsers(int numUsers) {
      this.numUsers = numUsers;
    }
  }

  public static class Typing {
    private String username;

    public String getUsername() {
      return username;
    }

    public void setUsername(String username) {
      this.username = username;
    }
  }

  public static class StopTyping {
    private String username;

    public String getUsername() {
      return username;
    }

    public void setUsername(String username) {
      this.username = username;
    }
  }
}
