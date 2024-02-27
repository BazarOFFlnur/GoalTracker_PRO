package com.lightcore.goaltracker_pro.DataSource;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.Filter;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.lightcore.goaltracker_pro.DoMain.DataModelDomain;
import com.lightcore.goaltracker_pro.DoMain.GetFirebaseInterface;

import java.util.ArrayList;
import java.util.List;

public class GetFirebase implements GetFirebaseInterface {
    private FirebaseFirestore fdb = FirebaseFirestore.getInstance();
    FirebaseAuth mAuth;


    @Override
    public Task<QuerySnapshot> getData() {
        mAuth = FirebaseAuth.getInstance();
        CollectionReference collection = fdb.collection("tasx");
        String uid = mAuth.getCurrentUser().getUid();
        String email = mAuth.getCurrentUser().getEmail();
        return collection.where(Filter.or(
                Filter.equalTo("uid", uid),
                Filter.greaterThanOrEqualTo("u2id", email)
        )).get();
    }

}
