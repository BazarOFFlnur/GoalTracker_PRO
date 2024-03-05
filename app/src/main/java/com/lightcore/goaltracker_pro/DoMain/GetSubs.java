package com.lightcore.goaltracker_pro.DoMain;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.QuerySnapshot;
import com.lightcore.goaltracker_pro.DataSource.GetSubsImpl;
import com.lightcore.goaltracker_pro.ui.Model.SubTasks;

import java.util.List;

public class GetSubs {
    GetSubsImpl getSubs;
    public GetSubs(GetSubsImpl getSubs){this.getSubs=getSubs;}
    public Task<QuerySnapshot> exec(String documentReference){
        return getSubs.ex(documentReference);
    }
}
