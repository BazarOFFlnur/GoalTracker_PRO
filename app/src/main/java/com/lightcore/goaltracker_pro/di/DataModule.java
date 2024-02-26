package com.lightcore.goaltracker_pro.di;

import com.lightcore.main.GetFirebaseInterface;
import com.lightcore.reposit.GetFirebase;

import dagger.Binds;
import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.android.internal.managers.ApplicationComponentManager;

@Module
@InstallIn(ApplicationComponentManager.class)
public class DataModule {
    @Binds
    GetFirebaseInterface bindFirebaseGet(GetFirebase getFirebase) {
        return null;
    }
}
