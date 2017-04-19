package com.example.crosbylanham.baseballstatscollector;

import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by crosbylanham on 1/30/17.
 */

public class PitchingStats implements Serializable {
    String pitchingStatsID;
    String gameID;
    String teamID;
    String playerID;
    int pitches;
    int balls;
    int strikes;
    int hits;
    int StrikeOuts;
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
    int outsPitched;   //if there is an out we need to record this
    int runs;
    int earnedRuns;

    public PitchingStats() {
    }

    //------------------------------------------------------------------------
    public void swingAndAMiss() {
        pitches++;
        untouched++;
        strikes++;
    }

    public void foul() {
        pitches++;
        strikes++;
        foulBalls++;
    }

    public void ball() {
        pitches++;
        balls++;
    }

    public void looking() {
        pitches++;
        strikes++;
        untouched++;
    }

    public void grounderhit() {
        pitches++;
        hits++;
        groundHits++;
    }

    public void grounderout() {
        pitches++;
        hits++;
        groundOuts++;
        outsPitched++;
    }

    public void linerhit() {
        pitches++;
        hits++;
        lineHits++;
    }

    public void linerout() {
        pitches++;
        hits++;
        lineOuts++;
        outsPitched++;
    }

    public void flyout() {
        pitches++;
        hits++;
        flyOut++;
        outsPitched++;
    }

    public void gapper() {
        pitches++;
        hits++;
        gapper++;
    }

    public void homerun() {
        pitches++;
        hits++;
        homeRuns++;
        runs++;
        earnedRuns++;
    }

    public void hitByPitch() {
        pitches++;
        hitsByPitch++;
    }

    public void hitStrikeout() {

        StrikeOuts++;
        outsPitched++;
    }

    public void hitruns() {
        runs++;
    }
    //------------------------------------------------------------------------


    public double getERA(ArrayList<PitchingStats> list){
        Log.d("Get ERA ","geting total hits "+getTotalHits(list));
        Log.d("Get ERA ","geting total outs "+getTotalOuts(list));
        Log.d("Get ERA ","geting total innings oitched "+(getTotalOuts(list)/3.0));
        return (9*getTotalHits(list))/(getTotalOuts(list)/3.0);
    }
    public double getWHIP(ArrayList<PitchingStats> list){
        return getTotalWalks(list)+getTotalHits(list)/getTotalOuts(list)/3.0;
    }

    public int getTotalOuts(ArrayList<PitchingStats> list) {
        int totalouts = 0;
        for (PitchingStats game : list) {
            totalouts += game.getOutsPitched();
        }
        return totalouts;
    }

    public int getTotalShutOuts(ArrayList<PitchingStats> list){
        int shutouts=0;
        for(PitchingStats x:list){
            if(x.getRuns() == 0){shutouts++;}
        }
        return shutouts;
    }

    public int getTotalHits(ArrayList<PitchingStats> list) {
        int totalhits = 0;
        for (PitchingStats game : list) {
            totalhits += game.getHits();
        }
        return totalhits;
    }

    public int getTotalPitches(ArrayList<PitchingStats> list) {
        int totalPitches = 0;
        for (PitchingStats game : list) {
            totalPitches += game.getPitches();
        }
        return totalPitches;
    }

    public int getTotalBB(ArrayList<PitchingStats> list) {
        int totalBB = 0;
        for (PitchingStats game : list) {
            totalBB += game.getWalks();
        }
        return totalBB;
    }

    public int getTotalSO(ArrayList<PitchingStats> list) {
        int totalSO = 0;
        for (PitchingStats game : list) {
            totalSO += game.getStrikeOuts();
        }
        return totalSO;
    }

    public int getTotalHomeRuns(ArrayList<PitchingStats> list) {
        int totalHomeRunes = 0;
        for (PitchingStats game : list) {
            totalHomeRunes += game.getHomeRuns();
        }
        return totalHomeRunes;
    }

    public int getTotalWalks(ArrayList<PitchingStats> list) {
        int totalWalks = 0;
        for (PitchingStats game : list) {
            totalWalks += game.getWalks();
        }
        return totalWalks;
    }

    public int getTotalHBP(ArrayList<PitchingStats> list) {
        int totalHitByPitches = 0;
        for (PitchingStats game : list) {
            totalHitByPitches += game.getHitsByPitch();
        }
        return totalHitByPitches;
    }

    public int getTotalRuns(ArrayList<PitchingStats> list) {
        int totalruns = 0;
        for (PitchingStats game : list) {
            totalruns += game.getRuns();
        }
        return totalruns;
    }
    //---------------------------------------------------------------------------------------


    public int getRuns() {
        return runs;
    }

    public void setRuns(int runs) {
        this.runs = runs;
    }

    public String getPitchingStatsID() {
        return pitchingStatsID;
    }

    public void setPitchingStatsID(String pitchingStatsID) {
        this.pitchingStatsID = pitchingStatsID;
    }

    public String getGameID() {
        return gameID;
    }

    public void setGameID(String gameID) {
        this.gameID = gameID;
    }

    public String getPlayerID() {
        return playerID;
    }

    public void setPlayerID(String playerID) {
        this.playerID = playerID;
    }

    public int getPitches() {
        return pitches;
    }

    public void setPitches(int pitchs) {
        this.pitches = pitchs;
    }

    public int getOutsPitched() {
        return outsPitched;
    }

    public void setOutsPitched(int outsPitched) {
        this.outsPitched = outsPitched;
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

    public int getStrikeOuts() {
        return StrikeOuts;
    }

    public void setStrikeOuts(int strikeOuts) {
        this.StrikeOuts = strikeOuts;
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

    public int getEarnedRuns() {
        return earnedRuns;
    }

    public String getTeamID() {
        return teamID;
    }

    public void setTeamID(String teamID) {
        this.teamID = teamID;
    }
}
