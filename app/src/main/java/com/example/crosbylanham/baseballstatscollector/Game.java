package com.example.crosbylanham.baseballstatscollector;

/**
 * Created by crosbylanham on 1/30/17.
 */

public class Game {
    long GameID;
    int HomeTeamID;
    int AwayTeamID;
    String Description;
    String name;

    public Game() {

    }

    public long getGameID() {
        return GameID;
    }

    public void setGameID(long gameID) {
        GameID = gameID;
    }

    public int getHomeTeamID() {
        return HomeTeamID;
    }

    public void setHomeTeamID(int homeTeamID) {
        HomeTeamID = homeTeamID;
    }

    public int getAwayTeamID() {
        return AwayTeamID;
    }

    public void setAwayTeamID(int awayTeamID) {
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
