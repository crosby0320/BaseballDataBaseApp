package com.example.crosbylanham.baseballstatscollector;

import java.text.SimpleDateFormat;
import java.util.Date;
public class DateFunctions {

    public static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");

    public DateFunctions(){}

    public String getCurrentTimeAndDate(){
        Date date = new Date();
        return sdf.format(date);
    }
}
