package com.example.crosbylanham.baseballstatscollector;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class QuickBattingStatsGameActivity extends AppCompatActivity {

    TextView ballsTextView, strikesTextView;
    TextView abs, avg, hits, ks, hr;

    Button ball, strike,foulballbutton;
    Button single, doubles, triple, homeRun;
    Button flyOut, foulOut, groundOut, lineOut;
    Button advancedrunner, hbp, fc, e;
    Button saveInformationButton;

    AtBats atBats = new AtBats();
    Player playeratbat = new Player();
    Game game;

    Spinner playerspinner;
    boolean neverBeenSaved = true;
    DataBaseHelper dataBaseHelper = new DataBaseHelper(QuickBattingStatsGameActivity.this);
    PitchCounter pitchCounter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quick_batting_stats);

        pitchCounter = new PitchCounter();
        game = new Game();
        saveGame();
        initButton();
        initTextViews();
        initSpinners();
        allButtonActions();
    }

    public void initSpinners() {
        playerspinner = (Spinner) findViewById(R.id.quickBattingStats_playerSpinner);

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                QuickBattingStatsGameActivity.this,
                android.R.layout.simple_spinner_item,
                dataBaseHelper.getAllPlayersNames());
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        playerspinner.setAdapter(spinnerArrayAdapter);


    }

    public void initButton() {
        ball = (Button) findViewById(R.id.batting_ballbutton);
        strike = (Button) findViewById(R.id.batting_strikesbutton);
        foulballbutton = (Button) findViewById(R.id.batting_foulballbutton);

        single = (Button) findViewById(R.id.batting_singlebutton);
        doubles = (Button) findViewById(R.id.batting_doublebutton);
        triple = (Button) findViewById(R.id.batting_tripplebutton);
        homeRun = (Button) findViewById(R.id.batting_homerunbutton);

        foulOut = (Button) findViewById(R.id.batting_fouloutbutton);
        groundOut = (Button) findViewById(R.id.batting_groundoutbutton);
        lineOut = (Button) findViewById(R.id.batting_lineoutbutton);
        flyOut = (Button) findViewById(R.id.batting_flyoutbutton);

        advancedrunner = (Button) findViewById(R.id.advancerunnerbutton);
        hbp = (Button) findViewById(R.id.batting_hitByPitch);
        fc = (Button) findViewById(R.id.batting_fielderschoicebutton);
        e = (Button) findViewById(R.id.batting_errorbutton);

        saveInformationButton = (Button) findViewById(R.id.batting_saveInformation);
    }

    public void initTextViews() {
        ballsTextView = (TextView) findViewById(R.id.batting_balls);
        strikesTextView = (TextView) findViewById(R.id.batting_strikes);

        abs = (TextView) findViewById(R.id.batting_ABs);
        avg = (TextView) findViewById(R.id.batting_average);
        hits = (TextView) findViewById(R.id.batting_hits);
        ks = (TextView) findViewById(R.id.batting_ks);
        hr = (TextView) findViewById(R.id.batting_hr);
    }

    public void updateInformation() {
        //ballsTextView.setText(String.valueOf(pitchCounter.getBalls()));
        ballsTextView.setText("58");
        strikesTextView.setText(String.valueOf(pitchCounter.getStrikes()));

        /*#TODO should i put in more stats that the players may need
        * or should i not do that to them like that.*/
        abs.setText("0");
        avg.setText("0");
        hits.setText("");
        ks.setText("");
        hr.setText("");
    }

    public void setBallButtonAction() {
        ball.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pitchCounter.getBalls() == 3) {
                    atBats.setBalls(pitchCounter.getBalls() + 1);
                    atBats.setStrikes(pitchCounter.getStrikes());
                    atBats.setOutcome(AtBats.WALK);
                    atBats.setPlayerAtBatId(getPlayer().getPlayerID());
                    atBats.setPitches(pitchCounter.getTotalAtBatPitches() + 1);

                    saveInformation();
                } else {
                    pitchCounter.calledBall();
                }
                updateInformation();
            }
        });
    }

    public void setStrikeAction() {
        strike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pitchCounter.getStrikes() == 2) {
                    atBats.setBalls(pitchCounter.getBalls());
                    atBats.setStrikes(pitchCounter.getStrikes() + 1);
                    atBats.setPitches(pitchCounter.getTotalAtBatPitches() + 1);
                    atBats.setOutcome(AtBats.STRIKEOUT);
                    atBats.setPlayerAtBatId(getPlayer().getPlayerID());

                    saveInformation();
                } else {
                    pitchCounter.calledStrike();
                }
                updateInformation();
            }
        });
    }

    public void setFoulBallAction() {
        foulballbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pitchCounter.foulAction();
                updateInformation();
            }
        });
    }

    public void setSingleAction() {
        single.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                atBats.setBalls(pitchCounter.getBalls());
                atBats.setStrikes(pitchCounter.getStrikes() + 1);
                atBats.setPitches(pitchCounter.getTotalAtBatPitches() + 1);
                atBats.setOutcome(AtBats.SINGLE);
                atBats.setPlayerAtBatId(getPlayer().getPlayerID());

                saveInformation();
            }
        });
    }

    public void setDoublesAction() {
        doubles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                atBats.setBalls(pitchCounter.getBalls());
                atBats.setStrikes(pitchCounter.getStrikes() + 1);
                atBats.setPitches(pitchCounter.getTotalAtBatPitches() + 1);
                atBats.setOutcome(AtBats.DOUBLE);
                atBats.setPlayerAtBatId(getPlayer().getPlayerID());

                saveInformation();
            }
        });
    }

    public void setTripleAction() {
        triple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                atBats.setBalls(pitchCounter.getBalls());
                atBats.setStrikes(pitchCounter.getStrikes() + 1);
                atBats.setPitches(pitchCounter.getTotalAtBatPitches() + 1);
                atBats.setOutcome(AtBats.TRIPLE);
                atBats.setPlayerAtBatId(getPlayer().getPlayerID());

                saveInformation();
            }
        });
    }

    public void setHomeRunAction() {
        homeRun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                atBats.setBalls(pitchCounter.getBalls());
                atBats.setStrikes(pitchCounter.getStrikes() + 1);
                atBats.setPitches(pitchCounter.getTotalAtBatPitches() + 1);
                atBats.setOutcome(AtBats.HOMERUN);
                atBats.setPlayerAtBatId(getPlayer().getPlayerID());

                saveInformation();
            }
        });
    }

    public void setFoulOutAction() {
        foulOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                atBats.setBalls(pitchCounter.getBalls());
                atBats.setStrikes(pitchCounter.getStrikes() + 1);
                atBats.setPitches(pitchCounter.getTotalAtBatPitches() + 1);
                atBats.setOutcome(AtBats.FOUL_OUT);
                atBats.setPlayerAtBatId(getPlayer().getPlayerID());

                saveInformation();
            }
        });

    }

    public void setGroundOutAction() {
        groundOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                atBats.setBalls(pitchCounter.getBalls());
                atBats.setStrikes(pitchCounter.getStrikes() + 1);
                atBats.setPitches(pitchCounter.getTotalAtBatPitches() + 1);
                atBats.setOutcome(AtBats.GROUNDOUT);
                atBats.setPlayerAtBatId(getPlayer().getPlayerID());

                saveInformation();
            }
        });
    }

    public void setFlyOutAction() {
        flyOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                atBats.setBalls(pitchCounter.getBalls());
                atBats.setStrikes(pitchCounter.getStrikes() + 1);
                atBats.setPitches(pitchCounter.getTotalAtBatPitches() + 1);
                atBats.setOutcome(AtBats.FLYOUT);
                atBats.setPlayerAtBatId(getPlayer().getPlayerID());

                saveInformation();
            }
        });
    }

    public void setLineOutAction() {
        lineOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                atBats.setBalls(pitchCounter.getBalls());
                atBats.setStrikes(pitchCounter.getStrikes() + 1);
                atBats.setPitches(pitchCounter.getTotalAtBatPitches() + 1);
                atBats.setOutcome(AtBats.LINEOUT);
                atBats.setPlayerAtBatId(getPlayer().getPlayerID());

                saveInformation();
            }
        });
    }

    public void setAdvancedrunnerAction() {
        advancedrunner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                atBats.setBalls(pitchCounter.getBalls());
                atBats.setStrikes(pitchCounter.getStrikes() + 1);
                atBats.setPitches(pitchCounter.getTotalAtBatPitches() + 1);
                atBats.setOutcome(AtBats.ADVANCERUNNER);
                atBats.setPlayerAtBatId(getPlayer().getPlayerID());

                saveInformation();
            }
        });
    }

    public void setHbpAction() {
        hbp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                atBats.setBalls(pitchCounter.getBalls());
                atBats.setStrikes(pitchCounter.getStrikes() + 1);
                atBats.setPitches(pitchCounter.getTotalAtBatPitches() + 1);
                atBats.setOutcome(AtBats.HIT_BY_PITCH);
                atBats.setPlayerAtBatId(getPlayer().getPlayerID());

                saveInformation();
            }
        });
    }

    public void setEAction() {
        e.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                atBats.setBalls(pitchCounter.getBalls());
                atBats.setStrikes(pitchCounter.getStrikes() + 1);
                atBats.setPitches(pitchCounter.getTotalAtBatPitches() + 1);
                atBats.setOutcome(AtBats.ERROR);
                atBats.setPlayerAtBatId(getPlayer().getPlayerID());

                saveInformation();
            }
        });
    }

    public void setFcAction() {
        fc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                atBats.setBalls(pitchCounter.getBalls());
                atBats.setStrikes(pitchCounter.getStrikes() + 1);
                atBats.setPitches(pitchCounter.getTotalAtBatPitches() + 1);
                atBats.setOutcome(AtBats.FIELDERS_CHOICE);
                atBats.setPlayerAtBatId(getPlayer().getPlayerID());

                saveInformation();
            }
        });
    }

    public void setSaveInformationButtonAction() {
        saveInformationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QuickBattingStatsGameActivity.this, HomePage.class);
                startActivity(intent);
            }
        });
    }

    public void allButtonActions() {
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
        setEAction();
        setFcAction();
        setSaveInformationButtonAction();
    }

    public void saveInformation() {
        if (neverBeenSaved) {
            saveGame();
            neverBeenSaved = false;
        }
        atBats.setGameID(game.getGameID());
        dataBaseHelper.saveAtBat(atBats);
        pitchCounter.reset();
        updateInformation();
        initSpinners();
        ((EditText) findViewById(R.id.quickBattingStats_PlayerNameTextField)).setText("");
        Toast.makeText(QuickBattingStatsGameActivity.this, "You information has been saved!", Toast.LENGTH_SHORT);
    }

    public Player getPlayer() {
        if (((EditText) findViewById(R.id.quickBattingStats_PlayerNameTextField))
                .getText().toString().matches("")) {
            return dataBaseHelper.getPlayer(playerspinner.getSelectedItem().toString());
        } else {
            playeratbat = new Player();
            playeratbat.setName(
                    ((EditText) findViewById(R.id.quickBattingStats_PlayerNameTextField))
                            .getText().toString());
            return dataBaseHelper.savePlayer(playeratbat);
        }
    }

    public void saveGame() {
        game.setName(new Datefunctions().getCurrentTimeAndDate());
        game = dataBaseHelper.saveGame(game);
    }
}
