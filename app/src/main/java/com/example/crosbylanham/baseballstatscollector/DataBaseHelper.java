package com.example.crosbylanham.baseballstatscollector;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * instructions for adding new element to table
 *
 *  set name to table
 *  set colum name
 *  and to on create
 *  adjest saveing and getting
 *  adjust class
 */

public class DataBaseHelper extends SQLiteOpenHelper {

    //----------------Variables static --------------------------------------
    static final String databaseName = "BaseballDataBase.db";
    static final int version = 1;
    //------------------------At Bat Table information-----------------------
    static final String ATBATTableName          = "AtBat";
    static final String ATBATid                 = "AtBatID";
    static final String ATBATplayeratbatid      = "PlayerAtBatID";
    static final String ATBATgameid             = "GameID";
    static final String ATBATinnningid          = "InningID";
    static final String ATBATpitcherid          = "PitcherID";
    static final String ATBATballs              = "Balls";
    static final String ATBATstrikes            = "Strikes";
    static final String ATBATpitches            = "Pitches";
    static final String ATBAToutcome            = "OutCome";
    //------------------------inning information-----------------------------
    static final String INNINGTABLENAME     = "Inning";
    static final String INNINGid            = "InningID";
    static final String INNINGgameid        = "GameId";
    static final String INNINGinningnumber  = "InningNumber";
    static final String INNINGtop           = "Top";
    static final String INNINGruns          = "Runs";
    //----------------------Game --------------------------------------------
    static final String GAMETABLENAME   = "Game";
    static final String GAMEid          = "GameID";
    static final String GAMEhometeam    = "HomeTeamID";
    static final String GAMEawayteam    = "AwayTeamID";
    static final String GAMEdescription = "Description";
    static final String GAMEname        = "Name";
    //----------------------Team --------------------------------------------
    static final String TEAMTABLENAME   = "Team";
    static final String TEAMid          = "TeamID";
    static final String TEAMname        = "Name";
    static final String TEAMloaction    = "Location";
    static final String TEAMmascot      = "Mascot";
    //---------------------Line Up-------------------------------------------
    static final String LINEUPTABLENAME     = "LineUP";
    static final String LINEUPid            = "LineUpID";
    static final String LINEUPteamid        = "LineUpTeamID";
    //------------------------- linker to line up ---------------------------

