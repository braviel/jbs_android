package com.example.jbs.repo;

import com.example.jbs.activity.ViewProfileFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class FragmentModule {
    @ContributesAndroidInjector
    abstract ViewProfileFragment contributeUserProfileFragment();
}