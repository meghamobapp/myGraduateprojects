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
    Button addpersonButtom;

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

        contactName = (EditText) getActivity().findViewById(R.id.ipname);
        Toast.makeText(getActivity(), "inside detail activity"+contactName.getText().toString(), Toast.LENGTH_SHORT).show();

        contactPhone = (EditText) getActivity().findViewById(R.id.ipphone);
        addpersonButtom = (Button) getActivity().findViewById(R.id.addpersonbtn);
        addpersonButtom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent3=new Intent(getActivity(),ContactDetailActivity.class);
                intent3.putExtra("newname",contactName.getText().toString());
                Toast.makeText(getActivity(), "inside addperson"+contactName.getText().toString(), Toast.LENGTH_SHORT).show();

                getActivity().setResult(RESULT_OK,intent3);
                getActivity().finish();
            }
        });

    }

}
