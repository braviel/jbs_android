package com.example.jbs.room;

import com.example.jbs.service.ProfileWebService;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.Executor;

//import javax.inject.Inject;
//import javax.inject.Singleton;

import androidx.lifecycle.LiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//@Singleton
public class ProfileRepository {
    private static int FRESH_TIME_OUT_IN_MINUTES = 3;

    private final ProfileWebService webService;
    private final ProfileDao profileDao;
    private final Executor executor;

//    @Inject
    public ProfileRepository(ProfileWebService webService, ProfileDao profileDao, Executor executor) {
        this.webService = webService;
        this.profileDao = profileDao;
        this.executor = executor;
    }

    public LiveData<Profile> getProfile(String profileUID) {
        refreshProfile(profileUID);
        return profileDao.load(profileUID);
    }

    private void refreshProfile(final String profileUID) {
        executor.execute(() -> {
            boolean profileExisted = profileDao.hasProfile(profileUID, getMaxRefreshTime(new Date())) != null;
            if(!profileExisted) {
                webService.getProfile(profileUID).enqueue(new Callback<com.example.jbs.room.Profile>() {
                    @Override
                    public void onResponse(Call<com.example.jbs.room.Profile> call, Response<com.example.jbs.room.Profile> response) {
//                        Toast.makeText(App.context, "Data refreshed from network !", Toast.LENGTH_LONG).show();
                        executor.execute(() -> {
                            Profile profile = response.body();
                            profile.setLastRefresh(new Date());
                            profileDao.save(profile);
                        });
                    }

                    @Override
                    public void onFailure(Call<com.example.jbs.room.Profile> call, Throwable t) {

                    }
                });
            }
        });
    }
    private Date getMaxRefreshTime(Date currentDate){
        Calendar cal = Calendar.getInstance();
        cal.setTime(currentDate);
        cal.add(Calendar.MINUTE, - FRESH_TIME_OUT_IN_MINUTES);
        return cal.getTime();
    }
}
