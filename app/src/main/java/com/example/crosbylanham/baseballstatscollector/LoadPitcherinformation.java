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

public class LoadPitcherinformation extends AppCompatActivity {
    Spinner playersSpinner;
    Spinner teamsSpinner;
    Spinner teamsSpinner2;

    Button saveplayersinformation;

    EditText playername;
    EditText teamname;
    EditText opponentname;

    DataBaseHelper dataBaseHelper;

    PitchingStats pitchingStats;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_pitcherinformation);

        dataBaseHelper = new DataBaseHelper(LoadPitcherinformation.this);
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
        playersSpinner = (Spinner) findViewById(R.id.Playerspinner);
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                LoadPitcherinformation.this,
                android.R.layout.simple_spinner_item,
                new DataBaseHelper(LoadPitcherinformation.this).getAllPlayersNames());
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        playersSpinner.setAdapter(spinnerArrayAdapter);
    }

    public void setTeamsSpinnerAction() {
        teamsSpinner = (Spinner) findViewById(R.id.TeamSpinner1);
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                LoadPitcherinformation.this,
                android.R.layout.simple_spinner_item,
                new DataBaseHelper(LoadPitcherinformation.this).getTeamNames());
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        teamsSpinner.setAdapter(spinnerArrayAdapter);
    }

    public void setTeamsSpinner2Action() {
        teamsSpinner2 = (Spinner) findViewById(R.id.TeamSpinner2);
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                LoadPitcherinformation.this,
                android.R.layout.simple_spinner_item,
                new DataBaseHelper(LoadPitcherinformation.this).getTeamNames());
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        teamsSpinner2.setAdapter(spinnerArrayAdapter);
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
                    if (teamsSpinner.getSelectedItem() == null){
                        Toast.makeText(
                                LoadPitcherinformation.this,
                                "You must enter a team name or select a team ",
                                Toast.LENGTH_SHORT).show();
                    }
                }else  if(opponentname.getText().toString() == "" || opponentname.getText() == null){
                    if (teamsSpinner2.getSelectedItem() == null){
                        Toast.makeText(
                                LoadPitcherinformation.this,
                                "You must enter an opponent or select an opponent ",
                                Toast.LENGTH_SHORT).show();
                    }
                }else {
                    //-------------------saveing inforamtion ---------------------------------
                    Player player = getPlayer();
                    Team team = getTeam();
                    Team opp = getOpponent();
                    int home = homeTeamOrNot();
                    if (home != -1) {
                        Game game = new Game();
                        if (home == 1){
                            game.setAwayTeamID(team.getTeamid());
                            game.setHomeTeamID(opp.getTeamid());
                        }else {
                            game.setHomeTeamID(team.getTeamid());
                            game.setAwayTeamID(opp.getTeamid());
                        }
                        game = dataBaseHelper.saveGame(game);

                        pitchingStats.setPlayerID(player.getPlayerID());
                        dataBaseHelper.savePitchingStats(pitchingStats);

                        Intent intent = new Intent(LoadPitcherinformation.this, HomePage.class);
                        startActivity(intent);
                    }
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

    public Player getPlayer(){
        Log.d("Playername", String.valueOf(playername));
        Log.d("Playername", String.valueOf(playername.getText()));
        Log.d("Playername",playername.getText().toString());
        if(playername.getText().toString().matches("")){
            String name = playersSpinner.getSelectedItem().toString();
            return new DataBaseHelper(LoadPitcherinformation.this).getPlayer(name);
        }else{
            Log.d("PlayerInformation",playername.getText().toString());
            String name = playername.getText().toString();
            Log.i("Name of player ",name+" is the name of the player being saved");
            return new DataBaseHelper(LoadPitcherinformation.this).savePlayer(new Player(name));
        }
    }

    public Team getTeam(){
        if (teamname.getText().toString().matches("")){
            String name = teamsSpinner.getSelectedItem().toString();
            return new DataBaseHelper(LoadPitcherinformation.this).getTeam(name);
        }else{
            String name = teamname.getText().toString();
            return new DataBaseHelper(LoadPitcherinformation.this).saveTeam(new Team(name));
        }
    }

    public Team getOpponent(){
        if (opponentname.getText().toString().matches("")){
            String name = teamsSpinner2.getSelectedItem().toString();
            return new DataBaseHelper(LoadPitcherinformation.this).getTeam(name);
        }else{
            String name = opponentname.getText().toString();
            return new DataBaseHelper(LoadPitcherinformation.this).saveTeam(new Team(name));
        }
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
}
