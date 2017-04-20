package com.example.crosbylanham.baseballstatscollector;

import android.content.Intent;
import android.media.Image;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SplashscreenActivity extends AppCompatActivity {
    private static final int SPLASHSCREENTIME = 4000;
    Button start;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        /*new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent homeintent = new Intent(SplashscreenActivity.this,LogIn.class);
                startActivity(homeintent);
                finish();
            }
        },SPLASHSCREENTIME);*/

        startbutton();
    }
    public void startbutton(){
        start = (Button) findViewById(R.id.Splash_StartButton);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homeintent = new Intent(SplashscreenActivity.this,LogIn.class);
                startActivity(homeintent);
                finish();
            }
        });
    }
}
