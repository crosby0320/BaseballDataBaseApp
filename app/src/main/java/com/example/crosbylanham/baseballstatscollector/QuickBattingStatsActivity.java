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

    TextView paTextView,abTextView,avgTextView,obpTextView,slgTextView,
            rTextView,hTextView,singleTextView,doubleTextView,triplesTextView,
            hrTextView,sbTextView,csTextView;

    TextView gamepaTextView,gameabTextView,gameavgTextView,gameobpTextView,gameslgTextView,
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
        playerSpinnerAction();

    }

    public void initTotalTextViews(){
        paTextView      = (TextView)findViewById(R.id.BattingStats_PA);
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
        gamepaTextView      = (TextView)findViewById(R.id.BattingStats_game_PA);
        gameabTextView      = (TextView)findViewById(R.id.BattingStats_game_AB);
        gameavgTextView     = (TextView)findViewById(R.id.BattingStats_game_AVG);
        gameobpTextView     = (TextView)findViewById(R.id.BattingStats_game_OBP);
        gameslgTextView     = (TextView)findViewById(R.id.BattingStats_game_SLG);
        gamerTextView       = (TextView)findViewById(R.id.BattingStats_game_R);
        gamehTextView       = (TextView)findViewById(R.id.BattingStats_game_H);
        gamesingleTextView  = (TextView)findViewById(R.id.BattingStats_game_1B);
        gamedoubleTextView  = (TextView)findViewById(R.id.BattingStats_game_2B);
        gametriplesTextView = (TextView)findViewById(R.id.BattingStats_game_3B);
        gamehrTextView      = (TextView)findViewById(R.id.BattingStats_game_HR);
        gamesbTextView      = (TextView)findViewById(R.id.BattingStats_game_SB);
        gamecsTextView      = (TextView)findViewById(R.id.BattingStats_game_CS);
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
                fillTotalStats();
                gameSpinnerAction();
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
        double totalavg = 0;
        double totalobp = 0;
        double totalslg = 0;
        int totalr  = 0;
        int totalh  = 0;
        int total1b = 0;
        int total2b = 0;
        int total3b = 0;
        int totalhr = 0;
        int totalsb = 0;
        int totalcs = 0;
        int totalab = 0;
        int totalBB = 0;

        ArrayList<AtBats> allabs = dataBaseHelper.getAllABsForPlayer(player.getPlayerID());
        for (AtBats a:allabs){
            if(a.getOutcome() == 0 ){total1b+=1;}
            else if(a.getOutcome() == 1 ){total2b+=1;}
            else if(a.getOutcome() == 2 ){total3b+=1;}
            else if(a.getOutcome() == 3 ){totalhr+=1;}
        }
        int totalHBP = new AtBats().getAllHBP(allabs);
        totalBB     = new AtBats().getAllBaseOnBalls(allabs);
        totalPA     = allabs.size();
        totalab     = new AtBats().getAllAtBats(allabs);
        totalh      = new AtBats().getallhits(allabs);
        totalr      = 0;
        totalavg    = totalh/ (double)totalab;
        totalobp    = (totalh + totalBB + totalHBP)/
                (double)(totalab + totalBB + totalHBP );
        totalslg    = (total1b + total2b*2 + total3b * 3 + totalhr * 4)/
                (double)(totalab);

        abTextView.setText(String.valueOf(totalab));
        paTextView.setText(String.valueOf(totalPA));
        avgTextView.setText(String.format("%.3f",totalavg));
        obpTextView.setText(String.format("%.3f",totalobp));
        slgTextView.setText(String.format("%.3f",totalslg));
        rTextView.setText(String.valueOf(totalr));
        hTextView.setText(String.valueOf(totalh));
        singleTextView.setText(String.valueOf(total1b));
        doubleTextView.setText(String.valueOf(total2b));
        triplesTextView.setText(String.valueOf(total3b));
        hrTextView.setText(String.valueOf(totalhr));
        sbTextView.setText("N/A");
        csTextView.setText("N/A");
    }

}
