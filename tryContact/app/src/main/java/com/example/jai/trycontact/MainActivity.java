package com.example.jai.trycontact;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {
    static final int reqcode=123;
    ArrayList<Contact> objContact;
    String nametobeadded, phonenotobeadded;
    ListView lstVwContact;
    ArrayAdapter<Contact> contactAdapter;
    Button addButton;
    Button deleteButton;
    //EditText contactName, contactPhone;
    CheckBox chkdelete;
    Scanner scan;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lstVwContact = (ListView) findViewById(R.id.lstvwidContact);
        addButton = (Button)findViewById(R.id.addbtn);
        deleteButton = (Button) findViewById(R.id.deletebtn);
        //  contactName = (EditText) findViewById(R.id.ipname);
        //contactPhone = (EditText) findViewById(R.id.ipphone);
        chkdelete = (CheckBox) findViewById(R.id.item_checkBtnSelect);

        objContact = new ArrayList<Contact>();

        contactAdapter = new CustomContactAdapter(this, objContact);
        lstVwContact.setAdapter(contactAdapter);

        populateContact();
        // addButtonclick();
        checkButtonClick();
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(getActivity(),"inside add button",Toast.LENGTH_SHORT).show();
//                Intent intent2=new Intent(getActivity(),ContactDetailActivity.class);
//                startActivity(intent2);
                Intent intentback=new Intent(MainActivity.this,ContactDetailActivity.class);
                Bundle mBundle = new Bundle();

                mBundle.putParcelableArrayList("contlist", objContact);
               // System.out.print("bundle to"+objContact.get(0));

                intentback.putExtras(mBundle);

                startActivityForResult(intentback,reqcode);
            }
        });
    }
    private void checkButtonClick() {

        Button myButton = (Button) findViewById(R.id.deletebtn);
        myButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringBuffer responseText = new StringBuffer();
                responseText.append("The following were selected...\n");

                //ArrayList<Contact> contactList = (ArrayList<Contact>)lstVwContact.getAdapter();
                for(int i=0;i<objContact.size();i++){
                    Contact cnt = objContact.get(i);
                    if(!cnt.isSelected()){
                        responseText.append("\n" + cnt.getContactName());
                        objContact.remove(i);
                        contactAdapter.notifyDataSetChanged();

                    }
                }


                Toast.makeText(getApplicationContext(),
                        responseText, Toast.LENGTH_LONG).show();
            }
        });


    }
        public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(reqcode==requestCode)
        {
            Bundle bd=data.getExtras();
            ArrayList<Contact> addedNew= bd.getParcelableArrayList("newname");
            contactAdapter = new CustomContactAdapter(this, addedNew);
            lstVwContact.setAdapter(contactAdapter);
           // ArrayList<Contact> addedNew=(ArrayList<Contact>) ("newname");

           // Toast.makeText(MainActivity.this,"got back"+val,Toast.LENGTH_SHORT).show();
        }
    }
    private void populateContact() {
        //objContact.add(new Contact("megha","1234",false));
        //objContact.add(new Contact("jai","5678",false));
    }





    public class CustomContactAdapter extends ArrayAdapter<Contact> {

        private int layout;
        private Context context;


        public CustomContactAdapter(Context context, ArrayList<Contact> contact) {
            super(context, R.layout.contact_view, contact);
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            View itemview = inflater.inflate(R.layout.contact_view, parent, false);

            final Contact currentTask = getItem(position);
            final TextView itmtitleview = (TextView) itemview.findViewById(R.id.item_txtname);
            // TextView itmdescview = (TextView) itemview.findViewById(R.id.item_txtdesc);
            final CheckBox itmchkview = (CheckBox) itemview.findViewById(R.id.item_checkBtnSelect);

            itmtitleview.setText(currentTask.getContactName());
            // itmdescview.setText(currentTask.getTaskDesc());
            itmchkview.setChecked(currentTask.isSelected());
            itmchkview.setTag(currentTask);

            itmtitleview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String Name=currentTask.getContactName();
                    Intent intentprofile=new Intent(MainActivity.this,ContactProfileActivity.class);
                    // intent.putExtra("ListofContact",currentTask);
                    intentprofile.putExtra("nameofperson",Name);
                    startActivity(intentprofile);

                }
            });


            itmchkview.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    CheckBox cb = (CheckBox) buttonView;
                    cb.setChecked(true);
                    final Contact checkedTask = (Contact) cb.getTag();
                    Toast.makeText(getApplicationContext(),
                            "Clicked on Checkbox: " + checkedTask.getContactName() + cb.getText() +
                                    " is " + cb.isChecked(),
                            Toast.LENGTH_LONG).show();
                    checkedTask.setSelected(cb.isChecked());
                    cb.setChecked(true);


                }
            });

            return itemview;
            //return super.getView(position, convertView, parent);

        }



    }

}
