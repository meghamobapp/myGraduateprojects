package com.mobileappclass.assignment3;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;




public class serverFragment extends Fragment {

    ListView listofstrings;
    ArrayAdapter<String> adapter;
    ArrayList<String> loc=new ArrayList<>();
    Button Syncbtn;
    TextView servtxtvw,nwtxtvw;
    FirebaseDatabase db;
    SqlHelper helper;
    IntentFilter filternw;

    public serverFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        return inflater.inflate(R.layout.fragment_server, container, false);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        //chkStatus();

        filternw = new IntentFilter();

        filternw.addAction(ConnectivityManager.CONNECTIVITY_ACTION);



        helper = new SqlHelper(getActivity());
        // Inflate the layout for this fragment
        // View view = inflater.inflate(R.layout.fragment_server, container, false);
        servtxtvw=(TextView)getActivity().findViewById(R.id.serverStatus);
        nwtxtvw=(TextView)getActivity().findViewById(R.id.networkType);
        Syncbtn=(Button)getActivity().findViewById(R.id.syncButton);
        listofstrings=(ListView)getActivity().findViewById(R.id.serverList);
        // serverList = (ListView) view.findViewById(R.id.serverList);

        //adapter=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,loc);
        //locationList.setAdapter(adapter);

        adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, loc);
        listofstrings.setAdapter(adapter);
        servtxtvw.setText("Not Connected");

        //readFirebaseDB();
        viewAll();


        Syncbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                servtxtvw.setText("Connected");

                new FirebaseUpload().execute();
              //  Syncdbanddisplay();
                viewmy();       }
        });
        super.onActivityCreated(savedInstanceState);

    }
    @Override
    public void onResume() {
        getActivity().registerReceiver(nwbroadcastReceiver, filternw);

        super.onResume();
    }

    @Override
    public void onDestroy() {
        if(nwbroadcastReceiver != null){
            getActivity().unregisterReceiver(nwbroadcastReceiver);
        }
        super.onDestroy();
    }
    public void viewmy(){
       // adapter.clear();

        loc.clear();
        adapter.notifyDataSetChanged();
        DatabaseReference ref2 = FirebaseDatabase.getInstance().getReference("Students").child("mmk181");
        final StringBuffer buffer = new StringBuffer();

        ref2.limitToLast(10).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    for (DataSnapshot ds2 : dataSnapshot.getChildren()){

                        for(DataSnapshot ds3 : ds2.getChildren()){
                            buffer.append(ds3.getValue()+" ");
                        }
                        loc.add(buffer.toString());
                        buffer.delete(0, buffer.length());
                    }
                }
                adapter.notifyDataSetChanged();
               // servtxtvw.setText("Connected");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }


    public void viewAll() {

            loc.clear();
            adapter.notifyDataSetChanged();
            DatabaseReference ref2 = FirebaseDatabase.getInstance().getReference("Students");
            final StringBuffer buffer = new StringBuffer();

            ref2.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for(DataSnapshot ds : dataSnapshot.getChildren()){
                        for (DataSnapshot ds2 : ds.getChildren()){
                            if(loc.size()==200)
                                break;
                            for(DataSnapshot ds3 : ds2.getChildren()){
                                buffer.append(ds3.getValue()+" ");
                            }
                            loc.add(buffer.toString());
                            buffer.delete(0, buffer.length());
                        }
                    }
                    adapter.notifyDataSetChanged();
                    servtxtvw.setText("Connected");
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

    }
    private BroadcastReceiver nwbroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String wifiname="";
            nwtxtvw.setText("");

            ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            // connected to the internet
            if (activeNetwork != null)
                if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                    // connected to wifi
                    final WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                    final WifiInfo connectionInfo = wifiManager.getConnectionInfo();
                    if (connectionInfo != null && !(connectionInfo.getSSID().equals(""))) {
                        //if (connectionInfo != null && !StringUtil.isBlank(connectionInfo.getSSID())) {
                        wifiname = connectionInfo.getSSID();
                    }
                    nwtxtvw.setText("WiFi: " + wifiname);
                    servtxtvw.setText("Connected");


                    new FirebaseUpload().execute();
                   // Toast.makeText(getActivity(), "wifiname " + activeNetwork.getTypeName(), Toast.LENGTH_SHORT).show();
                } else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                    // connected to the mobile provider's data plan
                    nwtxtvw.setText("Mobile Data");
                    servtxtvw.setText("");
                    Toast.makeText(getActivity(), "NW name" + activeNetwork.getTypeName(), Toast.LENGTH_SHORT).show();
                }
            else {
                nwtxtvw.setText("No Network");
                    servtxtvw.setText("Not Connected");
                Toast.makeText(getActivity(), "No wifi,Cannot upload data!\n" +
                        "Click Sync button to upload data to server", Toast.LENGTH_SHORT).show();

                // not connected to the internet
            }
        }
    };


    public class FirebaseUpload extends AsyncTask<Void, Void, Void>{
        String dateValue,latValue,lngValue;
        @Override

        protected Void doInBackground(Void... params) {


            SqlHelper helper = new SqlHelper(getActivity().getApplicationContext());
            Cursor cur = helper.getWritableDatabase().rawQuery("SELECT * FROM " + "loc", null);
            if (cur != null) {
                if (cur.moveToFirst()) {
                    do {

                        dateValue = cur.getString(cur.getColumnIndex("time"));
                        latValue =  cur.getString(cur.getColumnIndex("longitude"));
                        lngValue = cur.getString(cur.getColumnIndex("latitude"));

                        //System.out.println("DATEVALUE: "+dateValue);
                        //System.out.println("LATVALUE: "+latValue);
                        //System.out.println("LNGVALUE: "+lngValue);

                        DatabaseReference ref= FirebaseDatabase.getInstance().getReference("Students");
                        DatabaseReference netid=ref.child("mmk181");

                        DatabaseReference loc1=netid.child(dateValue);
                        loc1.child("date").setValue(dateValue);
                        loc1.child("netid").setValue("mmk181");
                        loc1.child("x").setValue(latValue);
                        loc1.child("y").setValue(lngValue);


                        loc1.child("y").setValue(lngValue, new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                                //System.out.println("ADDED!!!!!");
                                //counter++;

                            }
                        });


                        //counter++;
                    } while (cur.moveToNext());
                }
            }



            return null;





        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Toast.makeText(getActivity(), "Data Uploaded to Firebase", Toast.LENGTH_SHORT).show();
          // servtxtvw.setText("Not Connected");
            //readFirebaseDB();
            super.onPostExecute(aVoid);
        }
    }



}


