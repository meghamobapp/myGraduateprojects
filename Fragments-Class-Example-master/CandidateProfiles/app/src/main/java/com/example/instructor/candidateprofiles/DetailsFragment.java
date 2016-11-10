package com.example.instructor.candidateprofiles;


import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Scanner;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetailsFragment extends Fragment {

    private TextView nameText;
    private TextView detailsText;
    private ImageView candidateImage;
    private Activity myActivity;


    public DetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_details, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        myActivity = getActivity();

        nameText = (TextView) myActivity.findViewById(R.id.nameText);
        detailsText = (TextView) myActivity.findViewById(R.id.detailsText);
        candidateImage = (ImageView) myActivity.findViewById(R.id.candidateImage);

        Intent intent = myActivity.getIntent();
        String name = intent.getStringExtra("name");
        if(name == null){
            name = "trump";
        }

        int detailsID = getResources().getIdentifier(name,"raw",myActivity.getPackageName());
        int imageID = getResources().getIdentifier(name,"drawable",myActivity.getPackageName());

        candidateImage.setImageResource(imageID);
        nameText.setText(name.toUpperCase());
        detailsText.setText(readDetailsFile(detailsID));




    }

    public String readDetailsFile(int detailsID){
        Scanner scan = new Scanner(getResources().openRawResource(detailsID));
        return scan.nextLine();
    }

    public void updateFragment(String name){

        int detailsID = getResources().getIdentifier(name,"raw",myActivity.getPackageName());
        int imageID = getResources().getIdentifier(name,"drawable",myActivity.getPackageName());

        candidateImage.setImageResource(imageID);
        nameText.setText(name.toUpperCase());
        detailsText.setText(readDetailsFile(detailsID));

    }
}
