package com.example.crosbylanham.baseballstatscollector;

/**
 * Created by crosbylanham on 1/30/17.
 */

public class Game {
    String gameID;
    String homeTeamID;
    String awayTeamID;
    String description;
    String name;

    public Game() {
        homeTeamID = "";
        awayTeamID = "";
        description = "";
        name = "";
    }

    public void generateName(String awayTeamName, String homeTeamName){
        name = "" + awayTeamName + " Vs. " + homeTeamName + " "+
                new Datefunctions().getCurrentTimeAndDate();
    }

    public String getGameID() {
        return gameID;
    }

    public void setGameID(String gameID) {
        this.gameID = gameID;
    }

    public String getHomeTeamID() {
        return homeTeamID;
    }

    public void setHomeTeamID(String homeTeamID) {
        this.homeTeamID = homeTeamID;}

    public String getAwayTeamID() {
        return awayTeamID;
    }

    public void setAwayTeamID(String awayTeamID) {
        this.awayTeamID = awayTeamID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
