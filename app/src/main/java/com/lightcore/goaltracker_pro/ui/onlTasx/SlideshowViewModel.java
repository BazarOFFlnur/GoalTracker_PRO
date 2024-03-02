package com.lightcore.goaltracker_pro.ui.onlTasx;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.lightcore.goaltracker_pro.DoMain.CompleteTask;
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

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class SlideshowViewModel extends ViewModel {
GetDataFrbs getDataFrbs;
SetDataFirebase setDataFirebase;
CompleteTask completeTask;
HashMap<String, Integer> map = new HashMap<>();
List<QueryDocumentSnapshot> documentSnapshot = new ArrayList<>();
MutableLiveData<List<DataModel>> data = new MutableLiveData<>();
    @Inject
    SlideshowViewModel(GetDataFrbs getDataFrbs, SetDataFirebase setDataFirebase, CompleteTask completeTask){
        this.getDataFrbs=getDataFrbs;
        this.setDataFirebase=setDataFirebase;
        this.completeTask = completeTask;
    }

    public boolean set(Map<String, Object> map){
        return setDataFirebase.execute(map);
    }

    public LiveData<List<DataModel>> get() {
        ArrayList<DataModel> list = new ArrayList<>();
        getDataFrbs.execute().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                documentSnapshot.clear();
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
                    documentSnapshot.add(document);
                    Log.d("asd", list.size()+map.values().toString());
                    Log.d("asd", list.size()+String.valueOf(map.hashCode()));
                }
                data.setValue(list);
                Log.d("HashdataVM", String.valueOf(data.getValue().hashCode()));
            } else {
                Log.d("ERR", Objects.requireNonNull(Objects.requireNonNull(task.getException()).getMessage()));
            }
        });
        return data;
    }

    public boolean ItemComplete(int id){
            if (0 <= id && id < documentSnapshot.size()) {
                QueryDocumentSnapshot doc = documentSnapshot.get(id);
                Log.d("DocID", doc.getData().get("TaskName").toString()+ doc.getId());
                return completeTask.execute(doc);
            }
        return false;
    }
}