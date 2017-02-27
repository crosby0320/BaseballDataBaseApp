package com.example.crosbylanham.baseballstatscollector;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class QuickPitchingStatsGameActivity extends AppCompatActivity {
    Button savePitcherStatsButton;
    Button foul;
    Button swingmiss;
    Button ball;
    Button lookingButton;
    Button groundBall;
    Button groundout;
    Button linerHit;
    Button linerOut;
    Button flyOut;
    Button gapperhit;
    Button homeRunButton;
    Button hitByPitchButton;
    Button runs;
    PitchingStats pitchingstats;
    PitchCounter pitchCounter;
    TextView totalPitches;
    TextView totalStrikes;
    TextView totalBalls;
    TextView totalKs;
    TextView inningballs;
    TextView inningStrikes;
    TextView inningOuts;
    TextView inningKs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pitcher_game_stats);

        pitchingstats = new PitchingStats();
        pitchCounter = new PitchCounter();

        initButtons();
        initTotalTextViews();
        initPitchCounter();
        updateInningStatsCounter();
        updateTotalGameStatsCounter();
        //----------------------actionbuttons----------------------------------
        setActions();
    }
    public void setActions(){
        setsaveAction();
        setSwingmissAction();
        setFoulAction();
        setBallAction();
        setLookingButton();
        setGroundBallAction();
        setLinerHitAction();
        setGapperhitAction();
        setHomeRunButtonAction();
        setGroundoutButtonAction();
        setLinerOutButtonAction();
        setFlyOutButtonAction();
        setHitByPitchButtonAction();
        setRuns();
    }

    //--------------------------action buttons---------------------------------
    public void setsaveAction() {
        savePitcherStatsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QuickPitchingStatsGameActivity.this, LoadPitcherinformation.class);

                intent.putExtra("Stats", pitchingstats);

                startActivity(intent);
            }
        });
    }
    public void setSwingmissAction() {
        swingmiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pitchingstats.swingAndAMiss();
                if (pitchCounter.getStrikes() == 2) {
                    int x = pitchingstats.getStrikouts();
                    pitchingstats.outsPitched++;
                    pitchingstats.setStrikouts(++x);
                }
                pitchCounter.calledStrike();
                updateInningStatsCounter();
                updateTotalGameStatsCounter();
            }
        });
    }
    public void setFoulAction() {
        foul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pitchingstats.foul();
                pitchCounter.foulAction();
                updateInningStatsCounter();
                updateTotalGameStatsCounter();
            }
        });
    }
    public void setBallAction(){
        ball.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pitchingstats.ball();
                if(pitchCounter.getBalls() == 3){
                    int x = pitchingstats.getWalks();
                    pitchingstats.setWalks(++x);
                }
                pitchCounter.calledBall();
                updateInningStatsCounter();
                updateTotalGameStatsCounter();
            }
        });
    }
    public void setLookingButton(){
        lookingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pitchingstats.looking();
                if(pitchCounter.getStrikes() == 2){
                    pitchingstats.hitStrikeout();
                    pitchingstats.outsPitched++;
                }
                pitchCounter.calledStrike();
                updateInningStatsCounter();
                updateTotalGameStatsCounter();
            }
        });
    }
    //----------------------hit button action------------------------------------
    public void setGroundBallAction(){
        groundBall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pitchingstats.grounderhit();
                pitchCounter.hit();
                updateInningStatsCounter();
                updateTotalGameStatsCounter();
            }
        });
    }
    public void setLinerHitAction(){
        linerHit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pitchingstats.linerhit();
                pitchCounter.hit();
                updateInningStatsCounter();
                updateTotalGameStatsCounter();
            }
        });
    }
    public void setGapperhitAction(){
        gapperhit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pitchingstats.gapper();
                pitchCounter.hit();
                updateInningStatsCounter();
                updateTotalGameStatsCounter();
            }
        });
    }
    public void setHomeRunButtonAction(){
        homeRunButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pitchingstats.homerun();
                pitchCounter.hit();
                updateInningStatsCounter();
                updateTotalGameStatsCounter();
            }
        });
    }
    //----------------------out button action------------------------------------
    public void setGroundoutButtonAction(){
        groundout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pitchingstats.grounderout();
                pitchCounter.hitout();
                pitchingstats.outsPitched++;
                updateInningStatsCounter();
                updateTotalGameStatsCounter();
            }
        });
    }
    public void setLinerOutButtonAction(){
        linerOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pitchingstats.linerout();
                pitchCounter.hitout();
                pitchingstats.outsPitched++;
                updateInningStatsCounter();
                updateTotalGameStatsCounter();
            }
        });
    }
    public void setFlyOutButtonAction(){
        flyOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pitchingstats.flyout();
                pitchCounter.hitout();
                pitchingstats.outsPitched++;
                updateInningStatsCounter();
                updateTotalGameStatsCounter();
            }
        });
    }
    //---------------------missalanious-----------------------------------------
    public void setHitByPitchButtonAction(){
        hitByPitchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pitchingstats.hitByPitch();
                pitchCounter.hit();
                updateInningStatsCounter();
                updateTotalGameStatsCounter();
            }
        });
    }
    public void setRuns(){
        runs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pitchingstats.hitruns();
            }
        });
    }
    //---------------------inti buttons and update views----------------------

    public void initButtons() {
        savePitcherStatsButton = (Button) findViewById(R.id.savePitchersStats);
        foul = (Button) findViewById(R.id.foulBallButton);
        swingmiss = (Button) findViewById(R.id.swingAndMissButton);
        ball = (Button) findViewById(R.id.ballButton);
        lookingButton = (Button) findViewById(R.id.looking);
        //-------------------------hits---------------------------------------
        groundBall = (Button) findViewById(R.id.hitGroundBallButton);
        linerHit = (Button) findViewById(R.id.hitLinerButton);
        gapperhit = (Button) findViewById(R.id.hitGroundBallButton);
        homeRunButton = (Button) findViewById(R.id.homeRunButton);
        //-------------------------outs---------------------------------------
        groundout = (Button) findViewById(R.id.outGrounder);
        linerOut = (Button) findViewById(R.id.outLiner);
        flyOut = (Button) findViewById(R.id.outGapperButton);
        //--------------------------------------------------------------------
        hitByPitchButton = (Button) findViewById(R.id.hitByPitch);
        runs = (Button) findViewById(R.id.PitchingStatsRuns);
    }

    public void initTotalTextViews() {
        totalBalls = (TextView) findViewById(R.id.totalBallsTextView);
        totalPitches = (TextView) findViewById(R.id.totalPitches);
        totalStrikes = (TextView) findViewById(R.id.totalStrikesView);
        totalKs = (TextView) findViewById(R.id.totalKsTextView);
    }

    public void updateTotalGameStatsCounter() {
        totalBalls.setText(String.valueOf(pitchingstats.getBalls()));
        totalPitches.setText(String.valueOf(pitchingstats.getPitchs()));
        totalStrikes.setText(String.valueOf(pitchingstats.getStrikes()));
        totalKs.setText(String.valueOf(pitchingstats.getStrikouts()));
    }

    public void initPitchCounter() {
        inningStrikes = (TextView) findViewById(R.id.inningStrikes);
        inningballs = (TextView) findViewById(R.id.inningballs);
        inningOuts = (TextView) findViewById(R.id.inningouts);
        inningKs = (TextView) findViewById(R.id.inningKs);
    }

    public void updateInningStatsCounter() {
        inningballs.setText(String.valueOf(pitchCounter.getBalls()));
        inningStrikes.setText(String.valueOf(pitchCounter.getStrikes()));
        inningKs.setText(String.valueOf(pitchCounter.getKs()));
        inningOuts.setText(String.valueOf(pitchCounter.getOuts()));
    }

}
