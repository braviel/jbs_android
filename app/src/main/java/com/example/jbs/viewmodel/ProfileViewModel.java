package com.example.jbs.viewmodel;

import com.example.jbs.room.Profile;
import com.example.jbs.room.ProfileRepository;

import org.joda.time.LocalTime;

import java.util.Date;

//import javax.inject.Inject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class ProfileViewModel extends ViewModel {
    private LiveData<Profile> profile;
    private ProfileRepository profileRepo;

//    @Inject
    public ProfileViewModel(ProfileRepository profileRepo) {
        this.profileRepo = profileRepo;
    }

    // ----

    public void init(String profileUID) {
        if (this.profile != null) {
            return;
        }
        profile = profileRepo.getProfile(profileUID);
    }

    public LiveData<Profile> getProfile() {
        return this.profile;
    }
}
