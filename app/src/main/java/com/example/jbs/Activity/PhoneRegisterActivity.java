package com.example.jbs.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import com.example.jbs.R;
import com.google.android.material.textfield.TextInputEditText;

public class PhoneRegisterActivity extends AppCompatActivity {
    final int REQUEST_READ_PHONE_STATE = 1;
    private TextInputEditText tvPhoneNumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_register);
        tvPhoneNumber = findViewById(R.id.tvPhoneNumber);
        Toast.makeText(this, "Init Permission", Toast.LENGTH_LONG).show();
        CheckPermission(this, Manifest.permission.READ_PHONE_STATE, REQUEST_READ_PHONE_STATE);
    }

    private void CheckPermission(Activity activity, String perm, int MY_REQUEST_CODE) {

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
            getPhoneNo();
        }
    }
    private void getPhoneNo() {
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)
                == PackageManager.PERMISSION_GRANTED) {
            TelephonyManager tMgr = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
            String phoneNo = tMgr.getLine1Number();
            if (phoneNo.equals("")) {
                phoneNo = tMgr.getSubscriberId();
            }
            Toast.makeText(this, "Phone No: " + phoneNo, Toast.LENGTH_LONG).show();
            tvPhoneNumber.setText(phoneNo);
        } else {
            Toast.makeText(this, "No Permission", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch(requestCode) {
            case REQUEST_READ_PHONE_STATE: {
                if (grantResults.length > 0 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    TelephonyManager tMgr = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
                    getPhoneNo();
                } else {
                    //DENIED
                }
            }
        }
    }
}
