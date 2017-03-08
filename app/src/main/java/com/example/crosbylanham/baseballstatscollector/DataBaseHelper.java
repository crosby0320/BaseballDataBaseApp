package com.example.crosbylanham.baseballstatscollector;

import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

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

    //------------------------BattingStats ----------------------------------
    static final String BATTINGSTATSTableName   = "BattingStats";
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
    //--------------------getting from database-------------------------------------

    ArrayList<Player> playersArrayList;
    ArrayList<Team> teamArrayList;
    ArrayList<AtBats> atBatsArrayList;
    ArrayList<Game> gameArrayList;
    ArrayList<Inning> inningArrayList;
    ArrayList<PitchingStats> pitchingStatsArrayList;
    ArrayList<String> strings;
    ArrayList<String> strings2;

    public Player getPlayerByName(String name){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(PLAYERINFOTABLEBNAME);

        playersArrayList = new ArrayList<>();

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Iterable<DataSnapshot> children = dataSnapshot.getChildren();

                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                for(DataSnapshot child:children){
                    playersArrayList.add(child.getValue(Player.class));
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("nonn", "Failed to read value.", error.toException());
            }
        });

        for(Player x:playersArrayList){
            if (x.getName().equals(name)){return x;}
        }
        return null;
    }
    public Player getPlayerByID(String id){
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference(PLAYERINFOTABLEBNAME);

            playersArrayList = new ArrayList<>();

            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    Iterable<DataSnapshot> children = dataSnapshot.getChildren();

                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
                    for(DataSnapshot child:children){
                        playersArrayList.add(child.getValue(Player.class));
                    }
                }

                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value
                    Log.w("nonn", "Failed to read value.", error.toException());
                }
            });

            for(Player x:playersArrayList){
                if (x.getPlayerID().equals(id)){return x;}
            }
            return null;
    }
    public Team getTeamByName(String name){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(TEAMTABLENAME);

        teamArrayList = new ArrayList<>();

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Iterable<DataSnapshot> children = dataSnapshot.getChildren();

                for(DataSnapshot child:children){
                    teamArrayList.add(child.getValue(Team.class));
                }
            }
            @Override
            public void onCancelled(DatabaseError error) {
                Log.w("nonn", "Failed to read value.", error.toException());
            }
        });
        for (Team x:teamArrayList) {
            if(x.getName().trim().equals(name.trim())){return x;}
        }
        return null;
    }
    public Team getTeamByID(String id){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(TEAMTABLENAME);

        teamArrayList = new ArrayList<>();

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Iterable<DataSnapshot> children = dataSnapshot.getChildren();

                for(DataSnapshot child:children){
                    teamArrayList.add(child.getValue(Team.class));
                }
            }
            @Override
            public void onCancelled(DatabaseError error) {
                Log.w("nonn", "Failed to read value.", error.toException());
            }
        });
        for (Team x:teamArrayList) {
            if(x.getTeamID().equals(id)){return x;}
        }

        return null;
    }
    public Game getGame(String gameName){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(GAMETABLENAME);

        gameArrayList= new ArrayList<>();

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Iterable<DataSnapshot> children = dataSnapshot.getChildren();

                for(DataSnapshot child:children){
                    gameArrayList.add(child.getValue(Game.class));
                }
            }
            @Override
            public void onCancelled(DatabaseError error) {
                Log.w("nonn", "Failed to read value.", error.toException());
            }
        });

        for (Game x:gameArrayList) {
            if(x.getName().equals(gameName)){return x;}
        }
        return null;
    }
    public Game getgameByID(String id){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(GAMETABLENAME);

        gameArrayList= new ArrayList<>();

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Iterable<DataSnapshot> children = dataSnapshot.getChildren();

                for(DataSnapshot child:children){
                    gameArrayList.add(child.getValue(Game.class));
                }
            }
            @Override
            public void onCancelled(DatabaseError error) {
                Log.w("nonn", "Failed to read value.", error.toException());
            }
        });

        for (Game x:gameArrayList) {
            if(x.getGameID().equals(id)){return x;}
        }
        return null;
    }
    public PitchingStats getPitchingStats(String playerID,String gameID){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(PITCHINGSTATSTABLENAME);

        pitchingStatsArrayList= new ArrayList<>();

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Iterable<DataSnapshot> children = dataSnapshot.getChildren();

                for(DataSnapshot child:children){
                    pitchingStatsArrayList.add(child.getValue(PitchingStats.class));
                }
            }
            @Override
            public void onCancelled(DatabaseError error) {
                Log.w("nonn", "Failed to read value.", error.toException());
            }
        });

        for (PitchingStats x:pitchingStatsArrayList) {
            if(x.getPlayerID().equals(playerID) &&
                    x.getGameID().equals(gameID)){return x;}
        }
        return null;
    }
    public ArrayList<String> getAllTeamNames(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(TEAMTABLENAME);

        strings = new ArrayList<>();

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Iterable<DataSnapshot> children = dataSnapshot.getChildren();

                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                for(DataSnapshot child:children){
                    strings.add(child.getValue(Team.class).getName());
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("nonn", "Failed to read value.", error.toException());
            }
        });
        return strings;
    }
    public ArrayList<String> getAllPlayersNames() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(PLAYERINFOTABLEBNAME);

        strings = new ArrayList<>();

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Iterable<DataSnapshot> children = dataSnapshot.getChildren();

                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                for(DataSnapshot child:children){
                    strings.add(child.getValue(Player.class).getName());
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("nonn", "Failed to read value.", error.toException());
            }
        });
        return strings;
    }
    public ArrayList<AtBats> getAllAtBatsForPlayerByID(final String id){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(ATBATTableName);

        atBatsArrayList= new ArrayList<>();

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Iterable<DataSnapshot> children = dataSnapshot.getChildren();

                for(DataSnapshot child:children){
                    if(child.getValue(AtBats.class).getAtBatID().equals(id)){
                        atBatsArrayList.add(child.getValue(AtBats.class));
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError error) {
                Log.w("nonn", "Failed to read value.", error.toException());
            }
        });

        return atBatsArrayList;
    }
    public ArrayList<AtBats> getAllABsForPlayerBYIDGAME(final String id, final String gameid){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(ATBATTableName);

        atBatsArrayList= new ArrayList<>();

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Iterable<DataSnapshot> children = dataSnapshot.getChildren();

                for(DataSnapshot child:children){
                    if(child.getValue(AtBats.class).getAtBatID().equals(id) &&
                            child.getValue(AtBats.class).getGameID().equals(gameid)){
                        atBatsArrayList.add(child.getValue(AtBats.class));
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError error) {
                Log.w("nonn", "Failed to read value.", error.toException());
            }
        });

        return atBatsArrayList;
    }
    public ArrayList<String> getAllBattersNames(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(ATBATTableName);

        strings = new ArrayList<>();
        strings2 = new ArrayList<>();

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Iterable<DataSnapshot> children = dataSnapshot.getChildren();

                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                for(DataSnapshot child:children){
                    if(strings2.contains(child.getValue(AtBats.class))) {
                        strings2.add(child.getValue(AtBats.class).getPlayerAtBatId());
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {}
        });

        for(String x:strings2){
            strings.add(getPlayerByID(x).getName());
        }

        return strings;
    }
    public ArrayList<String> getAllGamesNamesForPitcher(final String id){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(PITCHINGSTATSTABLENAME);

        strings= new ArrayList<>();

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Iterable<DataSnapshot> children = dataSnapshot.getChildren();

                for(DataSnapshot child:children){
                    if(child.getValue(PitchingStats.class).getPlayerID().equals(id)){
                        strings.add(child.getValue(PitchingStats.class).getPitchingStatsID());
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError error) {
                Log.w("nonn", "Failed to read value.", error.toException());
            }
        });
        for(String x:strings){strings2.add(getgameByID(x).getName());}
        return strings2;
    }
    public ArrayList<String> getAllGameNames(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(GAMETABLENAME);

        gameArrayList= new ArrayList<>();
        strings = new ArrayList<>();

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Iterable<DataSnapshot> children = dataSnapshot.getChildren();

                for(DataSnapshot child:children){
                    gameArrayList.add(child.getValue(Game.class));
                }
            }
            @Override
            public void onCancelled(DatabaseError error) {
                Log.w("nonn", "Failed to read value.", error.toException());
            }
        });

        for (Game x:gameArrayList) {
            strings.add(x.getName());
        }
        return strings;
    }
    public ArrayList<Game> getAllGames(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(GAMETABLENAME);

        gameArrayList= new ArrayList<>();

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Iterable<DataSnapshot> children = dataSnapshot.getChildren();

                for(DataSnapshot child:children){
                    gameArrayList.add(child.getValue(Game.class));
                }
            }
            @Override
            public void onCancelled(DatabaseError error) {
                Log.w("nonn", "Failed to read value.", error.toException());
            }
        });

        return gameArrayList;
    }
    public ArrayList<PitchingStats> getAllPitchingStatsForPlayer(final String id){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(PITCHINGSTATSTABLENAME);

        pitchingStatsArrayList= new ArrayList<>();

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Iterable<DataSnapshot> children = dataSnapshot.getChildren();

                for(DataSnapshot child:children){
                    if(child.getValue(PitchingStats.class).playerID.equals(id)) {
                        pitchingStatsArrayList.add(child.getValue(PitchingStats.class));
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError error) {
                Log.w("nonn", "Failed to read value.", error.toException());
            }
        });

        return pitchingStatsArrayList;
    }

}
