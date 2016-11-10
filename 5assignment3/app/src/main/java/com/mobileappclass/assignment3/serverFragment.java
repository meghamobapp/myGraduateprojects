package com.mobileappclass.assignment3;

import android.database.Cursor;
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
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link serverFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class serverFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    ListView locationList;
    ArrayAdapter adapter;
    ArrayList<String> loc;
    Button Syncbtn;


    public serverFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment serverFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static serverFragment newInstance(String param1, String param2) {
        serverFragment fragment = new serverFragment();
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
    public static class locationRemote {

        public String date;
        public String netid;
        public String longi;
        public String latt;


        public locationRemote(String date, String netid, String longi, String latt) {
            this.date = date;
            this.netid = netid;
            this.longi = longi;
            this.latt = latt;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getNetid() {
            return netid;
        }

        public void setNetid(String netid) {
            this.netid = netid;
        }

        public String getLongi() {
            return longi;
        }

        public void setLongi(String longi) {
            this.longi = longi;
        }

        public String getLatt() {
            return latt;
        }

        public void setLatt(String latt) {
            this.latt = latt;
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        DatabaseReference ref1= FirebaseDatabase.getInstance().getReference("Students");


        ref1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                locationRemote post = dataSnapshot.getValue(locationRemote.class);
                System.out.println("post"+post);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });







        DatabaseReference ref= FirebaseDatabase.getInstance().getReference("Students");

        ref.orderByChild("date").limitToLast(200).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Log.v("child","value "+dataSnapshot.getValue());


            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
           /* DatabaseReference netid=ref.child("mmk181");

            DatabaseReference loc=netid.child("11-3 08:20:43");
            loc.child("date").setValue("11-03 08:20:43");
            loc.child("netid").setValue("mmk181");
            loc.child("x").setValue("37.625840");
            loc.child("y").setValue("-73.454590");*/

        Toast.makeText(getActivity(),"inside serverfrag",Toast.LENGTH_SHORT).show();

        Syncbtn=(Button) getActivity().findViewById(R.id.btnSync);
        Syncbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new asynchclass().execute();

            }
        });

        super.onCreate(savedInstanceState);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_server, container, false);
    }

    private class asynchclass extends AsyncTask<Void,Void,Void>{
        String dateValue,latValue,lngValue;
        public asynchclass() {
            super();
        }

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

                        DatabaseReference loc=netid.child(dateValue);
                        loc.child("date").setValue(dateValue);
                        loc.child("netid").setValue("mmk181");
                        loc.child("x").setValue(latValue);
                        loc.child("y").setValue(lngValue);


                        loc.child("y").setValue(lngValue, new DatabaseReference.CompletionListener() {
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
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }

        @Override
        protected void onProgressUpdate(Void... values) {

            super.onProgressUpdate(values);
        }
    }
}
