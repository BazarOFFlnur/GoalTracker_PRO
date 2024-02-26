package com.lightcore.goaltracker_pro;

import android.app.Application;
import android.util.Log;

import com.lightcore.main.GetFirebaseInterface;

import javax.inject.Inject;

import dagger.hilt.android.HiltAndroidApp;

@HiltAndroidApp
public class App extends Application {
    @Inject
    GetFirebaseInterface getDatabaseInterface;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("MSG", getDatabaseInterface.toString());
    }
}

