package com.lightcore.goaltracker_pro.DataSource;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;

public class SetSubsFirebase {
    private final FirebaseFirestore fdb = FirebaseFirestore.getInstance();
    public boolean set(Map<String, Object> map){
        CollectionReference ref = fdb.collection("subtasx");
        return ref.add(map).isSuccessful();
    }
}
