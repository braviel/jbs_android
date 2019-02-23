package com.example.jbs.controller;

import com.example.jbs.room.Profile;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileController implements Callback<Profile> {
    @Override
    public void onResponse(Call<Profile> call, Response<Profile> response) {

    }

    @Override
    public void onFailure(Call<Profile> call, Throwable t) {

    }
}

