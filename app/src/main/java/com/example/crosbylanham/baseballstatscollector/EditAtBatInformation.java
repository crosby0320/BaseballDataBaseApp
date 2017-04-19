package com.example.crosbylanham.baseballstatscollector;

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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class EditAtBatInformation extends AppCompatActivity {
    Button makeChanges, remove;
    Spinner playerSpinner, aBSpinner;
    ArrayList<Player> playerArrayList;
    ArrayList<AtBats> AtBattsArray;
    AtBats atBatToFix;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_at_bat_information);

        initButtons();
        initSpinners();
        initTextViews();
        playerArrayList = new ArrayList<>();
        AtBattsArray = new ArrayList<>();

        final ArrayAdapter<String> adapter = new ArrayAdapter<>(EditAtBatInformation.this,
                android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        playerSpinner.setAdapter(adapter);

        FirebaseDatabase.getInstance().getReference(DataBaseHelper.PLAYERINFOTABLEBNAME)
                .addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        doSomthing(dataSnapshot);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }

                    void doSomthing(DataSnapshot dataSnapshot) {
                        adapter.clear();
                        for(int i =0;i<playerArrayList.size();i++)playerArrayList.remove(0);
                        for (DataSnapshot x : dataSnapshot.getChildren()) {
                            adapter.add(x.getValue(Player.class).getName());
                            playerArrayList.add(x.getValue(Player.class));
                        }
                        onSelection();
                    }
                });
        setRemoveButtonAction();
        setMakeChangesAction();
    }

    public void initButtons() {
        makeChanges = (Button) findViewById(R.id.EditAtBatInfo_makeChanges);
        remove = (Button) findViewById(R.id.EditAtBatInfo_remove);
    }

    public void initSpinners() {
        playerSpinner = (Spinner) findViewById(R.id.EditAtBatInfo_Spinner);
        aBSpinner = (Spinner) findViewById(R.id.EditAtBatInfo_selectABspinner);
    }

    public void onSelection() {
        AtBattsArray = new ArrayList<>();
        final ArrayAdapter<String> atBatsAdapter = new ArrayAdapter<>(EditAtBatInformation.this,
                android.R.layout.simple_spinner_item);

        atBatsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        aBSpinner.setAdapter(atBatsAdapter);

        playerSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            /*
            after selection fill in the information
             */
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                FirebaseDatabase.getInstance().getReference(DataBaseHelper.ATBATTableName)
                        .orderByChild("playerAtBatId").equalTo(playerArrayList.get(position)
                        .getPlayerID()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot x : dataSnapshot.getChildren()){
                            atBatsAdapter.add(x.getValue(AtBats.class).description);
                            AtBattsArray.add(x.getValue(AtBats.class));
                        }
                        aBSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                atBatToFix = AtBattsArray.get(position);
                                ((TextView)findViewById(R.id.EditAtBatInfo_ballsView))
                                        .setText(String.valueOf(atBatToFix.getBalls()));
                                ((TextView)findViewById(R.id.EditAtBatInfo_descriptionView))
                                        .setText(String.valueOf(atBatToFix.getDescription()));
                                ((TextView)findViewById(R.id.EditAtBatInfo_outcomeView))
                                        .setText(String.valueOf(atBatToFix.getOutcome()));
                                ((TextView)findViewById(R.id.EditAtBatInfo_PitchesView))
                                        .setText(String.valueOf(atBatToFix.getPitches()));
                                ((TextView)findViewById(R.id.EditAtBatInfo_strikesview))
                                        .setText(String.valueOf(atBatToFix.getStrikes()));

                            }
                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {}});
                    }
                    @Override public void onCancelled(DatabaseError databaseError) {}});
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }


    ArrayList<EditText> editTextViews;
    public void initTextViews(){
        editTextViews = new ArrayList<>();
        editTextViews.add((EditText) findViewById(R.id.EditAtBatInfo_ballsEditText));
        editTextViews.add((EditText) findViewById(R.id.EditAtBatInfo_descriptionEditText));
        editTextViews.add((EditText) findViewById(R.id.EditAtBatInfo_outcomeEditText));
        editTextViews.add((EditText) findViewById(R.id.EditAtBatInfo_pitchesEditText));
        editTextViews.add((EditText) findViewById(R.id.EditAtBatInfo_strikesEditText));
    }
    public void setRemoveButtonAction(){
        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference(DataBaseHelper.PLAYERINFOTABLEBNAME);
                myRef.child(atBatToFix.getAtBatID()).setValue(null);
            }
        });
    }
    public void setMakeChangesAction() {
        makeChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!new DataBaseHelper().isEmpty(editTextViews.get(0))) {
                    atBatToFix.setBalls(Integer.parseInt(editTextViews.get(0).getText().toString()));
                    editTextViews.get(0).setText("");
                }
                if (!new DataBaseHelper().isEmpty(editTextViews.get(1))) {
                    atBatToFix.setDescription((editTextViews.get(1).getText().toString()));
                    editTextViews.get(1).setText("");
                }
                if (!new DataBaseHelper().isEmpty(editTextViews.get(2))) {
                    atBatToFix.setOutcome(Integer.parseInt(editTextViews.get(2).getText().toString()));
                    editTextViews.get(2).setText("");
                }
                if (!new DataBaseHelper().isEmpty(editTextViews.get(3))) {
                    atBatToFix.setPitches(Integer.parseInt(editTextViews.get(3).getText().toString()));
                    editTextViews.get(3).setText("");
                }
                if (!new DataBaseHelper().isEmpty(editTextViews.get(4))) {
                    atBatToFix.setStrikes(Integer.parseInt(editTextViews.get(4).getText().toString()));
                    editTextViews.get(4).setText("");
                }

                FirebaseDatabase.getInstance().getReference(DataBaseHelper.ATBATTableName)
                        .child(atBatToFix.getAtBatID()).setValue(atBatToFix);
            }
        });
    }
}
