package com.example.jbs;

import android.app.Activity;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class CommonService {
    private static final CommonService ourInstance = new CommonService();

    static CommonService getInstance() {
        return ourInstance;
    }

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
}
