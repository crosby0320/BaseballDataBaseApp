package com.example.crosbylanham.baseballstatscollector;

/**
 * Created by crosbylanham on 1/30/17.
 */

public class Inning {
    String inningID;
    String gameID;
    int inningNumber;
    boolean home;
    int runs;

    public Inning() {
    }

    public void setInningID(String inningID) {
        this.inningID = inningID;
    }

    public int getRuns() {
        return runs;
    }

    public void setRuns(int runs) {
        this.runs = runs;
    }

    public String getInningID() {
        return inningID;
    }

    public String getGameID() {
        return gameID;
    }

    public void setGameID(String gameID) {
        this.gameID = gameID;
    }

    public int getInningNumber() {
        return inningNumber;
    }

    public void setInningNumber(int inningNumber) {
        this.inningNumber = inningNumber;
    }

    public boolean isHome() {
        return home;
    }

    public void setHome(boolean home) {
        this.home = home;
    }
}
