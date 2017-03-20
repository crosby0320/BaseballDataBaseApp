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

    public boolean calledStrike() {
        strikes++;
        totalAtBatPitches++;
        totalInningPitches++;
        if (strikes == 3) {

            totalAtBatPitches=0;
            balls = 0;
            strikes = 0;
            return hitout();
        }
        return false;
    }

    public boolean calledBall() {
        totalAtBatPitches+=1;
        totalInningPitches+=1;
        if (balls < 4) {
            balls++;
            return false;
        } else {
            totalAtBatPitches=0;
            balls = 0;
            strikes = 0;
            return hitout();
        }
    }

    public boolean hitout() {
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
            return true;
        }
        return false;
    }

    public void foulAction(){
        totalInningPitches+=1;
        totalAtBatPitches+=1;
        if(strikes <= 2){
            strikes++;
        }else{

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

    public void setStrikes(int strikes) {
        this.strikes = strikes;
    }

    public void setBalls(int balls) {
        this.balls = balls;
    }

    public void setOuts(int outs) {
        this.outs = outs;
    }

    public void setKs(int ks) {
        Ks = ks;
    }

    public void setInning(int inning) {
        this.inning = inning;
    }

    public int getTotalInningPitches() {
        return totalInningPitches;
    }

    public void setTotalInningPitches(int totalInningPitches) {
        this.totalInningPitches = totalInningPitches;
    }

    public int getTotalAtBatPitches() {
        return totalAtBatPitches;
    }

    public void setTotalAtBatPitches(int totalAtBatPitches) {
        this.totalAtBatPitches = totalAtBatPitches;
    }
}
