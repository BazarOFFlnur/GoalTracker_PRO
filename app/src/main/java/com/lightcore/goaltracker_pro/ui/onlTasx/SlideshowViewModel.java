package com.lightcore.goaltracker_pro.ui.onlTasx;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.lightcore.goaltracker_pro.DoMain.GetDataFrbs;
import com.lightcore.goaltracker_pro.ui.Model.DataModel;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class SlideshowViewModel extends ViewModel {
private final GetDataFrbs getDataFrbs;

    @Inject
    public SlideshowViewModel(GetDataFrbs getDataFrbs) {
        this.getDataFrbs = getDataFrbs;
    }

    public LiveData<List<DataModel>> get() {
        MutableLiveData<List<DataModel>> data = new MutableLiveData<>();
        getDataFrbs.execute().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                for (QueryDocumentSnapshot documentSnapshot : task.getResult()){
                    data.setValue((List<DataModel>) documentSnapshot);
                }
            }
        });
        return data;
    }
}