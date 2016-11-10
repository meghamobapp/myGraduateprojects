package com.example.jai.contactsassi2;

import android.os.Parcel;
import android.os.Parcelable;

import static java.lang.System.in;

public class Contact implements Parcelable {

    private String contactName;
    private String contactPhone;
    private boolean selected = false;


    public Contact(String contactName, String contactPhone, boolean selected) {
        this.contactName = contactName;
        this.contactPhone = contactPhone;
        this.selected = selected;
    }

    protected Contact(Parcel in) {
        contactName = in.readString();
        contactPhone = in.readString();
        selected = in.readByte() != 0;
    }

    public static final Creator<Contact> CREATOR = new Creator<Contact>() {
        @Override
        public Contact createFromParcel(Parcel in) {
            return new Contact(in);
        }

        @Override
        public Contact[] newArray(int size) {
            return new Contact[size];
        }
    };

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(contactName);
        dest.writeString(contactPhone);
        dest.writeByte((byte) (selected ? 1 : 0));
    }



}