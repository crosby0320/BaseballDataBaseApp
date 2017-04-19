package com.example.crosbylanham.baseballstatscollector;

import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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

    Spinner playerSpinner;

    TextView paTextView,abTextView,avgTextView,obpTextView,slgTextView,
            rTextView,hTextView,hrTextView;

    Player player;
    ArrayList<AtBats> allAtBats;

    TextView[][] last4TextViews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quick_batting_stats2);

        initTotalTextViews();
        initLast4TextViews();
        playerSpinnerAction();
    }
    private void initLast4TextViews() {
        last4TextViews = new TextView[4][4];
        last4TextViews[0][0] = (TextView) findViewById(R.id.Battingstats_last4balls1);
        last4TextViews[0][1] = (TextView) findViewById(R.id.Battingstats_last4strikes1);
        last4TextViews[0][2] = (TextView) findViewById(R.id.Battingstats_last4pitches1);
        last4TextViews[0][3] = (TextView) findViewById(R.id.Battingstats_last4outcome1);

        last4TextViews[1][0] = (TextView) findViewById(R.id.Battingstats_last4balls2);
        last4TextViews[1][1] = (TextView) findViewById(R.id.Battingstats_last4strikes2);
        last4TextViews[1][2] = (TextView) findViewById(R.id.Battingstats_last4pitches2);
        last4TextViews[1][3] = (TextView) findViewById(R.id.Battingstats_last4outcome2);

        last4TextViews[2][0] = (TextView) findViewById(R.id.Battingstats_last4balls3);
        last4TextViews[2][1] = (TextView) findViewById(R.id.Battingstats_last4strikes3);
        last4TextViews[2][2] = (TextView) findViewById(R.id.Battingstats_last4pitches3);
        last4TextViews[2][3] = (TextView) findViewById(R.id.Battingstats_last4outcome3);

        last4TextViews[3][0] = (TextView) findViewById(R.id.Battingstats_last4balls4);
        last4TextViews[3][1] = (TextView) findViewById(R.id.Battingstats_last4strikes4);
        last4TextViews[3][2] = (TextView) findViewById(R.id.Battingstats_last4pitches4);
        last4TextViews[3][3] = (TextView) findViewById(R.id.Battingstats_last4outcome4);
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

    public void playerSpinnerAction(){
        playerSpinner = (Spinner) findViewById(R.id.BattingStats_playerSpinner);
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
            @Override public void onCancelled(DatabaseError databaseError) {}
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
                        fillLast4AtBats(allAtBats);
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {}});
            }@Override public void onNothingSelected(AdapterView<?> parentView) {}});
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

    public void fillLast4AtBats(ArrayList<AtBats> list){
        for (int i = 0;i<4;i++){
            for (int j=0;j<4;j++){
                last4TextViews[i][j].setText("");
            }
        }
        for(int i = list.size(),j=0;list.size()-i >=0 && i > 0 && j < 4;i--,j++){
            last4TextViews[j][0].setText(String.valueOf(list.get(i-1).getBalls()));
            last4TextViews[j][1].setText(String.valueOf(list.get(i-1).getStrikes()));
            last4TextViews[j][2].setText(String.valueOf(list.get(i-1).getPitches()));
            last4TextViews[j][3].setText(String.valueOf(list.get(i-1).getDescription()));
        }
    }
}
