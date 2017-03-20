package com.example.crosbylanham.baseballstatscollector;

import java.io.Serializable;

/**
 * Created by crosbylanham on 1/30/17.
 */

public class Player implements Serializable{
    String playerID;
    String name;
    int generated = 0;

    public Player() {
    }

    public Player(String name) {
        this.name = name;
    }
    public Player(String name,int s) {this.name = name;generated =1;}

    public String getPlayerID() {
        return playerID;
    }

    public void setPlayerID(String playerID) {
        this.playerID = playerID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String toString(){
        return name + " ID: "+playerID;
    }
}
