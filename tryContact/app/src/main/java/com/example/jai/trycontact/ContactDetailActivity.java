package com.example.jai.trycontact;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ContactDetailActivity extends Activity {
    String nametobeadded, phonenotobeadded;
    EditText contactName, contactPhone;
    Button addpersonButtom;
    ArrayList<Contact> relation;
    ArrayList<Contact> objCon;
    ListView lstVwContact;
    //CArrayAdapter<Contact> contactAdapter;
    MainActivity.CustomContactAdapter contactAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_detail);


        Bundle bd=getIntent().getExtras();
        final ArrayList<Contact> addedNew= bd.getParcelableArrayList("contlist");

       // objCon = new ArrayList<Contact>();
        contactAdapter = new MainActivity().new CustomContactAdapter(this,addedNew);
        lstVwContact.setAdapter(contactAdapter);

        contactName = (EditText)findViewById(R.id.ipname);
        contactPhone = (EditText)findViewById(R.id.ipphone);
        addpersonButtom = (Button) findViewById(R.id.addpersonbtn);

        relation=new ArrayList<Contact>();
        //relation.add(0,"Megha","1111",false,);

        addpersonButtom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Contact c=new Contact(contactName,contactPhone)


                for(int i=0;i<addedNew.size();i++){
                    Contact cnt = addedNew.get(i);
                    if(!cnt.isSelected()){
                       // responseText.append("\n" + cnt.getContactName());
                        relation.add(cnt);

                    }
                }

                Contact newC=new Contact(contactName.getText().toString(),contactPhone.getText().toString(),relation);
                objCon.add(newC);


                Intent intent3=new Intent(ContactDetailActivity.this,ContactDetailActivity.class);
                Bundle mBundle = new Bundle();
                mBundle.putParcelableArrayList("newname", objCon);

                intent3.putExtras(mBundle);

               // intent3.put(mBundle);
                Toast.makeText(ContactDetailActivity.this, "inside addperson"+newC.getContactName()+" "+newC.getContactPhone(), Toast.LENGTH_SHORT).show();

                setResult(RESULT_OK,intent3);
                finish();


            }
        });
    }
}
