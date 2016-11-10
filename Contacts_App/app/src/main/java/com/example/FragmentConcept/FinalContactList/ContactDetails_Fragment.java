package com.example.FragmentConcept.FinalContactList;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
interface OnDataPass {
    public void onDataPass(String name, String phone, Contact contact);
    public void passContact(Contact contact);
}


public class ContactDetails_Fragment extends Fragment {
    private Activity myActivity;
    EditText contactName, contactPhone;
    Button addPerson;
    ListView relationListView;
    ArrayList<Contact> relationList;
    OnDataPass dataPasser;

    @Override
    public void onAttach(Context a) {
        super.onAttach(a);
        dataPasser = (OnDataPass) a;
    }

    public ContactDetails_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.contact_details_fragment, container, false);
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        myActivity = getActivity();
        contactName = (EditText) myActivity.findViewById(R.id.contactName);
        contactPhone = (EditText) myActivity.findViewById(R.id.contactPhone);
        addPerson = (Button) myActivity.findViewById(R.id.addPerson);
        relationListView = (ListView) myActivity.findViewById(R.id.relationListDetails);
//        relationListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);


        final CustomAdapter customAdapter = new CustomAdapter(myActivity, Contact.getContactArrayList());
        relationListView.setAdapter(customAdapter);

        relationListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.e("In click", ""+Contact.getContactArrayList().get(i).getContactName());
                dataPasser.passContact(Contact.getContactArrayList().get(i));
              //  Intent intentP = new Intent(ContactDetails.this, ContactProfile.class);
               // intentP.putExtra("contact",Contact.getContactArrayList().get(i));
               // startActivity(intentP);


            }
        });

        addPerson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                relationList = new ArrayList<>();
                Contact contact = new Contact(contactName.getText().toString(), contactPhone.getText().toString());
                dataPasser.onDataPass(contactName.getText().toString(),contactPhone.getText().toString(),contact);

                for(Contact cc :customAdapter.getCheckedItems()){
                    Log.e("Checked",""+cc.getContactName());
                    relationList.add(cc);
                }
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
