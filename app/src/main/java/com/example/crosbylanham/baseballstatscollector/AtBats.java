package com.example.crosbylanham.baseballstatscollector;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by crosbylanham on 1/30/17.
 */

public class AtBats {
    long atBatID;
    long inningID;
    long gameID;
    long pitcherID;
    long PlayerAtBatId;
    int pitches;
    int Balls;
    int Strikes;
    int Outcome;


    public AtBats(){

    }
    //-------------------------------helper methods for collecting stats
    public int howManyOuts(){
        if(Outcome == FLYOUT || Outcome == STRIKEOUT ||Outcome == GROUNDOUT||
                Outcome == FOUL_OUT || Outcome == FIELDERS_CHOICE || Outcome == LINEOUT){
            return 1;
        }else{
            return 0;
        }
    }
    public int getAllAtBats(ArrayList<AtBats> allatbats){
        int total = allatbats.size();
        for (AtBats x:allatbats) {
            if(x.getOutcome() == AtBats.WALK||x.getOutcome() == AtBats.HIT_BY_PITCH||
                    x.getOutcome() == AtBats.ADVANCERUNNER){
                total--;
            }
        }
        return total;
    }
    public int getallhits(ArrayList<AtBats> allatbats){
        int hits = 0 ;
        for (AtBats x:allatbats) {
            if(x.getOutcome() == AtBats.SINGLE || x.getOutcome() == AtBats.DOUBLE
                    ||x.getOutcome() == AtBats.TRIPLE || x.getOutcome() == AtBats.HOMERUN){
                hits++;
            }
        }
        return hits;
    }
    public int getAllStrikeouts(ArrayList<AtBats> allatbats){
        int strikeouts = 0 ;
        for (AtBats x:allatbats) {
            if(x.getOutcome() == AtBats.STRIKEOUT){
                strikeouts++;
            }
        }
        return strikeouts;
    }
    public int getAllHomeruns(ArrayList<AtBats> allatbats){
        int strikeouts = 0 ;
        for (AtBats x:allatbats) {
            if(x.getOutcome() == AtBats.HOMERUN){
                strikeouts++;
            }
        }
        return strikeouts;
    }
    public int getAllHBP(ArrayList<AtBats> allatbats){
        int hitByPitch = 0 ;
        for (AtBats x:allatbats) {
            if(x.getOutcome() == AtBats.HIT_BY_PITCH){
                hitByPitch++;
            }
        }
        return hitByPitch;
    }
    public int getAllBaseOnBalls(ArrayList<AtBats> allatbats){
        int walks = 0 ;
        for (AtBats x:allatbats) {
            if(x.getOutcome() == AtBats.WALK){
                walks++;
            }
        }
        return walks;
    }
    //--------------------------getters and setters----------------
    public long getGameID() {
        return gameID;
    }

    public void setGameID(long gameID) {
        this.gameID = gameID;
    }

    public long getPitcherID() {
        return pitcherID;
    }

    public void setPitcherID(long pitcherID) {
        this.pitcherID = pitcherID;
    }

    public int getPitches() {
        return pitches;
    }

    public void setPitches(int pitches) {
        this.pitches = pitches;
    }
    public long getAtBatID() {
        return atBatID;
    }

    public void setAtBatID(long atBatID) {
        this.atBatID = atBatID;
    }

    public long getInningID() {
        return inningID;
    }

    public void setInningID(long inningID) {
        this.inningID = inningID;
    }

    public long getPlayerAtBatId() {
        return PlayerAtBatId;
    }

    public void setPlayerAtBatId(long playerAtBatId) {
        PlayerAtBatId = playerAtBatId;
    }

    public int getBalls() {
        return Balls;
    }

    public void setBalls(int balls) {
        Balls = balls;
    }

    public int getStrikes() {
        return Strikes;
    }

    public void setStrikes(int strikes) {
        Strikes = strikes;
    }

    public int getOutcome() {
        return Outcome;
    }

    public void setOutcome(int outcome) {
        Outcome = outcome;
    }

    public String[] getOutcomePositions() {return outcomePositions;}
    public final String[] outcomePositions = {
            "Single", "double","triple","Homerun","Fly Out",
            "StrikeOut","WALK","Hit By Pitch","Error","Ground Out",
            "Foul Out","","Fielder Choice","Line Out","Advanced Runner"
    };

    public final static int SINGLE = 0;
    public final static int DOUBLE = 1;
    public final static int TRIPLE = 2;
    public final static int HOMERUN = 3;
    public final static int FLYOUT = 4;
    public final static int STRIKEOUT = 5;
    public final static int WALK = 6;
    public final static int HIT_BY_PITCH = 7;
    public final static int ERROR = 8;
    public final static int GROUNDOUT = 9;
    public final static int FOUL_OUT= 10;

    public final static int FIELDERS_CHOICE =12;
    public final static int LINEOUT = 13;
    public final static int ADVANCERUNNER = 14;
}
