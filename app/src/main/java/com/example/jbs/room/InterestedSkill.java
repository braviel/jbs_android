package com.example.jbs.room;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class InterestedSkill {
    @PrimaryKey
    @NonNull
    @SerializedName("SkillCode")
    @Expose
    private int SkillCode;
    @SerializedName("SkillName")
    @Expose
    private String SkillName;
    @SerializedName("AreaCode")
    @Expose
    private String AreaCode;

    public InterestedSkill(@NonNull int skillCode, String skillName) {
        SkillCode = skillCode;
        SkillName = skillName;
    }

    @NonNull
    public int getSkillCode() {
        return SkillCode;
    }

    public void setSkillCode(@NonNull int skillCode) {
        SkillCode = skillCode;
    }

    public void setSkillName(String skillName) {
        SkillName = skillName;
    }

    public String getSkillName() {
        return SkillName;
    }

    public void setAreaCode(String areaCode) {
        AreaCode = areaCode;
    }

    public String getAreaCode() {
        return AreaCode;
    }
}
