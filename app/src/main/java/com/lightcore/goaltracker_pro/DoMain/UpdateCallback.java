package com.lightcore.goaltracker_pro.DoMain;

import com.google.firebase.firestore.DocumentSnapshot;

public interface UpdateCallback {
    void onUpdateSuccess(DocumentSnapshot updatedSnapshot);

    void onUpdateFailure(Exception e);
}
