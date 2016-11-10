package com.example.jai.contactsassi2;

public class Contact {

    private String contactName;
    private String contactPhone;
    private boolean selected = false;


    public Contact(String contactName, String contactPhone, boolean selected) {
        this.contactName = contactName;
        this.contactPhone = contactPhone;
        this.selected = selected;
    }

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
}