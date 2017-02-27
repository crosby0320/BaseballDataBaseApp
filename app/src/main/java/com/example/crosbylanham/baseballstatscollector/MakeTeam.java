package com.example.crosbylanham.baseballstatscollector;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import java.util.ArrayList;

public class MakeTeam extends AppCompatActivity {
    int[] hometeamids ={R.id.homeplayer1,R.id.homeplayer1,R.id.homeplayer1,R.id.homeplayer1,R.id.homeplayer1,
            R.id.homeplayer1,R.id.homeplayer1,R.id.homeplayer1,R.id.homeplayer1,R.id.homeplayer1};
    int[] awayteamids ={R.id.awayplayer1,R.id.awayplayer1,R.id.awayplayer1,R.id.awayplayer1,R.id.awayplayer1,
            R.id.awayplayer1,R.id.awayplayer1,R.id.awayplayer1,R.id.awayplayer1,R.id.awayplayer1};

    DataBaseHelper dataBaseHelper = new DataBaseHelper(this);

    CheckBox checkBoxHome, checkBoxAway;
    int numberOfBattersHome;
    int numberOfBattersAway;
    Button submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_team);

        initButtons();

        initCheckBox();
        submitButtonAction();
    }
    /*
    #TODO need to finish the make team option
     */
    public void initButtons(){
        submitButton = (Button)findViewById(R.id.makeTeams_Submitbutton);
    }
    public void initCheckBox(){
        checkBoxAway = (CheckBox)findViewById(R.id.awayDHCheckbox);
        checkBoxHome = (CheckBox) findViewById(R.id.homeDHCheckbox);
    }
    public void submitButtonAction(){
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numberOfBattersHome = checkBoxHome.isChecked() ? 10 : 9;
                numberOfBattersAway = checkBoxAway.isChecked() ? 10 : 9;
                ArrayList<Player>  homePlayers = getNamesForHomeTeam();
                ArrayList<Player> awayPlayers = getNamesForAwayTeam();

                saveTeams(getHomeTeamName(),getAwayTeamName());

                Intent intent = new Intent(MakeTeam.this,FullGame.class);
                intent.putExtra("homeTeam",homePlayers);
                intent.putExtra("homeTeamName",hT.getName());
                intent.putExtra("awayTeam",awayPlayers);
                intent.putExtra("awayTeamName",aT.getName());
                startActivity(intent);
            }
        });
    }

    Team hT,aT;
    public void saveTeams(String home, String away){
        hT = dataBaseHelper.getTeam(home);
        if(hT == null){hT = new Team();hT.setName(home);hT=dataBaseHelper.saveTeam(hT);}
        aT = dataBaseHelper.getTeam(away);
        if(aT == null){aT = new Team();aT.setName(away);aT=dataBaseHelper.saveTeam(aT);}
    }

    public String getHomeTeamName(){
        String name = ((EditText)findViewById(R.id.hometeamname)).getText().toString();
        if(name.matches("")){
            Log.d("No Home Team ","No Home Team Name was found");
            return "TeamName " + Math.random()*1000;
        }else{return name;}
    }
    public String getAwayTeamName(){
        String name = ((EditText)findViewById(R.id.awayteamname)).getText().toString();
        if(name.matches("")){
            Log.d("No away Team ","No away Team away was found");
            return "TeamName " + Math.random()*1000;
        }else{return name;}
    }
    public ArrayList<Player> getNamesForHomeTeam(){
        ArrayList<Player> homeTeam = new ArrayList<>();
        for(int i = 0;i<numberOfBattersHome;i++){
            Player p;
            String line = ((EditText)findViewById(hometeamids[i])).getText().toString();
            if(line.matches("")){
                p = new Player("Player "+ (int)Math.random()*1000);
            }else {
                Player player = dataBaseHelper.getPlayer(line);
                if(player == null){p = new Player(line);}
                else{p = player;}
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
            if(line.matches("")){
                p = new Player("Player "+ (int)Math.random()*1000);
            }else {
                Player player = dataBaseHelper.getPlayer(line);
                if(player == null){p = new Player(line);}
                else{p = player;}
            }
            awayTeam.add(p);
        }
        return awayTeam;
    }
}
