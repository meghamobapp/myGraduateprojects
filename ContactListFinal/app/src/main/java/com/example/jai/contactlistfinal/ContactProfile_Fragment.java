package com.example.jai.contactlistfinal;



import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
    public ContactProfile_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       // contactDetails = getArguments().getParcelable("contact");
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


        //Intent intent = new Intent();
      //  Contact contactDetails=getIntent().getExtras().getParcelable("contact");
        Log.e("Contact Name" ,""+contactDetails.getContactName());
        Log.e("Contact Phone" ,""+contactDetails.getContactPhone());
        nameProfile.setText(contactDetails.getContactName());
        phoneprofile.setText(contactDetails.getContactPhone());
        final CustomAdapter customAdapter = new CustomAdapter(myActivity,Contact.getContactHashmap().get(contactDetails.getContactName()));
        relationListProfile.setAdapter(customAdapter);
    }
    public void data(Contact contact){
        contactDetails = contact;
    }
}
