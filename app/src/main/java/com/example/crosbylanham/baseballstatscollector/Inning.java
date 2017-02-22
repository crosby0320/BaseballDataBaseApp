package com.example.crosbylanham.baseballstatscollector;

/**
 * Created by crosbylanham on 1/30/17.
 */

public class Inning {
    long inningID;
    long GameID;
    int inningNumber;
    boolean home;
    int runs;

    public Inning() {
    }

    public void setInningID(long inningID) {
        this.inningID = inningID;
    }

    public int getRuns() {
        return runs;
    }

    public void setRuns(int runs) {
        this.runs = runs;
    }

    public long getInningID() {
        return inningID;
    }

    public void setInningID(int inningID) {
        this.inningID = inningID;
    }

    public long getGameID() {
        return GameID;
    }

    public void setGameID(long gameID) {
        GameID = gameID;
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
