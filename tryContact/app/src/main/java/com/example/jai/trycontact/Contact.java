package com.example.jai.trycontact;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Contact implements Parcelable {

    private String contactName;
    private String contactPhone;
    private ArrayList<Contact> relation;
    public boolean isSelected;

    public boolean isSelected() {
        return true;
    }

    public void setSelected(boolean isSelected){
        this.isSelected = isSelected;
    }

    boolean isBoxCheched;


    public Contact(String contactName, String contactPhone, ArrayList<Contact> relation) {
        this.contactName = contactName;
        this.contactPhone = contactPhone;
        this.relation = relation;

    }

    protected Contact(Parcel in) {
        contactName = in.readString();
        contactPhone = in.readString();
        relation = in.createTypedArrayList(Contact.CREATOR);
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

    public ArrayList<Contact> getRelation() {
        return relation;
    }

    public void setRelation(ArrayList<Contact> relation) {
        this.relation = relation;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(contactName);
        dest.writeString(contactPhone);
        dest.writeTypedList(relation);
    }
}