package com.lightcore.goaltracker_pro.ui.stats;

import static android.content.Context.MODE_PRIVATE;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.lightcore.goaltracker_pro.ui.Adapt.EventDecorator;
import com.lightcore.goaltracker_pro.R;
import com.lightcore.goaltracker_pro.databinding.FragmentGalleryBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.Filter;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

public class GalleryFragment extends Fragment {

    private FragmentGalleryBinding binding;
    MaterialCalendarView calendarView;
    SQLiteDatabase db;
    private FirebaseFirestore fdb = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth;
    String iid;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        GalleryViewModel galleryViewModel =
                new ViewModelProvider(this).get(GalleryViewModel.class);
        db = getContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);
        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        BarChart chart = root.findViewById(R.id.chart);
        List<BarEntry> entries = new ArrayList<>();
        mAuth = FirebaseAuth.getInstance();
        calendarView = root.findViewById(R.id.cvv);
        Bundle bundle = getArguments();
        if (bundle!=null) {
            int id = bundle.getInt("id");
            Integer idd = bundle.getInt("idd");
            Log.d("id", Integer.toString(id));
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    if (mAuth.getCurrentUser()!=null){
                    Query fquery = fdb.collection("tasx").where(Filter.or(
                            Filter.equalTo("uid", mAuth.getCurrentUser().getUid().toString()),
                            Filter.greaterThanOrEqualTo("u2id", mAuth.getCurrentUser().getEmail().toString()
                            ), Filter.equalTo("TaskID", idd)
                    ));
                    final String[] dat = new String[1];
                    fquery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                iid = document.getId();
                                dat[0] = Objects.requireNonNull(document.get("CompleteLast")).toString();
                            }
                            String[] arr = null;
                            if (dat[0]!=null) {
                                arr = dat[0].split("_");
                                HashSet<CalendarDay> set = new HashSet<>();
                                for (int x = 0; x < arr.length; x++) {
                                    Calendar cal = Calendar.getInstance();
                                    cal.setTimeInMillis(Long.valueOf(arr[x]));
                                    Log.d("Day type", cal.getTime().toString());
                                    CalendarDay day = CalendarDay.from(cal);
                                    set.add(day);
                                    Log.w("das", set.toString());
                                    Log.d("TAS", String.valueOf(arr.length));
                                    EventDecorator eventDecorator = new EventDecorator(set);
                                    calendarView.addDecorator(eventDecorator);
                                    calendarView.invalidateDecorators();
                                }
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.e("sd", e.getMessage().toString());
                        }
                    });}
                }
            });
            thread.start();
            Cursor query = db.rawQuery("SELECT dates FROM tasx WHERE id =" + id, null);
            while (query.moveToNext()) {
                String d = query.getString(0);
                Log.d("dates", d);
                String[] arr = null;
                if (d!=null){
                arr = d.split("_");
                HashSet<CalendarDay> set = new HashSet<>();
                for (int x = 0; x < arr.length; x++) {
                    Calendar cal = Calendar.getInstance();
                    cal.setTimeInMillis(Long.valueOf(arr[x]));
                    CalendarDay day = CalendarDay.from(cal);
                    set.add(day);
                    entries.add(new BarEntry(day.getDay(), arr.length));
                    Log.d("entri", entries.get(0).toString());
                    BarDataSet dataSet = new BarDataSet((List<BarEntry>) entries, "Task"); // add entries to dataset
                    dataSet.setColor(Color.GREEN);
                    dataSet.setValueTextColor(Color.GREEN);
                    BarData lineData = new BarData(dataSet);
//                        lineData.setBarWidth(0.45f);
//                        chart.setAutoScaleMinMaxEnabled(true);
//                        chart.setKeepPositionOnRotation(true);

                    chart.setData(lineData);
//                        chart.groupBars(day.getDay(), 0.06f, 0.02f);
                    chart.invalidate();// styling, ...
                    Log.w("das", set.toString());
                    EventDecorator eventDecorator = new EventDecorator(set);
                    calendarView.addDecorator(eventDecorator);
                    calendarView.invalidateDecorators();
//                    Set<CalendarDay> set = new HashSet<CalendarDay>(Arrays.asList(arr));
                    Log.w("sdsd",set.toString());
                }}}
            }
        //если переход по меню
         else {
            Cursor query1 = db.rawQuery("SELECT dates FROM tasx", null);
            while (query1.moveToNext()) {
                String d = query1.getString(0);
                Log.d("id", d);
                String[] arr = null;
                if(d!=null) {
                    arr = d.split("_");
                    HashSet<CalendarDay> set = new HashSet<>();
                    for (int x = 0; x < arr.length; x++) {
                        Calendar cal = Calendar.getInstance();
                        cal.setTimeInMillis(Long.valueOf(arr[x]));
                        CalendarDay day = CalendarDay.from(cal);
                        set.add(day);
                        Log.d("Day type", cal.getTime().toString());
                        entries.add(new BarEntry(day.getDay(), arr.length));
                        Log.d("entri", entries.get(0).toString());
                        BarDataSet dataSet = new BarDataSet((List<BarEntry>) entries, "Task"); // add entries to dataset
                        dataSet.setColor(Color.GREEN);
                        dataSet.setValueTextColor(Color.GREEN);
                        BarData lineData = new BarData(dataSet);
//                        lineData.setBarWidth(0.45f);
//                        chart.setAutoScaleMinMaxEnabled(true);
//                        chart.setKeepPositionOnRotation(true);

                        chart.setData(lineData);
//                        chart.groupBars(day.getDay(), 0.06f, 0.02f);
                        chart.invalidate();// styling, ...
                        Log.w("das", set.toString());

                        EventDecorator eventDecorator = new EventDecorator(set);
                        calendarView.addDecorator(eventDecorator);
                        calendarView.invalidateDecorators();
                    }
                }
            }
            Thread r = new Thread(new Runnable() {
                @Override
                public void run() {
                    if (mAuth.getCurrentUser()!=null){
                    Query fquery = fdb.collection("tasx").where(Filter.or(
                            Filter.equalTo("uid", mAuth.getCurrentUser().getUid().toString()),
                            Filter.greaterThanOrEqualTo("u2id", mAuth.getCurrentUser().getEmail().toString()
                            )));
                    final String[] dat = new String[1];
                    fquery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                iid = document.getId();
                                dat[0] = Objects.requireNonNull(document.get("CompleteLast")).toString();
                            }
                            String[] arr = null;
                            if (dat[0] != null){
                                arr = dat[0].split("_");
                                HashSet<CalendarDay> set = new HashSet<>();
                                for (int x = 0; x < arr.length; x++) {
                                    Calendar cal = Calendar.getInstance();
                                    cal.setTimeInMillis(Long.valueOf(arr[x]));
                                    CalendarDay day = CalendarDay.from(cal);
                                    set.add(day);
                                    Log.w("das", set.toString());
                                    Log.d("TAS", String.valueOf(arr.length));
                                    EventDecorator eventDecorator = new EventDecorator(set);
                                    calendarView.addDecorator(eventDecorator);
                                    calendarView.invalidateDecorators();
                                }
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.e("sd", e.getMessage().toString());
                        }
                    });
                    }
                }
            });
            r.start();
        }
            return root;
        }
    }