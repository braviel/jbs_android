package com.example.jbs.repo;

import android.media.Image;

import com.example.jbs.room.Group;
import com.example.jbs.room.Profile;

import java.util.List;

public class DataRepo {
    private static final DataRepo ourInstance = new DataRepo();

    private Image profileAvatar;
    private Profile profile;
    private List<Group> groups;
    private List<Image> groupImages;
    public static DataRepo getInstance() {
        return ourInstance;
    }

    private DataRepo() {
    }
    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }
    public List<Group> getGroups() {
        return groups;
    }
    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }

    public void setProfileAvatar(Image profileAvatar) {
        this.profileAvatar = profileAvatar;
    }

    public void setGroupImages(List<Image> groupImages) {
        this.groupImages = groupImages;
    }

    public Image getProfileAvatar() {
        return profileAvatar;
    }

    public List<Image> getGroupImages() {
        return groupImages;
    }
}
