package com.example.crosbylanham.baseballstatscollector;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

class ScoreBoard {
    public int awayScore=0;
    public int homeScore = 0;
    public int homePositionAtBat = 0;
    public int awayPositionAtBat = 0;
    public boolean top = true;
    public int inningNumber = 1;
    public int outs = 0;
}
public class FullGame extends AppCompatActivity {

    DataBaseHelper dataBaseHelper = new DataBaseHelper(this);
    PitchCounter pitchCounter = new PitchCounter();
    ScoreBoard scoreBoard = new ScoreBoard();
    Game game;

    String homeTeamName = (String) getIntent().getSerializableExtra("homeTeamName");
    String awayTeamName = (String) getIntent().getSerializableExtra("awayTeamName");
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
    TextView player10name = (TextView)findViewById(R.id.awayplayernumber10);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_game);
        game = createGameFillGameInformation();
        startGame();
    }

    public void startGame(){

        while(scoreBoard.inningNumber <10 && scoreBoard.awayScore != scoreBoard.homeScore){
            Inning inning = saveInningInformation(game,false);
            while (scoreBoard.top){
                Player playerAtBat = awayTeamPlayers.get(scoreBoard.awayPositionAtBat);
                setAtbatplayername(playerAtBat.getName());
                AtBats atBat = waitForOutCome();
                saveOutCome(playerAtBat, atBat, inning);
                if (scoreBoard.outs == 3){
                    pitchCounter.reset();
                    scoreBoard.outs = 0;
                    scoreBoard.top = !scoreBoard.top;
                }
            }
            /*#TODO update inning score*/
            dataBaseHelper.saveInning(inning);
            inning = new Inning();

            //--------------------------------------------------------------------------------

            while(!scoreBoard.top){
            }
        }
    }
    public Inning saveInningInformation(Game game,boolean areTheyHome){
        Inning inning = new Inning();
        inning.setGameID(game.getGameID());
        inning.setHome(areTheyHome);
        inning.setInningNumber(scoreBoard.inningNumber);
        inning.setInningID(dataBaseHelper.saveInning(inning));
        return inning;
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
        if(awayTeamPlayers.size() == 10){
            player10name.setText(awayTeamPlayers.get(9).getName());
        }
    }
    public Player getPersonAtBat(){
        if(scoreBoard.top){
            return awayTeamPlayers.get(scoreBoard.awayPositionAtBat++);
        }else{
            return homeTeamPlayers.get(scoreBoard.homePositionAtBat++);
        }
    }
    public void saveOutCome(Player playerAtBats,AtBats atBats, Inning inning){

        atBats.setBalls(pitchCounter.getBalls());
        atBats.setGameID(game.getGameID());
        atBats.setInningID(inning.getInningID());
        atBats.setStrikes(pitchCounter.strikes);
        atBats.setPlayerAtBatId(playerAtBats.getPlayerID());
        atBats.setPitches(pitchCounter.totalAtBatPitches);
        atBats.setPitcherID(0);/*#TODO make sure to add the pitchers information*/

        dataBaseHelper.saveAtBat(atBats);
    }
    boolean outcome = true;
    public AtBats waitForOutCome(){
        AtBats atBats = new AtBats();
        Log.d("Full game","Going to start waiting for an out come");
        while(outcome){
            /*#TODO wait for outcome
            * i just have to remember to make sure that i wait for the outcome*/
        }
        outcome = true;
        scoreBoard.outs += atBats.howManyOuts();
        return atBats;
    }
    public Game createGameFillGameInformation(){
        Game game = new Game();

        game.setAwayTeamID(dataBaseHelper.getTeam(awayTeamName).getTeamid());
        game.setHomeTeamID(dataBaseHelper.getTeam(homeTeamName).getTeamid());

        game.generateName(homeTeamName, awayTeamName);

        return game;
    }
}
