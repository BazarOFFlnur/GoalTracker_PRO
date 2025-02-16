package com.lightcore.goaltracker_pro.ui.stats;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.github.mikephil.charting.data.BarEntry;
import com.lightcore.goaltracker_pro.R;
import com.lightcore.goaltracker_pro.databinding.FragmentGalleryBinding;
import com.lightcore.goaltracker_pro.ui.Adapt.EventDecorator;
import com.lightcore.goaltracker_pro.ui.Adapt.StatsAdapter;
import com.lightcore.goaltracker_pro.ui.Model.StatForDayModel;
import com.lightcore.goaltracker_pro.ui.onlTasx.SlideshowViewModel;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class GalleryFragment extends Fragment {
    SlideshowViewModel slideshowViewModel;
    private FragmentGalleryBinding binding;
    MaterialCalendarView calendarView;
    RecyclerView recyclerView;
    StatsAdapter statsAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        calendarView = root.findViewById(R.id.cvv);
        calendarView.setHeaderTextAppearance(R.style.CalendarHeaderText);
        calendarView.setWeekDayTextAppearance(R.style.CalendarWeekText);
        calendarView.setDateTextAppearance(R.style.CalendarDateText);

        recyclerView = root.findViewById(R.id.statForDay);
        slideshowViewModel = new ViewModelProvider(getActivity()).get(SlideshowViewModel.class);
        MutableLiveData<List<String>> data = slideshowViewModel.getDates();
        List<BarEntry> entries = new ArrayList<>();
//        List<String> arr = new ArrayList<>();
        List<StatForDayModel> dayModels = new ArrayList<>();
        data.observe(getViewLifecycleOwner(), dataGetModelTasks -> {
            for (String task : dataGetModelTasks) {
                if (task!=null){
                    String[] arr = task.split("_");
                    HashSet<CalendarDay> set = new HashSet<>();
                    for (int x = 0; x < arr.length; x++) {
                        Calendar cal = Calendar.getInstance();
                        cal.setTimeInMillis(Long.parseLong(arr[x]));
                        Log.d("Day type", cal.getTime().toString());
                        CalendarDay day = CalendarDay.from(cal);
                        set.add(day);
//                        dayf.add(String.valueOf(day.getDay()));
//                        month.add(String.valueOf(day.getMonth())); //TODO refactor to string
                        entries.add(new BarEntry(day.getDay(), arr.length));
//                        Log.d("entri", entries.get(0).toString());

        String s = "month";
        switch (day.getMonth()){
            case 0:{
                s = "January";
                break;}
            case 1:{
                s = "February";
                break;
            }
            case 2:{
                s= "March";
                break;
            }
            case 3:{
                s="April";
                break;
            }
            case 4:{
                s="May";
                break;
            }
            case 5:{
                s="June";
                break;}
            case 6:{
                s="July";
                break;
            }
            case 7:{
                s="August";
                break;
            }
            case 8:{
                s="September";
                break;
            }
            case 9:{
                s="October";
                break;
            }
            case 10:{
                s="November";
                break;
            }
            case 11:{
                s="December";
                break;
            }
        }
                        dayModels.add(new StatForDayModel(String.valueOf(day.getDay()), s, arr.length));
                    }
                    Map<String, Integer> dayMonthCountMap = new HashMap<>();
                    for (StatForDayModel model : dayModels) {
                        String key = model.getDay() + "-" + model.getWeek(); // Создаем ключ в формате "Day-Month"
                        dayMonthCountMap.put(key, dayMonthCountMap.getOrDefault(key, 1) + 1); // Увеличиваем счетчик
                    }

                    List<StatForDayModel> combinedDayModels = new ArrayList<>();
                    for (Map.Entry<String, Integer> entry : dayMonthCountMap.entrySet()) {
                        String[] parts = entry.getKey().split("-");
                        String day = parts[0];
                        String month = parts[1];
                        int count = entry.getValue();
                        combinedDayModels.add(new StatForDayModel(day, month, count));
                    }

                    EventDecorator eventDecorator = new EventDecorator(set);
                    calendarView.addDecorator(eventDecorator);
                    Log.d("prgtrs", combinedDayModels.get(0).getProgress().toString());
                    statsAdapter = new StatsAdapter(combinedDayModels, getContext());
                    recyclerView.setAdapter(statsAdapter);
                    calendarView.invalidateDecorators();
                }
            }
        });
//            Thread r = new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    if (mAuth.getCurrentUser()!=null){
//                    Query fquery = fdb.collection("tasx").where(Filter.or(
//                            Filter.equalTo("uid", mAuth.getCurrentUser().getUid().toString()),
//                            Filter.greaterThanOrEqualTo("u2id", mAuth.getCurrentUser().getEmail().toString()
//                            )));
//                    final String[] dat = new String[1];
//                    fquery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                        @Override
//                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                            for (QueryDocumentSnapshot document : task.getResult()) {
//                                iid = document.getId();
//                                dat[0] = Objects.requireNonNull(document.get("CompleteLast")).toString();
//                            }
//                            String[] arr = null;
//                            if (dat[0] != null){
//                                arr = dat[0].split("_");
//                                HashSet<CalendarDay> set = new HashSet<>();
//                                for (int x = 0; x < arr.length; x++) {
//                                    Calendar cal = Calendar.getInstance();
//                                    cal.setTimeInMillis(Long.valueOf(arr[x]));
//                                    CalendarDay day = CalendarDay.from(cal);
//                                    set.add(day);
//                                    Log.w("das", set.toString());
//                                    Log.d("TAS", String.valueOf(arr.length));
//                                    EventDecorator eventDecorator = new EventDecorator(set);
//                                    calendarView.addDecorator(eventDecorator);
//                                    calendarView.invalidateDecorators();
//                                }
//                            }
//                        }
//                    }).addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception e) {
//                            Log.e("sd", e.getMessage().toString());
//                        }
//                    });
//                    }
//                }
//            });
//            r.start();
            return root;
        }
    }