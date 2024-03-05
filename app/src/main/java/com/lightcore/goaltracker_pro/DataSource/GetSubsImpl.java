package com.lightcore.goaltracker_pro.DataSource;

import android.util.Log;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class GetSubsImpl {
    private final FirebaseFirestore fdb = FirebaseFirestore.getInstance();
    public Task<QuerySnapshot> ex(String documentReference){
        CollectionReference collection = fdb.collection("subtasx");

        return collection.whereEqualTo("docID", documentReference).get();
    }
}
