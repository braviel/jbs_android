package com.example.jbs.service;

import com.example.jbs.room.Profile;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface GroupWebService {
    @GET("/group")
    Call<List<Profile>> getProfiles();

    @GET("/group/{id}")
    Call<Profile> getProfile(@Path("id") String profileUID);

    @POST("/group")
    Call<Profile> createProfile(@Body Profile user);

    @PUT("/group/{id}")
    Call<Profile> updateProfile(@Path("id") String profileUID, @Body Profile profile);
}
