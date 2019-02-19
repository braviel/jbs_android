package com.example.jbs.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.jbs.R;

public class SplashActivity extends AppCompatActivity {
    private Intent myIntent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_splash);
        myIntent = new Intent(this, TermCondition.class);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(myIntent);
                finish();
            }
        }, 1000);
    }
}
