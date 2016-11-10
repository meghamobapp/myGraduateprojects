package com.example.jai.contactsassi2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ContactDetailActivity extends AppCompatActivity {


   // Button addButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_detail);






//        addButton = (Button) findViewById(R.id.addbtn);
//        //add button click
//        addButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                nametobeadded = contactName.getText().toString();
//                phonenotobeadded = contactPhone.getText().toString();
//                if (!nametobeadded.isEmpty()) {
//                    objContact.add(new Contact(nametobeadded, phonenotobeadded, false));
//                    contactAdapter.notifyDataSetChanged();
//                    contactName.setText("");
//                    contactPhone.setText("");
//                } else
//                    Toast.makeText(ContactDetailActivity.this, "can not be added", Toast.LENGTH_SHORT).show();
//            }
//        });
    }


}
