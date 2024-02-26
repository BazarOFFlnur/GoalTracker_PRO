package com.lightcore.goaltracker_pro.ui.onlTasx;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.Filter;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.lightcore.goaltracker_pro.ui.Adapt.CustomAdapter;
import com.lightcore.goaltracker_pro.ui.Model.DataModel;
import com.lightcore.goaltracker_pro.R;
import com.lightcore.goaltracker_pro.databinding.FragmentSlideshowBinding;
import com.lightcore.goaltracker_pro.ui.stats.GalleryFragment;
import com.lightcore.main.GetFirebaseInterface;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class SlideshowFragment extends Fragment {
    @Inject
    GetFirebaseInterface getFirebaseInterface;

    private FragmentSlideshowBinding binding;
    private FirebaseAuth mAuth;
    private FirebaseFirestore fdb;
    ArrayList<DataModel> list2 = new ArrayList<>();
    long d = System.currentTimeMillis();
    CustomAdapter adapter;
    private ListView lv;
    String iid;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        SlideshowViewModel slideshowViewModel =
                new ViewModelProvider(this).get(SlideshowViewModel.class);
        Log.d("Fragment", getFirebaseInterface.toString());
        mAuth = FirebaseAuth.getInstance();
        binding = FragmentSlideshowBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        updateDB();
        fdb = FirebaseFirestore.getInstance();


        lv = root.findViewById(R.id.olv);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener((parent, view, position, id) -> onCompliteTask((int) id));
        lv.setOnItemLongClickListener((parent, view, position, id) -> {
            Bundle bundle = new Bundle();
            Fragment f = new GalleryFragment();
            bundle.putInt("id", (int) id);
            bundle.putInt("idd", (int) id);
            f.setArguments(bundle);
            Navigation.findNavController(view).navigate(R.id.nav_gallery);
            Log.d("OnLONGCLICK", "sda");
            return false;
        });
        ImageButton fab = root.findViewById(R.id.ofab);


        fab.setOnClickListener(view -> {
        LayoutInflater li = LayoutInflater.from(getContext());
        View promptsView = li.inflate(R.layout.add_dialog, null);
        AlertDialog.Builder mDialogBuilder = new AlertDialog.Builder(getContext());

        //Настраиваем prompt.xml для нашего AlertDialog:
        mDialogBuilder.setView(promptsView);
        //Настраиваем отображение поля для ввода текста в открытом диалоге:
        final EditText inputName = (EditText) promptsView.findViewById(R.id.input_text);
        final EditText inpuSteps = (EditText) promptsView.findViewById(R.id.taskSteps);
        final EditText inputCompleted = (EditText) promptsView.findViewById(R.id.taskComplete);
        final Switch sw = (Switch) promptsView.findViewById(R.id.onlSwitch);
        final EditText inputUID = (EditText) promptsView.findViewById(R.id.uid2);
        inputUID.setVisibility(View.INVISIBLE);
        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    inputUID.setVisibility(View.VISIBLE);
                } else if (!isChecked) {
                    inputUID.setVisibility(View.INVISIBLE);
                }
            }
        });
        //Настраиваем сообщение в диалоговом окне:
        mDialogBuilder
                .setCancelable(false)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                if (mAuth.getCurrentUser()!= null) {
                                    Log.w("Chsda", "Online");
                                    CollectionReference ref = fdb.collection("tasx");
                                    Map<String, Object> data1 = new HashMap<>();
                                    data1.put("TaskName", inputName.getText().toString());
                                    if (inpuSteps.getText().toString()==null) {inpuSteps.setText("0");}
                                    else{
                                        if (inputCompleted.getText().toString()==null) {inputCompleted.setText("1");}
                                        else {
                                            if (Integer.valueOf(inpuSteps.getText().toString())>Integer.valueOf(inputCompleted.getText().toString()))
                                            {
                                                Toast.makeText(getContext(), "Выполнено шагов не может быть больше общего количества", Toast.LENGTH_LONG).show();} else {
                                                data1.put("TaskSteps", inpuSteps.getText().toString());
                                                data1.put("TaskCompleted", inputCompleted.getText().toString());
                                                data1.put("CompleteLast", String.valueOf(d));
                                                data1.put("u2id", inputUID.getText().toString());
                                                data1.put("uid", mAuth.getUid().toString());
                                                data1.put("TaskID", list2.size());
                                                Integer a = Integer.valueOf(inpuSteps.getText().toString());
                                                Integer b = Integer.valueOf(inputCompleted.getText().toString());
                                                Float prgrs3 = (((float)a/b)*100);
                                                String last = String.valueOf(d);
                                                String l = last.substring(last.lastIndexOf('_')+1);
                                                Calendar cal = Calendar.getInstance();
                                                cal.setTimeInMillis(Long.valueOf(l));
                                                Date s= cal.getTime();
                                                DateFormat inputFormat = new SimpleDateFormat("yyyy.MM.dd' 'HH:mm:ss.SSS");
                                                ref.document().set(data1).addOnSuccessListener(unused -> Log.d("addD", String.valueOf(data1.size()))).addOnFailureListener(e -> Log.e(String.valueOf(getContext()), e.getMessage().toString()));
                                                list2.add(new DataModel(inputName.getText().toString() ,"выполнено: "
                                                        + inpuSteps.getText().toString()+"из",
                                                        inputCompleted.getText().toString()+"     "+prgrs3.intValue()+"% ",
                                                        "последнее: "+inputFormat.format(s), prgrs3.intValue()));
//                                                adapter.notifyDataSetChanged();
                                            }
                                        }
                                    }
                                } else {
                                    Toast.makeText(getContext(), "Войдите чтобы использовать онлайн сервисы", Toast.LENGTH_LONG).show();}
                            }
                        })
                .setNegativeButton("Отмена",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                dialog.cancel();
                            }
                        });

        //Создаем AlertDialog:
        AlertDialog alertDialog = mDialogBuilder.create();

        //и отображаем его:
        alertDialog.show();
    });
        return root;
    }
    private void updateDB(){
        adapter = new CustomAdapter(list2, getContext());
        CollectionReference collection = fdb.collection("tasx");
        if (mAuth.getCurrentUser()!=null){
            Query fquery = collection.where(Filter.or(
                    Filter.equalTo("uid", mAuth.getCurrentUser().getUid().toString()),
                    Filter.greaterThanOrEqualTo("u2id", mAuth.getCurrentUser().getEmail().toString())
            ));
            fquery.get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Log.d("updDB", "CALLED");
                    list2.clear();
                    ArrayList<String> arrayList = new ArrayList<>();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Log.d(getTag(), document.getId() + " => " + document.get("TaskSteps"));
                        Integer a = Integer.valueOf(document.get("TaskSteps").toString());
                        Integer b = Integer.valueOf(document.get("TaskCompleted").toString());
                        Float prgrs2 = (((float)a/b)*100);
                        String last = document.get("CompleteLast").toString();
                        String l = last.substring(last.lastIndexOf('_')+1);
                        Calendar cal = Calendar.getInstance();
                        cal.setTimeInMillis(Long.valueOf(l));
                        Date d= cal.getTime();
                        DateFormat inputFormat = new SimpleDateFormat("yyyy.MM.dd' 'HH:mm:ss.SSS");
                        list2.add(new DataModel(document.get("TaskName").toString() , document.get("TaskSteps").toString(),
                                document.get("TaskCompleted").toString()+"     "+prgrs2.intValue()+"% ", inputFormat.format(d), prgrs2.intValue()));
                        arrayList.add(document.get("TaskName").toString());
                        Log.d("qwwe", String.valueOf(list2.size()));
                    }
//                    new mlsdkWork().execute(arrayList);
                    iid = String.valueOf(list2.size());
                    adapter.notifyDataSetChanged();
                } else {
                    Log.d(getTag(), "Error getting documents: ", task.getException());
                }
            });
        }
    }

    private void onCompliteTask(Integer i) {
            Log.d("steps", "0.1");
            Query fquery = fdb.collection("tasx").where(Filter.or(
                    Filter.equalTo("uid", mAuth.getUid().toString()),
                    Filter.greaterThanOrEqualTo("u2id", mAuth.getUid().toString()
                    ), Filter.equalTo("TaskID", i - list2.size())
            ));
            final Integer[] a = new Integer[1];
            final Integer[] b = new Integer[1];
            final String[] dat = new String[1];
            fquery.get().addOnCompleteListener(task -> {
                String res = new String();
                for (QueryDocumentSnapshot document : task.getResult()) {
                    Log.d("steps", "3");
                    iid = document.getId();
                    res = document.get("TaskName").toString();
                    a[0] = Integer.valueOf(document.get("TaskSteps").toString());
                    b[0] = Integer.valueOf(document.get("TaskCompleted").toString());
                    dat[0] = document.get("CompleteLast").toString();
                    Log.d("steps", iid);
                }
                if (a[0] != null) {
                    Log.d("steps", "2");
                    String finalRes = res;
                    Integer x = a[0] + 1;
                    Float prgr = (((float) a[0] / b[0]) * 100);
                    DocumentReference ref = fdb.collection("tasx").document(iid);
                    Map<String, Object> ps = new HashMap<>();
                    ps.put("TaskSteps", x);
                    ps.put("CompleteLast", dat[0] + "_" + System.currentTimeMillis());
                    ref.update(ps);
//                    fdb.collection("tasx").document(iid).update("TaskSteps", x);
//                    fdb.collection("tasx").document(iid).update("CompleteLast", dat[0] + "_" + System.currentTimeMillis());
                    String last = dat[0];
                    String l = last.substring(last.lastIndexOf('_') + 1);
                    Calendar cal = Calendar.getInstance();
                    cal.setTimeInMillis(Long.valueOf(l));
                    Date d = cal.getTime();
                    DateFormat inputFormat = new SimpleDateFormat("yyyy.MM.dd' 'HH:mm:ss.SSS");
                    if (i < list2.size()) {
                        Log.d("steps", "1");
                        list2.set(i, new DataModel(finalRes, x.toString(),
                                b[0].toString() + "     " + prgr.intValue() + "% ",
                                inputFormat.format(d), prgr.intValue()));
                        adapter.notifyDataSetChanged();
                    } else if (list2.size() < i) {
                        updateDB();
                    }
                    if (a[0] + 1 >= b[0]) {
                        Log.d("steps", "3");
                        ref.delete();
//                        fdb.collection("tasx").document(iid).delete();
                        list2.remove(i);
                            adapter.clear();
                            adapter.addAll(list2);
                        adapter.notifyDataSetChanged();
                    }
//                        }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.e(getTag(), e.getMessage().toString());
                }
            });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}