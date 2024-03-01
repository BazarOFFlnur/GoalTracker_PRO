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
    public boolean updateFirebase(int i){
        fdb.collection("tasx").where(Filter.or(
                Filter.equalTo("uid", mAuth.getUid().toString()),
                Filter.greaterThanOrEqualTo("u2id", mAuth.getUid().toString()
                ), Filter.equalTo("TaskID", i)
        )).get();
        final Integer[] a = new Integer[1];
        final Integer[] b = new Integer[1];
        final String[] dat = new String[1];
        return true;
    }
}
