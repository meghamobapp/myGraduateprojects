package com.example.FragmentConcept.FinalContactList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;


public class ContactProfile extends AppCompatActivity {

    TextView nameProfile, phoneprofile;
    ListView relationListProfile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_profile_layout);
        Log.e("In profile", "Contact Profile");
        nameProfile = (TextView) findViewById(R.id.contactNameProfile);
        phoneprofile = (TextView)findViewById(R.id.contactPhoneProfile);
        relationListProfile = (ListView) findViewById(R.id.relationListProfile);


        Contact contactDetails=getIntent().getExtras().getParcelable("contact");
        Log.e("Contact Name" ,""+contactDetails.getContactName());
        Log.e("Contact Phone" ,""+contactDetails.getContactPhone());
        nameProfile.setText(contactDetails.getContactName());
        phoneprofile.setText(contactDetails.getContactPhone());
        if(Contact.getContactHashmap().get(contactDetails.getContactName())!=null) {
            CustomAdapter customAdapter = new CustomAdapter(this, Contact.getContactHashmap().get(contactDetails.getContactName()));
            relationListProfile.setAdapter(customAdapter);
        }

        relationListProfile.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intentP = new Intent(ContactProfile.this, ContactProfile.class);
                intentP.putExtra("contact",Contact.getContactArrayList().get(i));
                startActivity(intentP);
            }
        });
    }

}
