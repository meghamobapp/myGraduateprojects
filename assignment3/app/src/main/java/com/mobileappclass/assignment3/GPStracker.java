package com.mobileappclass.assignment3;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class GPStracker extends Service {
    Location mLastLocation;

    String longitute,latitude;
    public Runnable mRunnable = null;
    private static final String TAG = GPStracker.class.getSimpleName();
    private LocationManager mLocationManager = null;
    private static final int INTERVAL = 1000; // minimum time interval between location updates (milliseconds)
    private static final float DISTANCE = 10f; // minimum distance between location updates (meters)

    private LocalBroadcastManager mLocalBroadcastManager;
    public static final String ACTION = "com.mobileappclass.assignment3.GPStracker";
    SqlHelper helper;

    @Override
    public void onCreate() {


        Toast.makeText(this,"inside service",Toast.LENGTH_SHORT).show();
        helper = new SqlHelper(getApplicationContext());
        SQLiteDatabase db = openOrCreateDatabase(
                "Locationdb", MODE_PRIVATE, null);
        helper.onCreate(db);



        mLocalBroadcastManager = LocalBroadcastManager.getInstance(this);

        if (mLocationManager == null) {
            mLocationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        }
        try {
            mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, INTERVAL, DISTANCE, mLocationListeners[1]);
        } catch (SecurityException ex) {
            Log.i(TAG, "fail to request location update, ignore", ex);
        } catch (IllegalArgumentException ex) {
            Log.d(TAG, "network provider does not exist, " + ex.getMessage());
        }
        try {
            mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, INTERVAL, DISTANCE, mLocationListeners[0]);
        } catch (SecurityException ex) {
            Log.i(TAG, "fail to request location update, ignore", ex);
        } catch (IllegalArgumentException ex) {
            Log.d(TAG, "gps provider does not exist " + ex.getMessage());
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mLocationManager != null) {
            for (int i = 0; i < mLocationListeners.length; i++) {
                try {
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    mLocationManager.removeUpdates(mLocationListeners[i]);
                } catch (Exception ex) {
                    Log.i(TAG, "fail to remove location listeners, ignore", ex);
                }
            }
        }
    }

    private class LocationListener implements android.location.LocationListener {
//        Location mLastLocation;
//        String longitute,latitude;


        public LocationListener(String provider) {
            Log.e(TAG, "LocationListener " + provider);
            mLastLocation = new Location(provider);
            if(mLastLocation!=null){
                longitute= String.valueOf(mLastLocation.getLongitude());
                latitude= String.valueOf(mLastLocation.getLatitude());
                Log.d(TAG, "longi and latt on location listen" +longitute+latitude );
                //Toast.makeText(GPStracker.this,"Longitude"+longitute+"latt"+latitude,Toast.LENGTH_SHORT).show();
            }

        }

        @Override
        public void onLocationChanged(Location location) {
            mLastLocation.set(location);
            Log.d("Service", "location changed: "+mLastLocation.toString());
            //Toast.makeText(GPStracker.this,"Changed Longitude"+longitute+"latt"+latitude,Toast.LENGTH_SHORT).show();


        }
        public Location getUserLocation(){
            Log.d("Service", "return location: "+mLastLocation.toString());
            return mLastLocation;
        }
        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderDisabled(String provider) {
            Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
        }

        @Override
        public void onProviderEnabled(String provider) {
        }


    }

    LocationListener[] mLocationListeners = new LocationListener[]{
            new LocationListener(LocationManager.GPS_PROVIDER),
            new LocationListener(LocationManager.NETWORK_PROVIDER)
    };

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    public int onStartCommand(final Intent intent, int flags, int startId) {






        final Handler mHandler = new Handler();
        mRunnable=new Runnable() {
            @Override
            public void run () {



                helper.addEntry(mLastLocation.getLatitude(), mLastLocation.getLongitude());



                      /*SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd HH:mm:ss ");
                      Date date = new Date();
                      DatabaseReference ref= FirebaseDatabase.getInstance().getReference("Students");
                      DatabaseReference netid=ref.child("mmk181");
                      DatabaseReference loc=netid.child(dateFormat.format(date));
                      loc.child("date").setValue(dateFormat.format(date));
                      loc.child("netid").setValue("mmk181");
                      loc.child("x").setValue(mLastLocation.getLatitude());
                      loc.child("y").setValue(mLastLocation.getLongitude());*/



               // ArrayList<String> words = helper.getEntireColumn();
                Intent brod = new Intent();
                brod.putExtra("result", mLastLocation.getLatitude() + " " + mLastLocation.getLongitude());
                brod.setAction(localFragment.broadcastString);

                //intent.setAction("finish");
                sendBroadcast(brod);
                Toast.makeText(getApplicationContext(), "Brod" + mLastLocation.getLatitude() + " " + mLastLocation.getLongitude(), Toast.LENGTH_LONG).show();

                // If desired, stop the service
                //stopSelf();
                Log.d("Service", mLastLocation.getLatitude() + " " + mLastLocation.getLongitude());



                mHandler.postDelayed(mRunnable, 10 * 1000);
            }
        };

        mHandler.postDelayed(mRunnable,10*1000);

        return Service.START_STICKY;


        //return super.onStartCommand(intent, flags, startId);








    }
}