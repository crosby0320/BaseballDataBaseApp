package com.example.crosbylanham.baseballstatscollector;

import java.io.Serializable;

/**
 * Created by crosbylanham on 1/30/17.
 */

public class Player implements Serializable{
    long PlayerID;
    String Name;

    public Player() {
    }

    public Player(String name) {
        Name = name;
    }

    public long getPlayerID() {
        return PlayerID;
    }

    public void setPlayerID(long playerID) {
        PlayerID = playerID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
