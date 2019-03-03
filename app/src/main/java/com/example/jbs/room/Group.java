package com.example.jbs.room;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Group {
    @NonNull
    public String getGroupUID() {
        return GroupUID;
    }

    public String getGroupName() {
        return GroupName;
    }

    public String getGroupPhone() {
        return GroupPhone;
    }

    public String getGroupEmail() {
        return GroupEmail;
    }

    public byte[] getGroupLogo() {
        return GroupLogo;
    }

    public String getGroupLogoURL() {
        return GroupLogoURL;
    }

    public String getBuildingName() {
        return BuildingName;
    }

    public String getAddress1() {
        return Address1;
    }

    public String getAddress2() {
        return Address2;
    }

    public String getPostalCode() {
        return PostalCode;
    }

    public void setGroupUID(@NonNull String groupUID) {
        GroupUID = groupUID;
    }

    public void setGroupName(String groupName) {
        GroupName = groupName;
    }

    public void setGroupPhone(String groupPhone) {
        GroupPhone = groupPhone;
    }

    public void setGroupEmail(String groupEmail) {
        GroupEmail = groupEmail;
    }

    public void setGroupLogo(byte[] groupLogo) {
        GroupLogo = groupLogo;
    }

    public void setGroupLogoURL(String groupLogoURL) {
        GroupLogoURL = groupLogoURL;
    }

    public void setBuildingName(String buildingName) {
        BuildingName = buildingName;
    }

    public void setAddress1(String address1) {
        Address1 = address1;
    }

    public void setAddress2(String address2) {
        Address2 = address2;
    }

    public void setPostalCode(String postalCode) {
        PostalCode = postalCode;
    }

    @PrimaryKey
    @NonNull
    @SerializedName("GroupUID")
    @Expose
    private String GroupUID;
    @SerializedName("GroupName")
    @Expose
    private String GroupName;
    @SerializedName("GroupPhone")
    @Expose
    private String GroupPhone;
    @SerializedName("GroupEmail")
    @Expose
    private String GroupEmail;
    @SerializedName("GroupLogo")
    @Expose
    private byte[] GroupLogo;
    @SerializedName("GroupLogoURL")
    @Expose
    private String GroupLogoURL;
    @SerializedName("BuildingName")
    @Expose
    private String BuildingName;
    @SerializedName("Address1")
    @Expose
    private String Address1;
    @SerializedName("Address2")
    @Expose
    private String Address2;
    @SerializedName("PostalCode")
    @Expose
    private String PostalCode;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("updateAt")
    @Expose
    private String updatedAt;

    @SerializedName("GroupMembers")
    @Expose
    List<GroupMember> GroupMembers;

    public Group() {
    }

    public Group(@NonNull String groupUID, String groupName, String groupPhone, String groupEmail,
                 byte[] groupLogo, String groupLogoURL, String buildingName, String address1,
                 String address2, String postalCode) {
        GroupUID = groupUID;
        GroupName = groupName;
        GroupPhone = groupPhone;
        GroupEmail = groupEmail;
        GroupLogo = groupLogo;
        GroupLogoURL = groupLogoURL;
        BuildingName = buildingName;
        Address1 = address1;
        Address2 = address2;
        PostalCode = postalCode;
    }
    public List<GroupMember> getGroupMembers() {
        return GroupMembers;
    }

    public void setGroupMembers(List<GroupMember> groupMembers) {
        GroupMembers = groupMembers;
    }
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }
}
