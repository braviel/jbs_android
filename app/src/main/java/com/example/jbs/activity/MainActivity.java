package com.example.jbs.activity;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
//import dagger.android.AndroidInjection;
//import dagger.android.DispatchingAndroidInjector;

import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.jbs.MyFragmentActivity;
import com.example.jbs.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

//import javax.inject.Inject;

public class MainActivity extends MyFragmentActivity implements
        ViewProfileFragment.OnFragmentInteractionListener,
        ViewEventFragment.OnFragmentInteractionListener,
        GroupFragment.OnFragmentInteractionListener
{
//    @Inject
//    DispatchingAndroidInjector<Fragment> dispatchingAndroidInjector;

    private static String PROFILE_UID = "+6585536798";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        this.configureDagger();
        BottomNavigationView bottomNavigationView = findViewById(R.id.bot_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.action_work:
                        GroupFragment groupFragment = GroupFragment.newInstance(PROFILE_UID,"");
                        replaceFragment(groupFragment, R.id.ctnFragment ,ViewProfileFragment.TAG);
                        return true;
                    case R.id.action_event:
                        ViewEventFragment viewEventFragment = ViewEventFragment.newInstance(PROFILE_UID,"");
                        replaceFragment(viewEventFragment, R.id.ctnFragment ,ViewProfileFragment.TAG);
                        return true;
                    case R.id.action_chat:
                        return true;
                    case R.id.action_profile:
                        ViewProfileFragment viewProfileFragment = ViewProfileFragment.newInstance("");
                        replaceFragment(viewProfileFragment, R.id.ctnFragment ,ViewProfileFragment.TAG);
                        return true;
                    case R.id.action_search:
                        return true;
                }
                return false;
            }
        });
        if(true) {//TODO: first Time login
            ViewProfileFragment viewProfileFragment = ViewProfileFragment.newInstance("");
            getSupportFragmentManager()
                    .beginTransaction()
                    .addToBackStack(ViewProfileFragment.TAG)
                    .replace(R.id.ctnFragment, viewProfileFragment)
                    .commit();
            bottomNavigationView.setSelectedItemId(R.id.action_profile);
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
//    private void configureDagger(){
//        AndroidInjection.inject(this);
//    }
}
