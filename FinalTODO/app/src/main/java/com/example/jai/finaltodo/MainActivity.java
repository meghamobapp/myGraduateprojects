package com.example.jai.finaltodo;

import android.content.Context;
import android.os.Handler;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    ArrayList<Task> objTask;
    String titletobeadded,desctobeadded;
    ListView lstVwTask;
    ArrayAdapter<Task> taskAdapter;
    Button submitButton;
    EditText taskTitle, taskDesc;
    CheckBox chkdelete;
    Scanner scan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lstVwTask = (ListView) findViewById(R.id.lstvwidtask);
        submitButton = (Button) findViewById(R.id.addbtn);
        taskTitle = (EditText) findViewById(R.id.iptitle);
        taskDesc = (EditText) findViewById(R.id.ipdesc);
        chkdelete = (CheckBox) findViewById(R.id.item_checkBtnSelect);

       objTask = new ArrayList<Task>();
        taskAdapter = new CustomTaskAdapter(this, objTask);
        lstVwTask.setAdapter(taskAdapter);

        populateTask();

        //add button click
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                titletobeadded = taskTitle.getText().toString();
                desctobeadded = taskDesc.getText().toString();
                if (!titletobeadded.isEmpty()) {
                    objTask.add(new Task(titletobeadded, desctobeadded));
                    taskAdapter.notifyDataSetChanged();
                    taskTitle.setText("");
                    taskDesc.setText("");
                }

                PrintStream output = null;
                try {
                    output = new PrintStream(openFileOutput("TaskList.txt", MODE_APPEND));
                    output.println(titletobeadded + "\t" + desctobeadded);
                    Toast.makeText(MainActivity.this, "write done", Toast.LENGTH_SHORT).show();

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });

        lstVwTask.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                        Task clickedTask = objTask.get(position);
                        objTask.remove(clickedTask);
                        taskAdapter.notifyDataSetChanged();

                        taskTitle.setText("");
                        taskDesc.setText("");

                        //  Toast.makeText(MainActivity.this,msg,Toast.LENGTH_SHORT).show();
                        return true;
                    }
                });

            }


    private void populateTask() {

        try {
            Scanner scan = new Scanner(openFileInput("TaskList.txt"));
            while (scan.hasNextLine())
            {
                String line = scan.nextLine();
                String[] pieces = line.split("\t");
                objTask.add(new Task(pieces[0], pieces[1]));
                taskAdapter.notifyDataSetChanged();
                taskTitle.setText("");
                taskDesc.setText("");


            }
            Toast.makeText(MainActivity.this,"Read from file done",Toast.LENGTH_SHORT).show();


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }


    private class CustomTaskAdapter extends ArrayAdapter<Task> {

        private int layout;
        private Context context;


        public CustomTaskAdapter(Context context,ArrayList<Task> task) {
            super(context, R.layout.task_view,task);
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            View itemview = inflater.inflate(R.layout.task_view, parent, false);

            Task currentTask = getItem(position);
            TextView itmtitleview = (TextView) itemview.findViewById(R.id.item_txttitle);
            TextView itmdescview = (TextView) itemview.findViewById(R.id.item_txtdesc);
            final CheckBox itmchkview = (CheckBox) itemview.findViewById(R.id.item_checkBtnSelect);

            itmtitleview.setText(currentTask.getTaskTitle());
            itmdescview.setText(currentTask.getTaskDesc());
            itmchkview.setChecked(currentTask.isSelected());
            itmchkview.setTag(currentTask);
            itmchkview.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    CheckBox cb = (CheckBox) buttonView;
                    cb.setChecked(true);
                    final Task checkedTask = (Task) cb.getTag();
                    Toast.makeText(getApplicationContext(),
                            "Clicked on Checkbox: " + checkedTask.getTaskTitle() + cb.getText() +
                                    " is " + cb.isChecked(),
                            Toast.LENGTH_LONG).show();
                    checkedTask.setSelected(cb.isChecked());
                    cb.setChecked(true);
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            // Do something after 5s = 5000ms
                            objTask.remove(checkedTask);
                            taskAdapter.notifyDataSetChanged();
                        }
                    }, 1000);


                }
            });

            return itemview;
            //return super.getView(position, convertView, parent);

        }


    }
}




