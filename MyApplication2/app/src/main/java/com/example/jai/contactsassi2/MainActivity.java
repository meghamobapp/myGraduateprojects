package com.example.jai.contactsassi2;

import android.content.Context;
import android.content.Intent;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



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





