package com.lightcore.reposit;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.Filter;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.lightcore.main.DataModelDomain;
import com.lightcore.main.GetFirebaseInterface;
import com.lightcore.main.UserModel;

public class GetFirebase implements GetFirebaseInterface {


    @Override
    public DataModelDomain getData(Object firebaseFirestore, UserModel userModel) {
        FirebaseFirestore fdb = (FirebaseFirestore) firebaseFirestore;
        final DataModelDomain[] dataModelDomain = new DataModelDomain[1];
        CollectionReference collection = fdb.collection("tasx");
        String uid = userModel.getUid();
        String email = userModel.getEmail();
        Query fquery = collection.where(Filter.or(
                    Filter.equalTo("uid", uid),
                    Filter.greaterThanOrEqualTo("u2id", email)
            ));
        fquery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                for(QueryDocumentSnapshot documentSnapshot : task.getResult()){
                    Log.i("FirebaseFirestoreResponsed", documentSnapshot.get("TaskName").toString());
                    dataModelDomain[0] = (DataModelDomain) documentSnapshot.getData();
                }
            }
        });
        return dataModelDomain[0];
    }

}
