package com.example.crosbylanham.baseballstatscollector;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class WhosLombo extends AppCompatActivity {
    Button batting,pithcing;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_whos_lombo);

        initButton();

        setPithcing();
        setBatting();
    }
    public void initButton(){
        batting = (Button) findViewById(R.id.battingstatsbutton);
        pithcing = (Button) findViewById(R.id.pitchingstatsbutton);
    }
    public void setBatting(){
        batting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
    public void setPithcing(){
        pithcing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WhosLombo.this,QuickPitchingStatsActivity.class);
                startActivity(intent);
            }
        });
    }
}
