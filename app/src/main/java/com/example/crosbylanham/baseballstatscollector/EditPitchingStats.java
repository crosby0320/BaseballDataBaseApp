package com.example.crosbylanham.baseballstatscollector;

import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class EditPitchingStats extends AppCompatActivity {
    Button makeChanges,remove;
    Spinner playerSpinner, pitchingStatsSpinner;
    ArrayList<Player> playerArray;
    TextView balls,strikes,earnedRuns,Flyouts,foulballs,gameID,gapper,groundhits,groundouts,hits;
    EditText editballs,editStrikes,editEarnedRuns,editFlyouts,editFoulBalls,editGameID,editGapper,
    editGroundHits,editGroundOuts,editHits;
    ArrayAdapter<String> pitchingStatsArrayAdapter;
    ArrayList<PitchingStats> pitchingStatsArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_pitching_stats);

        initButtons();
        initTextView();
        initSpinner();
        pitchingStatsArrayList = new ArrayList<>();
        playerArray = new ArrayList<>();

        final ArrayAdapter adapter = new ArrayAdapter(this,
                android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        playerSpinner.setAdapter(adapter);

        pitchingStatsArrayAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item);

        pitchingStatsArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        pitchingStatsSpinner.setAdapter(pitchingStatsArrayAdapter);

        FirebaseDatabase.getInstance().getReference(DataBaseHelper.PLAYERINFOTABLEBNAME)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        doSomthing(dataSnapshot);
                        onSelectionAction();
                    }

                    private void doSomthing(DataSnapshot dataSnapshot) {
                        adapter.clear();
                        playerArray.clear();
                        for (DataSnapshot x:dataSnapshot.getChildren()){
                            Player player = x.getValue(Player.class);
                            adapter.add(player.getName());
                            playerArray.add(player);
                        }
                    }
                    @Override public void onCancelled(DatabaseError databaseError) {}});
    }

    private void initSpinner() {
        playerSpinner = (Spinner)findViewById(R.id.EditPitchingStats_playerSpinner);
        pitchingStatsSpinner = (Spinner) findViewById(R.id.EditPitchingStats_PitchingStatsSpinner);
    }

    private void onSelectionAction() {
        playerSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Player playerSelected = playerArray.get(position);
                FirebaseDatabase.getInstance().getReference(DataBaseHelper.PITCHINGSTATSTABLENAME)
                        .orderByChild("playerID").equalTo(playerSelected.getPlayerID())
                        .addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                pitchingStatsArrayAdapter.clear();

                                for(DataSnapshot x:dataSnapshot.getChildren()){
                                    PitchingStats p = x.getValue(PitchingStats.class);
                                    pitchingStatsArrayAdapter.add(p.pitchingStatsID);
                                    pitchingStatsArrayList.add(p);
                                }
                                onSelected();

                            }
                            @Override public void onCancelled(DatabaseError databaseError) {}});
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void onSelected() {
        pitchingStatsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            if (pitchingStatsArrayList.size()>0){
                fillInformation(pitchingStatsArrayList.get(position));
                setUpButtons(pitchingStatsArrayList.get(position));
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    });
    }

    private void setUpButtons(final PitchingStats p) {
        makeChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                readInInformation(p);

                FirebaseDatabase.getInstance().getReference(DataBaseHelper.PITCHINGSTATSTABLENAME)
                        .child(p.getPitchingStatsID()).setValue(p);
            }
        });
        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase.getInstance().getReference(DataBaseHelper.PITCHINGSTATSTABLENAME)
                        .child(p.getPitchingStatsID()).setValue(null);
            }
        });
    }

    private void readInInformation(PitchingStats p) {
        if(!new DataBaseHelper().isEmpty(editballs)){p.setBalls(Integer.parseInt(editballs.getText().toString()));}
        if(!new DataBaseHelper().isEmpty(editEarnedRuns)){p.setRuns(Integer.parseInt(editEarnedRuns.getText().toString()));}
        if(!new DataBaseHelper().isEmpty(editFlyouts)){p.setFlyOut(Integer.parseInt(editFlyouts.getText().toString()));}
        if(!new DataBaseHelper().isEmpty(editGameID)){p.setGameID(editGameID.getText().toString());}
        if(!new DataBaseHelper().isEmpty(editGapper)){p.setGapper(Integer.parseInt(editGapper.getText().toString()));}
        if(!new DataBaseHelper().isEmpty(editGroundHits)){p.setGroundHits(Integer.parseInt(editGroundHits.getText().toString()));}
        if(!new DataBaseHelper().isEmpty(editGroundOuts)){p.setGroundOuts(Integer.parseInt(editGroundOuts.getText().toString()));}
        if(!new DataBaseHelper().isEmpty(editHits)){p.setHits(Integer.parseInt(editHits.getText().toString()));}
    }

    private void fillInformation(PitchingStats pitchStats) {
        balls.setText(String.valueOf(pitchStats.getBalls()));
        earnedRuns.setText(String.valueOf(pitchStats.getEarnedRuns()));
        Flyouts.setText(String.valueOf(pitchStats.getFlyOut()));
        foulballs.setText(String.valueOf(pitchStats.getFoulBalls()));
        gameID.setText(String.valueOf(pitchStats.getGameID()));
        gapper.setText(String.valueOf(pitchStats.getGapper()));
        groundhits.setText(String.valueOf(pitchStats.getGroundHits()));
        groundouts.setText(String.valueOf(pitchStats.getGroundOuts()));
        hits.setText(String.valueOf(pitchStats.getHits()));
    }

    private void initTextView() {
        balls = (TextView) findViewById(R.id.EditPitchingStats_BallsTextView);
        earnedRuns = (TextView) findViewById(R.id.EditPitchingStats_EarnedRunnsTextView);
        Flyouts = (TextView) findViewById(R.id.EditPitchingStats_FlyoutsTextView);
        foulballs = (TextView) findViewById(R.id.EditPitchingStats_foulBallTextView);
        gameID = (TextView) findViewById(R.id.EditPitchingStats_gameIDTextView);
        gapper = (TextView) findViewById(R.id.EditPitchingStats_GapperTextView);
        groundhits = (TextView) findViewById(R.id.EditPitchingStats_groundhitsTextViews);
        groundouts = (TextView) findViewById(R.id.EditPitchingStats_groundoutTextViews);
        hits = (TextView) findViewById(R.id.EditPitchingStats_hitsTextViews);


        editballs = (EditText) findViewById(R.id.EditPitchingStats_BallsEditTectView);
        editEarnedRuns = (EditText) findViewById(R.id.EditPitchingStats_EarndedReunsEditTextView);
        editFlyouts = (EditText) findViewById(R.id.EditPitchingStats_FlyOutEditTextView);
        editFoulBalls = (EditText) findViewById(R.id.EditPitchingStats_foulBallEditText);
        editGameID = (EditText) findViewById(R.id.EditPitchingStats_gameIDEditText);
        editGapper = (EditText) findViewById(R.id.EditPitchingStats_GapperEditTextView);
        editGroundHits = (EditText) findViewById(R.id.EditPitchingStats_groundhitsEditText);
        editGroundOuts = (EditText) findViewById(R.id.EditPitchingStats_groundoutsEditText);
        editHits = (EditText) findViewById(R.id.EditPitchingStats_hitsEditTextView);
    }

    private void initButtons() {
        makeChanges = (Button)findViewById(R.id.EditPitchingStats_MakeChanges);
        remove = (Button)findViewById(R.id.EditPitchingStats_remove);
    }
}
