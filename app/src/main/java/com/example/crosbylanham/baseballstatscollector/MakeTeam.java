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
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MakeTeam extends AppCompatActivity {
    int[] hometeamids ={R.id.homeplayer1,R.id.homeplayer2,R.id.homeplayer3,R.id.homeplayer4,R.id.homeplayer5,
            R.id.homeplayer6,R.id.homeplayer7,R.id.homeplayer8,R.id.homeplayer9,R.id.homeplayer10};
    int[] awayteamids ={R.id.awayplayer1,R.id.awayplayer2,R.id.awayplayer3,R.id.awayplayer4,R.id.awayplayer5,
            R.id.awayplayer6,R.id.awayplayer7,R.id.awayplayer8,R.id.awayplayer9,R.id.awayplayer10};

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
                if(isChecked){
                    findViewById(R.id.awayplayer10Layout)
                        .setVisibility(LinearLayout.VISIBLE);}
                else {
                    findViewById(R.id.awayplayer10Layout)
                            .setVisibility(LinearLayout.INVISIBLE);}
            }
        });
        checkBoxHome.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    findViewById(R.id.homeplayer10Layout)
                        .setVisibility(LinearLayout.VISIBLE);}
                else {
                    findViewById(R.id.homeplayer10Layout)
                        .setVisibility(LinearLayout.INVISIBLE);}
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

                for(Player x:awayPlayers){Log.d("So away players are",x.toString());}

                String homeTeamName = getHomeTeamName();
                String awayTeamName = getAwayTeamName();

                Team hT = new Team(); hT.setName(homeTeamName);
                Team aT = new Team(); aT.setName(awayTeamName);


                hT = new DataBaseHelper().saveTeam(hT,homePlayers);
                aT = new DataBaseHelper().saveTeam(aT,awayPlayers);



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

        final ArrayAdapter[] arrayAdapters = new ArrayAdapter[20];
        for(int i = 0; i < arrayAdapters.length;i++){
            arrayAdapters[i] = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item);
            arrayAdapters[i].setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            playerListSpinners[i].setAdapter(arrayAdapters[i]);
            arrayAdapters[i].add("Unknown Player");
        }
        listOfPlayers.add(defa);

        DatabaseReference br = FirebaseDatabase.getInstance().getReference(DataBaseHelper.PLAYERINFOTABLEBNAME);
        br.orderByChild("generated").equalTo(0).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> it = dataSnapshot.getChildren();
                for(DataSnapshot x:it) {
                    listOfPlayers.add(x.getValue(Player.class));
                    for(ArrayAdapter a: arrayAdapters){
                        a.add(x.getValue(Player.class).getName());
                    }
                }
                for (Player x:listOfPlayers){
                    Log.d("player ",x.toString());
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
            String line = ((EditText)findViewById(hometeamids[i])).getText().toString().trim();
            if(!line.matches("")){
                p = new Player(line);
                new DataBaseHelper().savePlayer(p);
            }else if(playerListSpinners[i+10].getSelectedItemPosition() != 0){
                p = listOfPlayers.get(playerListSpinners[i+10].getSelectedItemPosition());
            }else{
                p = new Player("Player #"+ (int)(Math.random()*10000),7);
                new DataBaseHelper().savePlayer(p);
            }
            homeTeam.add(p);
        }
        return homeTeam;
    }
    public ArrayList<Player> getNamesForAwayTeam(){
        ArrayList<Player> awayTeam = new ArrayList<>();
        for(int i = 0;i<numberOfBattersAway;i++){
            Log.d("Player "+i,"");
            Player p;
            String line = ((EditText)findViewById(awayteamids[i])).getText().toString().trim();
            if(line.length() > 0){
                p = new Player(line);
                new DataBaseHelper().savePlayer(p);
            }else if(playerListSpinners[i].getSelectedItemPosition()!= 0){
                Log.d("This player","is using a spinner");
                p = listOfPlayers.get(playerListSpinners[i].getSelectedItemPosition());
            }else{
                Log.d("This player","is generated");
                p = new Player("Player #"+ (int)(Math.random()*10000),7);
                new DataBaseHelper().savePlayer(p);
            }
            awayTeam.add(p);
        }
        return awayTeam;
    }
}