    //------------------------BattingStats ----------------------------------
    static final String BATTINGSTATSTableName   = "BattingStats";
    static final String BATTINGSTATSid          = "BattingStatsID";
    static final String BATTINGSTATSplayerid    = "PlayerID";
    static final String BATTINGSTATSgameid      = "GameID";
    static final String BATTINGSTATSteamid      = "TeamID";
    static final String BATTINGSTATSab          = "AB";
    static final String BATTINGSTATSruns        = "Runs";
    static final String BATTINGSTATShits        = "Hits";
    static final String BATTINGSTATSsingles     = "Singles";
    static final String BATTINGSTATSdoubles     = "Doubles";
    static final String BATTINGSTATStriples     = "Triples";
    static final String BATTINGSTATShomeruns    = "HomeRuns";
    static final String BATTINGSTATSstrikes     = "Strikes";
    static final String BATTINGSTATSballs       = "Balls";
    static final String BATTINGSTATSrbis        = "RBIs";
    static final String BATTINGSTATSSB          = "SB";
    static final String BATTINGSTATSCS          = "CS";
    static final String BATTINGSTATSBB          = "BB";
    static final String BATTINGSTATSSO          = "SO";
    //-----------------------Pitching Stats----------------------------------
    static final String PITCHINGSTATSTABLENAME          = "Pitchingstats";
    static final String PITCHINGSTATSpitchingstatsid    = "PitchingStatsID";
    static final String PITCHINGSTATSgameid             = "GameID";
    static final String PITCHINGSTATSteamid             = "TeamID";
    static final String PITCHINGSTATSplayerid           = "PlayerID";
    static final String PITCHINGSTATSpitches            = "Pitches";
    static final String PITCHINGSTATSballs              = "Balls";
    static final String PITCHINGSTATSstrikes            = "Strikes";
    static final String PITCHINGSTATShits               = "Hits";
    static final String PITCHINGSTATSstrikeouts         = "Strikeouts";
    static final String PITCHINGSTATSputouts            = "PutOuts";
    static final String PITCHINGSTATSwalks              = "Walks";
    static final String PITCHINGSTATShitsbypitch        = "HitByPitch";
    static final String PITCHINGSTATShomerun            = "HomeRuns";
    static final String PITCHINGSTATSfoulballs          = "FouldBalls";
    static final String PITCHINGSTATSgroundouts         = "GroundOuts";
    static final String PITCHINGSTATSgroundhits         = "GrounHits";
    static final String PITCHINGSTATSlineouts           = "LineOuts";
    static final String PITCHINGSTATSlinehits           = "LineHits";
    static final String PITCHINGSTATSflyouts            = "FlyOuts";
    static final String PITCHINGSTATSlooking            = "Looking";
    static final String PITCHINGSTATSgaper              = "Gapper";
    static final String PITCHINGSTATSuntouched          = "Untounched";
    static final String PITCHINGSTATSoutspitched        = "Outs Pitched";
    //------------------------Pplayer Information ---------------------------
    static final String PLAYERINFOTABLEBNAME    = "Player";
    static final String PLAYERINFOid            = "PlayerID";
    static final String PLAYERINFOname          = "PlayerName";
    //----------------------creating tables commands-----------------------------------
    private static final String CREATEPLAYERTBALE = "CREATE TABLE IF NOT EXISTS " + PLAYERINFOTABLEBNAME    +
            "(" +
            PLAYERINFOid        + " INTEGER PRIMARY KEY," +
            PLAYERINFOname      + " TEXT" +
            ");";
    private static final String CREATETEAMTBALE = "CREATE TABLE IF NOT EXISTS " + TEAMTABLENAME    +
            "(" +
            TEAMid        + " INTEGER PRIMARY KEY," +
            TEAMname      + " TEXT," +
            TEAMloaction      + " TEXT," +
            TEAMmascot      + " TEXT" +
            ");";
    private static final String CREATEGAMETBALE = "CREATE TABLE IF NOT EXISTS " + GAMETABLENAME    +
            "(" +
            GAMEid              + " INTEGER PRIMARY KEY," +
            GAMEhometeam        + " TEXT," +
            GAMEawayteam        + " TEXT," +
            GAMEname            + " TEXT," +
            GAMEdescription     + " TEXT" +
            ");";
    private static final String CREATEPITCHINGSTATSTABLE = "CREATE TABLE IF NOT EXISTS " + PITCHINGSTATSTABLENAME    +
            "(" +
            PITCHINGSTATSpitchingstatsid    + " INTEGER PRIMARY KEY," +
            PITCHINGSTATSgameid             + " INTEGER," +
            PITCHINGSTATSteamid             + " INTEGER," +
            PITCHINGSTATSplayerid           + " INTEGER," +
            PITCHINGSTATSpitches            + " INTEGER," +
            PITCHINGSTATSballs              + " INTEGER," +
            PITCHINGSTATSstrikes            + " INTEGER," +
            PITCHINGSTATShits               + " INTEGER," +
            PITCHINGSTATSstrikeouts         + " INTEGER," +
            PITCHINGSTATSputouts            + " INTEGER," +
            PITCHINGSTATSwalks              + " INTEGER," +
            PITCHINGSTATShitsbypitch        + " INTEGER," +
            PITCHINGSTATShomerun            + " INTEGER," +
            PITCHINGSTATSfoulballs          + " INTEGER," +
            PITCHINGSTATSgroundouts         + " INTEGER," +
            PITCHINGSTATSgroundhits         + " INTEGER," +
            PITCHINGSTATSlineouts           + " INTEGER," +
            PITCHINGSTATSlinehits           + " INTEGER," +
            PITCHINGSTATSflyouts            + " INTEGER," +
            PITCHINGSTATSlooking            + " INTEGER," +
            PITCHINGSTATSgaper              + " INTEGER," +
            PITCHINGSTATSoutspitched        + " INTEGER," +
            PITCHINGSTATSuntouched          + " INTEGER"  +
            ");";
    private static final String CREATEATBATTABLE = "CREATE TABLE IF NOT EXISTS " + ATBATTableName +
            "("+
            ATBATid                         + " INTEGER PRIMARY KEY," +
            ATBATplayeratbatid              + " INTEGER," +
            ATBATgameid                     + " INTEGER," +
            ATBATinnningid                  + " INTEGER," +
            ATBATpitcherid                  + " INTEGER," +
            ATBATballs                      + " INTEGER," +
            ATBATstrikes                    + " INTEGER," +
            ATBATpitches                    + " INTEGER," +
            ATBAToutcome                    + " INTEGER"  +
            ");";
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    public DataBaseHelper(Context context) {
        super(context, databaseName, null, version);
        Log.i("Database Operations", "Created opened the database");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATEPLAYERTBALE);
        db.execSQL(CREATETEAMTBALE);
        db.execSQL(CREATEGAMETBALE);
        db.execSQL(CREATEPITCHINGSTATSTABLE);
        db.execSQL(CREATEATBATTABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    //-------------------saving information------------------------------------------
    public Player savePlayer(Player p){
        ContentValues contentvalues = new ContentValues();

        contentvalues.put(PLAYERINFOname       , p.getName());

        SQLiteDatabase db = getWritableDatabase();
        db.insert(PLAYERINFOTABLEBNAME,null,contentvalues);
        db.close();
        return getPlayer(p.getName());
    }
    public Team saveTeam(Team team){
        ContentValues contentValues = new ContentValues();

        contentValues.put(TEAMname      , team.getName());
        contentValues.put(TEAMloaction  , team.getLocation());
        contentValues.put(TEAMmascot    , team.getMascot());

        SQLiteDatabase db = getWritableDatabase();
        db.insert(TEAMTABLENAME,null,contentValues);
        db.close();
        return getTeam(team.getName());
    }
    public Game saveGame(Game game){
        ContentValues contentValues = new ContentValues();

        contentValues.put(GAMEhometeam      , game.getHomeTeamID());
        contentValues.put(GAMEawayteam      , game.getAwayTeamID());
        contentValues.put(GAMEdescription   , game.getDescription());
        contentValues.put(GAMEname          , game.getName());

        SQLiteDatabase db = getWritableDatabase();
        long gameid = db.insert(GAMETABLENAME,null,contentValues);
        db.close();

        return getGame(gameid);
    }
    public PitchingStats savePitchingStats(PitchingStats pitchingStats){
        ContentValues contentValues = new ContentValues();

        contentValues.put(PITCHINGSTATSgameid           , pitchingStats.getGameID());
        contentValues.put(PITCHINGSTATSteamid           , pitchingStats.getTeamID());
        contentValues.put(PITCHINGSTATSplayerid         , pitchingStats.getPlayerID());
        contentValues.put(PITCHINGSTATSpitches          , pitchingStats.getPitchs());
        contentValues.put(PITCHINGSTATSballs            , pitchingStats.getBalls());
        contentValues.put(PITCHINGSTATSstrikes          , pitchingStats.getStrikes());
        contentValues.put(PITCHINGSTATShits             , pitchingStats.getHits());
        contentValues.put(PITCHINGSTATSstrikeouts       , pitchingStats.getStrikouts());
        contentValues.put(PITCHINGSTATSputouts          , pitchingStats.getPutouts());
        contentValues.put(PITCHINGSTATSwalks            , pitchingStats.getWalks());
        contentValues.put(PITCHINGSTATShitsbypitch      , pitchingStats.getHitsByPitch());
        contentValues.put(PITCHINGSTATShomerun          , pitchingStats.getHomeRuns());
        contentValues.put(PITCHINGSTATSfoulballs        , pitchingStats.getFoulBalls());
        contentValues.put(PITCHINGSTATSgroundouts       , pitchingStats.getGroundOuts());
        contentValues.put(PITCHINGSTATSgroundhits       , pitchingStats.getGroundHits());
        contentValues.put(PITCHINGSTATSlineouts         , pitchingStats.getLineOuts());
        contentValues.put(PITCHINGSTATSlinehits         , pitchingStats.getLineHits());
        contentValues.put(PITCHINGSTATSflyouts          , pitchingStats.getFlyOut());
        contentValues.put(PITCHINGSTATSlooking          , pitchingStats.getLooking());
        contentValues.put(PITCHINGSTATSgaper            , pitchingStats.getGapper());
        contentValues.put(PITCHINGSTATSoutspitched      , pitchingStats.getOutsPitched());
        contentValues.put(PITCHINGSTATSuntouched        , pitchingStats.getUntouched());

        SQLiteDatabase db = getWritableDatabase();
        long pitchingStatsID = db.insert(PITCHINGSTATSTABLENAME,null,contentValues);
        db.close();

        pitchingStats.setPitchingStatsID(pitchingStatsID);

        return pitchingStats;
    }
    public long saveAtBat(AtBats ab){
        ContentValues contentValues = new ContentValues();

        contentValues.put(ATBATplayeratbatid        , ab.getPlayerAtBatId());
        contentValues.put(ATBATgameid               , ab.getGameID());
        contentValues.put(ATBATinnningid            , ab.getInningID());
        contentValues.put(ATBATpitcherid            , ab.getPitcherID());
        contentValues.put(ATBATballs                , ab.getBalls());
        contentValues.put(ATBATstrikes              , ab.getStrikes());
        contentValues.put(ATBATpitches              , ab.getPitches());
        contentValues.put(ATBAToutcome              , ab.getOutcome());

        SQLiteDatabase db = getWritableDatabase();
        long atbatid = db.insert(ATBATTableName,null,contentValues);
        db.close();

        return atbatid;
    }
    //--------------------getting from database-------------------------------------
    public Player getPlayer(String name){
        String Query = "SELECT * "+
                "FROM " + PLAYERINFOTABLEBNAME +" "+
                "WHERE "+ PLAYERINFOname +" = \"" + name +"\" ;";

        Player player = new Player();
        Log.d("Database comand", Query);
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery(Query, null);

        cursor.moveToFirst();

        player.setPlayerID(cursor.getLong(cursor.getColumnIndex(PLAYERINFOid)));
        player.setName(cursor.getString(cursor.getColumnIndex(PLAYERINFOname)));

        db.close();
        return player;
    }

    public Player getPlayer(long id){
        String Query = "SELECT * "+
                "FROM " + PLAYERINFOTABLEBNAME +" "+
                "WHERE "+ PLAYERINFOid +" = " + id +" ;";

        Player player = new Player();
        Log.d("Database comand", Query);
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery(Query, null);

        cursor.moveToFirst();

        player.setPlayerID(cursor.getLong(cursor.getColumnIndex(PLAYERINFOid)));
        player.setName(cursor.getString(cursor.getColumnIndex(PLAYERINFOname)));

        db.close();
        return player;
    }

    public ArrayList<String> getTeamNames(){
        String Query = "SELECT * "+
                "FROM " + TEAMTABLENAME +" "+
                "ORDER BY "+ TEAMname +" ;";

        ArrayList<String> names = new ArrayList<>();

        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery(Query, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            names.add(cursor.getString(cursor.getColumnIndex(TEAMname)));
            cursor.moveToNext();
        }
        db.close();
        return names;
    }
    public ArrayList<String> getAllPlayersNames() {
        String Query = "SELECT " + PLAYERINFOname + " "+
                "FROM " + PLAYERINFOTABLEBNAME +" "+
                "ORDER BY "+ PLAYERINFOname +";";

        ArrayList<String> names = new ArrayList<>();

        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery(Query, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            names.add(cursor.getString(cursor.getColumnIndex(PLAYERINFOname)));
            cursor.moveToNext();
        }
        db.close();
        return names;
    }
    public Team getTeam(String name){
        String Query = "SELECT * "+
                "FROM " + TEAMTABLENAME +" "+
                "WHERE "+ TEAMname + " = \"" + name +"\" ;";

        Team team = new Team();

        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery(Query, null);

        cursor.moveToFirst();

        team.setTeamid( cursor.getLong(cursor.getColumnIndex(TEAMid)));
        team.setName(cursor.getString(cursor.getColumnIndex(TEAMname)));
        team.setLocation(cursor.getString(cursor.getColumnIndex(TEAMloaction)));
        team.setMascot(cursor.getString(cursor.getColumnIndex(TEAMmascot)));

        db.close();
        return team;
    }
    public Team getTeam(long id){
        String Query = "SELECT * "+
                "FROM " + TEAMTABLENAME +" "+
                "WHERE "+ TEAMid + " = " + id +" ;";

        Team team = new Team();

        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery(Query, null);

        cursor.moveToFirst();

        team.setTeamid(cursor.getLong(cursor.getColumnIndex(TEAMid)));
        team.setName(cursor.getString(cursor.getColumnIndex(TEAMname)));
        team.setLocation(cursor.getString(cursor.getColumnIndex(TEAMloaction)));
        team.setMascot(cursor.getString(cursor.getColumnIndex(TEAMmascot)));

        db.close();
        return team;
    }
    public Game getGame(long id){
        String Query = "SELECT * "+
                "FROM " + GAMETABLENAME +" "+
                "WHERE "+ GAMEid + " = " + id +" ;";

        Game game = new Game();

        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery(Query, null);

        cursor.moveToFirst();

        game.setGameID(cursor.getLong(cursor.getColumnIndex(GAMEid)));
        game.setHomeTeamID(cursor.getInt(cursor.getColumnIndex(GAMEhometeam)));
        game.setAwayTeamID(cursor.getInt(cursor.getColumnIndex(GAMEawayteam)));
        game.setDescription(cursor.getString(cursor.getColumnIndex(GAMEdescription)));
        game.setName(cursor.getString(cursor.getColumnIndex(GAMEname)));

        db.close();
        return game;
    }
    public Game getGame(String gameName){
        String Query = "SELECT * "+
                "FROM " + GAMETABLENAME +" "+
                "WHERE "+ GAMEname + " = \"" + gameName +"\" ;";

        Game game = new Game();

        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery(Query, null);

        cursor.moveToFirst();

        game.setGameID(cursor.getLong(cursor.getColumnIndex(GAMEid)));
        game.setHomeTeamID(cursor.getInt(cursor.getColumnIndex(GAMEhometeam)));
        game.setAwayTeamID(cursor.getInt(cursor.getColumnIndex(GAMEawayteam)));
        game.setDescription(cursor.getString(cursor.getColumnIndex(GAMEdescription)));
        game.setName(cursor.getString(cursor.getColumnIndex(GAMEname)));

        db.close();
        return game;
    }

    public ArrayList<AtBats> getAllABsForPlayer(long id){
        ArrayList<AtBats> list = new ArrayList<>();

        String query = "SELECT * "+
                "FROM "+ ATBATTableName +" "+
                "WHERE "+ATBATid+" = "+id+ ";";

        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            AtBats a = new AtBats();
            a.setAtBatID(cursor.getLong(cursor.getColumnIndex(ATBATplayeratbatid)));
            a.setInningID(cursor.getLong(cursor.getColumnIndex(ATBATinnningid)));
            a.setBalls(cursor.getInt(cursor.getColumnIndex(ATBATballs)));
            a.setStrikes(cursor.getInt(cursor.getColumnIndex(ATBATstrikes)));
            a.setOutcome(cursor.getInt(cursor.getColumnIndex(ATBAToutcome)));
            cursor.moveToNext();
        }
        db.close();
        return list;
    }

