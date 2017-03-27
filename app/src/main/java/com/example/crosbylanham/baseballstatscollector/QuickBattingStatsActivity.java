package com.example.crosbylanham.baseballstatscollector;

import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;



public class QuickBattingStatsActivity extends AppCompatActivity {

    Spinner playerSpinner,gameSpinner;

    TextView paTextView,abTextView,avgTextView,obpTextView,slgTextView,
            rTextView,hTextView,hrTextView;

    TextView gamepaTextView,gameabTextView,gameavgTextView,gameobpTextView,gameslgTextView,
            gamerTextView,gamehTextView,gamesingleTextView,gamedoubleTextView,gametriplesTextView,
            gamehrTextView;

    Player player;
    ArrayList<AtBats> allAtBats;
    Game game;

    TableLayout last4table;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quick_batting_stats2);

        initTotalTextViews();
        initTable();
        initGameTextViews();
        playerSpinnerAction();
    }

    private void initTable() {
        last4table = (TableLayout) findViewById(R.id.BattingStats_lastGames);
    }

    public void initTotalTextViews(){
        paTextView      = (TextView)findViewById(R.id.BattingStats_PA);
        abTextView      = (TextView)findViewById(R.id.BattingStats_AB);
        avgTextView     = (TextView)findViewById(R.id.BattingStats_AVG);
        obpTextView     = (TextView)findViewById(R.id.BattingStats_OBP);
        slgTextView     = (TextView)findViewById(R.id.BattingStats_SLG);
        rTextView       = (TextView)findViewById(R.id.BattingStats_R);
        hTextView       = (TextView)findViewById(R.id.BattingStats_H);
        hrTextView      = (TextView)findViewById(R.id.BattingStats_HR);
    }

    public void initGameTextViews(){
        gamepaTextView      = (TextView)findViewById(R.id.BattingStats_game_PA);
        gameabTextView      = (TextView)findViewById(R.id.BattingStats_game_AB);
        gameavgTextView     = (TextView)findViewById(R.id.BattingStats_game_AVG);
        gameobpTextView     = (TextView)findViewById(R.id.BattingStats_game_OBP);
        gameslgTextView     = (TextView)findViewById(R.id.BattingStats_game_SLG);
        gamerTextView       = (TextView)findViewById(R.id.BattingStats_game_R);
        gamehTextView       = (TextView)findViewById(R.id.BattingStats_game_H);
        gamesingleTextView  = (TextView)findViewById(R.id.BattingStats_game_1B);
        gamedoubleTextView  = (TextView)findViewById(R.id.BattingStats_game_2B);
        gametriplesTextView = (TextView)findViewById(R.id.BattingStats_game_3B);
        gamehrTextView      = (TextView)findViewById(R.id.BattingStats_game_HR);
    }
    public void playerSpinnerAction(){
        playerSpinner = (Spinner) findViewById(R.id.BattingStats_playerSpinner);
        gameSpinner = (Spinner) findViewById(R.id.BattingStats_gameSpinner);
        final ArrayList<Player> listOfPlayers = new ArrayList<>();

        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        playerSpinner.setAdapter(adapter);
        DatabaseReference br = FirebaseDatabase.getInstance().getReference(DataBaseHelper.PLAYERINFOTABLEBNAME);
        br.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> it = dataSnapshot.getChildren();
                for(DataSnapshot x:it) {
                    adapter.add(x.getValue(Player.class).getName());
                    listOfPlayers.add(x.getValue(Player.class));
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });

        playerSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                allAtBats = new ArrayList<>();
                player = listOfPlayers.get(position);

                DatabaseReference dr = FirebaseDatabase.getInstance().getReference(DataBaseHelper.ATBATTableName);
                Query query = dr.orderByChild("playerAtBatId").equalTo(listOfPlayers.get(position).getPlayerID());
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Iterable<DataSnapshot> it = dataSnapshot.getChildren();
                        for (DataSnapshot x:it){
                            allAtBats.add(x.getValue(AtBats.class));
                        }
                        fillTotalStats(allAtBats);
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {}
                });
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {}
        });
    }

    public void fillTotalStats(ArrayList<AtBats> allAtBats){
        BattingStatsCalculator bs = new BattingStatsCalculator(allAtBats);
        bs.calcStats();
        abTextView.setText(String.valueOf(bs.totalab));
        paTextView.setText(String.valueOf(bs.totalPA));
        avgTextView.setText(String.format("%.3f",bs.totalavg));
        obpTextView.setText(String.format("%.3f",bs.totalobp));
        slgTextView.setText(String.format("%.3f",bs.totalslg));
        rTextView.setText(String.valueOf(bs.totalr));
        hTextView.setText(String.valueOf(bs.totalh));
        hrTextView.setText(String.valueOf(bs.totalhr));
    }

}
