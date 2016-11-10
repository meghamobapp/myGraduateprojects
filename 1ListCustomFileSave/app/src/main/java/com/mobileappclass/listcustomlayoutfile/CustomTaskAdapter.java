package com.mobileappclass.listcustomlayoutfile;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jai on 11/3/2016.
 */

public class CustomTaskAdapter extends ArrayAdapter<Task> {
    ArrayList<Task> tasks;

    public CustomTaskAdapter(Context context,ArrayList<Task> task) {
        super(context, R.layout.task_item,task);
        this.tasks=task;
    }


    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View itemview = inflater.inflate(R.layout.task_item, parent, false);

        Task currentTask = getItem(position);

        TextView itmtitleview = (TextView) itemview.findViewById(R.id.item_title);
        TextView itmdescview = (TextView) itemview.findViewById(R.id.item_desc);
        final CheckBox itmchkview = (CheckBox) itemview.findViewById(R.id.chkBtn);

        itmtitleview.setText(currentTask.getTitle());
        itmdescview.setText(currentTask.getDesc());
        itmchkview.setChecked(currentTask.isChk());
        itmchkview.setTag(currentTask);

       itmchkview.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            CheckBox cb=(CheckBox)buttonView;
                cb.setChecked(true);
               Task curr=(Task) cb.getTag();
               tasks.remove(curr);




            }
        });

        return itemview;
        //return super.getView(position, convertView, parent);
    }
}
