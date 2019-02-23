package com.example.jbs;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.util.Log;

import com.example.jbs.service.ProfileWebService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

//import javax.inject.Singleton;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
//import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CommonService {
    private static final String TAG = CommonService.class.getSimpleName();
    private static final CommonService ourInstance = new CommonService();
    private static String BASE_URL = "https://jbs-bo.herokuapp.com/";
    private static String LOCAL_URL = "http://vn-hcm3623:5000/";

    public static CommonService getInstance() {
        return ourInstance;
    }
    private Retrofit retrofit;
    private Gson gson;
    private CommonService() {

    }
    public interface OnRequestPermissionListener{
        void onRequested();
    }

    public static void requestPermission(Activity activity, String perm, int MY_REQUEST_CODE, OnRequestPermissionListener listener){
        if(ContextCompat.checkSelfPermission(activity, perm)
                != PackageManager.PERMISSION_GRANTED) {
            if(ActivityCompat.shouldShowRequestPermissionRationale(activity, perm)) {
                // Show explaination
                ActivityCompat.requestPermissions(activity, new String[]{perm}, MY_REQUEST_CODE);
            } else {
                // Request for permission
                ActivityCompat.requestPermissions(activity, new String[]{perm}, MY_REQUEST_CODE);
            }
        } else {
            //Granted
            listener.onRequested();
        }
    }
    public void initWebservice() {
        if(gson == null) {
            gson = new GsonBuilder().create();
        }
        if(retrofit == null) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient client = new OkHttpClient.Builder().addInterceptor(logging).build();

            Log.i(TAG, "Init Retrofit2 with url " + BASE_URL);
            retrofit = new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .baseUrl(BASE_URL)
                    .client(client)
                    .build();
        }
    }
    public ProfileWebService provideApiWebservice() {
        return retrofit.create(ProfileWebService.class);
    }
}
