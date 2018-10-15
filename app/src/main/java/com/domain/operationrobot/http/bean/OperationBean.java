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
    private String name;
    private String phone;
    private String id;

    protected OperationBean(Parcel in) {
        name = in.readString();
        phone = in.readString();
        id = in.readString();
    }

    public String getName() {
        return name == null ? "" : name;
    }

    public String getId() {
        return id == null ? "" : id;
    }

    public String getPhone() {
        return phone == null ? "" : phone;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(phone);
        parcel.writeString(id);
    }
}
