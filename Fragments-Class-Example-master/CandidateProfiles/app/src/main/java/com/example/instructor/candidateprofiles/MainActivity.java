package com.example.instructor.candidateprofiles;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void profileClick(View view) {

        String name = view.getTag().toString();

        Intent intent = new Intent(this,ProfileActivity.class);
        intent.putExtra("name",name);
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            startActivity(intent);
        }

        DetailsFragment frag = (DetailsFragment)this.getFragmentManager().findFragmentById(R.id.detailsFragment);
        frag.updateFragment(name);


    }
}
