package com.example.jai.contactsassi2;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.PrintStream;
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

        lstVwContact = (ListView) getActivity().findViewById(R.id.lstvwidContact);
        addButton = (Button) getActivity().findViewById(R.id.addbtn);
        deleteButton = (Button) getActivity().findViewById(R.id.deletebtn);
        //  contactName = (EditText) findViewById(R.id.ipname);
        //contactPhone = (EditText) findViewById(R.id.ipphone);
        chkdelete = (CheckBox) getActivity().findViewById(R.id.item_checkBtnSelect);

        objContact = new ArrayList<Contact>();
        contactAdapter = new MainFragment.CustomContactAdapter(getActivity(), objContact);
        lstVwContact.setAdapter(contactAdapter);

        populateContact();
        //addButtonclick();
        Button addButton = (Button) getActivity().findViewById(R.id.addbtn);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"inside add button",Toast.LENGTH_SHORT).show();
                Intent intent2=new Intent(getActivity(),ContactDetailActivity.class);
                intent2.putExtra("message","startacti");



                if(getResources().getConfiguration().orientation== Configuration.ORIENTATION_PORTRAIT)
                {
                    startActivity(intent2);
                }
                else
                {

                    ContactDetailFragment frag;
                    frag = (ContactDetailFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.detailfragment2);
                    //frag.updateFragment(Name);

                }


            }
        });

        checkButtonClick();
//        addButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                intent=new Intent(getActivity(),ContactDetailActivity.class);
//                startActivityForResult(intent,reqcode);
//            }
//        });

    }

//    private void addButtonclick() {
//        Button addButton = (Button) getActivity().findViewById(R.id.addbtn);
//        addButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(getActivity(),"inside add button",Toast.LENGTH_SHORT).show();
//                Intent intent2=new Intent(getActivity(),ContactDetailActivity.class);
//                intent2.putExtra("message","startacti");
//
//                startActivity(intent2);
////                Toast.makeText(getActivity(),"inside add button",Toast.LENGTH_SHORT).show();
////
////                intent=new Intent(getActivity(),ContactDetailActivity.class);
////                startActivityForResult(intent,reqcode);
//
//            }
//        });
//
//    }

//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if(reqcode==requestCode)
//        {
//            String val= data.getStringExtra("newname");
//            Toast.makeText(getActivity(),"got back"+val,Toast.LENGTH_SHORT).show();
//        }
//    }

    private void checkButtonClick() {

        Button myButton = (Button) getActivity().findViewById(R.id.deletebtn);
        myButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringBuffer responseText = new StringBuffer();
                responseText.append("The following were selected...\n");

                //ArrayList<Contact> contactList = contactAdapter.;
                for(int i=0;i<objContact.size();i++){
                    Contact cnt = objContact.get(i);
                    if(cnt.isSelected()){
                        responseText.append("\n" + cnt.getContactName());
                        objContact.remove(i);
                        contactAdapter.notifyDataSetChanged();

                    }
                }

                Toast.makeText(getActivity().getApplicationContext(),
                        responseText, Toast.LENGTH_LONG).show();
            }
        });


    }

    private void populateContact() {
        objContact.add(new Contact("megha","1234",false));
        objContact.add(new Contact("jai","5678",false));
    }





    private class CustomContactAdapter extends ArrayAdapter<Contact> {

        private int layout;
        private Context context;


        public CustomContactAdapter(Context context, ArrayList<Contact> contact) {
            super(context, R.layout.contact_view, contact);
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            View itemview = inflater.inflate(R.layout.contact_view, parent, false);

            Contact currentTask = getItem(position);
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
                    String Name=itmtitleview.getText().toString();
                    Intent intent=new Intent(getActivity(),ContactProfileActivity.class);
                    intent.putExtra("nameofperson",Name);
                    if(getResources().getConfiguration().orientation== Configuration.ORIENTATION_PORTRAIT)
                    {
                        startActivity(intent);
                    }
                    else
                    {

                        ContactProfileFragment frag;
                        frag = (ContactProfileFragment)getActivity().getSupportFragmentManager().findFragmentById(R.id.profilefragment2);
                        frag.updateFragment(Name);

                    }
                }
            });


            itmchkview.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    CheckBox cb = (CheckBox) buttonView;
                    cb.setChecked(true);
                    final Contact checkedTask = (Contact) cb.getTag();
                    Toast.makeText(getActivity().getApplicationContext(),
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





        //add button click
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
//                    Toast.makeText(MainActivity.this, "can not be added", Toast.LENGTH_SHORT).show();
//            }
//        });
    }


}





