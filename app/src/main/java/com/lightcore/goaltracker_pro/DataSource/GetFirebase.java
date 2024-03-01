package com.lightcore.goaltracker_pro.DataSource;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.Filter;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Objects;


public class GetFirebase{
    private final FirebaseFirestore fdb = FirebaseFirestore.getInstance();
    FirebaseAuth mAuth;

    public Task<QuerySnapshot> getData() {
        mAuth = FirebaseAuth.getInstance();
        CollectionReference collection = fdb.collection("tasx");
        String uid = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
        String email = mAuth.getCurrentUser().getEmail();
        return collection.where(Filter.or(
                Filter.equalTo("uid", uid),
                Filter.greaterThanOrEqualTo("u2id", email)
        )).get();
    }

}
