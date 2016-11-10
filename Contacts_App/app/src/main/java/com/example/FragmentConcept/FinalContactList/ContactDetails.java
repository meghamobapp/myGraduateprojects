package com.example.FragmentConcept.FinalContactList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by Thara Philips on 10/18/2016.
 */
public class ContactDetails extends AppCompatActivity  {

    EditText contactName, contactPhone;
    Button addPerson;
    ListView relationListView;
    ArrayList<Contact> relationList;
    SparseBooleanArray checked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_details_layout);

        contactName = (EditText) findViewById(R.id.contactName);
        contactPhone = (EditText) findViewById(R.id.contactPhone);
        addPerson = (Button)findViewById(R.id.addPerson);
        relationListView = (ListView) findViewById(R.id.relationListDetails);
        relationListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);


        final CustomAdapter customAdapter = new CustomAdapter(this,Contact.getContactArrayList());
        relationListView.setAdapter(customAdapter);

        relationListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.e("In click", ""+Contact.getContactArrayList().get(i).getContactName());
                Intent intentP = new Intent(ContactDetails.this, ContactProfile.class);
                intentP.putExtra("contact",Contact.getContactArrayList().get(i));
                startActivity(intentP);


            }
        });




        addPerson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                relationList= new ArrayList<>();
                Intent intent = new Intent();
                intent.putExtra("name", contactName.getText().toString());
                intent.putExtra("phone", contactPhone.getText().toString());
                Contact contact=new Contact( contactName.getText().toString(), contactPhone.getText().toString());
                intent.putExtra("Contactobj", new Contact( contactName.getText().toString(), contactPhone.getText().toString()));
                Log.e("name sending back", "" + contactName.getText().toString());
                setResult(RESULT_OK, intent);
                finish();

                for(Contact cc :customAdapter.getCheckedItems()){
                    Log.e("Checked",""+cc.getContactName());
                    relationList.add(cc);
                }

//                int size = Contact.getContactArrayList().size();
//                for( int i=0;i<size;i++){
//                   // if(Contact.getContactArrayList().get(i).isCheckBox()){
//                    if(relationListView.isItemChecked(i)){
//                        //relationList.add(relationListView.getItemAtPosition(i));
//                        relationList.add(Contact.getContactArrayList().get(i));
//                    }
//                }
                Contact.contactHashmap.put(contactName.getText().toString(),relationList);
                for(Contact con: relationList){
                    if( Contact.contactHashmap.containsKey(con.getContactName())){
                        Log.e("Contains key",""+con.getContactName());
                        Contact.contactHashmap.get(con.getContactName()).add(contact);
                        Log.e("So now contains",""+Contact.contactHashmap.get(con.getContactName()));
                    }
                }

                for(Object objname:Contact.getContactHashmap().keySet()) {
                    for(int i=0;i<Contact.getContactHashmap().get(objname).size();i++)
                        Log.e("Key  "+objname, "Values  "+Contact.getContactHashmap().get(objname).get(i));

                }

            }

        });



    }

 }
