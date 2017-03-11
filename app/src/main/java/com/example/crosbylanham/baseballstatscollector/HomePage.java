package com.example.crosbylanham.baseballstatscollector;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;

import com.google.firebase.crash.FirebaseCrash;

public class HomePage extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        initButtons();
        fullGameActionButton();
        quickPitchStatsButtonAction();
        LoadTeamActionButton();
        CheckStatsButtonAction();
        viewtables();
        //Cleaner c = new Cleaner();
        //c.removeNonGeneratedPlayers();
    }

    Button fullGameButton;
    Button quickPitchButton;
    Button quickBattingStatsButton;
    Button checkStatsButton;
    Button viewTablesButton;

    public void initButtons(){
        fullGameButton = (Button) findViewById(R.id.fullgamebutton);
        quickPitchButton = (Button) findViewById(R.id.quickpitchlink);
        quickBattingStatsButton = (Button) findViewById(R.id.quickbattingstatsbutton);
        checkStatsButton = (Button) findViewById(R.id.homepage_checkstatsbutton);
        viewTablesButton = (Button) findViewById(R.id.ViewTablesButton);
    }

    public void fullGameActionButton(){
        fullGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePage.this,MakeTeam.class);
                startActivity(intent);
            }
        });
    }
    public void quickPitchStatsButtonAction(){
        quickPitchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePage.this,QuickPitchingStatsGameActivity.class);
                startActivity(intent);
            }
        });
    }
    public void LoadTeamActionButton(){
        quickBattingStatsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePage.this,QuickBattingStatsGameActivity.class);
                startActivity(intent);
            }
        });
    }
    public void CheckStatsButtonAction(){
        checkStatsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePage.this,WhosLombo.class);
                startActivity(intent);
            }
        });
    }
    //---------------------------just used for debugging see tables and elements-------
    public void viewtables(){
        viewTablesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePage.this,EditorInfo.class);
                startActivity(intent);
            }
        });
    }
}
