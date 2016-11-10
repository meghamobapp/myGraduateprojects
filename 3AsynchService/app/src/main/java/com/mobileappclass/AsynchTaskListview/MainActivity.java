package com.mobileappclass.AsynchTaskListview;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView lstvwid;
    private String[] names = {"a", "b", "c", "d", "e"};
    ArrayAdapter<String> arrAdapter;
    ArrayList<String> arrNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lstvwid = (ListView) findViewById(R.id.lstvw);
       // arrNames = new ArrayList<>();
        arrAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, new ArrayList<String>());
        lstvwid.setAdapter(arrAdapter);
        new myclass().execute();

    }

    private class myclass extends AsyncTask<String ,String,String>{
        ArrayAdapter<String> asynchAdapter;
        ProgressBar pgbar;
        int cnt;

        public myclass() {
            super();
        }

        @Override
        protected void onPreExecute() {
            asynchAdapter= (ArrayAdapter<String>) lstvwid.getAdapter();

            pgbar=(ProgressBar) findViewById(R.id.pbar);
            pgbar.setMax(5);//since max is 5 string in array
            pgbar.setProgress(0);//current is on 0
            pgbar.setVisibility(View.VISIBLE);


            super.onPreExecute();
        }
        @Override
        protected String doInBackground(String ... params) {
            for(String str:names)
            {
                publishProgress(str);
                Log.d("in bg",str);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return "Done";
        }
        @Override
        protected void onProgressUpdate(String... values) {
            asynchAdapter.add(values[0]);
            cnt++;
            pgbar.setProgress(cnt);
           // Toast.makeText(getApplicationContext(),,Toast.LENGTH_SHORT).show();

        }


        @Override
        protected void onPostExecute(String res) {
            Toast.makeText(getApplicationContext(),res,Toast.LENGTH_SHORT).show();
            pgbar.setVisibility(View.GONE);//pgbar gone
        }


    }




}