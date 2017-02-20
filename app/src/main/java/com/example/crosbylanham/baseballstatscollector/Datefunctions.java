package com.example.crosbylanham.baseballstatscollector;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by crosbylanham on 2/14/17.
 */

public class Datefunctions {
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
    public Datefunctions(){}
    public String getCurrentTimeAndDate(){
        Date date = new Date();
        return sdf.format(date);
    }
}
