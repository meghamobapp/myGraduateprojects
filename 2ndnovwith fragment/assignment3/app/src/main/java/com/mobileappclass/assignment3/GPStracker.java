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
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

public class GPStracker extends Service {
    Location mLastLocation;
    String longitute,latitude;
    public Runnable mRunnable = null;
    private static final String TAG = GPStracker.class.getSimpleName();
    private LocationManager mLocationManager = null;
    private static final int INTERVAL = 1000; // minimum time interval between location updates (milliseconds)
    private static final float DISTANCE = 10f; // minimum distance between location updates (meters)


    @Override
    public void onCreate() {
        Toast.makeText(this,"inside service",Toast.LENGTH_SHORT).show();
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
    public int onStartCommand(Intent intent, int flags, int startId) {


        final Handler mHandler = new Handler();
        mRunnable = new Runnable() {
            @Override
            public void run() {


                SqlHelper helper = new SqlHelper(getApplicationContext());

                // This is the only time we will use SQLiteDatabase in an Activity
               // SQLiteDatabase db = openOrCreateDatabase("Locationdb", MODE_PRIVATE, null);

                //helper.onCreate(db);
               // helper.addEntry(44.00f, 46.00f);
                //helper.addEntry(44.00f, 46.00f);
                //helper.addEntry(34.00f, (float) 36.00);
                helper.addEntry(mLastLocation.getLatitude(),mLastLocation.getLongitude());

                // helper.addEntry("2010-05-28T15:36:56.200", 34.00f, (float) 36.00);


                ArrayList<String> words = helper.getEntireColumn();
                for (String i : words)
                {
                    // Toast.makeText(getApplicationContext(),"inside add database:2010-05-28T15:36:56.200" , Toast.LENGTH_LONG).show();
                    Log.d("Service", i);
            }
                mHandler.postDelayed(mRunnable, 10 * 1000);
            }
        };
        mHandler.postDelayed(mRunnable, 10 * 1000);

        return super.onStartCommand(intent, flags, startId);







    }
}