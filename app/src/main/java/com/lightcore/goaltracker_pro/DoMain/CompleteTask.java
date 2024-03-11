package com.lightcore.goaltracker_pro.DoMain;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.lightcore.goaltracker_pro.DataSource.UpdateFirebase;

public class CompleteTask {
    UpdateFirebase updateFirebase;
    public CompleteTask(UpdateFirebase updateFirebase){
        this.updateFirebase=updateFirebase;
    }
    public Task<DocumentSnapshot> execute(QueryDocumentSnapshot doc, UpdateCallback callback){
        return updateFirebase.updateFirebase(doc, callback);
    }
}
