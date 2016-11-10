package com.mobileappclass.listcustomlayoutfile;

import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {
    EditText etTitle,etDesc;
    Button btnBdd;
    ListView lstvw;
    ArrayList<Task> arrayListTask;
    ArrayAdapter<Task> taskvwadapter;
    String titletobeadded,desctobeadded;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        etTitle=(EditText) findViewById(R.id.titl);
        etDesc=(EditText) findViewById(R.id.desc);
        lstvw=(ListView) findViewById(R.id.lstvwtask);
        btnBdd=(Button) findViewById(R.id.addbtn);


        arrayListTask=new ArrayList<>();
        taskvwadapter=new CustomTaskAdapter(this,arrayListTask);
        lstvw.setAdapter(taskvwadapter);

        populateTask();


        //checkButtonClick();

    }

    private void populateTask() {

        try {
            Scanner scan = new Scanner(openFileInput("TaskList.txt"));
            while (scan.hasNextLine())
            {
                String line = scan.nextLine();
                String[] pieces = line.split("\t");
                arrayListTask.add(new Task(pieces[0], pieces[1]));
                taskvwadapter.notifyDataSetChanged();
                etTitle.setText("");
                etDesc.setText("");


            }
            Toast.makeText(MainActivity.this,"Read from file done",Toast.LENGTH_SHORT).show();


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }



    private void checkButtonClick() {
        lstvw.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Task delTask = arrayListTask.get(position);
                if (delTask.isChk() == true) {
                    arrayListTask.remove(delTask);
                    taskvwadapter.notifyDataSetChanged();
                }
            }
        });

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {

        Log.d("restore","inside restore");

        if(savedInstanceState == null || !savedInstanceState.containsKey("tasklist")){

            arrayListTask=new ArrayList<>();
        }
        else {
            arrayListTask = savedInstanceState.getParcelableArrayList("tasklist");

        }
        taskvwadapter=new CustomTaskAdapter(this,arrayListTask);
        lstvw.setAdapter(taskvwadapter);
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList("tasklist",arrayListTask);
        super.onSaveInstanceState(outState);
        Log.d("save","inside save");


    }

    public void addbtnclick(View view) {
        titletobeadded = etTitle.getText().toString();
        desctobeadded = etDesc.getText().toString();



        Task current=new Task(titletobeadded,desctobeadded);
        Log.i("addbtn",etTitle.getText().toString()+etDesc.getText().toString());
        arrayListTask.add(current);
        taskvwadapter.notifyDataSetChanged();


        PrintStream output = null;
        try {
            output = new PrintStream(openFileOutput("TaskList.txt", MODE_APPEND));
            output.println(titletobeadded + "\t" + desctobeadded);
            Toast.makeText(MainActivity.this, "write done", Toast.LENGTH_SHORT).show();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        etTitle.setText("");
        etDesc.setText("");
    }
}
