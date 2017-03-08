package com.example.crosbylanham.baseballstatscollector;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MakeTeam extends AppCompatActivity {
    int[] hometeamids ={R.id.homeplayer1,R.id.homeplayer1,R.id.homeplayer1,R.id.homeplayer1,R.id.homeplayer1,
            R.id.homeplayer1,R.id.homeplayer1,R.id.homeplayer1,R.id.homeplayer1,R.id.homeplayer1};
    int[] awayteamids ={R.id.awayplayer1,R.id.awayplayer1,R.id.awayplayer1,R.id.awayplayer1,R.id.awayplayer1,
            R.id.awayplayer1,R.id.awayplayer1,R.id.awayplayer1,R.id.awayplayer1,R.id.awayplayer1};

    CheckBox checkBoxHome, checkBoxAway;
    int numberOfBattersHome;
    int numberOfBattersAway;
    Button submitButton,awayLoadTeam,homeLoadTeam;

    Spinner[] playerListSpinners;
    Player defa = new Player();
    ArrayList<Player> listOfPlayers;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_team);

        playerListSpinners = new Spinner[20];
        listOfPlayers = new ArrayList<>();
        initButtons();
        loadPlayers();
        initCheckBox();
        makeSomeChangesToSpinners();
        submitButtonAction();
    }
    /*
    #TODO need to finish the make team option
     */
    public void makeSomeChangesToSpinners(){
        playerListSpinners[0] = (Spinner) findViewById(R.id.spinneraway1);
        playerListSpinners[1] = (Spinner) findViewById(R.id.spinneraway2);
        playerListSpinners[2] = (Spinner) findViewById(R.id.spinneraway3);
        playerListSpinners[3] = (Spinner) findViewById(R.id.spinneraway4);
        playerListSpinners[4] = (Spinner) findViewById(R.id.spinneraway5);
        playerListSpinners[5] = (Spinner) findViewById(R.id.spinneraway6);
        playerListSpinners[6] = (Spinner) findViewById(R.id.spinneraway7);
        playerListSpinners[7] = (Spinner) findViewById(R.id.spinneraway8);
        playerListSpinners[8] = (Spinner) findViewById(R.id.spinneraway9);
        playerListSpinners[9] = (Spinner) findViewById(R.id.spinneraway10);

        playerListSpinners[10] = (Spinner) findViewById(R.id.spinnerhome1);
        playerListSpinners[11] = (Spinner) findViewById(R.id.spinnerhome2);
        playerListSpinners[12] = (Spinner) findViewById(R.id.spinnerhome3);
        playerListSpinners[13] = (Spinner) findViewById(R.id.spinnerhome4);
        playerListSpinners[14] = (Spinner) findViewById(R.id.spinnerhome5);
        playerListSpinners[15] = (Spinner) findViewById(R.id.spinnerhome6);
        playerListSpinners[16] = (Spinner) findViewById(R.id.spinnerhome7);
        playerListSpinners[17] = (Spinner) findViewById(R.id.spinnerhome8);
        playerListSpinners[18] = (Spinner) findViewById(R.id.spinnerhome9);
        playerListSpinners[19] = (Spinner) findViewById(R.id.spinnerhome10);

        for (Spinner x: playerListSpinners){
            ViewGroup.LayoutParams p = x.getLayoutParams();
            p.width = 77;
            x.setLayoutParams(p);
        }
    }

    public void initButtons(){
        awayLoadTeam = (Button)findViewById(R.id.awayloadteambutton);
        homeLoadTeam = (Button)findViewById(R.id.homeloadteambutton);
        submitButton = (Button)findViewById(R.id.makeTeams_Submitbutton);
    }
    public void initCheckBox(){
        checkBoxAway = (CheckBox)findViewById(R.id.awayDHCheckbox);
        checkBoxHome = (CheckBox) findViewById(R.id.homeDHCheckbox);
        checkBoxAway.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){((LinearLayout)findViewById(R.id.awayplayer10Layout))
                        .setVisibility(LinearLayout.INVISIBLE);}
                else {((LinearLayout)findViewById(R.id.awayplayer10Layout))
                            .setVisibility(LinearLayout.VISIBLE);}
            }
        });
        checkBoxHome.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){((LinearLayout)findViewById(R.id.homeplayer10Layout))
                        .setVisibility(LinearLayout.INVISIBLE);}
                else {((LinearLayout)findViewById(R.id.homeplayer10Layout))
                        .setVisibility(LinearLayout.VISIBLE);}
            }
        });
    }
    public void submitButtonAction(){
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numberOfBattersHome = checkBoxHome.isChecked() ? 10 : 9;
                numberOfBattersAway = checkBoxAway.isChecked() ? 10 : 9;
                ArrayList<Player>  homePlayers = getNamesForHomeTeam();
                ArrayList<Player> awayPlayers = getNamesForAwayTeam();

                String homeTeamName = getHomeTeamName();
                String awayTeamName = getAwayTeamName();

                Team hT = new Team(); hT.setName(homeTeamName);
                Team aT = new Team(); aT.setName(awayTeamName);

                hT = new DataBaseHelper().saveTeam(hT);
                aT = new DataBaseHelper().saveTeam(aT);

                Intent intent = new Intent(MakeTeam.this,FullGame.class);
                intent.putExtra("homeTeam",homePlayers);
                intent.putExtra("homeTeamName",hT.getName());
                intent.putExtra("awayTeam",awayPlayers);
                intent.putExtra("awayTeamName",aT.getName());
                Log.d("Team ID",aT.getTeamID());
                Log.d("Team ID",hT.getTeamID());
                intent.putExtra("awayTeamId",aT.getTeamID());
                intent.putExtra("homeTeamId",hT.getTeamID());
                startActivity(intent);
            }
        });
    }

    public void setAwayLoadButtonAction(){
                /*#TODO*/
        Toast.makeText(this,"Sorry this function does Not work Yet",Toast.LENGTH_LONG).show();
    }

    public void setHomeLoadButtonAction(){
                /*#TODO*/
        Toast.makeText(this,"Sorry this function does Not work Yet",Toast.LENGTH_LONG).show();
    }

    public void loadPlayers(){
        playerListSpinners[0] = (Spinner) findViewById(R.id.spinneraway1);
        playerListSpinners[1] = (Spinner) findViewById(R.id.spinneraway2);
        playerListSpinners[2] = (Spinner) findViewById(R.id.spinneraway3);
        playerListSpinners[3] = (Spinner) findViewById(R.id.spinneraway4);
        playerListSpinners[4] = (Spinner) findViewById(R.id.spinneraway5);
        playerListSpinners[5] = (Spinner) findViewById(R.id.spinneraway6);
        playerListSpinners[6] = (Spinner) findViewById(R.id.spinneraway7);
        playerListSpinners[7] = (Spinner) findViewById(R.id.spinneraway8);
        playerListSpinners[8] = (Spinner) findViewById(R.id.spinneraway9);
        playerListSpinners[9] = (Spinner) findViewById(R.id.spinneraway10);

        playerListSpinners[10] = (Spinner) findViewById(R.id.spinnerhome1);
        playerListSpinners[11] = (Spinner) findViewById(R.id.spinnerhome2);
        playerListSpinners[12] = (Spinner) findViewById(R.id.spinnerhome3);
        playerListSpinners[13] = (Spinner) findViewById(R.id.spinnerhome4);
        playerListSpinners[14] = (Spinner) findViewById(R.id.spinnerhome5);
        playerListSpinners[15] = (Spinner) findViewById(R.id.spinnerhome6);
        playerListSpinners[16] = (Spinner) findViewById(R.id.spinnerhome7);
        playerListSpinners[17] = (Spinner) findViewById(R.id.spinnerhome8);
        playerListSpinners[18] = (Spinner) findViewById(R.id.spinnerhome9);
        playerListSpinners[19] = (Spinner) findViewById(R.id.spinnerhome10);

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        for (int i = 0; i< playerListSpinners.length; i++) {
            playerListSpinners[i].setAdapter(arrayAdapter);
        }

        arrayAdapter.add("Unknown Player");
        listOfPlayers.add(defa);

        DatabaseReference br = FirebaseDatabase.getInstance().getReference(DataBaseHelper.PLAYERINFOTABLEBNAME);
        br.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> it = dataSnapshot.getChildren();
                for(DataSnapshot x:it) {
                    arrayAdapter.add(x.getValue(Player.class).getName());
                    listOfPlayers.add(x.getValue(Player.class));
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
    }

    public String getHomeTeamName(){
        String name = ((EditText)findViewById(R.id.hometeamname)).getText().toString();
        if(name.matches("")){
            Log.d("No Home Team ","No Home Team name was found");
            return "TeamName " + (int)(Math.random()*10000);
        }else{return name;}
    }
    public String getAwayTeamName(){
        String name = ((EditText)findViewById(R.id.awayteamname)).getText().toString();
        if(name.matches("")){
            Log.d("No away Team ","No away Team away was found");
            return "TeamName " + (int)(Math.random()*10000);
        }else{return name;}
    }
    public ArrayList<Player> getNamesForHomeTeam(){
        ArrayList<Player> homeTeam = new ArrayList<>();
        for(int i = 0;i<numberOfBattersHome;i++){
            Player p;
            String line = ((EditText)findViewById(hometeamids[i])).getText().toString();
            if(!line.matches("")){
                p = new Player(line);
                new DataBaseHelper().savePlayer(p);
            }else if(!playerListSpinners[i].getSelectedItem().toString().equals("Unknown Player")){
                p = listOfPlayers.get(playerListSpinners[i].getSelectedItemPosition());

            }else{
                p = new Player("Player "+ (int)(Math.random()*10000));
                new DataBaseHelper().savePlayer(p);
            }
            homeTeam.add(p);
        }
        return homeTeam;
    }
    public ArrayList<Player> getNamesForAwayTeam(){
        ArrayList<Player> awayTeam = new ArrayList<>();
        for(int i = 0;i<numberOfBattersAway;i++){
            Player p;
            String line = ((EditText)findViewById(awayteamids[i])).getText().toString();
            if(!line.matches("")){
                p = new Player(line);
                new DataBaseHelper().savePlayer(p);
            }else if(!playerListSpinners[i+10].getSelectedItem().toString().equals("Unknown Player")){
                p = listOfPlayers.get(playerListSpinners[i].getSelectedItemPosition());
            }else{
                p = new Player("Player "+ (int)(Math.random()*10000));
                new DataBaseHelper().savePlayer(p);
            }
            new DataBaseHelper().savePlayer(p);
            awayTeam.add(p);
        }
        return awayTeam;
    }
}
