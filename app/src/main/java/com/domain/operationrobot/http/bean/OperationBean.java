package com.domain.operationrobot.http.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Project Name : OperationRobot
 * description:null
 *
 * @author : yinbi.zhang.o
 * Create at : 2018/10/15 00:04
 */
public class OperationBean implements Parcelable {

  public static final Creator<OperationBean> CREATOR = new Creator<OperationBean>() {
    @Override
    public OperationBean createFromParcel(Parcel in) {
      return new OperationBean(in);
    }

    @Override
    public OperationBean[] newArray(int size) {
      return new OperationBean[size];
    }
  };
  private String opUserName;
  private String opMobile;
  private String userId;
  private String opCompanyId;

  public OperationBean(String opusername, String opmobile, String userid, String opcompanyid) {
    this.opCompanyId = opcompanyid;
    this.opMobile = opmobile;
    this.opUserName = opusername;
    this.userId = userid;
  }

  protected OperationBean(Parcel in) {
    opUserName = in.readString();
    opMobile = in.readString();
    userId = in.readString();
    opCompanyId = in.readString();
  }

  public String getUserId() {
    return userId == null ? "" : userId;
  }

  public String getOpCompanyId() {
    return opCompanyId == null ? "" : opCompanyId;
  }

  public String getOpMobile() {
    return opMobile == null ? "" : opMobile;
  }

  public String getOpUserName() {
    return opUserName == null ? "" : opUserName;
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel parcel, int i) {
    parcel.writeString(opUserName);
    parcel.writeString(opMobile);
    parcel.writeString(userId);
    parcel.writeString(opCompanyId);
  }
}
