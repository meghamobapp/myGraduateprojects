package com.example.jai.contactsassi2;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.ListIterator;


public class ContactProfileFragment extends Fragment {


    TextView txtvwcontactname,txtvwcontactphone;
    ArrayList<Contact> objectcontact;
    String contactnamepassed;
    Contact currentTask;
    public ContactProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       // inflater = LayoutInflater.from(getContext());
        //View itemview = inflater.inflate(R.layout.contact_view, container, false);

        //itmchkview.setChecked(currentTask.isSelected());
       // itmchkview.setTag(currentTask);

        return inflater.inflate(R.layout.contact_view, container, false);



    //public View getView(int position, View convertView, ViewGroup parent) {
       // LayoutInflater inflater = LayoutInflater.from(getContext());
       // View itemview = inflater.inflate(R.layout.contact_view, parent, false);


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Intent intent=getActivity().getIntent();
//        Bundle bundle=intent.getExtras();
//       // currentTask=bundle.getParcelable("ListofContact");
//        contactnamepassed=bundle.getString("nameofperson","megha");
//        //if(!contactname.isEmpty()) {
////            for (Contact d : objectcontact) {
////                if (d.getContactName().equalsIgnoreCase(contactname))
////                    currentTask = d;
////                //something here
////            }
//        //}
//        Toast.makeText(getActivity(),contactnamepassed,Toast.LENGTH_LONG).show();
//        txtvwcontactname=(TextView)getActivity().findViewById(R.id.contactname);
//        //txtvwcontactphone=(TextView)getActivity().findViewById(R.id.contactphone);
//
//        txtvwcontactname.setText(contactnamepassed);
       // txtvwcontactphone.setText(currentTask.getContactPhone());
        String contname=intent.getStringExtra("nameofperson");

        txtvwcontactname.setText(contname);


    }
    public void updateFragment(String name){

        txtvwcontactname.setText(name);


    }
}
