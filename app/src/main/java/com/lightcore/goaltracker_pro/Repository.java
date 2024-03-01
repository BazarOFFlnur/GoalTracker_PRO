//package com.lightcore.goaltracker_pro;
//
//import androidx.lifecycle.LiveData;
//import androidx.lifecycle.MutableLiveData;
//
//import com.google.firebase.firestore.FirebaseFirestore;
//import com.google.firebase.firestore.QueryDocumentSnapshot;
//import com.lightcore.goaltracker_pro.ui.Model.DataModel;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class Repository {
//    private FirebaseFirestore db = FirebaseFirestore.getInstance();
//    private MutableLiveData<List<DataModel>> mutableLiveData = new MutableLiveData<>();
//
//    public LiveData<List<DataModel>> getData() {
//        db.collection("tasx").get().addOnCompleteListener(task -> {
//            if (task.isSuccessful()) {
//                List<DataModel> list = new ArrayList<>();
//                for (QueryDocumentSnapshot document : task.getResult()) {
//                    DataModel model = document.toObject(DataModel.class);
//                    list.add(model);
//                }
//                mutableLiveData.setValue(list);
//            }
//        });
//        return mutableLiveData;
//    }
//}
