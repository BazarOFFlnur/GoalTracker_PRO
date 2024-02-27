package com.lightcore.goaltracker_pro.DoMain;


import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QuerySnapshot;

import javax.inject.Inject;

import dagger.Provides;
public interface GetFirebaseInterface {
    public Task<QuerySnapshot> getData();
}
