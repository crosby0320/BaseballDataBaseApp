package com.example.crosbylanham.baseballstatscollector;

import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DataBaseHelper {

    //----------------Variables static --------------------------------------
    //------------------------At Bat Table information-----------------------
    static final String ATBATTableName          = "AtBat";
    //------------------------inning information-----------------------------
    static final String INNINGTABLENAME     = "Inning";
    //----------------------Game --------------------------------------------
    static final String GAMETABLENAME   = "Game";
    //----------------------Team --------------------------------------------
    static final String TEAMTABLENAME   = "Team";
    //---------------------Line Up-------------------------------------------
    static final String LINEUPTABLENAME     = "LineUP";
    //------------------------- linker to line up ---------------------------

    //------------------------battingStats ----------------------------------
    static final String BATTINGSTATSTableName   = "battingStats";
    //-----------------------Pitching Stats----------------------------------
    static final String PITCHINGSTATSTABLENAME          = "Pitchingstats";
    //------------------------Pplayer Information ---------------------------
    static final String PLAYERINFOTABLEBNAME    = "Player";
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------


    public DataBaseHelper() {}

    //-------------------saving information------------------------------------------
    public Player savePlayer(Player p){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(PLAYERINFOTABLEBNAME);
        DatabaseReference r = myRef.push();

        p.setPlayerID(r.getKey());
        r.setValue(p);
        return p;
    }
    public Team saveTeam(Team team){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(TEAMTABLENAME);
        DatabaseReference r = myRef.push();

        team.setTeamID(r.getKey());
        r.setValue(team);
        return team;
    }
    public Team saveTeam(Team team,ArrayList<Player> players){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(TEAMTABLENAME);
        DatabaseReference r = myRef.push();

        team.setTeamID(r.getKey());
        r.setValue(team);
        for (Player x:players) {
            DatabaseReference dr = r.push();
            dr.setValue(x.getPlayerID());
        }

        return team;
    }
    public Game saveGame(Game game){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(GAMETABLENAME);
        DatabaseReference r = myRef.push();

        game.setGameID(r.getKey());
        r.setValue(game);
        return game;
    }
    public Inning saveInning(Inning inning){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(INNINGTABLENAME);
        DatabaseReference r = myRef.push();

        inning.setInningID(r.getKey());
        r.setValue(inning);
        return inning;
    }
    public PitchingStats savePitchingStats(PitchingStats pitchingStats){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(PITCHINGSTATSTABLENAME);
        DatabaseReference r = myRef.push();

        pitchingStats.setPitchingStatsID(r.getKey());
        r.setValue(pitchingStats);
        return pitchingStats;
    }
    public AtBats saveAtBat(AtBats ab){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(ATBATTableName);
        DatabaseReference r = myRef.push();

        ab.setAtBatID(r.getKey());
        r.setValue(ab);
        return ab;
    }

    public boolean isEmpty(EditText editText){
        String ed_text = editText.getText().toString().trim();
        return (ed_text.isEmpty() || ed_text.length() == 0 || ed_text.equals("") || ed_text == null);
    }

}
