package com.example.crosbylanham.baseballstatscollector;

import android.util.Log;

import java.util.ArrayList;

/**
 * Created by crosby on 3/15/17.
 */

public class BattingStatsCalculator {
        ArrayList<AtBats> allabs = new ArrayList<>();
        int totalPA =0;
        double totalavg = 0;
        double totalobp = 0;
        double totalslg = 0;
        int totalr  = 0;
        int totalh  = 0;
        int total1b = 0;
        int total2b = 0;
        int total3b = 0;
        int totalhr = 0,totalks=0;
        int totalsb = 0;
        int totalcs = 0;
        int totalab = 0;
        int totalHBP = 0;
        int totalBB = 0;
        public BattingStatsCalculator(){}
        public BattingStatsCalculator(ArrayList<AtBats> allabs) {this.allabs = allabs;}
        public void setAllaTbatsList(ArrayList<AtBats> list){allabs = list;}
        public void calcStats() {
            for (AtBats a:allabs){
                if(a.getOutcome() == AtBatInformation.SINGLE ){total1b+=1;}
                else if(a.getOutcome() == AtBatInformation.DOUBLE ){total2b+=1;}
                else if(a.getOutcome() == AtBatInformation.TRIPLE ){total3b+=1;}
                else if(a.getOutcome() == AtBatInformation.HOMERUN ){totalhr+=1;}
                else if(a.getOutcome() == AtBatInformation.STRIKEOUT){totalks+=1;}
            }
            totalHBP = new AtBats().getAllHBP(allabs);
            totalBB     = new AtBats().getAllBaseOnBalls(allabs);
            totalPA     = allabs.size();
            totalab     = new AtBats().getAllAtBats(allabs);
            totalh      = new AtBats().getAllHits(allabs);
            Log.d("Total hits","are: "+totalh);
            totalr      = 0;
            totalavg    = totalh/(double)totalab;
            totalobp    = (totalh + totalBB + totalHBP)/
                    (double)(totalab + totalBB + totalHBP );
            totalslg    = (total1b + total2b * 2 + total3b * 3 + totalhr * 4)/
                    (double)(totalab);
        }
}
