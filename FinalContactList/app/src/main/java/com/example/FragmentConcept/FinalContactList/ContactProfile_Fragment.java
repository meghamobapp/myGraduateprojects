package com.example.FragmentConcept.FinalContactList;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class ContactProfile_Fragment extends Fragment {

    Activity myActivity;
    TextView nameProfile, phoneprofile;
    ListView relationListProfile;
    Contact contactDetails;
    OnDataPass dataPasser;
    @Override
    public void onAttach(Context a) {
        super.onAttach(a);
        dataPasser = (OnDataPass) a;
    }    public ContactProfile_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.contact_profile_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        myActivity = getActivity();
        Log.e("In profile", "Contact Profile");
        nameProfile = (TextView) myActivity.findViewById(R.id.contactNameProfile);
        phoneprofile = (TextView)myActivity.findViewById(R.id.contactPhoneProfile);
        relationListProfile = (ListView) myActivity.findViewById(R.id.relationListProfile);
        Log.e("Contact Name" ,""+contactDetails.getContactName());
        Log.e("Contact Phone" ,""+contactDetails.getContactPhone());
        nameProfile.setText(contactDetails.getContactName());
        phoneprofile.setText(contactDetails.getContactPhone());
        final CustomAdapter customAdapter = new CustomAdapter(myActivity,Contact.getContactHashmap().get(contactDetails.getContactName()));
        relationListProfile.setAdapter(customAdapter);


        relationListProfile.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                                       @Override
                                                       public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                                           Log.e("In click", "" + Contact.getContactArrayList().get(i).getContactName());
                                                           dataPasser.passContact(Contact.getContactArrayList().get(i));

                                                       }
                                                   }
                );


    }
            public void data(Contact contact) {
                contactDetails = contact;
            }



}