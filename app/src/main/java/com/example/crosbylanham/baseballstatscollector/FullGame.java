package com.example.crosbylanham.baseballstatscollector;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;

class ScoreBoard {
    public int awayScore=0;
    public int homScore = 0;
    public int homePositionAtBat = 0;
    public int awayPositionAtBat = 0;
    public boolean top = true;
}
public class FullGame extends AppCompatActivity {


    PitchCounter pitchCounter = new PitchCounter();
    ScoreBoard scoreBoard = new ScoreBoard();

    ArrayList<Player> awayTeamPlayers = (ArrayList<Player>) getIntent().getSerializableExtra("awayTeam");
    ArrayList<Player> homeTeamPlayers = (ArrayList<Player>) getIntent().getSerializableExtra("homeTeam");

    TextView player1name = (TextView)findViewById(R.id.awayplayername1);
    TextView player2name = (TextView)findViewById(R.id.awayplayername2);
    TextView player3name = (TextView)findViewById(R.id.awayplayername3);
    TextView player4name = (TextView)findViewById(R.id.awayplayernumber4);
    TextView player5name = (TextView)findViewById(R.id.awayplayernumber5);
    TextView player6name = (TextView)findViewById(R.id.awayplayernumber6);
    TextView player7name = (TextView)findViewById(R.id.awayplayernumber7);
    TextView player8name = (TextView)findViewById(R.id.awayplayernumber8);
    TextView player9name = (TextView)findViewById(R.id.awayplayernumber9);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_game);

        startGame();
    }

    public void startGame(){
        atBatFinished();
    }
    public void setAtbatplayername(String playerName){
        ((TextView)findViewById(R.id.personAtHomePlate)).setText(playerName);
    }
    public void setFirstBasePlayer(String playerName){
        ((TextView)findViewById(R.id.personAtHomePlate)).setText(playerName);
    }
    public void atBatFinished(){
        fillTeamNames(awayTeamPlayers);
        Player atbat = getPersonAtBat();
        setAtbatplayername(atbat.getName());
    }
    public void fillTeamNames(ArrayList<Player> awayTeamPlayers){
        player1name.setText(awayTeamPlayers.get(0).getName());
        player2name.setText(awayTeamPlayers.get(1).getName());
        player3name.setText(awayTeamPlayers.get(2).getName());
        player4name.setText(awayTeamPlayers.get(3).getName());
        player5name.setText(awayTeamPlayers.get(4).getName());
        player6name.setText(awayTeamPlayers.get(5).getName());
        player7name.setText(awayTeamPlayers.get(6).getName());
        player8name.setText(awayTeamPlayers.get(7).getName());
        player9name.setText(awayTeamPlayers.get(8).getName());
    }
    public Player getPersonAtBat(){
        if(scoreBoard.top){
            return awayTeamPlayers.get(scoreBoard.awayPositionAtBat++);
        }else{
            return homeTeamPlayers.get(scoreBoard.homePositionAtBat++);
        }
    }
}
