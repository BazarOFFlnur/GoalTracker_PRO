package com.lightcore.goaltracker_pro.DataSource;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.HashMap;
import java.util.Map;

public class UpdateFirebase {
    private final FirebaseFirestore fdb = FirebaseFirestore.getInstance();
    FirebaseAuth mAuth;
    String iid;
    public boolean updateFirebase(QueryDocumentSnapshot doc){
        DocumentReference ref = fdb.collection("tasx").document(doc.getId());
        int a = Integer.parseInt(doc.getData().get("TaskSteps").toString());
        int b = Integer.parseInt(doc.getData().get("TaskCompleted").toString());
        if ((a+1)<b){
            Map<String, Object> ps = new HashMap<>();
            ps.put("TaskSteps", a+1);
            ps.put("CompleteLast", doc.getData().get("CompleteLast").toString() + "_" + System.currentTimeMillis());
            Log.d("UPD", "ds");
            return ref.update(ps).isSuccessful();
        } else
            Log.d("Delete", "ds");
            return ref.delete().isSuccessful();
    }
}
