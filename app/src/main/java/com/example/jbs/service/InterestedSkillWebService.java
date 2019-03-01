package com.example.jbs.service;

import com.example.jbs.room.InterestedArea;
import com.example.jbs.room.InterestedSkill;
import com.example.jbs.room.Profile;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface InterestedSkillWebService {
    @GET("/interestedskill")
    Call<List<InterestedSkill>> getSkills();

    @GET("/interestedskill/{id}")
    Call<InterestedSkill> getSkill(@Path("id") int skillCode);

    @POST("/interestedskill")
    Call<InterestedSkill> createSkill(@Body InterestedSkill skill);

    @PUT("/interestedskill/{id}")
    Call<InterestedSkill> updateSkill(@Path("id") int skillCode, @Body InterestedSkill skill);
}
