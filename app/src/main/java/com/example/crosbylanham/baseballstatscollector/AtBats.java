package com.example.crosbylanham.baseballstatscollector;

import android.util.Log;

import java.util.ArrayList;

public class AtBats {
    String atBatID;
    String inningID;
    String gameID;
    String pitcherID;
    String playerAtBatId;


    String description;
    int pitches;
    int balls;
    int strikes;
    int outcome;


    public AtBats(){}
    //-------------------------------helper methods for collecting stats ----------------------
    public int howManyOuts(){
        if(outcome == AtBatInformation.FLYOUT || outcome == AtBatInformation.STRIKEOUT || outcome == AtBatInformation.GROUNDOUT||
                outcome == AtBatInformation.FOUL_OUT || outcome == AtBatInformation.FIELDERS_CHOICE || outcome == AtBatInformation.LINEOUT){
            return 1;
        }else{
            return 0;
        }
    }
    public int getAllAtBats(ArrayList<AtBats> allatbats){
        int total = allatbats.size();
        for (AtBats x:allatbats) {
            if(x.getOutcome() == AtBatInformation.WALK||x.getOutcome() == AtBatInformation.HIT_BY_PITCH||
                    x.getOutcome() == AtBatInformation.ADVANCERUNNER){
                total--;
            }
        }
        return total;
    }
    public int getAllHits(ArrayList<AtBats> allatbats){
        int hits = 0 ;
        Log.d("Total At Bats " , String.valueOf(allatbats.size()));
        for (AtBats x:allatbats) {
            if(x.getOutcome() == AtBatInformation.SINGLE || x.getOutcome() == AtBatInformation.DOUBLE ||
                    x.getOutcome() == AtBatInformation.TRIPLE || x.getOutcome() == AtBatInformation.HOMERUN){
                Log.d("Out come ","we did get a hit");
                hits++;
            }
            Log.d("outcome was ", String.valueOf(x.getOutcome()));
        }
        return hits;
    }
    public int getAllStrikeouts(ArrayList<AtBats> allatbats){
        int strikeouts = 0 ;
        for (AtBats x:allatbats) {
            if(x.getOutcome() == AtBatInformation.STRIKEOUT){
                strikeouts++;
            }
        }
        return strikeouts;
    }
    public int getAllHomeruns(ArrayList<AtBats> allatbats){
        int strikeouts = 0 ;
        for (AtBats x:allatbats) {
            if(x.getOutcome() == AtBatInformation.HOMERUN){
                strikeouts++;
            }
        }
        return strikeouts;
    }
    public int getAllHBP(ArrayList<AtBats> allatbats){
        int hitByPitch = 0 ;
        for (AtBats x:allatbats) {
            if(x.getOutcome() == AtBatInformation.HIT_BY_PITCH){
                hitByPitch++;
            }
        }
        return hitByPitch;
    }
    public int getAllBaseOnBalls(ArrayList<AtBats> allatbats){
        int walks = 0 ;
        for (AtBats x:allatbats) {
            if(x.getOutcome() == AtBatInformation.WALK){
                walks++;
            }
        }
        return walks;
    }
    //--------------------------getters and setters----------------
    public String getGameID() {
        return gameID;
    }
    public String getDescription() {return description;}
    public void setDescription(String description) {this.description = description;}

    public void setGameID(String gameID) {
        this.gameID = gameID;
    }

    public String getPitcherID() {
        return pitcherID;
    }

    public void setPitcherID(String pitcherID) {
        this.pitcherID = pitcherID;
    }

    public int getPitches() {
        return pitches;
    }

    public void setPitches(int pitches) {
        this.pitches = pitches;
    }
    public String getAtBatID() {
        return atBatID;
    }

    public void setAtBatID(String atBatID) {
        this.atBatID = atBatID;
    }

    public String getInningID() {
        return inningID;
    }

    public void setInningID(String inningID) {
        this.inningID = inningID;
    }

    public String getPlayerAtBatId() {
        return playerAtBatId;
    }

    public void setPlayerAtBatId(String playerAtBatId) {
        this.playerAtBatId = playerAtBatId;
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

    public int getOutcome() {
        return outcome;
    }

    public void setOutcome(int outcome) {
        this.outcome = outcome;
        this.description = AtBatInformation.outcomePositions[outcome];
    }

}
