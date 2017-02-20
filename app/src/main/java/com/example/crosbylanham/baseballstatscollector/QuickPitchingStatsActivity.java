package com.example.crosbylanham.baseballstatscollector;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class QuickPitchingStatsActivity extends AppCompatActivity {

    DataBaseHelper dataBaseHelper = new DataBaseHelper(this);

    Player player = null;
    Game game;

    TextView gamesPlayered,wins,losses,totalERA,GS,shutouts,saves;
    TextView totalhits, totalHR,totalIP,totalR, totalER,totalBB,totalHBP;
    TextView IP,H,Runs,Pitches,ER,BB,SO,WHIP,ERA;

    Spinner spinner,gamespiner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pitching_stats);

        initGameTextViews();
        initTotalStats();
        playerSpinnerAction();
    }

    public void playerSpinnerAction() {
        spinner = (Spinner) findViewById(R.id.pitchingPlayers);
        gamespiner = (Spinner) findViewById(R.id.PitchingStats_gamespinner);

        List<String> list = new ArrayList<>(dataBaseHelper.getAllPlayersNames());

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,
                list);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                player = dataBaseHelper.getPlayer(parentView.getSelectedItem().toString());
                gameSpinnerAction();
                fillTotalStats();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

            }
        });
        //--------------------------------------------------------------------------------------------------------
    }
    public void gameSpinnerAction(){
        if(player == null){}else {
            List<String> listOfGames = new ArrayList<String>(dataBaseHelper.getAllGamesNamesForPitcher(player.getPlayerID()));

            ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,
                    listOfGames);
            adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            gamespiner.setAdapter(adapter1);
            gamespiner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    game = dataBaseHelper.getGame(parent.getSelectedItem().toString());
                    fillGameTextFields();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }
    }

    public void initGameTextViews() {
        IP = (TextView) findViewById(R.id.PitchingStats_Game_IP);
        H = (TextView) findViewById(R.id.PitchingStats_Game_H);
        Runs = (TextView) findViewById(R.id.PitchingStats_Game_R);
        Pitches = (TextView) findViewById(R.id.PitchingStats_Game_Pitches);
        ER = (TextView) findViewById(R.id.PitchingStats_Game_ER);
        BB = (TextView) findViewById(R.id.PitchingStats_Game_BB);
        SO = (TextView) findViewById(R.id.PitchingStats_Game_SO);
        WHIP = (TextView) findViewById(R.id.PitchingStats_Game_WHIP);
        ERA = (TextView) findViewById(R.id.PitchingStats_Game_ERA);
    }
    public void initTotalStats(){
        gamesPlayered = (TextView)findViewById(R.id.PitchingStats_games);
        wins = (TextView)findViewById(R.id.PitchingStats_wins);
        losses = (TextView)findViewById(R.id.PitchingStats_losses);
        totalERA = (TextView)findViewById(R.id.PitchingStats_era);
        GS = (TextView)findViewById(R.id.PitchingStats_gs);
        shutouts = (TextView)findViewById(R.id.PitchingStats_shutouts);
        saves = (TextView)findViewById(R.id.PitchingStats_saves);

        totalhits = (TextView)findViewById(R.id.PitchingStats_hits);
        totalHR = (TextView)findViewById(R.id.PitchingStats_Total_HRs);
        totalIP = (TextView)findViewById(R.id.PitchingStats_Total_IP);
        totalR = (TextView)findViewById(R.id.PitchingStats_Total_R);
        totalER = (TextView)findViewById(R.id.PitchingStats_Total_ER);
        totalBB = (TextView)findViewById(R.id.PitchingStats_Total_BB);
        totalHBP = (TextView)findViewById(R.id.PitchingStats_Total_HBP);
    }
    public void fillGameTextFields() {
        IP.setText(     "0"     );
        H.setText(      "0"     );
        Runs.setText(   "0"     );
        Pitches.setText("0"  );
        ER.setText(     "0"    );
        BB.setText(     "0"    );
        SO.setText(     "0"    );
        WHIP.setText(   "0"    );
        ERA.setText(    "0"     );
    }
    public void fillTotalStats(){
        ArrayList<PitchingStats> allPitchingStats = dataBaseHelper.getAllPitchingStatsForPlayer(player.getPlayerID());
        int gamesPlayed = allPitchingStats.size();
        int countwins = 0;
        int countlossses=0;
        double ERA = 0;
        int gs =0;
        int shutout=0;
        int countsaves=0;
        int h=0;
        int homeRuns = 0,walks = 0;
        int outspitched=0;int runs =0;int er=0;int bb =0;int hbp=0;
        for (PitchingStats x:allPitchingStats) {
            homeRuns += x.getHomeRuns();
            h += x.getHits();
            walks+=x.getWalks();
            hbp+=x.getHitsByPitch();
            outspitched += x.getOutsPitched();
        }
        ERA = er/outspitched/3.0;/* #TODO i need to fix the stats so
        that we can have innings pitched and total inings pitched */

        gamesPlayered.setText(String.valueOf(gamesPlayed));
        wins.setText("N/A");
        losses.setText("N/A");
        totalERA.setText(String.format("%.3f",ERA));
        GS.setText("N/A");
        shutouts.setText("N/A");
        saves.setText("N/A");
        totalhits.setText(String.valueOf(h));
        totalHR.setText(String.valueOf(homeRuns));
        totalIP.setText(String.format("%.3f",outspitched/3.0));
        totalR.setText("0");
        totalER.setText("N/A");
        totalBB.setText(String.valueOf(walks));
        totalHBP.setText(String.valueOf(hbp));
    }
}