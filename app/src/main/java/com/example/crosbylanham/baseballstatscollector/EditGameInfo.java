package com.example.crosbylanham.baseballstatscollector;

import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class EditGameInfo extends AppCompatActivity {
    Spinner gameSpinner;
    ArrayList<Game> gameArrayList;
    TextView[] views;
    EditText[] editTexts;

    Button remove,makeChanges;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_game_info);

        gameArrayList = new ArrayList<>();
        gameSpinner = (Spinner) findViewById(R.id.EditGameInfo_selectGameSpinner);
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        gameSpinner.setAdapter(adapter);

        initViews();
        initButtons();

        FirebaseDatabase.getInstance().getReference(DataBaseHelper.GAMETABLENAME)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        loadInformation(dataSnapshot);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                    public void loadInformation(DataSnapshot dataSnapshot){
                        adapter.clear();
                        gameArrayList = new ArrayList<>();
                        for (DataSnapshot x : dataSnapshot.getChildren()) {
                            Game g = x.getValue(Game.class);
                            adapter.add(g.getName());
                            gameArrayList.add(g);
                        }
                        onSelection();
                    }
                });
    }

    private void initButtons() {
        remove = (Button) findViewById(R.id.EditGameInfo_RemoveButton);
        makeChanges = (Button) findViewById(R.id.EditGameInfo_MakeChangesButton);
    }

    private void initViews() {
        views = new TextView[4];
        editTexts = new EditText[4];

        views[0] = (TextView) findViewById(R.id.EditGameInfo_GameDateTextVire);
        views[1] = (TextView) findViewById(R.id.EditGameInfo_TeamsAgainstTextView);
        views[2] = (TextView) findViewById(R.id.EditGameInfo_AwayTeamTextView);
        views[3] = (TextView) findViewById(R.id.EditGameInfo_HomeTeamTextView);


        editTexts[0] = (EditText) findViewById(R.id.EditGameInfo_GameDateEditText);
        editTexts[1] = (EditText) findViewById(R.id.EditGameInfo_AwayTeamEditTextView);
        editTexts[2] = (EditText) findViewById(R.id.EditGameInfo_AwayTeamEditTextView);
        editTexts[3] = (EditText) findViewById(R.id.EditGameInfo_HomeTeamEditTextView);
    }

    public void onSelection(){
        gameSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                fillInformation(gameArrayList.get(position));
                makeChangesButtonAction(gameArrayList.get(position));
                removeGameButtonAction(gameArrayList.get(position));
            }
            @Override public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    private void removeGameButtonAction(final Game g) {
        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase.getInstance().getReference(DataBaseHelper.GAMETABLENAME)
                        .child(g.getGameID()).setValue(null);
            }
        });
    }

    private void makeChangesButtonAction(Game g) {
        if (editTexts[0].getText() == null){
            g.setName(editTexts[0].getText().toString());
        }
        if (editTexts[1].getText() == null){
            g.setName(editTexts[1].getText().toString());
        }
        if (editTexts[2].getText() == null){
            g.setName(editTexts[2].getText().toString());
        }
        if (editTexts[3].getText() == null){
            g.setName(editTexts[3].getText().toString());
        }
        FirebaseDatabase.getInstance().getReference(DataBaseHelper.GAMETABLENAME)
                .child(g.getGameID()).setValue(g);
    }

    private void fillInformation(Game g) {
        views[0].setText(g.getName());
        views[1].setText(g.getName());
        views[2].setText(g.getAwayTeamID());
        views[3].setText(g.getHomeTeamID());
    }

}
