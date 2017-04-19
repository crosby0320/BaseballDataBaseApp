package com.example.crosbylanham.baseballstatscollector;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

public class EditPlayerInformation extends AppCompatActivity {

    Spinner nameSpinner;
    Button makeChanges, removeButton;

    ArrayList<Player> playersList;

    Player playerSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_player_information);

        removeButton = (Button) findViewById(R.id.EditPlayerInfo_RemoveButton);
        makeChanges = (Button) findViewById(R.id.EditPlayerInfo_MakeChanges);
        nameSpinner = (Spinner) findViewById(R.id.EditPlayerInfoSpinner);
        playersList = new ArrayList<>();

        final ArrayAdapter<String> adapter = new ArrayAdapter<>(EditPlayerInformation.this,
                android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        nameSpinner.setAdapter(adapter);

        FirebaseDatabase.getInstance().getReference(DataBaseHelper.PLAYERINFOTABLEBNAME).
                orderByChild("name").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                doSomething(dataSnapshot);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

            void doSomething(DataSnapshot dataSnapshot){
                adapter.clear();playersList.clear();
                for(DataSnapshot x:dataSnapshot.getChildren()){
                    adapter.add(x.getValue(Player.class).getName());
                    playersList.add(x.getValue(Player.class));
                }
                onSelection();
                onRemoveButtonAction();
                onMakeChangesButtonAction();
            }
        });

    }

    public void onSelection() {
        nameSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) findViewById(R.id.EditPlayerInfo_Name)).
                        setText(playersList.get(position).getName());
                playerSelected = playersList.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    public void onRemoveButtonAction() {
        removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference(DataBaseHelper.PLAYERINFOTABLEBNAME);
                myRef.child(playerSelected.getPlayerID()).setValue(null);
            }
        });
    }

    public void onMakeChangesButtonAction() {
        makeChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playerSelected.setName((((EditText) findViewById(R.id.EditPlayerInfo_NameFillIn)).getText().toString()));
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference(DataBaseHelper.PLAYERINFOTABLEBNAME);
                myRef.child(playerSelected.getPlayerID()).setValue(playerSelected);
            }
        });
    }
}
