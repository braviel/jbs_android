package com.example.jbs.controller;

import android.util.Log;

import com.example.jbs.CommonService;
import com.example.jbs.room.Profile;
import com.example.jbs.service.ProfileWebService;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ProfileController {
    public static String TAG = ProfileController.class.getSimpleName();

    public ProfileWebService getProfileService() {
        return mProfileService;
    }

    ProfileWebService mProfileService;
    private static final ProfileController ourInstance = new ProfileController();
    private ProfileController() {
        Retrofit retrofit = CommonService.getInstance().initWebservice();
        mProfileService = retrofit.create(ProfileWebService.class);
    }
    public static ProfileController getInstance() {
        return ourInstance;
    }
    public Profile getProfile(String profileUID) {
        Call<Profile> getProfile = mProfileService.getProfile(profileUID);
        try {
            Response<Profile> resp = getProfile.execute();
            int code = resp.code();
            if(code == 200) {
                return resp.body();
            } else {
                return null;
            }
        } catch (IOException ioExc) {
            Log.e(TAG, ioExc.getMessage());
        }
        return null;
    }
}

