package com.example.crosbylanham.baseballstatscollector;
/**
 * Created by crosbylanham on 1/15/17.
 */

public class PitchCounter {
    int strikes;
    int balls;
    int outs;
    int Ks;
    int inning;
    int totalInningPitches;
    int totalAtBatPitches;

    public PitchCounter() {
        strikes = 0;
        balls = 0;
        outs = 0;
        Ks = 0;
        totalInningPitches=0;
        totalAtBatPitches=0;
        inning = 1;
    }

    public void hit() {
        strikes = 0;
        balls = 0;
        totalAtBatPitches=0;
    }

    public boolean hitstrike() {
        strikes++;
        totalAtBatPitches+=1;
        totalInningPitches+=1;
        if (strikes == 3) {
            hitout();
            totalAtBatPitches=0;
            return true;
        }
        return false;
    }

    public boolean hitball() {
        totalAtBatPitches+=1;
        totalInningPitches+=1;
        if (balls < 4) {
            balls++;
            return false;
        } else {
            totalAtBatPitches=0;
            balls = 0;
            strikes = 0;
            return true;
        }
    }

    public void hitout() {
        totalAtBatPitches=0;
        totalInningPitches+=1;
        outs++;
        strikes = 0;
        balls = 0;
        if (outs == 3) {
            outs = 0;
            inning++;
            totalAtBatPitches=0;
            totalInningPitches=0;
        }
    }

    public void hitfoul(){
        totalInningPitches+=1;
        totalAtBatPitches+=1;
        if(strikes <= 2){

        }else{
            strikes++;
        }
    }

    public void hitbypitch() {
        strikes = 0;
        balls = 0;
    }

    public void reset(){
        strikes = 0;
        balls = 0;
        Ks = 0;
        outs = 0;
        inning = 0;
    }

    public int getStrikes() {
        return strikes;
    }

    public int getBalls() {
        return balls;
    }

    public int getKs() {
        return Ks;
    }

    public int getInning() {
        return inning;
    }

    public int getOuts() {
        return outs;
    }
}
