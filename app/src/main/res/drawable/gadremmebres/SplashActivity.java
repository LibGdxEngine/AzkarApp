package com.HNY.gadremmebres;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {
    Intent intent2;
    int TIME_SPLASH = 3000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                CheckIntoActivity();
            }
        }, TIME_SPLASH);


    }
    private void CheckIntoActivity() {

        intent2 = new Intent(SplashActivity.this, MainActivity.class);
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                startActivity(intent2);
            }
        });
        t.start();
        finish();
    }
}
