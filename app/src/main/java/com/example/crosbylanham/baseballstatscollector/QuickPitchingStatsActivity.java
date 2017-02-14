package com.example.crosbylanham.baseballstatscollector;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class QuickPitchingStatsActivity extends AppCompatActivity {

    DataBaseHelper dataBaseHelper = new DataBaseHelper(this);

    TextView strikes;
    TextView balls;
    TextView pitches;
    TextView hits;

    Player player;
    Game game;

    PitchingStats totalstats;

    Spinner spinner,gamespiner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pitching_stats);

        totalstats = new PitchingStats();

        initTextViews();
        spinnerAction();
    }

    public void spinnerAction() {
        spinner = (Spinner) findViewById(R.id.pitchingPlayers);
        gamespiner = (Spinner) findViewById(R.id.PitchingStats_gamespinner);

        List<String> list = new ArrayList<>(
                dataBaseHelper.getAllPlayersNames()
        );

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,
                list);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                player = dataBaseHelper.getPlayer(parentView.getSelectedItem().toString());
                getTotalStats();
                fillinformation();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                totalstats = new PitchingStats();
                fillinformation();
            }
        });

        List<String> listOfGames = new ArrayList<String>(dataBaseHelper.getAllGamesForPlayer());

        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,
                listOfGames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        gamespiner.setAdapter(adapter1);
        gamespiner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                game = dataBaseHelper.getGame(parent.getSelectedItem().toString());

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void initTextViews() {
        strikes = (TextView) findViewById(R.id.strikes);
        balls = (TextView) findViewById(R.id.balls);
        pitches = (TextView) findViewById(R.id.pitches);
        hits = (TextView) findViewById(R.id.hits);
    }

    public void fillinformation() {
        strikes.setText(String.valueOf(totalstats.getStrikes()));
        balls.setText(String.valueOf(totalstats.getBalls()));
        pitches.setText(String.valueOf(totalstats.getPitchs()));
        hits.setText(String.valueOf(totalstats.getHits()));
    }

    public void getTotalStats() {
        ArrayList<PitchingStats> stats = new ArrayList<>(
                new DataBaseHelper(QuickPitchingStatsActivity.this)
                        .getAllPitchingStatsForPlayer(player.PlayerID)
        );
        totalstats = new PitchingStats();
        for (PitchingStats x : stats) {
            Log.d("FROM DATABASE", String.valueOf(x.getPlayerID()));
            Log.d("FROM DATABASE", String.valueOf(x.getBalls()));
            Log.d("FROM DATABASE", String.valueOf(x.getPitchs()));
            totalstats.strikes += x.getStrikes();
            totalstats.balls += x.getBalls();
            totalstats.pitchs += x.getPitchs();
            totalstats.hits += x.getHits();
        }
    }
}
