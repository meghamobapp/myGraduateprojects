package com.mobileappclass.listcustomlayoutfile;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Jai on 11/3/2016.
 */

public class Task implements Parcelable {
    String title;
    String desc;
    boolean chk;


    public Task(String desc, String title) {
        this.desc = desc;
        this.title = title;

    }


    protected Task(Parcel in) {
        title = in.readString();
        desc = in.readString();
        chk = in.readByte() != 0;
    }

    public static final Creator<Task> CREATOR = new Creator<Task>() {
        @Override
        public Task createFromParcel(Parcel in) {
            return new Task(in);
        }

        @Override
        public Task[] newArray(int size) {
            return new Task[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public boolean isChk() {
        return chk;
    }

    public void setChk(boolean chk) {
        this.chk = chk;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(desc);
        dest.writeByte((byte) (chk ? 1 : 0));
    }
}
