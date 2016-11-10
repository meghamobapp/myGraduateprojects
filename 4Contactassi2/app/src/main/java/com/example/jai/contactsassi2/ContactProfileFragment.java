package com.example.jai.contactsassi2;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class ContactProfileFragment extends Fragment {


    TextView txtvwcontactname,txtvwcontactphone;
    public ContactProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_contact_profile, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Intent intent=getActivity().getIntent();
        String contname=intent.getStringExtra("nameofperson");
        txtvwcontactname=(TextView)getActivity().findViewById(R.id.contactname);
        txtvwcontactphone=(TextView)getActivity().findViewById(R.id.contactphone);
        txtvwcontactname.setText(contname);


    }
    public void updateFragment(String name){

        txtvwcontactname.setText(name);


    }
}
