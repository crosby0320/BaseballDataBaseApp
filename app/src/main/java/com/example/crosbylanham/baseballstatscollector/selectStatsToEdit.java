package com.example.crosbylanham.baseballstatscollector;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class selectStatsToEdit extends AppCompatActivity {

    Button pitchingStats, battingStats,gameStats;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_stats_to_edit);

        initButtons();
    }
    public void initButtons(){
        pitchingStats = (Button) findViewById(R.id.EditStats_pitchingStats);
        battingStats = (Button)findViewById(R.id.EditStats_battingstats);
        gameStats = (Button) findViewById(R.id.EditStats_teamlist);
    }
    public void buttonActions(){
        /*pitchingStats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(this, );
                startActivity(intent);
            }
        });

        battingStats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(this, );
                startActivity(intent);
            }
        });
        gameStats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(this, );
                startActivity(intent);
            }
        });*/
    }

}