    public ArrayList<String> getallBattersNames(){
        ArrayList<String> list = new ArrayList<>();

        String query = "SELECT * "+
                "FROM "+ ATBATTableName +";";

        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            String id = cursor.getString(cursor.getColumnIndex(ATBATplayeratbatid));
            if(!list.contains(id)){
                list.add(id);
            }
            cursor.moveToNext();
        }
        db.close();
        ArrayList<String> battersNames = new ArrayList<>();
        for (String id:list){
            battersNames.add(getPlayer(Long.valueOf(id)).getName());
        }
        return battersNames;
    }

    public ArrayList<String> getAllGamesNamesForPitcher(long id){
        ArrayList<String> gameNames = new ArrayList<String>();

        String Query = "SELECT "+PITCHINGSTATSgameid+" "+
                "FROM " + PITCHINGSTATSTABLENAME +" "+
                "WHERE " +PITCHINGSTATSplayerid + " = " + id + " ;";

        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery(Query, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            String name = cursor.getString(cursor.getColumnIndex(PITCHINGSTATSgameid));
            if(!gameNames.contains(name)){
                gameNames.add(name);
            }
            cursor.moveToNext();
        }
        db.close();
        return gameNames;
    }

    public ArrayList<String> getAllGameNames(){
        String Query = "SELECT "+GAMEname+" "+
                "FROM " + GAMETABLENAME +" ;";

        ArrayList<String> names = new ArrayList<>();

        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery(Query, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            names.add(cursor.getString(cursor.getColumnIndex(GAMEname)));
            cursor.moveToNext();
        }
        db.close();
        return names;
    }

    public ArrayList<String > getAllTeamNames(){
        ArrayList<String> names = new ArrayList<>();

        String query = "SELECT "+TEAMname + " " +
                "FROM "+TEAMTABLENAME+";";

        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery(query,null);

        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            names.add(
                    cursor.getString(cursor.getColumnIndex(TEAMname))
            );
            cursor.moveToNext();
        }
        db.close();

        return names;
    }
    public ArrayList<Game> getAllGames(){
        ArrayList<Game> games = new ArrayList<>();

        String query = "SELECT "+ "* " +
                "FROM "+GAMETABLENAME+";";

        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery(query,null);

        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            Game game = new Game();
            game.setGameID(cursor.getLong(cursor.getColumnIndex(GAMEid)));
            game.setHomeTeamID(cursor.getInt(cursor.getColumnIndex(GAMEhometeam)));
            game.setAwayTeamID(cursor.getInt(cursor.getColumnIndex(GAMEawayteam)));
            game.setDescription(cursor.getString(cursor.getColumnIndex(GAMEdescription)));
            game.setName(cursor.getString(cursor.getColumnIndex(GAMEname)));
            games.add(game);
            cursor.moveToNext();
        }
        db.close();

        return games;
    }

    /*public ArrayList<AtBats> getAllAtBatsForPlayer(long id){
        String query = "SELECT * "+
                "FROM "+ATBATTableName + " "+
                "WHERE "+ATBATplayeratbatid +" = "+id+";";

        ArrayList<AtBats> list = new ArrayList<>();

        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery(query,null);

        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            AtBats a = new AtBats();

            a.setAtBatID(       cursor.getLong(cursor.getColumnIndex(    ATBATid)));
            a.setPlayerAtBatId(      cursor.getLong(cursor.getColumnIndex(    ATBATplayeratbatid)));
            a.setGameID(        cursor.getLong(cursor.getColumnIndex(    ATBATgameid       )));
            a.setInningID(     cursor.getLong(cursor.getColumnIndex(    ATBATinnningid    )));
            a.setPitcherID( cursor.getLong(cursor.getColumnIndex(    ATBATpitcherid    )));
            a.setBalls(       cursor.getInt(cursor.getColumnIndex(    ATBATballs        )));
            a.setStrikes(         cursor.getInt(cursor.getColumnIndex(    ATBATstrikes      )));
            a.setPitches(       cursor.getInt(cursor.getColumnIndex(    ATBATpitches      )));
            a.setOutcome(       cursor.getInt(cursor.getColumnIndex(    ATBAToutcome      )));
            list.add(a);
            cursor.moveToNext();
        }
        db.close();
        return list;
    }*/

    public ArrayList<PitchingStats> getAllPitchingStatsForPlayer(long id){
        String Query = "SELECT * "+
                "FROM " + PITCHINGSTATSTABLENAME +" "+
                "WHERE "+ PITCHINGSTATSplayerid + " = " + id +" ;";


        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery(Query, null);

        ArrayList<PitchingStats> list = new ArrayList<>();

        cursor.moveToFirst();
        while(!cursor.isAfterLast()) {
            PitchingStats pitchingStats = new PitchingStats();

            pitchingStats.setPitchingStatsID(cursor.getInt(cursor.getColumnIndex(PITCHINGSTATSpitchingstatsid )));
            pitchingStats.setGameID(cursor.getLong(cursor.getColumnIndex(PITCHINGSTATSgameid          )));
            pitchingStats.setTeamID(cursor.getLong(cursor.getColumnIndex(PITCHINGSTATSteamid          )));
            pitchingStats.setPlayerID(cursor.getLong(cursor.getColumnIndex(PITCHINGSTATSplayerid        )));
            pitchingStats.setPitchs(cursor.getInt(cursor.getColumnIndex(PITCHINGSTATSpitches         )));
            pitchingStats.setBalls(cursor.getInt(cursor.getColumnIndex(PITCHINGSTATSballs           )));
            pitchingStats.setStrikes(cursor.getInt(cursor.getColumnIndex(PITCHINGSTATSstrikes         )));
            pitchingStats.setHits(cursor.getInt(cursor.getColumnIndex(PITCHINGSTATShits            )));
            pitchingStats.setStrikouts(cursor.getInt(cursor.getColumnIndex(PITCHINGSTATSstrikeouts      )));
            pitchingStats.setPutouts(cursor.getInt(cursor.getColumnIndex(PITCHINGSTATSputouts         )));
            pitchingStats.setWalks(cursor.getInt(cursor.getColumnIndex(PITCHINGSTATSwalks           )));
            pitchingStats.setHitsByPitch(cursor.getInt(cursor.getColumnIndex(PITCHINGSTATShitsbypitch     )));
            pitchingStats.setHomeRuns(cursor.getInt(cursor.getColumnIndex(PITCHINGSTATShomerun         )));
            pitchingStats.setFoulBalls(cursor.getInt(cursor.getColumnIndex(PITCHINGSTATSfoulballs       )));
            pitchingStats.setGroundOuts(cursor.getInt(cursor.getColumnIndex(PITCHINGSTATSgroundouts      )));
            pitchingStats.setGroundHits(cursor.getInt(cursor.getColumnIndex(PITCHINGSTATSgroundhits      )));
            pitchingStats.setLineOuts(cursor.getInt(cursor.getColumnIndex(PITCHINGSTATSlineouts        )));
            pitchingStats.setLineHits(cursor.getInt(cursor.getColumnIndex(PITCHINGSTATSlinehits        )));
            pitchingStats.setFlyOut(cursor.getInt(cursor.getColumnIndex(PITCHINGSTATSflyouts         )));
            pitchingStats.setLooking(cursor.getInt(cursor.getColumnIndex(PITCHINGSTATSlooking         )));
            pitchingStats.setGapper(cursor.getInt(cursor.getColumnIndex(PITCHINGSTATSgaper           )));
            pitchingStats.setGapper(cursor.getInt(cursor.getColumnIndex(PITCHINGSTATSoutspitched           )));
            pitchingStats.setUntouched(cursor.getInt(cursor.getColumnIndex(PITCHINGSTATSuntouched       )));

            cursor.moveToNext();
            list.add(pitchingStats);
        }
        db.close();
        return list;
    }
}
