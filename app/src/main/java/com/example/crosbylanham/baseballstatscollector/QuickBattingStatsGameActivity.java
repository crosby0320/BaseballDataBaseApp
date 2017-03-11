package com.example.crosbylanham.baseballstatscollector;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

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

    ArrayList<Player> list;

    Spinner playerspinner;
    boolean neverBeenSaved = true;
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
        initPlayerSelectedAction();
        allButtonActions();
    }

    public void initSpinners() {
        playerspinner = (Spinner) findViewById(R.id.quickBattingStats_playerSpinner);

        list = new ArrayList<>();
        final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(
                QuickBattingStatsGameActivity.this,
                android.R.layout.simple_spinner_item);

        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        playerspinner.setAdapter(spinnerArrayAdapter);

        final DatabaseReference dr = FirebaseDatabase.getInstance().getReference(DataBaseHelper.PLAYERINFOTABLEBNAME);
        dr.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot x : dataSnapshot.getChildren()) {
                    spinnerArrayAdapter.add(x.getValue(Player.class).getName());
                    list.add(x.getValue(Player.class));
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
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
        ballsTextView.setText(String.valueOf(pitchCounter.getBalls()));
        strikesTextView.setText(String.valueOf(pitchCounter.getStrikes()));
    }

    public void setBallButtonAction() {
        ball.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pitchCounter.getBalls() == 3) {
                    atBats.setBalls(pitchCounter.getBalls() + 1);
                    atBats.setStrikes(pitchCounter.getStrikes());
                    atBats.setOutcome(AtBatInformation.WALK);
                    atBats.setPlayerAtBatId(playeratbat.getPlayerID());
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
                    atBats.setOutcome(AtBatInformation.STRIKEOUT);
                    atBats.setPlayerAtBatId(playeratbat.getPlayerID());

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
                atBats.setOutcome(AtBatInformation.SINGLE);
                atBats.setPlayerAtBatId(playeratbat.getPlayerID());

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
                atBats.setOutcome(AtBatInformation.DOUBLE);
                atBats.setPlayerAtBatId(playeratbat.getPlayerID());

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
                atBats.setOutcome(AtBatInformation.TRIPLE);
                atBats.setPlayerAtBatId(playeratbat.getPlayerID());

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
                atBats.setOutcome(AtBatInformation.HOMERUN);
                atBats.setPlayerAtBatId(playeratbat.getPlayerID());

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
                atBats.setOutcome(AtBatInformation.FOUL_OUT);
                atBats.setPlayerAtBatId(playeratbat.getPlayerID());

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
                atBats.setOutcome(AtBatInformation.GROUNDOUT);
                atBats.setPlayerAtBatId(playeratbat.getPlayerID());

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
                atBats.setOutcome(AtBatInformation.FLYOUT);
                atBats.setPlayerAtBatId(playeratbat.getPlayerID());

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
                atBats.setOutcome(AtBatInformation.LINEOUT);
                atBats.setPlayerAtBatId(playeratbat.getPlayerID());

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
                atBats.setOutcome(AtBatInformation.ADVANCERUNNER);
                atBats.setPlayerAtBatId(playeratbat.getPlayerID());

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
                atBats.setOutcome(AtBatInformation.HIT_BY_PITCH);
                atBats.setPlayerAtBatId(playeratbat.getPlayerID());

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
                atBats.setOutcome(AtBatInformation.ERROR);
                atBats.setPlayerAtBatId(playeratbat.getPlayerID());

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
                atBats.setOutcome(AtBatInformation.FIELDERS_CHOICE);
                atBats.setPlayerAtBatId(playeratbat.getPlayerID());

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
        getPlayerThatIsSelected();
        atBats.setGameID(game.getGameID());
        new DataBaseHelper().saveAtBat(atBats);
        pitchCounter.reset();
        updateInformation();
        ((EditText) findViewById(R.id.quickBattingStats_PlayerNameTextField)).setText("");
        Toast.makeText(QuickBattingStatsGameActivity.this, "You information has been saved for player ! " + playeratbat.getName(), Toast.LENGTH_SHORT).show();

        //save information
        final ArrayList<AtBats> allAtBats = new ArrayList<>();
        DatabaseReference dr = FirebaseDatabase.getInstance().getReference(DataBaseHelper.ATBATTableName);
        Query query = dr.orderByChild("playerAtBatId").equalTo(playeratbat.getPlayerID());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> it = dataSnapshot.getChildren();
                for (DataSnapshot x : it) {
                    allAtBats.add(x.getValue(AtBats.class));
                }
                UpdatePlayerStats(allAtBats);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {}});
        if(newPlayer){initSpinners();newPlayer = false;}
    }
    boolean newPlayer = false;
    public void initPlayerSelectedAction(){
        playerspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                playeratbat = list.get(position);

                final ArrayList<AtBats> allAtBats = new ArrayList<>();
                DatabaseReference dr = FirebaseDatabase.getInstance().getReference(DataBaseHelper.ATBATTableName);
                Query query = dr.orderByChild("playerAtBatId").equalTo(playeratbat.getPlayerID());
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Iterable<DataSnapshot> it = dataSnapshot.getChildren();
                        for (DataSnapshot x : it) {
                            allAtBats.add(x.getValue(AtBats.class));
                        }
                        UpdatePlayerStats(allAtBats);
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {}});
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                playeratbat = list.get(0);
            }
        });
    }
    public void getPlayerThatIsSelected(){
        if(!(((EditText) findViewById(R.id.quickBattingStats_PlayerNameTextField))
                .getText().toString().matches(""))) {
            newPlayer = true;
            playeratbat = new DataBaseHelper().savePlayer(new Player(((EditText) findViewById(R.id.quickBattingStats_PlayerNameTextField)).getText().toString()));
        }
    }
    public void UpdatePlayerStats(ArrayList<AtBats> allAtBats){
        BatStatsCalculator bs = new BatStatsCalculator(allAtBats);
        bs.calcStats();
        abs.setText(String.valueOf(bs.totalab));
        avg.setText(String.format("%.3f",bs.totalavg));
        hits.setText(String.valueOf(bs.totalh));
        ks.setText(String.valueOf(bs.totalks));
        hr.setText(String.valueOf(bs.totalhr));
    }
    public void saveGame() {
        if(game == null) {
            game.setName(new Datefunctions().getCurrentTimeAndDate());
            game = new DataBaseHelper().saveGame(game);
        }
    }
}
