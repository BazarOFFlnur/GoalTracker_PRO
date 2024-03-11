package com.lightcore.goaltracker_pro.DataSource;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.Transaction;
import com.lightcore.goaltracker_pro.DoMain.UpdateCallback;

import java.util.HashMap;
import java.util.Map;

public class UpdateFirebase {
    private final FirebaseFirestore fdb = FirebaseFirestore.getInstance();

    public Task<DocumentSnapshot> updateFirebase(QueryDocumentSnapshot doc, UpdateCallback callback) {
        DocumentReference ref = fdb.collection("tasx").document(doc.getId());

        fdb.runTransaction(new Transaction.Function<Void>() {
            @Override
            public Void apply(Transaction transaction) throws FirebaseFirestoreException {
                DocumentSnapshot snapshot = transaction.get(ref);

                int a = Integer.parseInt(snapshot.get("TaskSteps").toString());
                int b = Integer.parseInt(snapshot.get("TaskCompleted").toString());

                if ((a + 1) < b) {
                    Map<String, Object> ps = new HashMap<>();
                    ps.put("TaskSteps", a + 1);
                    ps.put("CompleteLast", snapshot.get("CompleteLast").toString() + "_" + System.currentTimeMillis());
                    transaction.update(ref, ps);
                } else {
                    transaction.delete(ref);
                }

                return null;
            }
        }).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    ref.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot updatedSnapshot) {
                            callback.onUpdateSuccess(updatedSnapshot);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            callback.onUpdateFailure(e);
                        }
                    });
                } else {
                    callback.onUpdateFailure(new Exception("Transaction failed"));
                }
            }
        });
        return null;
    }
}

