package com.example.jbs.room;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Profile {
    @PrimaryKey
    @NonNull
    @SerializedName("ProfileUID")
    @Expose
    private String ProfileUID;
    @SerializedName("ProfilePhone")
    @Expose
    private String ProfilePhone;
    @SerializedName("ProfileEmail")
    @Expose
    private String ProfileEmail;
    @SerializedName("ProfileUEN")
    @Expose
    private String ProfileUEN;
    @SerializedName("ProfileImageURL")
    @Expose
    private String ProfileImageURL;
    @SerializedName("ProfilePhoto")
    @Expose
    private byte[] Photo;
    @SerializedName("CommonName")
    @Expose
    private String CommonName;
    @SerializedName("FirstName")
    @Expose
    private String FirstName;
    @SerializedName("LastName")
    @Expose
    private String LastName;
    @SerializedName("DateOfBirth")
    @Expose
    private String DoB;
    @SerializedName("Gender")
    @Expose
    private String Gender;
    @SerializedName("BuildingName")
    @Expose
    private String BuildingName;
    @SerializedName("Address1")
    @Expose
    private String Address1;

    public String getProfilePhone() {
        return ProfilePhone;
    }

    public String getProfileEmail() {
        return ProfileEmail;
    }

    public String getProfileUEN() {
        return ProfileUEN;
    }

    public String getProfileImageURL() {
        return ProfileImageURL;
    }

    public byte[] getPhoto() {
        return Photo;
    }

    public String getCommonName() {
        return CommonName;
    }

    public String getFirstName() {
        return FirstName;
    }

    public String getLastName() {
        return LastName;
    }

    public String getDoB() {
        return DoB;
    }

    public String getGender() {
        return Gender;
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

    public String getDrivingLicenseClass() {
        return DrivingLicenseClass;
    }

    public String getDrivingLicenseDate() {
        return DrivingLicenseDate;
    }

    public String getReligion() {
        return Religion;
    }

    public String getCityCode() {
        return CityCode;
    }

    public String getCountryCode() {
        return CountryCode;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    @SerializedName("Address2")
    @Expose
    private String Address2;
    @SerializedName("PostalCode")
    @Expose
    private String PostalCode;
    @SerializedName("DrivingLicenseClass")
    @Expose
    private String DrivingLicenseClass;
    @SerializedName("DrivingLicenseDate")
    @Expose
    private String DrivingLicenseDate;
    @SerializedName("Religion")
    @Expose
    private String Religion;
    @SerializedName("CityCode")
    @Expose
    private String CityCode;
    @SerializedName("CountryCode")
    @Expose
    private String CountryCode;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("updateAt")
    @Expose
    private String updatedAt;

    private Date lastRefresh;
    @NonNull
    public String getProfileUID() {
        return ProfileUID;
    }

    public Date getLastRefresh() {
        return lastRefresh;
    }

    public void setLastRefresh(Date lastRefresh) {
        this.lastRefresh = lastRefresh;
    }
    public void setProfileUID(@NonNull String profileUID) {
        ProfileUID = profileUID;
    }

    public void setProfilePhone(String profilePhone) {
        ProfilePhone = profilePhone;
    }

    public void setProfileEmail(String profileEmail) {
        ProfileEmail = profileEmail;
    }

    public void setProfileUEN(String profileUEN) {
        ProfileUEN = profileUEN;
    }

    public void setProfileImageURL(String profileImageURL) {
        ProfileImageURL = profileImageURL;
    }

    public void setPhoto(byte[] photo) {
        Photo = photo;
    }

    public void setCommonName(String commonName) {
        CommonName = commonName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public void setDoB(String doB) {
        DoB = doB;
    }

    public void setGender(String gender) {
        Gender = gender;
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

    public void setDrivingLicenseClass(String drivingLicenseClass) {
        DrivingLicenseClass = drivingLicenseClass;
    }

    public void setDrivingLicenseDate(String drivingLicenseDate) {
        DrivingLicenseDate = drivingLicenseDate;
    }

    public void setReligion(String religion) {
        Religion = religion;
    }

    public void setCityCode(String cityCode) {
        CityCode = cityCode;
    }

    public void setCountryCode(String countryCode) {
        CountryCode = countryCode;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Profile() {
    }

    public Profile(long id, String profileUID, String profilePhone, String profileEmail,
                   String profileUEN, byte[] photo, String commonName, String firstName,
                   String lastName, String doB, String gender, String buildingName, String address1,
                   String address2, String postalCode, String cityCode, String countryCode, String sysDateCreate,
                   String sysDateUpdate) {
        ProfileUID = profileUID;
        ProfilePhone = profilePhone;
        ProfileEmail = profileEmail;
        ProfileUEN = profileUEN;
        Photo = photo;
        CommonName = commonName;
        FirstName = firstName;
        LastName = lastName;
        DoB = doB;
        Gender = gender;
        BuildingName = buildingName;
        Address1 = address1;
        Address2 = address2;
        PostalCode = postalCode;
        CityCode = cityCode;
        CountryCode = countryCode;
        updatedAt = sysDateUpdate;
        createdAt = sysDateCreate;
    }
}
