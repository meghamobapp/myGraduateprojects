package com.mobileappclass.assignment3;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Jai on 10/27/2016.
 */

public class SqlHelper extends SQLiteOpenHelper {


    public static final String DATABASE_NAME = "Locationdb.db";
    // Database table
    public static final String TABLE_Location = "loc";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TIME = "time";
    public static final String COLUMN_LONGITUDE = "longitude";
    public static final String COLUMN_LATITUDE = "latitude";

    // Database creation SQL statement
    private static final String DATABASE_CREATE = "CREATE TABLE IF NOT EXISTS "
            + TABLE_Location
            + " ("
            + COLUMN_ID + " integer primary key autoincrement, "
            + COLUMN_TIME + " text not null, "
            + COLUMN_LONGITUDE + " text not null, "
            + COLUMN_LATITUDE + " text not null"
            + ");";

    public SqlHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }




    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE);
    }

    public void addEntry(double lg, double ln){
        // we are grabbing a reference to a cached version of the DB we created
        // in onCreate


        SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd HH:mm:ss ");
        Date date = new Date();
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
      //  cv.put(COLUMN_ID,id);
        cv.put(COLUMN_TIME, dateFormat.format(date));
       // cv.put(COLUMN_TIME, String.valueOf(ts));
        cv.put(COLUMN_LONGITUDE,lg);
        cv.put(COLUMN_LATITUDE,ln);
        db.insert(TABLE_Location,null,cv);
    }
    public ArrayList<String> getEntireColumn(){
        ArrayList<String> values = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM " + TABLE_Location+" ORDER BY "+COLUMN_TIME + " desc limit 200";
        Cursor cursor = db.rawQuery(query,null);
        cursor.moveToFirst();
        if(cursor!=null && cursor.moveToFirst()) {
            do {
                String value = (String) cursor.getString(cursor.getColumnIndex(COLUMN_ID)) + " : ";
                value += (String) cursor.getString(cursor.getColumnIndex(COLUMN_TIME)) + " : ";
                value += (String) cursor.getString(cursor.getColumnIndex(COLUMN_LONGITUDE)) + " , ";
                value += (String) cursor.getString(cursor.getColumnIndex(COLUMN_LATITUDE)) + " ";
                values.add(value);

            } while (cursor.moveToNext());
        }
        cursor.close();
        return values;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(SqlHelper.class.getName(), "Upgrading database from version "
                + oldVersion + " to " + newVersion
                + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_Location);
        onCreate(db);
    }
}
