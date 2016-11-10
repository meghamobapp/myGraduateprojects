package com.mobileappclass.assignment3;

import android.app.Fragment;
import android.content.Intent;
import android.content.res.Configuration;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;


import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.location.LocationManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    public static Location LOCATION = null;
    private Toolbar toolbar;
    localFragment locFrag=new localFragment();
    serverFragment serFrag=new serverFragment();
    private FirebaseDatabase db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        toolbar = (Toolbar) findViewById(R.id.customToolbar);
        //toolbar.setTitle(null);
        setSupportActionBar(toolbar);

/*
       Thread t = new Thread(){
            public void run(){

           }
        };
        t.start();*/

//
        //      Intent location = new Intent(getApplicationContext(), GPStracker.class);
        //    startService(location);

        /*if(helper.getEntireColumn()!=null){
            ArrayList<String> words = helper.getEntireColumn();
            ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,words);
            ListView locationList = (ListView) findViewById(R.id.LocLIST);
            locationList.setAdapter(adapter);
        }*/

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater(); // reads XML
        inflater.inflate(R.menu.menu_main, menu); // to create
        return super.onCreateOptionsMenu(menu); // the menu

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        if (item.getItemId() == R.id.action_send){
            Toast.makeText(this,"inside Offline",Toast.LENGTH_SHORT).show();


        /*  works when we dont want toolbaar on Activity to toggle or if we dont need add or replace frag
             if(getResources().getConfiguration().orientation== Configuration.ORIENTATION_PORTRAIT)
                    {
                        Intent in=new Intent(this,localActivity.class);
                        startActivity(in);
                    }*/
            if(getResources().getConfiguration().orientation== Configuration.ORIENTATION_PORTRAIT)
            {

                android.support.v4.app.Fragment fragmentById = getSupportFragmentManager().findFragmentById(R.id.fragment_container);

                if (fragmentById != null) {
                    if (fragmentById.isAdded()) {

                        getSupportFragmentManager().beginTransaction()
                                .remove(fragmentById).commit();

                    }
                }
            getSupportFragmentManager().beginTransaction().addToBackStack(null)
                    .add(R.id.fragment_container, locFrag).commit();
            }
        }
        if (item.getItemId() == R.id.action_archive) {

           /* Toast.makeText(this, "inside Online", Toast.LENGTH_SHORT).show();
            if(getResources().getConfiguration().orientation== Configuration.ORIENTATION_PORTRAIT)
            {
                Intent in=new Intent(this,serverActivity.class);
                startActivity(in);
            }*/
            if(getResources().getConfiguration().orientation== Configuration.ORIENTATION_PORTRAIT) {

                android.support.v4.app.Fragment fragmentById = getSupportFragmentManager().findFragmentById(R.id.fragment_container);

                if (fragmentById != null) {
                    if (fragmentById.isAdded()) {

                        getSupportFragmentManager().beginTransaction()
                                .remove(fragmentById).commit();

                    }
                }
            getSupportFragmentManager().beginTransaction().addToBackStack(null)
                    .add(R.id.fragment_container, serFrag).commit();
            }

        }
        if (item.getItemId() == R.id.action_query) {

            Toast.makeText(this, "inside query", Toast.LENGTH_SHORT).show();




        }

        return super.onOptionsItemSelected(item);
    }





}
