package com.example.FragmentConcept.FinalContactList;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;

public class MainActivity extends AppCompatActivity implements OnDataPass{
    ListView contactListView;

    Button addContact , deleteContact;
    ArrayList<Contact> listData;
    CustomAdapter customAdapter;
    Contact contact;
    private static final int REQ_CODE = 123;
    ContactDetails_Fragment contactDetails = new ContactDetails_Fragment();
    ContactProfile_Fragment contactProfile = new ContactProfile_Fragment();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        addContact = (Button) findViewById(R.id.add);
        deleteContact = (Button) findViewById(R.id.delete);
        contactListView = (ListView) findViewById(R.id.contactList);
        listData = new ArrayList<Contact>();

        if(savedInstanceState != null){       //On screen rotation
            Log.d("Cycle","Screen rotated");
            listData = savedInstanceState.getParcelableArrayList("ArrayList");
            contact = savedInstanceState.getParcelable("contactObj");
            customAdapter = new CustomAdapter(this,listData);
            contactListView.setAdapter(customAdapter);
        }
        else {                                //Normal
            customAdapter = new CustomAdapter(this,listData);
            contactListView.setAdapter(customAdapter);
        }


        addContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                    Intent intent = new Intent(MainActivity.this, ContactDetails.class);
                    startActivityForResult(intent, REQ_CODE);
                }else {

                        getSupportFragmentManager().beginTransaction().addToBackStack(null)
                                .add(R.id.fragment_container, contactDetails).commit();
                }
            }
        });

        deleteContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                for(Contact cc :customAdapter.getCheckedItems()) {
                   // Log.e("Deleting", "" + cc.getContactName());
                    listData.remove(cc);
                    contact.removeContactArrayList(cc);
                    Contact.removeContactHashmap(cc.getContactName());
                    for (String objname : Contact.getContactHashmap().keySet()) {
                        for (Iterator<Contact> it = Contact.getContactHashmap().get(objname).iterator(); it.hasNext(); ) {

                            Contact user = it.next();
                            if (user.getContactName().equals(cc.getContactName())) {
                                it.remove();
                            }
                        }

                       // list.remove(cc);

                       // Contact.removeObject(objname,cc);
                      //  Log.e("xx",""+Contact.getContactHashmap().get(objname).get(0).getContactName());
                    }
                    for(String key : Contact.getContactHashmap().keySet()){
                        System.out.println("Key "+ key);
                        for(int i=0;i<Contact.getContactHashmap().get(key).size();i++)
                            System.out.println("val "+Contact.getContactHashmap().get(key).get(i).getContactName());
                    }


                }
                customAdapter.notifyDataSetChanged();

            }
        });
        contactListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                    Log.e("In click", "" + listData.get(i).getContactName());
                    Intent intentP = new Intent(MainActivity.this, ContactProfile.class);
                    intentP.putExtra("contact", listData.get(i));
                    startActivity(intentP);
                }else{

                    android.support.v4.app.Fragment fragmentById = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
                    Log.e("Fragmnet name",""+fragmentById.toString());
                    if(fragmentById.isAdded())
                    {

                        getSupportFragmentManager().beginTransaction()
                            .remove(fragmentById).commit();

                        Log.e("Remove","ing");
                    }
//                    Bundle bundle = new Bundle();
//                    bundle.putParcelable("contact",listData.get(i));
//                    contactProfile.setArguments(bundle);

                    getSupportFragmentManager().beginTransaction().addToBackStack(null)
                                .add(R.id.fragment_container, contactProfile).commit();
                    contactProfile.data(listData.get(i));

                }

            }
        });
    }

    @Override
    public void onDataPass(String name, String phone, Contact contact1) {
       // Toast.makeText(this, "Got back: " + name +" " +phone,Toast.LENGTH_SHORT).show();
        listData.add(contact1);
        contact1.setContactArrayList(contact1);
        contact = contact1;
        customAdapter.notifyDataSetChanged();
        getSupportFragmentManager().beginTransaction().remove(contactDetails).commit();

    }

    @Override
    public void passContact(Contact contact) {
        Log.e("In passContact Main", contact.getContactName());
        getSupportFragmentManager().beginTransaction().remove(contactDetails).commit();
        getSupportFragmentManager().beginTransaction().addToBackStack(null)
                .add(R.id.fragment_container, contactProfile).commit();
        Bundle bundle = new Bundle();
        bundle.putParcelable("contact",contact);
        contactProfile.setArguments(bundle);
    }


    public void onActivityResult(int requestCode,int resultCode, Intent intent) {
            super.onActivityResult(requestCode, resultCode, intent);
            if (requestCode == REQ_CODE) {
                String name = intent.getStringExtra("name");
                String phone = intent.getStringExtra("phone");
                Toast.makeText(this, "Got back: " + name +" " +phone,Toast.LENGTH_SHORT).show();
              //  contact = new Contact(name,phone);
                contact = intent.getParcelableExtra("Contactobj");
                Log.e("Name" , ""+contact.getContactName());
                listData.add(contact);
                contact.setContactArrayList(contact);
                customAdapter.notifyDataSetChanged();

                for(Object objname:Contact.getContactHashmap().keySet()) {
                    Log.e("Key  "+objname, "Values  "+Contact.getContactHashmap().get(objname));

                }

            }
        }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("ArrayList",  listData);
        outState.putParcelable("contactObj",contact);
        Log.d("Cycle","onSaveInstanceState");
    }


}
