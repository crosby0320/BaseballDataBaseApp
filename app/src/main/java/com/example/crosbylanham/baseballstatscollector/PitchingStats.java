package com.example.crosbylanham.baseballstatscollector;

import java.io.Serializable;

/**
 * Created by crosbylanham on 1/30/17.
 */

public class PitchingStats implements Serializable{
    long PitchingStatsID;
    long GameID;
    long TeamID;
    long playerID;
    int pitchs;
    int balls;
    int strikes;
    int hits;
    int strikouts;
    int putouts;
    int walks;
    int hitsByPitch;
    int homeRuns;
    int foulBalls;
    int groundOuts;
    int groundHits;
    int lineOuts;
    int lineHits;
    int flyOut;
    int looking;
    int gapper;
    int untouched;
    public PitchingStats() {
    }

    //------------------------------------------------------------------------
    public void swingAndAMiss(){pitchs++;untouched++;strikes++;}
    public void foul(){pitchs++;strikes++;foulBalls++;}
    public void ball(){pitchs++;balls++;}
    public void looking(){pitchs++;strikes++;untouched++;}
    public void grounderhit(){pitchs++;hits++;groundHits++;}
    public void grounderout(){pitchs++;hits++;groundOuts++;}
    public void linerhit(){pitchs++;hits++;lineHits++;}
    public void linerout(){pitchs++;hits++;lineOuts++;}
    public void flyout(){pitchs++;hits++;flyOut++;}
    public void gapper(){pitchs++;hits++;gapper++;}
    public void homerun(){pitchs++;hits++;homeRuns++;}
    public void hitByPitch(){pitchs++;hitsByPitch++;}
    //------------------------------------------------------------------------

    public long getPitchingStatsID() {
        return PitchingStatsID;
    }

    public void setPitchingStatsID(long pitchingStatsID) {
        PitchingStatsID = pitchingStatsID;
    }

    public long getGameID() {
        return GameID;
    }

    public void setGameID(long gameID) {
        GameID = gameID;
    }

    public long getPlayerID() {
        return playerID;
    }

    public void setPlayerID(long playerID) {
        this.playerID = playerID;
    }

    public int getPitchs() {
        return pitchs;
    }

    public void setPitchs(int pitchs) {
        this.pitchs = pitchs;
    }

    public int getBalls() {
        return balls;
    }

    public void setBalls(int balls) {
        this.balls = balls;
    }

    public int getStrikes() {
        return strikes;
    }

    public void setStrikes(int strikes) {
        this.strikes = strikes;
    }

    public int getHits() {
        return hits;
    }

    public void setHits(int hits) {
        this.hits = hits;
    }

    public int getStrikouts() {
        return strikouts;
    }

    public void setStrikouts(int strikouts) {
        this.strikouts = strikouts;
    }

    public int getPutouts() {
        return putouts;
    }

    public void setPutouts(int putouts) {
        this.putouts = putouts;
    }

    public int getWalks() {
        return walks;
    }

    public void setWalks(int walks) {
        this.walks = walks;
    }

    public int getHitsByPitch() {
        return hitsByPitch;
    }

    public int getLooking() {
        return looking;
    }

    public void setLooking(int looking) {
        this.looking = looking;
    }

    public void setHitsByPitch(int hitsByPitch) {
        this.hitsByPitch = hitsByPitch;
    }

    public int getHomeRuns() {
        return homeRuns;
    }

    public void setHomeRuns(int homeRuns) {
        this.homeRuns = homeRuns;
    }

    public int getFoulBalls() {
        return foulBalls;
    }

    public void setFoulBalls(int foulBalls) {
        this.foulBalls = foulBalls;
    }

    public int getGroundOuts() {
        return groundOuts;
    }

    public void setGroundOuts(int groundOuts) {
        this.groundOuts = groundOuts;
    }

    public int getGroundHits() {
        return groundHits;
    }

    public void setGroundHits(int groundHits) {
        this.groundHits = groundHits;
    }

    public int getLineOuts() {
        return lineOuts;
    }

    public void setLineOuts(int lineOuts) {
        this.lineOuts = lineOuts;
    }

    public int getLineHits() {
        return lineHits;
    }

    public void setLineHits(int lineHits) {
        this.lineHits = lineHits;
    }

    public int getFlyOut() {
        return flyOut;
    }

    public void setFlyOut(int flyOut) {
        this.flyOut = flyOut;
    }

    public int getGapper() {
        return gapper;
    }

    public void setGapper(int gapper) {
        this.gapper = gapper;
    }

    public int getUntouched() {
        return untouched;
    }

    public void setUntouched(int untouched) {
        this.untouched = untouched;
    }

    public long getTeamID() {return TeamID;}

    public void setTeamID(long teamID) {
        TeamID = teamID;
    }
}
