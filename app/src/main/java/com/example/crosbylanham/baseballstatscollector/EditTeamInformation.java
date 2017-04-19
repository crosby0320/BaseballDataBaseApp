package com.example.crosbylanham.baseballstatscollector;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class EditTeamInformation extends AppCompatActivity {

    Button remove,makeChanges;
    Spinner teamSpinner;
    ArrayList<Team> teamArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_team_information);

        initButton();
        initSpinner();
        initTextViews();
        teamArrayList = new ArrayList<>();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        teamSpinner.setAdapter(adapter);

        FirebaseDatabase.getInstance();
    }

    private void initTextViews() {

    }

    private void initSpinner() {

    }

    private void initButton() {
    }
}
