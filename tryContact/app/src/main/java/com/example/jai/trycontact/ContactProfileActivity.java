package com.example.jai.trycontact;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ContactProfileActivity extends AppCompatActivity {
    TextView txtvwcontactname,txtvwcontactphone;
    ArrayList<Contact> objectcontact;
    String contactnamepassed;
    Contact currentTask;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_profile);


        Intent intent=getIntent();
                Bundle bundle=intent.getExtras();
       // currentTask=bundle.getParcelable("ListofContact");
        contactnamepassed=bundle.getString("nameofperson","megha");
        //if(!contactname.isEmpty()) {
//            for (Contact d : objectcontact) {
//                if (d.getContactName().equalsIgnoreCase(contactname))
//                    currentTask = d;
//                //something here
//            }
        //}
       // Toast.makeText(getActivity(),contactnamepassed,Toast.LENGTH_LONG).show();
        txtvwcontactname=(TextView)findViewById(R.id.contactname);
        //txtvwcontactphone=(TextView)getActivity().findViewById(R.id.contactphone);

        txtvwcontactname.setText(contactnamepassed);
      //   txtvwcontactphone.setText(currentTask.getContactPhone());
      //  String contname=intent.getStringExtra("nameofperson");

       // txtvwcontactname.setText(contname);


    }
}
