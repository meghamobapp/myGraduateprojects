package com.example.FragmentConcept.FinalContactList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Thara Philips on 10/18/2016.
 */
public class CustomAdapter extends ArrayAdapter<Contact> {
    ArrayList<Contact> checkedList;

    public CustomAdapter(Context context, ArrayList<Contact> contacts) {
        super(context,R.layout.listview_item, contacts);
        checkedList = new ArrayList<>();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(getContext());
        View customView = inflater.inflate(R.layout.listview_item,parent,false);
        final Contact contactItem = getItem(position);

        TextView titleItem = (TextView)customView.findViewById(R.id.contactNameList);
        final CheckBox checkBox = (CheckBox) customView.findViewById(R.id.contactCheckBox);
        titleItem.setText(contactItem.getContactName());
        checkBox.setChecked(false);
      //  checkBox.setOnCheckedChangeListener((ContactDetails) getContext());
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {

                if(isChecked){
                    checkBox.setChecked(true);
                    checkedList.add(contactItem);
                }else {
                    checkBox.setChecked(false);
                    checkedList.remove(contactItem);
                }
               Contact.setCheckBox(isChecked);
            }
        });
        return customView;
    }
    public ArrayList<Contact> getCheckedItems() {
        return checkedList;
    }
}
