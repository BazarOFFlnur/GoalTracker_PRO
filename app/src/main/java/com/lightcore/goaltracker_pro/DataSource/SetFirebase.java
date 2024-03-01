package com.lightcore.goaltracker_pro.DataSource;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SetFirebase {
    private final FirebaseFirestore fdb = FirebaseFirestore.getInstance();
    public boolean setData(Map<String, Object> map){
        CollectionReference ref = fdb.collection("tasx");
        return  ref.document().set(map).isSuccessful();
    }
}
