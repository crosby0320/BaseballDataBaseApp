package com.example.crosbylanham.baseballstatscollector;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

class ScoreBoard {
    public ScoreBoard(){}
    public int awayScore = 0;
    public int homeScore = 0;
    public int homePositionAtBat = 0;
    public int awayPositionAtBat = 0;
    public boolean top = true;
    public int inningNumber = 1;
    public int outs = 0;
}

public class FullGame extends AppCompatActivity {

    PitchCounter pitchCounter = new PitchCounter();
    ScoreBoard scoreBoard;
    Game game;AtBats atBats;Player playeratbat;

    String homeTeamName,homeTeamID;
    String awayTeamName,awayTeamID;
    ArrayList<Player> awayTeamPlayers,homeTeamPlayers;

    ImageView topInning,bottomInning;

    TextView[] baseRunners;

    TextView player1name ;
    TextView player2name ;
    TextView player3name ;
    TextView player4name ;
    TextView player5name ;
    TextView player6name ;
    TextView player7name ;
    TextView player8name ;
    TextView player9name ;
    TextView player10name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_game);
        baseRunners = new TextView[4];
        homeTeamName =  getIntent().getStringExtra("homeTeamName");
        awayTeamName =  getIntent().getStringExtra("awayTeamName");
        awayTeamPlayers = (ArrayList<Player>) getIntent().getSerializableExtra("awayTeam");
        homeTeamPlayers = (ArrayList<Player>) getIntent().getSerializableExtra("homeTeam");
        awayTeamID = getIntent().getStringExtra("awayTeamId");
        homeTeamID = getIntent().getStringExtra("homeTeamId");

        topInning = (ImageView)findViewById(R.id.FullGame_toparow);
        bottomInning = (ImageView)findViewById(R.id.FullGame_buttomArow);

        initBaseRunners();
        saveGameinformation();
        initButton();
        removeAllBaseRunners();
        initButtonActions();
        initTextViews();
        fillTeamNames(awayTeamPlayers);
        atBats = new AtBats();
        bottomInning.setVisibility(ImageView.INVISIBLE);topInning.setVisibility(ImageView.VISIBLE);
        scoreBoard = new ScoreBoard();
    }

    public void saveInformation(){

        if(scoreBoard.top){
            bottomInning.setVisibility(ImageView.INVISIBLE);topInning.setVisibility(ImageView.VISIBLE);
            fillTeamNames(awayTeamPlayers);
            setAtbatplayername(awayTeamPlayers.get(scoreBoard.awayPositionAtBat).toString());
            playeratbat = awayTeamPlayers.get(scoreBoard.awayPositionAtBat);
            scoreBoard.awayPositionAtBat = (scoreBoard.awayPositionAtBat +1) % awayTeamPlayers.size();
            atBats = new AtBats();
        }else{

        }
    }

    public void initBaseRunners(){
        baseRunners[0] = (TextView)findViewById(R.id.FullGame_HomePlate);
        baseRunners[1] = (TextView)findViewById(R.id.FullGame_FirstBase);
        baseRunners[2] = (TextView)findViewById(R.id.FullGame_SecondBase);
        baseRunners[3] = (TextView)findViewById(R.id.FullGame_ThirdBase);
    }

    public void initTextViews(){
        player1name = (TextView) findViewById(R.id.awayplayername1);
        player2name = (TextView) findViewById(R.id.awayplayername2);
        player3name = (TextView) findViewById(R.id.awayplayername3);
        player4name = (TextView) findViewById(R.id.awayplayernumber4);
        player5name = (TextView) findViewById(R.id.awayplayernumber5);
        player6name = (TextView) findViewById(R.id.awayplayernumber6);
        player7name = (TextView) findViewById(R.id.awayplayernumber7);
        player8name = (TextView) findViewById(R.id.awayplayernumber8);
        player9name = (TextView) findViewById(R.id.awayplayernumber9);
        player10name = (TextView) findViewById(R.id.awayplayernumber10);
    }

    public void saveGameinformation() {
        game = new Game();
        game.setAwayTeamID(awayTeamID);
        game.setHomeTeamID(homeTeamID);
        game.generateName(awayTeamName,homeTeamName);
        game = new DataBaseHelper().saveGame(game);
    }

    public void setAtbatplayername(String playerName) {
        baseRunners[0].setVisibility(View.VISIBLE);
        baseRunners[0].setText(playerName);
    }

    public void setFirstBasePlayer(String playerName) {
        baseRunners[1].setVisibility(View.VISIBLE);
        baseRunners[1].setText(playerName);
    }
    public void setSecondBasePlayer(String playerName){
        baseRunners[2].setVisibility(View.VISIBLE);
        baseRunners[2].setText(playerName);
    }
    public void setThirdBasePlayer(String playerName){
        baseRunners[3].setVisibility(View.VISIBLE);
        baseRunners[3].setText(playerName);
    }
    public void removeAllBaseRunners(){
        for (TextView x:baseRunners){
            x.setVisibility(View.GONE);
        }
    }

    public void fillTeamNames(ArrayList<Player> awayTeamPlayers) {
        player1name.setText(awayTeamPlayers.get(0).getName());
        player2name.setText(awayTeamPlayers.get(1).getName());
        player3name.setText(awayTeamPlayers.get(2).getName());
        player4name.setText(awayTeamPlayers.get(3).getName());
        player5name.setText(awayTeamPlayers.get(4).getName());
        player6name.setText(awayTeamPlayers.get(5).getName());
        player7name.setText(awayTeamPlayers.get(6).getName());
        player8name.setText(awayTeamPlayers.get(7).getName());
        player9name.setText(awayTeamPlayers.get(8).getName());
        if (awayTeamPlayers.size() == 10) {
            player10name.setText(awayTeamPlayers.get(9).getName());
            LinearLayout l = (LinearLayout)findViewById(R.id.FullGame_LastName);
            l.setVisibility(LinearLayout.VISIBLE);
        }else{
            LinearLayout l = (LinearLayout)findViewById(R.id.FullGame_LastName);
            l.setVisibility(LinearLayout.GONE);
        }
    }

    Button ballButton,strikesButton,foulButton,
    singleButton,doubleButton,tripleButton,homerunButton,
    foulOutButton,groundOutButton,flyOutButton,lineOutButton,
    advanceRunnerButton,hBPButton,fieldersChoiceButton,errorButton;

    public void initButton() {
        ballButton = (Button) findViewById(R.id.FullGame_Balls);
        strikesButton = (Button) findViewById(R.id.FullGame_Strikes);
        foulButton = (Button) findViewById(R.id.FullGame_Fouls);
        singleButton = (Button) findViewById(R.id.FullGame_Singles);
        doubleButton = (Button) findViewById(R.id.FullGame_Double);
        tripleButton = (Button) findViewById(R.id.FullGame_Triple);
        homerunButton = (Button) findViewById(R.id.FullGame_HomeRun);
        foulOutButton = (Button) findViewById(R.id.FullGame_FoulOut);
        groundOutButton = (Button) findViewById(R.id.FullGame_GroundOut);
        flyOutButton = (Button) findViewById(R.id.FullGame_FoulOut);
        lineOutButton = (Button) findViewById(R.id.FullGame_LineOut);
        advanceRunnerButton = (Button) findViewById(R.id.FullGame_AdvanceRunner);
        hBPButton = (Button) findViewById(R.id.FullGame_HBP);
        fieldersChoiceButton = (Button) findViewById(R.id.FullGame_FC);
        errorButton = (Button) findViewById(R.id.FullGame_E);
    }

    public void updateInformation(){
        ((TextView)findViewById(R.id.fullGame_ScoreBoard_Ball)).setText(pitchCounter.balls);
        ((TextView)findViewById(R.id.fullGame_ScoreBoard_Strikes)).setText(pitchCounter.strikes);
        ((TextView)findViewById(R.id.fullGame_ScoreBoard_Outs)).setText(pitchCounter.outs);
    }


    public void initButtonActions(){
        setBallButtonAction();
        setStrikeAction();
        setFoulBallAction();
        setSingleAction();
        setDoublesAction();
        setTripleAction();
        setHomeRunAction();
        setFoulOutAction();
        setGroundOutAction();
        setFlyOutAction();
        setLineOutAction();
        setAdvancedrunnerAction();
        setHbpAction();
        setFcAction();
        setEAction();
    }

    public void setBallButtonAction() {
        ballButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pitchCounter.getBalls() == 3) {
                    atBats.setBalls(pitchCounter.getBalls() + 1);
                    atBats.setStrikes(pitchCounter.getStrikes());
                    atBats.setOutcome(AtBatInformation.WALK);
                    atBats.setPlayerAtBatId(playeratbat.getPlayerID());
                    atBats.setPitches(pitchCounter.getTotalAtBatPitches() + 1);
                } else {
                    pitchCounter.calledBall();
                }
                updateInformation();
            }
        });
    }

    public void setStrikeAction() {
        strikesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pitchCounter.getStrikes() == 2) {
                    atBats.setBalls(pitchCounter.getBalls());
                    atBats.setStrikes(pitchCounter.getStrikes() + 1);
                    atBats.setPitches(pitchCounter.getTotalAtBatPitches() + 1);
                    atBats.setOutcome(AtBatInformation.STRIKEOUT);
                    atBats.setPlayerAtBatId(playeratbat.getPlayerID());
                } else {
                    pitchCounter.calledStrike();
                }
                updateInformation();
            }
        });
    }

    public void setFoulBallAction() {
        foulButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pitchCounter.foulAction();
                updateInformation();
            }
        });
    }

    public void setSingleAction() {
        singleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                atBats.setBalls(pitchCounter.getBalls());
                atBats.setStrikes(pitchCounter.getStrikes() + 1);
                atBats.setPitches(pitchCounter.getTotalAtBatPitches() + 1);
                atBats.setOutcome(AtBatInformation.SINGLE);
                atBats.setPlayerAtBatId(playeratbat.getPlayerID());

                setFirstBasePlayer(playeratbat.getName());

                saveInformation();
            }
        });
    }

    public void setDoublesAction() {
        doubleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                atBats.setBalls(pitchCounter.getBalls());
                atBats.setStrikes(pitchCounter.getStrikes() + 1);
                atBats.setPitches(pitchCounter.getTotalAtBatPitches() + 1);
                atBats.setOutcome(AtBatInformation.DOUBLE);
                atBats.setPlayerAtBatId(playeratbat.getPlayerID());

                setSecondBasePlayer(playeratbat.getName());

                saveInformation();
            }
        });
    }

    public void setTripleAction() {
        tripleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                atBats.setBalls(pitchCounter.getBalls());
                atBats.setStrikes(pitchCounter.getStrikes() + 1);
                atBats.setPitches(pitchCounter.getTotalAtBatPitches() + 1);
                atBats.setOutcome(AtBatInformation.TRIPLE);
                atBats.setPlayerAtBatId(playeratbat.getPlayerID());

                setThirdBasePlayer(playeratbat.getName());

                saveInformation();
            }
        });
    }

    public void setHomeRunAction() {
        homerunButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                atBats.setBalls(pitchCounter.getBalls());
                atBats.setStrikes(pitchCounter.getStrikes() + 1);
                atBats.setPitches(pitchCounter.getTotalAtBatPitches() + 1);
                atBats.setOutcome(AtBatInformation.HOMERUN);
                atBats.setPlayerAtBatId(playeratbat.getPlayerID());



                saveInformation();
            }
        });
    }

    public void setFoulOutAction() {
        foulOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                atBats.setBalls(pitchCounter.getBalls());
                atBats.setStrikes(pitchCounter.getStrikes() + 1);
                atBats.setPitches(pitchCounter.getTotalAtBatPitches() + 1);
                atBats.setOutcome(AtBatInformation.FOUL_OUT);
                atBats.setPlayerAtBatId(playeratbat.getPlayerID());

                saveInformation();
            }
        });

    }

    public void setGroundOutAction() {
        groundOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                atBats.setBalls(pitchCounter.getBalls());
                atBats.setStrikes(pitchCounter.getStrikes() + 1);
                atBats.setPitches(pitchCounter.getTotalAtBatPitches() + 1);
                atBats.setOutcome(AtBatInformation.GROUNDOUT);
                atBats.setPlayerAtBatId(playeratbat.getPlayerID());

                saveInformation();
            }
        });
    }

    public void setFlyOutAction() {
        flyOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                atBats.setBalls(pitchCounter.getBalls());
                atBats.setStrikes(pitchCounter.getStrikes() + 1);
                atBats.setPitches(pitchCounter.getTotalAtBatPitches() + 1);
                atBats.setOutcome(AtBatInformation.FLYOUT);
                atBats.setPlayerAtBatId(playeratbat.getPlayerID());

                saveInformation();
            }
        });
    }

    public void setLineOutAction() {
        lineOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                atBats.setBalls(pitchCounter.getBalls());
                atBats.setStrikes(pitchCounter.getStrikes() + 1);
                atBats.setPitches(pitchCounter.getTotalAtBatPitches() + 1);
                atBats.setOutcome(AtBatInformation.LINEOUT);
                atBats.setPlayerAtBatId(playeratbat.getPlayerID());

                saveInformation();
            }
        });
    }

    public void setAdvancedrunnerAction() {
        advanceRunnerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                atBats.setBalls(pitchCounter.getBalls());
                atBats.setStrikes(pitchCounter.getStrikes() + 1);
                atBats.setPitches(pitchCounter.getTotalAtBatPitches() + 1);
                atBats.setOutcome(AtBatInformation.ADVANCERUNNER);
                atBats.setPlayerAtBatId(playeratbat.getPlayerID());

                saveInformation();
            }
        });
    }

    public void setHbpAction() {
        hBPButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                atBats.setBalls(pitchCounter.getBalls());
                atBats.setStrikes(pitchCounter.getStrikes() + 1);
                atBats.setPitches(pitchCounter.getTotalAtBatPitches() + 1);
                atBats.setOutcome(AtBatInformation.HIT_BY_PITCH);
                atBats.setPlayerAtBatId(playeratbat.getPlayerID());

                saveInformation();
            }
        });
    }

    public void setEAction() {
        errorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                atBats.setBalls(pitchCounter.getBalls());
                atBats.setStrikes(pitchCounter.getStrikes() + 1);
                atBats.setPitches(pitchCounter.getTotalAtBatPitches() + 1);
                atBats.setOutcome(AtBatInformation.ERROR);
                atBats.setPlayerAtBatId(playeratbat.getPlayerID());

                saveInformation();
            }
        });
    }

    public void setFcAction() {
        fieldersChoiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                atBats.setBalls(pitchCounter.getBalls());
                atBats.setStrikes(pitchCounter.getStrikes() + 1);
                atBats.setPitches(pitchCounter.getTotalAtBatPitches() + 1);
                atBats.setOutcome(AtBatInformation.FIELDERS_CHOICE);
                atBats.setPlayerAtBatId(playeratbat.getPlayerID());

                saveInformation();
            }
        });
    }

}
