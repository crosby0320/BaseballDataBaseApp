package com.example.crosbylanham.baseballstatscollector;

/**
 * Created by crosbylanham on 1/30/17.
 */

public class Team {
    long teamid;
    String name;
    String location;
    String Mascot;

    public Team() {
    }

    public Team(String name) {
        this.name = name;
    }

    public long getTeamid() {
        return teamid;
    }

    public void setTeamid(long teamid) {
        this.teamid = teamid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String  getMascot() {
        return Mascot;
    }

    public void setMascot(String mascot) {
        Mascot = mascot;
    }
}
