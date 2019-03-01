package com.example.jbs.room;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class InterestedArea {
    @PrimaryKey
    @NonNull
    @SerializedName("AreaCode")
    @Expose
    private int AreaCode;
    @SerializedName("AreaName")
    @Expose
    private String AreaName;

    public InterestedArea(@NonNull int areaCode, String areaName) {
        AreaCode = areaCode;
        AreaName = areaName;
    }

    @NonNull
    public int getAreaCode() {
        return AreaCode;
    }

    public void setAreaCode(@NonNull int areaCode) {
        AreaCode = areaCode;
    }

    public void setAreaName(String areaName) {
        AreaName = areaName;
    }

    public String getAreaName() {
        return AreaName;
    }
}
