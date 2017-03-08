package com.example.crosbylanham.baseballstatscollector;

/**
 * Created by crosby on 3/1/17.
 */

public class AtBatInformation{
    public AtBatInformation() {
    }

    public final static String[] outcomePositions = {
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
