package com.mobileappclass.assignment3;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * Use the {@link localFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class localFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    public static final String broadcastString = "com.mobileappclass.assignment3.broad";
    public static final String networkType_wifi = "WiFi";
    public static final String networkType_roaming = "Roaming";
    IntentFilter filter;
    ListView locationList;
    ArrayAdapter<String> adapter;
    ArrayList<String> words=new ArrayList<>();



    public localFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment localFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static localFragment newInstance(String param1, String param2) {
        localFragment fragment = new localFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


    }

    @Override
    public void onPause() {
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(testReceiver);

        super.onPause();
    }

    @Override
    public void onResume() {
        getActivity().registerReceiver(testReceiver, filter);

        super.onResume();
    }

    @Override
    public void onDestroy() {
        if(testReceiver != null){
            getActivity().unregisterReceiver(testReceiver);
        }
        super.onDestroy();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        filter = new IntentFilter();
        filter.addAction(broadcastString);
       // filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        //filter.addAction(networkType_wifi);
        //filter.addAction(networkType_roaming);



        populateList();

        super.onActivityCreated(savedInstanceState);
    }
    public void populateList()
    {
        SqlHelper helper = new SqlHelper(getActivity());

        // This is the only time we will use SQLiteDatabase in an Activity
//        SQLiteDatabase db = openOrCreateDatabase("Locationdb", null);

        // helper.onCreate(db);
        // helper.addEntry(44.67,43.97);
        // helper.addEntry(32.67,64.97);

        locationList = (ListView) getActivity().findViewById(R.id.LocLIST);
        if(locationList!=null)
        {
        if(helper.getEntireColumn()!=null){
            words = helper.getEntireColumn();
            adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1,words);
            locationList.setAdapter(adapter);
        }
        }
       // ArrayList<String> words = helper.getEntireColumn();
       /* for (String i : words)
        {
            // Toast.makeText(getApplicationContext(),"inside add database:2010-05-28T15:36:56.200" , Toast.LENGTH_LONG).show();
            Log.d("Activity", i);
        } */
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_local, container, false);
    }

    private BroadcastReceiver testReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            populateList();

            /*String result = intent.getStringExtra("result");
            words.add(result);
            adapter.notifyDataSetChanged();
            for (String i : words)
            {

                //adapter.notifyDataSetChanged();
                // Toast.makeText(getApplicationContext(),"inside add database:2010-05-28T15:36:56.200" , Toast.LENGTH_LONG).show();

            }


            Log.d("receiver", result);
            Toast.makeText(getActivity(), "inside receiver", Toast.LENGTH_SHORT).show();
            // Toast.makeText(MainActivity.this, result, Toast.LENGTH_SHORT).show();


            Intent stopIntent = new Intent(getActivity(), GPStracker.class);
            getActivity().stopService(stopIntent);*/

        }
    };

}
