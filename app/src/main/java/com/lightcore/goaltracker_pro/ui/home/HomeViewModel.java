package com.lightcore.goaltracker_pro.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.lightcore.goaltracker_pro.ui.Model.DataModel;
import com.lightcore.goaltracker_pro.Repository;

import java.util.List;

public class HomeViewModel extends ViewModel {

    private Repository repository;

    public HomeViewModel() {
       repository = new Repository();
    }

    public LiveData<List<DataModel>> getText() {
        return repository.getData();
    }
}