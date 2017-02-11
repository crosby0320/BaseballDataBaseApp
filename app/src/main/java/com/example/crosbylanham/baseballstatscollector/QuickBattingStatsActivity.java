package com.example.crosbylanham.baseballstatscollector;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.LinkedList;

public class QuickBattingStatsActivity extends AppCompatActivity implements View.OnClickListener {
    Spinner playerSpinner, gameSpinner;

    TextView ballsTextView, strikesTextView;
    TextView abs, avg, hits, ks, hr;

    Button ball, strike;
    Button single, doubles, triple, homeRun;
    Button flyOut, foulOut, groundOut, lineOut;
    Button advancedrunner, hbp;
    Button backHomeButton;

    AtBats atBats;
    Player playeratbat;
    Game game;

    PitchCounter pitchCounter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quick_batting_stats2);

        pitchCounter = new PitchCounter();

        initButton();
        initSpinner();
        initTextViews();
        buttonActions();
    }

    public void buttonActions() {
        ball.setOnClickListener(this);
        strike.setOnClickListener(this);

        single.setOnClickListener(this);
        doubles.setOnClickListener(this);
        triple.setOnClickListener(this);
        homeRun.setOnClickListener(this);

        flyOut.setOnClickListener(this);
        foulOut.setOnClickListener(this);
        groundOut.setOnClickListener(this);
        lineOut.setOnClickListener(this);

        advancedrunner.setOnClickListener(this);
        hbp.setOnClickListener(this);
    }

    public void initButton() {
        ball = (Button) findViewById(R.id.batting_ballbutton);
        strike = (Button) findViewById(R.id.batting_strikesbutton);
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

        backHomeButton = (Button) findViewById(R.id.batting_backhomebutton);
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

    public void initSpinner() {
        playerSpinner = (Spinner) findViewById(R.id.playersSpinner);
        LinkedList<String> list = new LinkedList<>(
                new DataBaseHelper(QuickBattingStatsActivity.this).getAllPlayersNames()
        );
        list.addFirst("no player selected");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,
                list);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        playerSpinner.setAdapter(adapter);

        playerSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if (position == 0) {

                } else {
                    playeratbat = new DataBaseHelper(QuickBattingStatsActivity.this).getPlayer(
                            parentView.getSelectedItem().toString()
                    );

                    ArrayList<AtBats> listOfABs = new DataBaseHelper(QuickBattingStatsActivity.this)
                            .getAllABsForPlayer(playeratbat.getPlayerID());
                    double avgCount;
                    int abcount = 0, hitstemp = 0, kscount = 0, hrcount = 0;
                    for (AtBats x : listOfABs) {
                        if (x.getOutcome() != AtBats.WALK && x.getOutcome() != AtBats.HIT_BY_PITCH) {
                            abcount++;
                        } else if (x.getOutcome() == 0 || x.getOutcome() == 1 ||
                                x.getOutcome() == 2 || x.getOutcome() == 3) {
                            hitstemp++;
                        } else if (x.getOutcome() == 5) {
                            kscount++;
                        }
                        if (x.getOutcome() == 3) {
                            hrcount++;
                        }
                    }
                    avg.setText(String.valueOf(hitstemp / ((double) (abcount))));
                    hits.setText(String.valueOf(hitstemp));
                    abs.setText(String.valueOf(abcount));
                    ks.setText(String.valueOf(kscount));
                    hr.setText(String.valueOf(hrcount));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                //nothing
            }
        });
        final DataBaseHelper dataBaseHelper = new DataBaseHelper(QuickBattingStatsActivity.this);
        game = new Game();
        /*#TODO we need to set the game id so that people know what game they got the stats from*/
        game.setGameID(999);
        final ArrayList<Game> gamelist = dataBaseHelper.getAllGames();
        LinkedList<String> gamesNamesList = new LinkedList<>();
        for (Game g:gamelist){
            gamesNamesList.add(
                    dataBaseHelper.getTeam(g.getAwayTeamID()).getName()
                    +" Vs. "+
                    dataBaseHelper.getTeam(g.getHomeTeamID()).getName() + " "+game.getGameID());
        }
        gameSpinner = (Spinner) findViewById(R.id.gameSpinner);
        gamesNamesList.addFirst("New Game");

        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,
                list);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        gameSpinner.setAdapter(adapter);

        gameSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0){
                    game = new Game();
                    game.setGameID(0);
                }else{
                    game = dataBaseHelper.getGame(
                            gamelist.get(position).getGameID()
                    );
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.batting_ballbutton) {
            if (pitchCounter.getBalls() == 3) {
                atBats = new AtBats();
                atBats.setOutcome(AtBats.WALK);
                atBats.setPlayerAtBatId(playeratbat.getPlayerID());
                atBats.setBalls(4);
                atBats.setStrikes(pitchCounter.getStrikes());
                atBats.setGameID(game.getGameID());

                new DataBaseHelper(QuickBattingStatsActivity.this).saveAtBat(atBats);
                Toast.makeText(QuickBattingStatsActivity.this, "Stats Has Been Saved", Toast.LENGTH_SHORT).show();
                pitchCounter.reset();
            } else {
                pitchCounter.hitball();
            }
            updateInformation();
        } else if (v.getId() == R.id.batting_strikesbutton) {
            if (pitchCounter.getStrikes() == 2) {
                atBats = new AtBats();
                atBats.setOutcome(AtBats.STRIKEOUT);
                atBats.setPlayerAtBatId(playeratbat.getPlayerID());
                atBats.setBalls(pitchCounter.getBalls());
                atBats.setStrikes(3);
                atBats.setGameID(game.getGameID());

                new DataBaseHelper(QuickBattingStatsActivity.this).saveAtBat(atBats);
                Toast.makeText(QuickBattingStatsActivity.this, "Stats Has Been Saved", Toast.LENGTH_SHORT).show();
                pitchCounter.reset();
            } else {
                pitchCounter.hitstrike();
            }
            updateInformation();
        } else if(v.getId() == R.id.batting_foulballbutton){
            pitchCounter.hitfoul();
            updateInformation();
        } else {
            switch (v.getId()) {
                case R.id.batting_singlebutton:
                    atBats = new AtBats();
                    atBats.setOutcome(AtBats.SINGLE);
                    atBats.setPlayerAtBatId(playeratbat.getPlayerID());
                    atBats.setPlayerAtBatId(playeratbat.getPlayerID());
                    atBats.setBalls(pitchCounter.getBalls());
                    atBats.setStrikes(pitchCounter.getStrikes()+1);
                    atBats.setGameID(game.getGameID());
                    saveInformation();
                    break;
                case R.id.batting_doublebutton:
                    atBats = new AtBats();
                    atBats.setOutcome(AtBats.DOUBLE);
                    atBats.setPlayerAtBatId(playeratbat.getPlayerID());
                    atBats.setPlayerAtBatId(playeratbat.getPlayerID());
                    atBats.setBalls(pitchCounter.getBalls());
                    atBats.setStrikes(pitchCounter.getStrikes()+1);
                    atBats.setGameID(game.getGameID());
                    saveInformation();
                    break;
                case R.id.batting_tripplebutton:
                    atBats = new AtBats();
                    atBats.setOutcome(AtBats.TRIPLE);
                    atBats.setPlayerAtBatId(playeratbat.getPlayerID());
                    atBats.setPlayerAtBatId(playeratbat.getPlayerID());
                    atBats.setBalls(pitchCounter.getBalls());
                    atBats.setStrikes(pitchCounter.getStrikes()+1);
                    atBats.setGameID(game.getGameID());
                    saveInformation();
                    break;
                case R.id.batting_homerunbutton:
                    atBats = new AtBats();
                    atBats.setOutcome(AtBats.HOMERUN);
                    atBats.setPlayerAtBatId(playeratbat.getPlayerID());
                    atBats.setPlayerAtBatId(playeratbat.getPlayerID());
                    atBats.setBalls(pitchCounter.getBalls());
                    atBats.setStrikes(pitchCounter.getStrikes()+1);
                    atBats.setGameID(game.getGameID());
                    saveInformation();
                    break;
                case R.id.batting_fouloutbutton:
                    atBats = new AtBats();
                    atBats.setOutcome(AtBats.FOUL_OUT);
                    atBats.setPlayerAtBatId(playeratbat.getPlayerID());
                    atBats.setPlayerAtBatId(playeratbat.getPlayerID());
                    atBats.setBalls(pitchCounter.getBalls());
                    atBats.setStrikes(pitchCounter.getStrikes()+1);
                    atBats.setGameID(game.getGameID());
                    saveInformation();
                    break;
                case R.id.batting_groundoutbutton:
                    atBats = new AtBats();
                    atBats.setOutcome(AtBats.GROUNDOUT);
                    atBats.setPlayerAtBatId(playeratbat.getPlayerID());
                    atBats.setPlayerAtBatId(playeratbat.getPlayerID());
                    atBats.setBalls(pitchCounter.getBalls());
                    atBats.setStrikes(pitchCounter.getStrikes()+1);
                    atBats.setGameID(game.getGameID());
                    saveInformation();
                    break;
                case R.id.batting_flyoutbutton:
                    atBats = new AtBats();
                    atBats.setOutcome(AtBats.FLYOUT);
                    atBats.setPlayerAtBatId(playeratbat.getPlayerID());
                    atBats.setPlayerAtBatId(playeratbat.getPlayerID());
                    atBats.setBalls(pitchCounter.getBalls());
                    atBats.setStrikes(pitchCounter.getStrikes()+1);
                    atBats.setGameID(game.getGameID());
                    saveInformation();
                    break;
                case R.id.batting_lineoutbutton:
                    atBats = new AtBats();
                    atBats.setOutcome(AtBats.LINEOUT);
                    atBats.setPlayerAtBatId(playeratbat.getPlayerID());
                    atBats.setPlayerAtBatId(playeratbat.getPlayerID());
                    atBats.setBalls(pitchCounter.getBalls());
                    atBats.setStrikes(pitchCounter.getStrikes()+1);
                    atBats.setGameID(game.getGameID());
                    saveInformation();
                    break;
                case R.id.advancerunnerbutton:
                    atBats = new AtBats();
                    atBats.setOutcome(AtBats.HOMERUN);
                    atBats.setPlayerAtBatId(playeratbat.getPlayerID());
                    atBats.setPlayerAtBatId(playeratbat.getPlayerID());
                    atBats.setBalls(pitchCounter.getBalls());
                    atBats.setStrikes(pitchCounter.getStrikes()+1);
                    atBats.setGameID(game.getGameID());
                    saveInformation();
                    break;
                case R.id.batting_hitByPitch:
                    atBats = new AtBats();
                    atBats.setOutcome(AtBats.HIT_BY_PITCH);
                    atBats.setPlayerAtBatId(playeratbat.getPlayerID());
                    atBats.setPlayerAtBatId(playeratbat.getPlayerID());
                    atBats.setBalls(pitchCounter.getBalls());
                    atBats.setStrikes(pitchCounter.getStrikes()+1);
                    atBats.setGameID(game.getGameID());
                    saveInformation();
                    break;
                case R.id.batting_errorbutton:
                    atBats = new AtBats();
                    atBats.setOutcome(AtBats.ERROR);
                    atBats.setPlayerAtBatId(playeratbat.getPlayerID());
                    atBats.setPlayerAtBatId(playeratbat.getPlayerID());
                    atBats.setBalls(pitchCounter.getBalls());
                    atBats.setStrikes(pitchCounter.getStrikes()+1);
                    atBats.setGameID(game.getGameID());
                    saveInformation();
                    break;
                case R.id.batting_fielderschoicebutton:
                    atBats = new AtBats();
                    atBats.setOutcome(AtBats.FIELDERS_CHOICE);
                    atBats.setPlayerAtBatId(playeratbat.getPlayerID());
                    atBats.setPlayerAtBatId(playeratbat.getPlayerID());
                    atBats.setBalls(pitchCounter.getBalls());
                    atBats.setStrikes(pitchCounter.getStrikes()+1);
                    atBats.setGameID(game.getGameID());
                    saveInformation();
                    break;
                case R.id.batting_backhomebutton:
                    Intent intent = new Intent(QuickBattingStatsActivity.this, HomePage.class);
                    startActivity(intent);
                    break;
                default:

                    break;
            }


        }
    }
    public void saveInformation(){
        new DataBaseHelper(QuickBattingStatsActivity.this).saveAtBat(atBats);
        Toast.makeText(QuickBattingStatsActivity.this, "Stats Has Been Saved", Toast.LENGTH_SHORT).show();
        pitchCounter.reset();
        updateInformation();
    }
}
