package com.example.jbs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toolbar;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    private ActionBar actionBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        actionBar = getSupportActionBar();
        BottomNavigationView bottomNavigationView = (BottomNavigationView)findViewById(R.id.bot_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);

        actionBar.setTitle(R.string.BotMenu_work);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            Fragment fragment;
            switch (menuItem.getItemId()) {
                case R.id.action_work:
                    actionBar.setTitle(R.string.BotMenu_work);
                    return true;
                case R.id.action_event:
                    actionBar.setTitle(R.string.BotMenu_event);
                    return true;
                case R.id.action_chat:
                    actionBar.setTitle(R.string.BotMenu_chat);
                    return true;
                case R.id.action_profile:
                    actionBar.setTitle(R.string.BotMenu_profile);
                    return true;
                case R.id.action_search:
                    actionBar.setTitle(R.string.BotMenu_search);
                    return true;
            }
            return false;
        }
    };
}
