package com.example.crosbylanham.baseballstatscollector;

/**
 * Created by crosbylanham on 1/30/17.
 */

public class Game {
    long GameID;
    long HomeTeamID;
    long AwayTeamID;
    String Description;
    String name;

    public Game() {

    }

    public void generateName(String awayTeamName, String homeTeamName){
        name = "" + awayTeamName + " Vs. " + homeTeamName + " "+
                new Datefunctions().getCurrentTimeAndDate();
    }

    public long getGameID() {
        return GameID;
    }

    public void setGameID(long gameID) {
        GameID = gameID;
    }

    public long getHomeTeamID() {
        return HomeTeamID;
    }

    public void setHomeTeamID(long homeTeamID) {HomeTeamID = homeTeamID;}

    public long getAwayTeamID() {
        return AwayTeamID;
    }

    public void setAwayTeamID(long awayTeamID) {
        AwayTeamID = awayTeamID;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
