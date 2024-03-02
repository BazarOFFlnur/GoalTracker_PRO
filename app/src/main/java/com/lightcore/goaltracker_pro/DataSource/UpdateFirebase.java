package com.lightcore.goaltracker_pro.DataSource;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.Filter;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.lightcore.goaltracker_pro.ui.Model.DataModel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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
        if (a+1<b){
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
