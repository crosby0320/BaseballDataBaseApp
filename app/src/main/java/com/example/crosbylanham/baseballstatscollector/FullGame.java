package com.example.crosbylanham.baseballstatscollector;

import android.app.Dialog;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

class ScoreBoard {
    public ScoreBoard(){}
    public int awayScore = 0;
    public int homeScore = 0;
    public int homePositionAtBat = 0;
    public int awayPositionAtBat = 0;
    public boolean top = true;
    public int inningNumber = 1;
}

public class FullGame extends AppCompatActivity {

    PitchCounter pitchCounter = new PitchCounter();
    ScoreBoard scoreBoard;
    Game game;AtBats atBats;Player playeratbat;

    String homeTeamName,homeTeamID;
    String awayTeamName,awayTeamID;
    ArrayList<Player> awayTeamPlayers,homeTeamPlayers;

    ImageView topInning,bottomInning;

    TextView[] baseRunnersTextViews;
    Player[] playerBaseRunners;
    TextView[] playername;

    boolean lastOutFlag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_game);
        playerBaseRunners = new Player[4];
        baseRunnersTextViews = new TextView[4];
        playername = new TextView[10];
        homeTeamName =  getIntent().getStringExtra("homeTeamName");
        awayTeamName =  getIntent().getStringExtra("awayTeamName");
        awayTeamPlayers = (ArrayList<Player>) getIntent().getSerializableExtra("awayTeam");
        homeTeamPlayers = (ArrayList<Player>) getIntent().getSerializableExtra("homeTeam");
        awayTeamID = getIntent().getStringExtra("awayTeamId");
        homeTeamID = getIntent().getStringExtra("homeTeamId");

        topInning = (ImageView)findViewById(R.id.FullGame_toparow);
        bottomInning = (ImageView)findViewById(R.id.FullGame_buttomArow);

        lastOutFlag = false;

        initBaseRunners();
        saveGameinformation();
        initButton();
        removeAllBaseRunners();
        initButtonActions();
        initTextViews();
        scoreBoard = new ScoreBoard();
        ///beginning
        fillTeamNames(awayTeamPlayers);
        atBats = new AtBats();
        playeratbat = awayTeamPlayers.get(scoreBoard.awayPositionAtBat);
        setTextColor(scoreBoard.awayPositionAtBat);
        setAtbatplayername(awayTeamPlayers.get(scoreBoard.awayPositionAtBat).getName());
        bottomInning.setVisibility(ImageView.INVISIBLE);topInning.setVisibility(ImageView.VISIBLE);

    }

    public void setTextColor(int a){
        for(int i =0;i<10;i++){
            if(i == a){playername[i].setTextColor(Color.rgb(200,0,0));}
            else{playername[i].setTextColor(Color.rgb(0,0,0));}
        }
    }

    public void saveInformation(){
        if(scoreBoard.top){
            bottomInning.setVisibility(ImageView.INVISIBLE);topInning.setVisibility(ImageView.VISIBLE);//changing the Arrow up or down
            fillTeamNames(awayTeamPlayers);//filling team names to the screen
            new DataBaseHelper().saveAtBat(atBats);
            scoreBoard.awayPositionAtBat = (scoreBoard.awayPositionAtBat +1) % awayTeamPlayers.size();//goes to the next players iteration.
            playeratbat = awayTeamPlayers.get(scoreBoard.awayPositionAtBat); //get score board position for the next player at bat
            setAtbatplayername(awayTeamPlayers.get(scoreBoard.awayPositionAtBat).getName()); // sets up the player at bases name
            setTextColor(scoreBoard.awayPositionAtBat);
            atBats = new AtBats();
            if (lastOutFlag){
                scoreBoard.top = false;
                removeAllBaseRunners();
                fillTeamNames(homeTeamPlayers);
                setAtbatplayername(homeTeamPlayers.get(scoreBoard.homePositionAtBat).getName()); // sets up the player at bases name
                setTextColor(scoreBoard.homePositionAtBat);
                bottomInning.setVisibility(ImageView.VISIBLE);topInning.setVisibility(ImageView.INVISIBLE);//changing the Arrow up or down
            }
        }else{
            bottomInning.setVisibility(ImageView.VISIBLE);topInning.setVisibility(ImageView.INVISIBLE);//changing the Arrow up or down
            fillTeamNames(homeTeamPlayers);//filling team names to the screen
            new DataBaseHelper().saveAtBat(atBats);
            scoreBoard.homePositionAtBat = (scoreBoard.homePositionAtBat +1) % homeTeamPlayers.size();//goes to the next players iteration.
            playeratbat = homeTeamPlayers.get(scoreBoard.homePositionAtBat); //get score board position for the next player at bat
            setAtbatplayername(homeTeamPlayers.get(scoreBoard.homePositionAtBat).getName()); // sets up the player at bases name
            setTextColor(scoreBoard.homePositionAtBat);
            atBats = new AtBats();
            if (lastOutFlag){
                scoreBoard.top = true;
                removeAllBaseRunners();
                fillTeamNames(awayTeamPlayers);
                setAtbatplayername(homeTeamPlayers.get(scoreBoard.homePositionAtBat).getName()); // sets up the player at bases name
                setTextColor(scoreBoard.homePositionAtBat);
                scoreBoard.inningNumber+=1;
                bottomInning.setVisibility(ImageView.INVISIBLE);topInning.setVisibility(ImageView.VISIBLE);//changing the Arrow up or down
            }
        }
        updateInformation();
    }
    public void makeAlert(final TextView[] baseRunnersTextViews, final int position){
        final TextView baseHeIsOn = baseRunnersTextViews[position];
        final String oldplayer = baseHeIsOn.getText().toString();
        final Dialog dialog = new Dialog(this);
        dialog.setTitle("Were do you want "+baseHeIsOn.getText().toString()+" to go.");
        dialog.setContentView(R.layout.activity_ask_runner_stats);
        dialog.show();

        TextView textView = (TextView) dialog.findViewById(R.id.telltheuserweretherunnerwent);
        String l = "Were do you want "+baseHeIsOn.getText().toString()+" to go.";
        textView.setText(l);

        Button button2b = (Button) dialog.findViewById(R.id.BaseRunner_2b);
        Button button3b = (Button) dialog.findViewById(R.id.BaseRunner_3b);
        Button buttonhp = (Button) dialog.findViewById(R.id.BaseRunner_hp);
        Button buttoncs = (Button) dialog.findViewById(R.id.BaseRunner_cs);
        Button buttonsb = (Button) dialog.findViewById(R.id.BaseRunner_sb);
        Button buttonto = (Button) dialog.findViewById(R.id.BaseRunner_TO);

        button2b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Person on base ",baseHeIsOn.getText().toString());
                setSecondBasePlayer(oldplayer);
                dialog.cancel();
            }
        });
        button3b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setThirdBasePlayer(oldplayer);
                dialog.cancel();
            }
        });
        buttonhp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                baseHeIsOn.setText("");
                baseHeIsOn.setVisibility(View.INVISIBLE);
                if (scoreBoard.top) {
                    scoreBoard.awayScore += 1;
                } else {
                    scoreBoard.homeScore += 1;
                }
                dialog.cancel();
            }
        });
        buttoncs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                baseHeIsOn.setText("");
                baseHeIsOn.setVisibility(View.INVISIBLE);
                pitchCounter.hitout();
                dialog.cancel();
            }
        });
        buttonsb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position == 3) {
                    baseHeIsOn.setText("");
                    baseHeIsOn.setVisibility(View.INVISIBLE);
                    if (scoreBoard.top) {
                        scoreBoard.awayScore += 1;
                    } else {
                        scoreBoard.homeScore += 1;
                    }
                } else {
                    baseRunnersTextViews[position + 1].setText(oldplayer);
                    baseRunnersTextViews[position + 1].setVisibility(View.VISIBLE);
                    baseHeIsOn.setText("");
                    baseHeIsOn.setVisibility(View.INVISIBLE);
                }
                dialog.cancel();
            }
        });
        buttonto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                baseHeIsOn.setText("");
                baseHeIsOn.setVisibility(View.INVISIBLE);
                pitchCounter.hitout();
                dialog.cancel();
            }
        });
    }

    public void initBaseRunners() {
        baseRunnersTextViews[0] = (TextView) findViewById(R.id.FullGame_HomePlate);
        baseRunnersTextViews[1] = (TextView) findViewById(R.id.FullGame_FirstBase);
        baseRunnersTextViews[2] = (TextView) findViewById(R.id.FullGame_SecondBase);
        baseRunnersTextViews[3] = (TextView) findViewById(R.id.FullGame_ThirdBase);

        baseRunnersTextViews[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeAlert(baseRunnersTextViews,1);
            }
        });
        baseRunnersTextViews[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeAlert(baseRunnersTextViews,2);
            }
        });
        baseRunnersTextViews[3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeAlert(baseRunnersTextViews,3);
            }
        });
    }

    public void initTextViews(){
        playername[0] = (TextView) findViewById(R.id.awayplayername1);
        playername[1] = (TextView) findViewById(R.id.awayplayername2);
        playername[2] = (TextView) findViewById(R.id.awayplayername3);
        playername[3] = (TextView) findViewById(R.id.awayplayernumber4);
        playername[4] = (TextView) findViewById(R.id.awayplayernumber5);
        playername[5] = (TextView) findViewById(R.id.awayplayernumber6);
        playername[6] = (TextView) findViewById(R.id.awayplayernumber7);
        playername[7] = (TextView) findViewById(R.id.awayplayernumber8);
        playername[8] = (TextView) findViewById(R.id.awayplayernumber9);
        playername[9] = (TextView) findViewById(R.id.awayplayernumber10);
    }

    public void saveGameinformation() {
        game = new Game();
        game.setAwayTeamID(awayTeamID);
        game.setHomeTeamID(homeTeamID);
        game.generateName(awayTeamName,homeTeamName);
        game = new DataBaseHelper().saveGame(game);
    }

    public void setAtbatplayername(String playerName) {
        baseRunnersTextViews[0].setVisibility(View.VISIBLE);
        baseRunnersTextViews[0].setText(playerName);
    }

    public void setFirstBasePlayer(String playerName) {
        if(!(baseRunnersTextViews[1].getText().toString().matches("") ||
                baseRunnersTextViews[1].getText().toString().matches("Player"))){
            makeAlert(baseRunnersTextViews,1);}
        baseRunnersTextViews[1].setVisibility(View.VISIBLE);
        baseRunnersTextViews[1].setText(playerName);
    }
    public void setSecondBasePlayer(String playerName){
        if(!(baseRunnersTextViews[2].getText().toString().matches("") ||
                baseRunnersTextViews[2].getText().toString().matches("Player"))){
            makeAlert(baseRunnersTextViews,2);}
        if(!(baseRunnersTextViews[1].getText().toString().matches("")||
                baseRunnersTextViews[1].getText().toString().matches("Player"))){
            makeAlert(baseRunnersTextViews,1);}
        baseRunnersTextViews[2].setVisibility(View.VISIBLE);
        baseRunnersTextViews[2].setText(playerName);
    }
    public void setThirdBasePlayer(String playerName){
        if(!(baseRunnersTextViews[3].getText().toString().matches("") ||
                baseRunnersTextViews[3].getText().toString().matches("Player"))){
            makeAlert(baseRunnersTextViews,3);}
        if(!(baseRunnersTextViews[2].getText().toString().matches("") ||
                baseRunnersTextViews[2].getText().toString().matches("Player"))){
            makeAlert(baseRunnersTextViews,2);}
        if(!(baseRunnersTextViews[1].getText().toString().matches("")||
                baseRunnersTextViews[1].getText().toString().matches("Player"))){
            makeAlert(baseRunnersTextViews,1);}
        baseRunnersTextViews[3].setVisibility(View.VISIBLE);
        baseRunnersTextViews[3].setText(playerName);
    }
    public void removeFirstBaseRunner(){
        baseRunnersTextViews[1].setVisibility(View.INVISIBLE);
        baseRunnersTextViews[1].setText("");}
    public void removeSecondBaseRunner(){
        baseRunnersTextViews[2].setVisibility(View.INVISIBLE);
        baseRunnersTextViews[2].setText("");}
    public void removeThirdBaseRunner(){
        baseRunnersTextViews[3].setVisibility(View.INVISIBLE);
        baseRunnersTextViews[3].setText("");}
    public void removeAllBaseRunners(){for (TextView x: baseRunnersTextViews){x.setText("");x.setVisibility(View.GONE);}}

    public void fillTeamNames(ArrayList<Player> awayTeamPlayers) {
        for(int i=0;i<9;i++){playername[i].setText(awayTeamPlayers.get(i).getName());}

        if (awayTeamPlayers.size() == 10) {
            playername[9].setText(awayTeamPlayers.get(9).getName());
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
        flyOutButton = (Button) findViewById(R.id.FullGame_FlyOut);
        lineOutButton = (Button) findViewById(R.id.FullGame_LineOut);
        advanceRunnerButton = (Button) findViewById(R.id.FullGame_AdvanceRunner);
        hBPButton = (Button) findViewById(R.id.FullGame_HBP);
        fieldersChoiceButton = (Button) findViewById(R.id.FullGame_FC);
        errorButton = (Button) findViewById(R.id.FullGame_E);
    }

    public void updateInformation(){
        ((TextView)findViewById(R.id.fullGame_ScoreBoard_Ball)).setText(String.valueOf(pitchCounter.balls));
        ((TextView)findViewById(R.id.fullGame_ScoreBoard_Strikes)).setText(String.valueOf(pitchCounter.strikes));
        ((TextView)findViewById(R.id.fullGame_ScoreBoard_Outs)).setText(String.valueOf(pitchCounter.outs));
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
                    setFirstBasePlayer(playeratbat.getName());
                    saveInformation();

                    pitchCounter.calledBall();
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
                    lastOutFlag = pitchCounter.calledStrike();
                    saveInformation();
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
                updateInformation();
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
                updateInformation();
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

                lastOutFlag = pitchCounter.hitout();
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

                lastOutFlag = pitchCounter.hitout();

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

                lastOutFlag = pitchCounter.hitout();

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

                lastOutFlag = pitchCounter.hitout();

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

                lastOutFlag = pitchCounter.hitout();

                saveInformation();
            }
        });
    }

}
