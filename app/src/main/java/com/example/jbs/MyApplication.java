package com.example.jbs;

import android.app.Application;
import android.content.Context;

//import javax.inject.Inject;

import androidx.fragment.app.FragmentActivity;
//import dagger.android.DaggerApplication;
//import dagger.android.DaggerApplication_MembersInjector;
//import dagger.android.DispatchingAndroidInjector;
//import dagger.android.HasActivityInjector;
//import dagger.android.support.HasSupportFragmentInjector;

public class MyApplication extends Application
//        implements HasActivityInjector
{
    public static final String TAG = MyApplication.class.getSimpleName();
//    @Inject
//    DispatchingAndroidInjector<FragmentActivity> dispatchingAndroidInjector;
    public static Context context;

    private static final MyApplication ourInstance = new MyApplication();

    static MyApplication getInstance() {
        return ourInstance;
    }

    public MyApplication() {

    }

    @Override
    public void onCreate() {
        super.onCreate();
//        this.initDagger();
        context = getApplicationContext();
    }

//    @Override
//    public DispatchingAndroidInjector<FragmentActivity> activityInjector() {
//        return dispatchingAndroidInjector;
//    }
//    private void initDagger(){
//        DaggerAppComponent.builder().application(this).build().inject(this);
//    }
}
