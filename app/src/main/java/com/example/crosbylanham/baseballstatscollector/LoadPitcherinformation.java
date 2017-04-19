package com.example.crosbylanham.baseballstatscollector;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import static com.example.crosbylanham.baseballstatscollector.DataBaseHelper.PLAYERINFOTABLEBNAME;

public class LoadPitcherinformation extends AppCompatActivity {
    Spinner playersSpinner;
    Spinner teamsSpinner;
    Spinner teamsSpinner2;

    Button saveplayersinformation;

    EditText playername;
    EditText teamname;
    EditText opponentname;

    PitchingStats pitchingStats;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_pitcherinformation);

        pitchingStats = (PitchingStats) getIntent().getSerializableExtra("Stats");

        initRadioButton();
        initEditTextFields();
        setPlayersSpinnerAction();
        setTeamsSpinner2Action();
        setTeamsSpinnerAction();
        setSaveplayersinformationAction();
    }
    //---------------------------init textfields-------------------------------------------

    public void initEditTextFields(){
        playername = (EditText) findViewById(R.id.PlayerName);
        teamname = (EditText) findViewById(R.id.TeamName);
        opponentname = (EditText) findViewById(R.id.OpponentName);
    }

    //-----------------------spinners -----------------------------------------------------
    public void setPlayersSpinnerAction() {
        playersSpinner = (Spinner) findViewById(R.id.LoadPitcher_Player);
        fillspinnerplayer(playersSpinner);
    }


    public void setTeamsSpinnerAction() {
        teamsSpinner = (Spinner) findViewById(R.id.LoadPitcher_TeamSpinner1);
        fillspinnerTeam(teamsSpinner);
    }

    public void setTeamsSpinner2Action() {
        teamsSpinner2 = (Spinner) findViewById(R.id.LoadPitcher_TeamSpinner2);
        fillspinnerTeam(teamsSpinner2);
    }
    //---------------------button actions -------------------------------------------------

    public void setSaveplayersinformationAction() {
        saveplayersinformation = (Button) findViewById(R.id.savenewpitcherbutton);
        saveplayersinformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(playername.getText().toString() == "" || playername.getText() == null){
                    if (playersSpinner.getSelectedItem() == null){
                        Toast.makeText(
                                LoadPitcherinformation.this,
                                "You must enter a player name or select a player ",
                                Toast.LENGTH_SHORT).show();
                    }
                } else  if(teamname.getText().toString() == "" || teamname.getText() == null){
                    Log.d("save player action","team name was null");
                    if (teamsSpinner.getSelectedItem() == null){
                        Toast.makeText(
                                LoadPitcherinformation.this,
                                "You must enter a team name or select a team ",
                                Toast.LENGTH_SHORT).show();
                    }
                }else  if(opponentname.getText().toString() == "" || opponentname.getText() == null){
                    Log.d("save player action","opp name was null");
                    if (teamsSpinner2.getSelectedItem() == null){
                        Toast.makeText(
                                LoadPitcherinformation.this,
                                "You must enter an opponent or select an opponent ",
                                Toast.LENGTH_SHORT).show();
                    }
                }else {
                    //-------------------saveing inforamtion ---------------------------------
                    final FirebaseDatabase database = FirebaseDatabase.getInstance();
                    final DatabaseReference myRef = database.getReference(PLAYERINFOTABLEBNAME);
                    if(playername.getText().toString().matches("")) {

                        Log.d("its empty ","retreving data");
                        Query query = myRef.orderByChild("name").equalTo(playersSpinner.getSelectedItem().toString());
                        query.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                Iterable<DataSnapshot> itds = dataSnapshot.getChildren();
                                Player p = new Player("Unknown");
                                for (DataSnapshot x:itds){
                                    Log.d("Player info",x.getValue(Player.class).getName());
                                    p = x.getValue(Player.class);
                                }
                                pitchingStats.setPlayerID(p.getPlayerID());
                                new DataBaseHelper().savePitchingStats(pitchingStats);
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }else{
                        Player info = new DataBaseHelper().savePlayer(new Player(playername.getText().toString()));
                        Game g = new Game();
                        g.setName(new DateFunctions().getCurrentTimeAndDate());
                        Game gameInfo = new DataBaseHelper().saveGame(g);
                        pitchingStats.setPlayerID(info.getPlayerID());
                        pitchingStats.setGameID(gameInfo.getGameID());
                        new DataBaseHelper().savePitchingStats(pitchingStats);
                    }
                        Intent intent = new Intent(LoadPitcherinformation.this, HomePage.class);
                        startActivity(intent);
                }
            }
        });
    }
    public int homeTeamOrNot(){
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        int home = 1;
        int checkedRadioButtonId = radioGroup.getCheckedRadioButtonId();

        if(checkedRadioButtonId == R.id.radiobuttonhome){
            home = 2;
        }else if(checkedRadioButtonId == R.id.radiobuttonaway){
            home = 1;
        }else{
            home = -1;
        }
        return home;
    }

    RadioButton radioButtonHome;
    RadioButton radioButtonAway;

    public void initRadioButton(){
        radioButtonHome = (RadioButton) findViewById(R.id.radiobuttonhome);
        radioButtonAway = (RadioButton) findViewById(R.id.radiobuttonaway);

    }
    public void onRadioButtonClicked(View view) {
    // Is the button now checked?
    boolean checked = ((RadioButton) view).isChecked();

    // Check which radio button was clicked
    switch(view.getId()) {
        case R.id.radiobuttonhome:
            if (checked)
                // Pirates are the best
                break;
        case R.id.radiobuttonaway:
            if (checked)
                // Ninjas rule
                break;
    }
}
    public void fillspinnerplayer(Spinner playersSpinner){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(PLAYERINFOTABLEBNAME);
        final ArrayAdapter<String> array = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                for(DataSnapshot child:children){
                    array.add(child.getValue(Player.class).getName());
                }
            }

            @Override public void onCancelled(DatabaseError databaseError) {}});
        playersSpinner.setAdapter(array);
    }
    public void fillspinnerTeam(Spinner playersSpinner) {


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(DataBaseHelper.TEAMTABLENAME);
        final ArrayAdapter<String> array = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                for (DataSnapshot child : children) {
                    array.add(child.getValue(Team.class).getName());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        playersSpinner.setAdapter(array);
    }
}
