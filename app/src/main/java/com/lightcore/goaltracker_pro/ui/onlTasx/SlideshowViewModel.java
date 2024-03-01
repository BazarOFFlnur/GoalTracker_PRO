package com.lightcore.goaltracker_pro.ui.onlTasx;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.lightcore.goaltracker_pro.DataSource.GetFirebase;
import com.lightcore.goaltracker_pro.DoMain.GetDataFrbs;
import com.lightcore.goaltracker_pro.DoMain.SetDataFirebase;
import com.lightcore.goaltracker_pro.ui.Model.DataModel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.android.scopes.ViewModelScoped;
@HiltViewModel
public class SlideshowViewModel extends ViewModel {
GetDataFrbs getDataFrbs;
SetDataFirebase setDataFirebase;
HashMap<Integer, String> map = new HashMap<>();
    @Inject
    SlideshowViewModel(GetDataFrbs getDataFrbs, SetDataFirebase setDataFirebase){
        this.getDataFrbs=getDataFrbs;
        this.setDataFirebase=setDataFirebase;
    }

    public boolean set(Map<String, Object> map){
        return setDataFirebase.execute(map);
    }

    public LiveData<List<DataModel>> get() {
        MutableLiveData<List<DataModel>> data = new MutableLiveData<>();
        ArrayList<DataModel> list = new ArrayList<>();
        getDataFrbs.execute().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    int a = Integer.parseInt(document.get("TaskSteps").toString());
                    int b = Integer.parseInt(document.get("TaskCompleted").toString());
                    float prgrs2 = (((float) a / b) * 100);
                    String last = document.get("CompleteLast").toString();
                    String l = last.substring(last.lastIndexOf('_') + 1);
                    Calendar cal = Calendar.getInstance();
                    cal.setTimeInMillis(Long.parseLong(l));
                    Date d = cal.getTime();
                    DateFormat inputFormat = new SimpleDateFormat("yyyy.MM.dd' 'HH:mm:ss.SSS");
                    Log.d("LSD", document.get("TaskName").toString());
                    list.add(new DataModel(document.get("TaskName").toString(), document.get("TaskSteps").toString(),
                            document.get("TaskCompleted").toString() + "     " + (int) prgrs2 + "% ", inputFormat.format(d), (int) prgrs2));
                    map.put(list.size()-1, document.getId());
                    Log.d("asd", list.size()+map.values().toString());
                }
                data.setValue(list);
            } else {
                Log.d("ERR", Objects.requireNonNull(Objects.requireNonNull(task.getException()).getMessage()));
            }
        });
        return data;
    }
}