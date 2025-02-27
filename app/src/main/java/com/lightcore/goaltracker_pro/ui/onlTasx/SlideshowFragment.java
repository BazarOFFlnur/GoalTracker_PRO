package com.lightcore.goaltracker_pro.ui.onlTasx;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.Filter;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.lightcore.goaltracker_pro.R;
import com.lightcore.goaltracker_pro.databinding.FragmentSlideshowBinding;
import com.lightcore.goaltracker_pro.ui.Adapt.CustomAdapter;
import com.lightcore.goaltracker_pro.ui.Model.DataGetModelTasks;
import com.lightcore.goaltracker_pro.ui.Model.SubTasks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dagger.hilt.android.AndroidEntryPoint;

//@FragmentScoped
@AndroidEntryPoint
public class SlideshowFragment extends Fragment {

    SlideshowViewModel slideshowViewModel;

    private FragmentSlideshowBinding binding;
    private FirebaseAuth mAuth;
    private final FirebaseFirestore fdb = FirebaseFirestore.getInstance();
    long d = System.currentTimeMillis();
    CustomAdapter adapter;
    private ListView lv;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mAuth = FirebaseAuth.getInstance();
        binding = FragmentSlideshowBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        if (mAuth.getCurrentUser()!=null) {
//        updateDB();
//        fdb = FirebaseFirestore.getInstance();
            slideshowViewModel = new ViewModelProvider(getActivity()).get(SlideshowViewModel.class);
            MutableLiveData<List<DataGetModelTasks>> data = slideshowViewModel.get();
            data.observe(getViewLifecycleOwner(), dataModels -> {
                adapter = new CustomAdapter(dataModels, getContext());
                adapter.notifyDataSetChanged();
                adapter.setOnItemClickListener(new CustomAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        slideshowViewModel.ItemDeleted(position);
                    }
                });
                lv.setAdapter(adapter);
            });
        }
        lv = root.findViewById(R.id.olv);
        lv.setOnItemClickListener((parent, view, position, id) -> {
            slideshowViewModel.ItemComplete((int) id);
        });
        lv.setOnItemLongClickListener((parent, view, position, id) -> {
            slideshowViewModel.setSubTasksId((int) id);
            Navigation.findNavController(getView()).navigate(R.id.SecondFragment);
            return false;
        });

        ImageButton fab = root.findViewById(R.id.ofab);


        fab.setOnClickListener(view -> {
        LayoutInflater li = LayoutInflater.from(getContext());
        View promptsView = li.inflate(R.layout.add_dialog, null);
        AlertDialog.Builder mDialogBuilder = new AlertDialog.Builder(getContext(), R.style.CustomAlertDialog);

        //Настраиваем prompt.xml для нашего AlertDialog:
        mDialogBuilder.setView(promptsView);
        //Настраиваем отображение поля для ввода текста в открытом диалоге:
        final EditText inputName = promptsView.findViewById(R.id.input_text);
        final EditText inpuSteps = promptsView.findViewById(R.id.taskSteps);
        final EditText inputCompleted = promptsView.findViewById(R.id.taskComplete);
        final Switch sw = promptsView.findViewById(R.id.onlSwitch);
        final EditText inputUID = promptsView.findViewById(R.id.uid2);
        inputUID.setVisibility(View.INVISIBLE);
        sw.setOnCheckedChangeListener((buttonView, isChecked12) -> {
            if(isChecked12) {
                inputUID.setVisibility(View.VISIBLE);
            } else {
                inputUID.setVisibility(View.INVISIBLE);
            }
        });
        //Настраиваем сообщение в диалоговом окне:
        mDialogBuilder
                .setCancelable(false)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                if (mAuth.getCurrentUser()!= null) {
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
                                                data1.put("uid", mAuth.getUid());
                                                List<SubTasks> subTasksList = new ArrayList<>();
                                                subTasksList.add(new SubTasks("", "|"));
                                                data1.put("SubTasks", subTasksList);
//                                                data1.put("TaskID", list2.size());

//                                                Calendar calendar = Calendar.getInstance();
//                                                calendar.setTimeInMillis(System.currentTimeMillis());
//                                                calendar.set(Calendar.HOUR, inputTime.getHour());
//                                                calendar.set(Calendar.MINUTE, inputTime.getMinute());
//                                                Log.d("TASD", calendar.getTime().toString());
//                                                Log.d("TASD2", String.valueOf(calendar.getTime().getTime()));
//                                                if (System.currentTimeMillis() > calendar.getTimeInMillis()) {
//                                                    calendar.add(Calendar.DAY_OF_YEAR, 1);
//                                                }
//                                                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
//                                                        AlarmManager.INTERVAL_DAY, pendingIntent);
//                                                Intent intent = new Intent(getContext(), NotificationService.class);
//                                                PendingIntent pendingIntent = PendingIntent.getService(
//                                                        getContext(),
//                                                        0,
//                                                        intent,
//                                                        PendingIntent.FLAG_IMMUTABLE
//                                                );
//
//                                                long startTime = calendar.getTimeInMillis() - System.currentTimeMillis();  // Здесь устанавливается время уведомления
//                                                long interval = AlarmManager.INTERVAL_DAY;
////                                                NotificationManagerCompat notificationManager = NotificationManagerCompat.from(ContextCompat.getSystemService(getContext(), NotificationManager.class));
//
//                                                AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
//                                                alarmManager.setInexactRepeating(
//                                                        AlarmManager.ELAPSED_REALTIME_WAKEUP,
//                                                        SystemClock.elapsedRealtime() + startTime,
//                                                        interval,
//                                                        pendingIntent
//                                                );
//                                                Log.d("TAF0", String.valueOf(calendar.getTimeInMillis()-System.currentTimeMillis()));
//                                                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
//                                                alarmManager.setInexactRepeating(
//                                                        AlarmManager.ELAPSED_REALTIME_WAKEUP,
//                                                        SystemClock.elapsedRealtime() + inputTime.getText().toString(),
//                                                        AlarmManager.INTERVAL_DAY,
//                                                        PendingIntent.getService(getContext(), 0, new Intent(getContext(), ))
//                                                );
//                                                ref.document().set(data1).addOnSuccessListener(unused -> Log.d("addD", String.valueOf(data1.size()))).addOnFailureListener(e -> Log.e(String.valueOf(getContext()), e.getMessage().toString()));
                                                boolean e = slideshowViewModel.set(data1);
                                                    slideshowViewModel.get();

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



    private void createNotificationChannel() {

    }


    private void updateDB(){
//        adapter = new CustomAdapter(list2, getContext());
        CollectionReference collection = fdb.collection("tasx");
        if (mAuth.getCurrentUser()!=null){
            Query fquery = collection.where(Filter.or(
                    Filter.equalTo("uid", mAuth.getCurrentUser().getUid()),
                    Filter.greaterThanOrEqualTo("u2id", mAuth.getCurrentUser().getEmail())
            ));
            fquery.get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Log.d("updDB", "CALLED");
//                    list2.clear();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Log.d(getTag(), document.getId() + " => " + document.get("TaskSteps"));
//
//                        Log.d("qwwe", String.valueOf(list2.size()));
                    }
//                    new mlsdkWork().execute(arrayList);
//                    iid = String.valueOf(list2.size());
//                    adapter.notifyDataSetChanged();
                } else {
                    Log.d(getTag(), "Error getting documents: ", task.getException());
                }
            });
        }
    }

