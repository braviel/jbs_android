package com.example.jbs.activity;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.example.jbs.R;
import com.example.jbs.controller.ProfileController;
import com.example.jbs.fragment.ProfileMenuFragment;
import com.example.jbs.repo.DataRepo;
import com.example.jbs.room.Profile;

public class SplashActivity extends AppCompatActivity {
    public final static String TAG = SplashActivity.class.getSimpleName();
    private Intent myIntent;
    private Context mContext;
    String mProfileUID;
    @BindView(R.id.pgLoading)
    ProgressBar pgLoading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        mContext = this;
        SharedPreferences sharedPref = getSharedPreferences(getString(R.string.kJbsPref), Context.MODE_PRIVATE);
        mProfileUID = sharedPref.getString(getString(R.string.kJbsProfileUID), "");
        Log.i(TAG, "Login as : " + mProfileUID);
        Profile p = DataRepo.getInstance().getProfile();
        if(mProfileUID != null && !mProfileUID.isEmpty()) {
            if( p == null ) {
                new LoadingTask().execute();
                return;
            }
        } else {
            myIntent = new Intent(this, TermCondition.class);
            new Handler().postDelayed(() -> {
                startActivity(myIntent);
                finish();
            }, 1000);
            return;
        }
        myIntent = new Intent(this, MainActivity.class);
        startActivity(myIntent);
    }
    private class LoadingTask extends AsyncTask<Void, Integer, Profile> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pgLoading.setVisibility(View.VISIBLE);
        }

        @Override
        protected Profile doInBackground(Void... voids) {
            return ProfileController.getInstance().getProfile(mProfileUID);
        }

        @Override
        protected void onPostExecute(Profile profile) {
            super.onPostExecute(profile);
            if(profile != null) {
                DataRepo.getInstance().setProfile(profile);
            }
            myIntent = new Intent(mContext, MainActivity.class);
            startActivity(myIntent);
            pgLoading.setVisibility(View.INVISIBLE);
        }
    }
}
