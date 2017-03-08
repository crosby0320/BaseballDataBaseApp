package com.example.crosbylanham.baseballstatscollector;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class EditHome extends AppCompatActivity {

    Button playerInformationButton,atBatButton,gameButton,pitchingStats,teamImnformationButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_home);
        initButtons();
    }

    public void initButtons(){
        playerInformationButton = (Button)findViewById(R.id.EditTable_PLayerInfoButton);
        playerInformationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*#TODO*/
            }
        });
        atBatButton = (Button) findViewById(R.id.EditTable_AtBatButton);
        atBatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*#TODO*/
            }
        });
        gameButton = (Button) findViewById(R.id.EditTable_GameButton);
        gameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*#TODO*/
            }
        });
        pitchingStats = (Button) findViewById(R.id.EditTable_PitchongStatsButton);
        pitchingStats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*#TODO*/
            }
        });
        teamImnformationButton = (Button) findViewById(R.id.EditTable_TeamButton);
        teamImnformationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*#TODO*/
            }
        });
    }
}
