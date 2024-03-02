package com.lightcore.goaltracker_pro.DoMain;

import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.lightcore.goaltracker_pro.DataSource.UpdateFirebase;

public class CompleteTask {
    UpdateFirebase updateFirebase;
    public CompleteTask(UpdateFirebase updateFirebase){
        this.updateFirebase=updateFirebase;
    }
    public boolean execute(QueryDocumentSnapshot doc){
        return updateFirebase.updateFirebase(doc);
    }
}
