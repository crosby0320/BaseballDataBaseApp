package com.example.crosbylanham.baseballstatscollector;

import android.os.Bundle;
import android.os.health.PidHealthStats;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

class Stats {
    ArrayList<PitchingStats> allstats = new ArrayList<>();

    public Stats(ArrayList<PitchingStats> allstats) {
        this.allstats = allstats;
    }

    public void calcStats() {
        gamesplayed = allstats.size();

        for (PitchingStats x : allstats) {
            totalhits += x.getHits();
            putouts += x.getOutsPitched();
            totalhr += x.getHomeRuns();
            walks += x.getWalks();
            totalhbp += x.getHitsByPitch();
            totalEarnedRuns += x.earnedRuns;
            if (x.runs == 0) {
                shutouts++;
            }
        }
    }

    int gamesplayed, wins, losses, totalERA, gs, shutouts, saves, totalhits, totalhr,
            totalip, tataler, totalbb, totalhbp, putouts, totalEarnedRuns, walks;

    public double getERA() {
        return 9 * totalEarnedRuns / (putouts / 3.0);
    }
}

public class QuickPitchingStatsActivity extends AppCompatActivity {

    Player player = null;
    Game game;

    TextView gamesPlayered, wins, losses, totalERA, GS, shutouts, saves;
    TextView totalhits, totalHR, totalIP, totalR, totalER, totalBB, totalHBP;

    Spinner spinner, gamespiner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pitching_stats);

        initGameTextViews();
        initTotalStats();
        playerSpinnerAction();
    }

    public void playerSpinnerAction() {
        spinner = (Spinner) findViewById(R.id.pitchingPlayers);

        final List<Player> list = new ArrayList<>();

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);

        FirebaseDatabase.getInstance().getReference(DataBaseHelper.PLAYERINFOTABLEBNAME)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Iterable<DataSnapshot> it = dataSnapshot.getChildren();
                        for (DataSnapshot x : dataSnapshot.getChildren()) {
                            adapter.add(x.getValue(Player.class).getName());
                            list.add(x.getValue(Player.class));
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                DatabaseReference dr = FirebaseDatabase.getInstance().getReference(DataBaseHelper.PITCHINGSTATSTABLENAME);
                Query q = dr.orderByChild("playerID")
                        .equalTo(list.get(position).getPlayerID());
                Log.d("Position ", String.valueOf(position));
                Log.d("Looking for", "Player nameds" + list.get(position).getPlayerID());
                Log.d("Playername", list.get(position).getName());
                q.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Log.d("Pitches stats", dataSnapshot.toString());
                        ArrayList<PitchingStats> pitchingStatsArrayList = new ArrayList<>();
                        for (DataSnapshot x : dataSnapshot.getChildren()) {
                            pitchingStatsArrayList.add(x.getValue(PitchingStats.class));
                            Log.d("Pithcing stats ", String.valueOf(x.getValue(PitchingStats.class)));
                        }
                        //----------------------- calculating stats -----
                        Stats stats = new Stats(pitchingStatsArrayList);
                        fillTotalStats(stats);
                        //-----------------------------------------------
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        //-------------------------------------------------------------------------
    }

    public void initGameTextViews() {

    }

    public void initTotalStats() {
        gamesPlayered = (TextView) findViewById(R.id.PitchingStats_games);
        wins = (TextView) findViewById(R.id.PitchingStats_wins);
        losses = (TextView) findViewById(R.id.PitchingStats_losses);
        totalERA = (TextView) findViewById(R.id.PitchingStats_era);
        GS = (TextView) findViewById(R.id.PitchingStats_gs);
        shutouts = (TextView) findViewById(R.id.PitchingStats_shutouts);
        saves = (TextView) findViewById(R.id.PitchingStats_saves);

        totalhits = (TextView) findViewById(R.id.PitchingStats_hits);
        totalHR = (TextView) findViewById(R.id.PitchingStats_Total_HRs);
        totalIP = (TextView) findViewById(R.id.PitchingStats_Total_IP);
        totalR = (TextView) findViewById(R.id.PitchingStats_Total_R);
        totalER = (TextView) findViewById(R.id.PitchingStats_Total_ER);
        totalBB = (TextView) findViewById(R.id.PitchingStats_Total_BB);
        totalHBP = (TextView) findViewById(R.id.PitchingStats_Total_HBP);
    }

    public void fillTotalStats(Stats stats) {
        stats.calcStats();
        gamesPlayered.setText(String.valueOf(stats.gamesplayed));
        wins.setText("N/A");
        GS.setText("N/A");
        saves.setText("N/A");
        losses.setText("N/A");
        totalERA.setText(String.format("%.3f", stats.getERA()));
        shutouts.setText(String.valueOf(stats.shutouts));
        totalhits.setText(String.valueOf(stats.totalhits));
        totalHR.setText(String.valueOf(stats.totalhr));
        totalIP.setText(String.format("%.1f", stats.putouts / 3.0));
        totalER.setText(String.format("%d", stats.totalEarnedRuns));
        totalBB.setText(String.valueOf(stats.walks));
        totalHBP.setText(String.valueOf(stats.totalhbp));
    }
}