package com.example.FragmentConcept.FinalContactList;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.HashMap;


public class Contact implements Parcelable {
    String contactName;
    String contactPhone;



   static boolean checkBox = false;
    public static ArrayList<Contact> contactArrayList = new ArrayList<>();
    public static HashMap<String,ArrayList<Contact>> contactHashmap = new HashMap<>();

    public boolean isCheckBox() {
        return checkBox;
    }

    public static void setCheckBox(boolean checkBox1) {
        checkBox = checkBox1;
    }

    public static ArrayList<Contact> getContactArrayList() {
        return contactArrayList;
    }

    public  void setContactArrayList(Contact contactArrayList) {
        this.contactArrayList.add(contactArrayList);
    }

    public void removeContactArrayList(Contact contactobj){
        contactArrayList.remove(contactobj);
    }

    public static HashMap<String, ArrayList<Contact>> getContactHashmap() {
        return contactHashmap;
    }

    public  void setContactHashmap(String key, ArrayList<Contact> listOfRealations) {
        Contact.contactHashmap.put(key,listOfRealations);
    }

    public static void removeContactHashmap(String key){
        Contact.contactHashmap.remove(key);
    }

    public static void removeObject(String key,Contact cc){Contact.contactHashmap.get(key).remove(cc);}

    Contact(String contactName , String contactPhone){
        this.contactName = contactName;
        this.contactPhone = contactPhone;
    }

    protected Contact(Parcel in) {
        contactName = in.readString();
        contactPhone = in.readString();
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(contactName);
        parcel.writeString(contactPhone);
    }
}
