package com.example.instructor.candidateprofiles;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Scanner;

public class ProfileActivity extends AppCompatActivity {

    private TextView nameText;
    private TextView detailsText;
    private ImageView candidateImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        nameText = (TextView) findViewById(R.id.nameText);
        detailsText = (TextView) findViewById(R.id.detailsText);
        candidateImage = (ImageView) findViewById(R.id.candidateImage);

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");

        int detailsID = getResources().getIdentifier(name,"raw",getPackageName());
        int imageID = getResources().getIdentifier(name,"drawable",getPackageName());

        candidateImage.setImageResource(imageID);
        nameText.setText(name.toUpperCase());
        detailsText.setText(readDetailsFile(detailsID));



    }

    public String readDetailsFile(int detailsID){
        Scanner scan = new Scanner(getResources().openRawResource(detailsID));
        return scan.nextLine();
    }
}
