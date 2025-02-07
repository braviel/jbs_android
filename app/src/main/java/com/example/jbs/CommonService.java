package com.example.jbs;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

//import javax.inject.Singleton;

import java.io.IOException;

import androidx.core.app.ActivityCompat;
//import dagger.Provides;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CommonService {
    private static final String TAG = CommonService.class.getSimpleName();
    private static final CommonService ourInstance = new CommonService();
    public static String SERVICE_BASE_URL = "https://jbs-bo.herokuapp.com/";
//    private static String SERVICE_BASE_URL = "http://vn-hcm3623:5000/";
    private static String TOKEN="eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9" +
        ".eyJpZCI6MSwibmFtZSI6IkFudGhvbnkgVmFsaWQgVXNlciIsImlhdCI6MTQyNTQ3MzUzNX0" +
        ".Y_uFBFnqtF22fFuCd8bHP8VYrFKtn_Jmy9z1clHo_6Y";
    public static CommonService getInstance() {
        return ourInstance;
    }
    private Retrofit retrofit;
    private Gson gson;
    private CommonService() {

    }
    public interface OnRequestPermissionListener{
        void onPermissionRequested(int REQ_CODE);
    }
    public static boolean hasPermissions(Context context, String... permissions) {
        if(context != null && permissions != null) {
            for (String permission : permissions) {
                if(ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }
    public static void requestPermission(Activity activity, String[] perms, int MY_REQUEST_CODE, OnRequestPermissionListener listener){
        if(!CommonService.hasPermissions(activity, perms)) {
//            if(ActivityCompat.shouldShowRequestPermissionRationale(activity, perm)) {
//                // Show explaination
//                ActivityCompat.requestPermissions(activity, new String[]{perm}, MY_REQUEST_CODE);
//            } else {
                // Request for permission
                ActivityCompat.requestPermissions(activity, perms, MY_REQUEST_CODE);
//            }
        } else {
            //Granted
            listener.onPermissionRequested(MY_REQUEST_CODE);
        }
    }
    public Retrofit initWebservice() {
        if(gson == null) {
            gson = new GsonBuilder().create();
        }
        if(retrofit == null) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            //
            Interceptor authInterceptor = new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request request = chain.request().newBuilder().addHeader("Authorization",
                            TOKEN).build();
                    return  chain.proceed(request);
                }
            };
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder
                .addInterceptor(logging)
                .addInterceptor(authInterceptor);

            OkHttpClient client = builder.build();

            Log.i(TAG, "Init Retrofit2 with url " + SERVICE_BASE_URL);
            retrofit = new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .baseUrl(SERVICE_BASE_URL)
                    .client(client)
                    .build();
        }
        return retrofit;
    }
    public void displayImage(Context context, ImageView imageView, String encodedURL) {
        Glide.with(context)
                .load(encodedURL)
                .error(R.drawable.unknown_user)
                .placeholder(R.drawable.unknown_user)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .apply(RequestOptions.circleCropTransform())
                .into(imageView)
        ;

    }
//    public ProfileWebService createService() {
//        return retrofit.create(ProfileWebService.class);
//    }
//    public <T> T createService(final Class<T> service) {
//        return retrofit.create(service);
//    }
}
