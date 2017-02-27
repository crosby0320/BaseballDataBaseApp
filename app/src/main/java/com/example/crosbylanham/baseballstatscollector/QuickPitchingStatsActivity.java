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
        PitchingStats p = dataBaseHelper.getPitchingStats(player.getPlayerID(),game.getGameID());
        IP.setText(         String.format("%.1f",(p.getOutsPitched()/3.0))     );
        H.setText(          String.valueOf(p.getHits())     );
        Runs.setText(       String.valueOf(p.getRuns())     );
        Pitches.setText(    String.valueOf(p.getPitchs())     );
        BB.setText(         String.valueOf(p.getWalks())    );
        SO.setText(         String.valueOf(p.getStrikouts())   );
        WHIP.setText(       String.format("%.3f",(p.getWalks()+p.getHits())/(p.getOutsPitched()/3.0))    );
        ERA.setText(        String.format("%.3f",(9*p.getRuns()/(p.getOutsPitched()/3.0)))    );
        Log.d("ERA", "9 * runs "+p.getRuns()+" / outs pitched "+p.getOutsPitched());
        Log.d("ERA", "9 * runs "+p.getRuns()+" / inningspitched "+ (p.getOutsPitched()/3.0));
        Log.d("ERA", "So total is "+(9*p.getRuns()/(p.getOutsPitched()/3.0)));
    }
    public void fillTotalStats(){
        ArrayList<PitchingStats> list = dataBaseHelper.getAllPitchingStatsForPlayer(player.getPlayerID());
        int gamesPlayed = list.size();
        PitchingStats p = new PitchingStats();
        gamesPlayered.setText(  String.valueOf(gamesPlayed));
        wins.setText(           "N/A");
        losses.setText(         "N/A");
        totalERA.setText(       String.format("%.3f",p.getERA(list)));
        GS.setText(             "N/A");
        shutouts.setText(       String.valueOf(p.getTotalShutOuts(list)));
        saves.setText(          "N/A");
        totalhits.setText(      String.valueOf(p.getTotalHits(list)));
        totalHR.setText(        String.valueOf(p.getTotalHomeRuns(list)));
        totalIP.setText(        String.format("%.1f",p.getOutsPitched()/3.0));
        totalER.setText(        String.format("%d",p.getTotalRuns(list)));
        totalBB.setText(        String.valueOf(p.getTotalWalks(list)));
        totalHBP.setText(       String.valueOf(p.getTotalHBP(list)));
    }
}