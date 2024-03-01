package com.lightcore.goaltracker_pro.DoMain;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QuerySnapshot;
import com.lightcore.goaltracker_pro.DataSource.GetFirebase;



public class GetDataFrbs {

     GetFirebase getFirebase;

    public GetDataFrbs(GetFirebase getFirebase) {
        this.getFirebase = getFirebase;
    }

    public Task<QuerySnapshot> execute(){
        return getFirebase.getData();
    }

}