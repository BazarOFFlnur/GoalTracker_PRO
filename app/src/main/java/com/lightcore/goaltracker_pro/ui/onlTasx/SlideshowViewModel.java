package com.lightcore.goaltracker_pro.ui.onlTasx;

import static com.android.volley.VolleyLog.TAG;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.lightcore.goaltracker_pro.DoMain.CompleteTask;
import com.lightcore.goaltracker_pro.DoMain.GetDataFrbs;
import com.lightcore.goaltracker_pro.DoMain.GetSubs;
import com.lightcore.goaltracker_pro.DoMain.SetDataFirebase;
import com.lightcore.goaltracker_pro.DoMain.SetSubTasx;
import com.lightcore.goaltracker_pro.ui.Model.DataGetModelTasks;
import com.lightcore.goaltracker_pro.ui.Model.SubTasks;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class SlideshowViewModel extends ViewModel {
GetDataFrbs getDataFrbs;
SetDataFirebase setDataFirebase;
CompleteTask completeTask;
GetSubs getSubs;
DocumentReference ref;
SetSubTasx setSubTasx;
String docIdForElement, subdocID;
List<QueryDocumentSnapshot> documentSnapshot = new ArrayList<>();
List<QueryDocumentSnapshot> subdocsnap = new ArrayList<>();
MutableLiveData<List<DataGetModelTasks>> data = new MutableLiveData<>();
MutableLiveData<List<SubTasks>> subTasks = new MutableLiveData<>();
    @Inject
    SlideshowViewModel(GetDataFrbs getDataFrbs, SetDataFirebase setDataFirebase, CompleteTask completeTask, GetSubs getSubs, SetSubTasx setSubTasx){
        this.getDataFrbs=getDataFrbs;
        this.setDataFirebase=setDataFirebase;
        this.completeTask = completeTask;
        this.getSubs=getSubs;
        this.setSubTasx=setSubTasx;
    }

    public void completeSubTask(int id){
        CollectionReference reference = FirebaseFirestore.getInstance().collection("subtasx");
        reference.document(subdocsnap.get(id).getId()).delete();
    }
    public boolean set(Map<String, Object> map){
        return setDataFirebase.execute(map);
    }

    public boolean setSubs(Map<String, Object> objectMap){
        objectMap.put("docID", docIdForElement);
        return setSubTasx.exec(objectMap);
    }

    public MutableLiveData<List<DataGetModelTasks>> get() {
        ArrayList<DataGetModelTasks> list = new ArrayList<>();
        final List<SubTasks>[] list1 = new List[]{new ArrayList<>()};
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
                    ArrayList<SubTasks> subTasksList = new ArrayList<>();
                    subTasksList.add(new SubTasks("", ""));
                    Log.d("LSD", document.get("TaskName").toString());
                    list.add(new DataGetModelTasks(document.get("TaskName").toString(), document.get("TaskSteps").toString(),
                            document.get("TaskCompleted").toString() + "     " + (int) prgrs2 + "% ", inputFormat.format(d), (int) prgrs2,
                            subTasksList.get(subTasksList.size() - 1)));
                    documentSnapshot.add(document);

//                    DataModel dataModel= document.toObject(DataModel.class);
//                    Object sd = document.get("SubTasks");
//                    list1.add(dataModel.getSubTasksList().get(list1.size()-1));
//                    Log.d("sd", list1.get(list1.size()-1).getName());
//                    Log.d("sddd", list1[0].stream().forEach(););
                    Log.d("asd", String.valueOf(list.size()));
                }
                data.setValue(list);
                Log.d("HashdataVM", String.valueOf(data.getValue().hashCode()));
            } else {
                Log.d("ERR", Objects.requireNonNull(Objects.requireNonNull(task.getException()).getMessage()));
            }
        });
        return data;
    }

    public MutableLiveData<List<SubTasks>> getSubsVM(){
        return subTasks;
    }
    public boolean ItemComplete(int id){
            if (0 <= id && id < documentSnapshot.size()) {
                QueryDocumentSnapshot doc = documentSnapshot.get(id);
                return completeTask.execute(doc);
            }
        return false;
    }
    public void setSubTasksId(int id){
//           subTasks.setValue((List<SubTasks>) documentSnapshot.get(id).get("SubTasks"));
        docIdForElement = documentSnapshot.get(id).getId();
        Log.w("sd", docIdForElement);
           ref = documentSnapshot.get(id).getReference();
           List<SubTasks> subTasksList = new ArrayList<>();
           getSubs.exec(docIdForElement).addOnCompleteListener(task -> {
               if (task.isSuccessful()) {
                   subTasksList.clear();
                   subdocsnap.clear();
                   for (QueryDocumentSnapshot d : task.getResult()) {
                       Log.d(TAG, d.getId() + " => " + d.getData());
                       subTasksList.add(new SubTasks(d.get("name").toString(), d.get("progress").toString()));
                       Log.d("Name+", d.get("name").toString());
                       Log.d("prg", d.get("progress").toString());
                       Log.d("stlsize", String.valueOf(subTasksList.size()));
                       subdocsnap.add(d);
                   }
                   subTasks.setValue(subTasksList);
               } else {
                   Log.d(TAG, "Error getting documents: ", task.getException());
               }
           });
//           Log.d("st= ", documentSnapshot.get(id).get("SubTasks").toString());
    }
}