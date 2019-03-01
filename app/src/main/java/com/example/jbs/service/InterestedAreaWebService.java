package com.example.jbs.service;

import com.example.jbs.room.InterestedArea;
import com.example.jbs.room.Profile;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface InterestedAreaWebService {
    @GET("/interestedarea")
    Call<List<InterestedArea>> getAreas();

    @GET("/interestedarea/{id}")
    Call<InterestedArea> getArea(@Path("id") int areaCode);

    @POST("/interestedarea")
    Call<InterestedArea> createArea(@Body InterestedArea user);

    @PUT("/interestedarea/{id}")
    Call<InterestedArea> updateArea(@Path("id") int areaCode, @Body InterestedArea area);
}
