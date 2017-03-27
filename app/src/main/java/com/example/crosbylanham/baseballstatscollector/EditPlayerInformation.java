package com.example.crosbylanham.baseballstatscollector;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class EditPlayerInformation extends AppCompatActivity {

    Spinner nameSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_player_information);

        nameSpinner = (Spinner) findViewById(R.id.EditPlayerInfoSpinner);
        ArrayList<Player> playersList = new ArrayList<>();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(EditPlayerInformation.this,
                android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        nameSpinner.setAdapter(adapter);

        

        FirebaseDatabase.getInstance().getReference(DataBaseHelper.PLAYERINFOTABLEBNAME).
                orderByChild("name").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
