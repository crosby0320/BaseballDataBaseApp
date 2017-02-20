package com.example.crosbylanham.baseballstatscollector;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class QuickBattingStatsActivity extends AppCompatActivity {

    Spinner playerSpinner,gameSpinner;

    TextView abTextView,avgTextView,obpTextView,slgTextView,
            rTextView,hTextView,singleTextView,doubleTextView,triplesTextView,
            hrTextView,sbTextView,csTextView;

    TextView gameabTextView,gameavgTextView,gameobpTextView,gameslgTextView,
            gamerTextView,gamehTextView,gamesingleTextView,gamedoubleTextView,gametriplesTextView,
            gamehrTextView,gamesbTextView,gamecsTextView;

    Player player;
    Game game;

    DataBaseHelper dataBaseHelper = new DataBaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quick_batting_stats2);

        initTotalTextViews();
        initGameTextViews();

    }

    public void initTotalTextViews(){
        abTextView      = (TextView)findViewById(R.id.BattingStats_AB);
        avgTextView     = (TextView)findViewById(R.id.BattingStats_AVG);
        obpTextView     = (TextView)findViewById(R.id.BattingStats_OBP);
        slgTextView     = (TextView)findViewById(R.id.BattingStats_SLG);
        rTextView       = (TextView)findViewById(R.id.BattingStats_R);
        hTextView       = (TextView)findViewById(R.id.BattingStats_H);
        singleTextView  = (TextView)findViewById(R.id.BattingStats_1B);
        doubleTextView  = (TextView)findViewById(R.id.BattingStats_2B);
        triplesTextView = (TextView)findViewById(R.id.BattingStats_3B);
        hrTextView      = (TextView)findViewById(R.id.BattingStats_HR);
        sbTextView      = (TextView)findViewById(R.id.BattingStats_SB);
        csTextView      = (TextView)findViewById(R.id.BattingStats_CS);
    }

    public void initGameTextViews(){
        gameabTextView = (TextView)findViewById(R.id.BattingStats_game_AB);
        gameavgTextView = (TextView)findViewById(R.id.BattingStats_game_AVG);
        gameobpTextView     = (TextView)findViewById(R.id.BattingStats_game_OBP);
        gameslgTextView     = (TextView)findViewById(R.id.BattingStats_game_SLG);
        gamerTextView       = (TextView)findViewById(R.id.BattingStats_game_R);
        gamehTextView       = (TextView)findViewById(R.id.BattingStats_game_H);
        gamesingleTextView = (TextView)findViewById(R.id.BattingStats_game_1B);
        gamedoubleTextView = (TextView)findViewById(R.id.BattingStats_game_2B);
        gametriplesTextView = (TextView)findViewById(R.id.BattingStats_game_3B);
        gamehrTextView = (TextView)findViewById(R.id.BattingStats_game_HR);
        gamesbTextView = (TextView)findViewById(R.id.BattingStats_game_SB);
        gamecsTextView = (TextView)findViewById(R.id.BattingStats_game_CS);
    }
    public void playerSpinnerAction(){
        playerSpinner = (Spinner) findViewById(R.id.BattingStats_playerSpinner);
        gameSpinner = (Spinner) findViewById(R.id.BattingStats_gameSpinner);

        List<String> list = new ArrayList<>(dataBaseHelper.getallBattersNames());

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,
                list);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        playerSpinner.setAdapter(adapter);

        playerSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

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
    }

    public void gameSpinnerAction(){
        if(player == null){}else {
            List<String> listOfGames = new ArrayList<String>();

            ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,
                    listOfGames);
            adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            gameSpinner.setAdapter(adapter1);
            gameSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    game = dataBaseHelper.getGame(parent.getSelectedItem().toString());
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }
    }

    public void fillTotalStats(){
        /*#TODO make sure that i add some functionality to
        * the players need to be able to get the Stolen basses and caught Stealing*/
        int totalPA =0;
        int totalavg = 0;
        int totalobp = 0;
        int totalslg = 0;
        int totalr = 0;
        int totalh = 0;
        int total1b = 0;
        int total2b = 0;
        int total3b = 0;
        int totalhr = 0;
        int totalsb = 0;
        int totalcs = 0;
        int totalab = 0;

                ArrayList<AtBats> allabs = dataBaseHelper.getAllABsForPlayer(player.getPlayerID());
        totalPA =totalab = allabs.size();
        for (AtBats a:allabs){
            if(a.getOutcome() == 0 ||a.getOutcome() == 1||a.getOutcome() == 2||
                    a.getOutcome() == 3){totalh+=1;}
            if(a.getOutcome() == AtBats.WALK ||a.getOutcome() == AtBats.HIT_BY_PITCH
                    || a.getOutcome() == AtBats.ADVANCERUNNER){totalab--;}
            if(a.getOutcome() == 0 ){total1b+=1;}
            else if(a.getOutcome() == 1 ){total2b+=1;}
            else if(a.getOutcome() == 2 ){total3b+=1;}
            else if(a.getOutcome() == 3 ){totalhr+=1;}
        }

        abTextView.setText(String.valueOf(totalPA));
        avgTextView.setText(String.format("%.3f",totalh/(double)(totalab)));
        obpTextView.setText("N/A");
        slgTextView.setText("N/A");
        rTextView.setText("N/A");
        hTextView.setText(String.valueOf(totalh));
        singleTextView.setText(String.valueOf(total1b));
        doubleTextView.setText(String.valueOf(total2b));
        triplesTextView.setText(String.valueOf(total3b));
        hrTextView.setText(String.valueOf(totalhr));
        sbTextView.setText("N/A");
        csTextView.setText("N/A");
    }

}
