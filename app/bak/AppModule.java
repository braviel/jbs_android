package com.example.jbs.repo;

import android.app.Application;

import com.example.jbs.room.LocalDatabase;
import com.example.jbs.room.Profile;
import com.example.jbs.room.ProfileDao;
import com.example.jbs.room.ProfileRepository;
import com.example.jbs.service.ProfileWebService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.inject.Singleton;

import androidx.room.Room;
import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module(includes = ViewModelModule.class)
public class AppModule {

    // --- DATABASE INJECTION ---

    @Provides
    @Singleton
    LocalDatabase provideDatabase(Application application) {
        return Room.databaseBuilder(application,
                LocalDatabase.class, "LocalDatabase.db")
                .build();
    }

    @Provides
    @Singleton
    ProfileDao provideUserDao(LocalDatabase database) { return database.profileDao(); }

    // --- REPOSITORY INJECTION ---

    @Provides
    Executor provideExecutor() {
        return Executors.newSingleThreadExecutor();
    }

    @Provides
    @Singleton
    ProfileRepository provideUserRepository(ProfileWebService webservice, ProfileDao profileDao, Executor executor) {
        return new ProfileRepository(webservice, profileDao, executor);
    }

    // --- NETWORK INJECTION ---

    private static String BASE_URL = "https://jbs-bo.herokuapp.com/";

    @Provides
    Gson provideGson() { return new GsonBuilder().create(); }

    @Provides
    Retrofit provideRetrofit(Gson gson) {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(BASE_URL)
                .build();
        return retrofit;
    }

    @Provides
    @Singleton
    ProfileWebService provideApiWebservice(Retrofit restAdapter) {
        return restAdapter.create(ProfileWebService.class);
    }
}