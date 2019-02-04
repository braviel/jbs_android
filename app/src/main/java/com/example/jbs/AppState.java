package com.example.jbs;

import android.content.Context;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.io.Console;
import java.io.PrintStream;

class AppState {
    private static final AppState ourInstance = new AppState();
    TelephonyManager telephonyManager;

    static AppState getInstance() {
        return ourInstance;
    }

    private AppState() {
    }

    public String GetPhoneNumber(Context ctx) {
        String myNumber = "";
        try {
            telephonyManager = (TelephonyManager) ctx.getSystemService(Context.TELEPHONY_SERVICE);
            myNumber = telephonyManager.getLine1Number();

        } catch (SecurityException se) {
            Log.e("Permission", se.getMessage());
        }
        return myNumber;
    }
}
