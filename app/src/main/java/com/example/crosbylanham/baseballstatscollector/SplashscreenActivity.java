package com.example.crosbylanham.baseballstatscollector;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashscreenActivity extends AppCompatActivity {
    private static final int SPLASHSCREENTIME = 001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent homeintent = new Intent(SplashscreenActivity.this,HomePage.class);
                startActivity(homeintent);
                finish();
            }
        },SPLASHSCREENTIME);
    }
}
