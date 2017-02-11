package com.example.crosbylanham.baseballstatscollector;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ViewStats extends AppCompatActivity {

    Player player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_stats);



        Intent intent = new Intent(ViewStats.this,QuickPitchingStatsActivity.class);
        startActivity(intent);

    /*
    #TODO make interface so that we can view and edit stats if needed
     */
    }

}
