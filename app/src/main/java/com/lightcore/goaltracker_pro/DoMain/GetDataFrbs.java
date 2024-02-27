package com.lightcore.goaltracker_pro.DoMain;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QuerySnapshot;
import com.lightcore.goaltracker_pro.ui.Model.DataModel;

import java.util.List;

import javax.inject.Inject;

public class GetDataFrbs {
    private final GetFirebaseInterface getFirebaseInterface;
    @Inject
    public GetDataFrbs(GetFirebaseInterface getFirebaseInterface) {
        this.getFirebaseInterface = getFirebaseInterface;
    }

    public Task<QuerySnapshot> execute(){
        return getFirebaseInterface.getData();
    }

}