package com.example.jbs.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.example.jbs.R;

public class SplashActivity extends AppCompatActivity {
    private Intent myIntent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        myIntent = new Intent(this, TermCondition.class);
        new Handler().postDelayed(() -> {
            SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
            String profileUID = pref.getString( getString(R.string.KeyProfileUID), "");
            if(profileUID != null && !profileUID.isEmpty()) {
                Intent i = new Intent(this, MainActivity.class);
                startActivity(i);
            } else {
                startActivity(myIntent);
                finish();
            }
        }, 1000);
    }
}
