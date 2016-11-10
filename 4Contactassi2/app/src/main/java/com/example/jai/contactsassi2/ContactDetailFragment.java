package com.example.jai.contactsassi2;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class ContactDetailFragment extends Fragment {
    String nametobeadded, phonenotobeadded;
    EditText contactName, contactPhone;
    Button addpersonbtn1;

    public ContactDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_contact_detail, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Intent intent=getActivity().getIntent();
        String msg=intent.getStringExtra("message");
        Toast.makeText(getActivity(),msg,Toast.LENGTH_SHORT).show();

        contactName = (EditText) getActivity().findViewById(R.id.ipname);
        contactPhone = (EditText) getActivity().findViewById(R.id.ipphone);
        addpersonbtn1 = (Button) getActivity().findViewById(R.id.addpersonbtn);

        addpersonbtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent=new Intent(getActivity(),ContactDetailActivity.class);
//                intent.putExtra("newname",contactName.getText().toString());
                 Toast.makeText(getActivity(), "inside addperson"+contactName.getText().toString(), Toast.LENGTH_SHORT).show();

                //getActivity().setResult(RESULT_OK,intent);
                //getActivity().finish();
            }
        });

    }

}
