package com.example.jbs.room;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class GroupMember {

    @PrimaryKey
    @NonNull
    @SerializedName("ID")
    @Expose
    private int ID;
    @SerializedName("GroupAdmin")
    @Expose
    private String GroupAdmin;
    @SerializedName("GroupUID")
    @Expose
    private String GroupUID;
    @SerializedName("ProfileUID")
    @Expose
    private String ProfileUID;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("updateAt")
    @Expose
    private String updatedAt;

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setGroupAdmin(String groupAdmin) {
        GroupAdmin = groupAdmin;
    }

    public void setGroupUID(String groupUID) {
        GroupUID = groupUID;
    }

    public void setProfileUID(String profileUID) {
        ProfileUID = profileUID;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public int getID() {
        return ID;
    }

    public String getGroupAdmin() {
        return GroupAdmin;
    }

    public String getGroupUID() {
        return GroupUID;
    }

    public String getProfileUID() {
        return ProfileUID;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public GroupMember(int ID, String groupAdmin, String groupUID, String profileUID, String createdAt, String updatedAt) {
        this.ID = ID;
        GroupAdmin = groupAdmin;
        GroupUID = groupUID;
        ProfileUID = profileUID;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