//    private void onCompliteTask(Integer i) {
//            Log.d("steps", "0.1");
//            Query fquery = fdb.collection("tasx").where(Filter.or(
//                    Filter.equalTo("uid", mAuth.getUid()),
//                    Filter.greaterThanOrEqualTo("u2id", mAuth.getUid()
//                    ), Filter.equalTo("TaskID", i - list2.size())
//            ));
//            final Integer[] a = new Integer[1];
//            final Integer[] b = new Integer[1];
//            final String[] dat = new String[1];
//            fquery.get().addOnCompleteListener(task -> {
//                String res = "";
//                for (QueryDocumentSnapshot document : task.getResult()) {
//                    Log.d("steps", "3");
//                    iid = document.getId();
//                    res = document.get("TaskName").toString();
//                    a[0] = Integer.valueOf(document.get("TaskSteps").toString());
//                    b[0] = Integer.valueOf(document.get("TaskCompleted").toString());
//                    dat[0] = document.get("CompleteLast").toString();
//                    Log.d("steps", iid);
//                }
//                if (a[0] != null) {
//                    Log.d("steps", "2");
//                    String finalRes = res;
//                    Integer x = a[0] + 1;
//                    Float prgr = (((float) a[0] / b[0]) * 100);
//                    DocumentReference ref = fdb.collection("tasx").document(iid);
//                    Map<String, Object> ps = new HashMap<>();
//                    ps.put("TaskSteps", x);
//                    ps.put("CompleteLast", dat[0] + "_" + System.currentTimeMillis());
//                    ref.update(ps);
//                    String last = dat[0];
//                    String l = last.substring(last.lastIndexOf('_') + 1);
//                    Calendar cal = Calendar.getInstance();
//                    cal.setTimeInMillis(Long.valueOf(l));
//                    Date d = cal.getTime();
//                    DateFormat inputFormat = new SimpleDateFormat("yyyy.MM.dd' 'HH:mm:ss.SSS");
//                    if (i < list2.size()) {
//                        Log.d("steps", "1");
//                        list2.set(i, new DataModel(finalRes, x.toString(),
//                                b[0].toString() + "     " + prgr.intValue() + "% ",
//                                inputFormat.format(d), prgr.intValue()));
//                        adapter.notifyDataSetChanged();
//                    } else if (list2.size() < i) {
////                        updateDB();
//                    }
//                    if (a[0] + 1 >= b[0]) {
//                        Log.d("steps", "3");
//                        ref.delete();
//                        fdb.collection("tasx").document(iid).delete();
//                        list2.remove(i);
//                            adapter.clear();
//                            adapter.addAll(list2);
//                        adapter.notifyDataSetChanged();
//                    }
//                        }
//                slideshowViewModel.get();
//            }).addOnFailureListener(new OnFailureListener() {
//                @Override
//                public void onFailure(@NonNull Exception e) {
//                    Log.e(getTag(), e.getMessage());
//                }
//            });
//    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}