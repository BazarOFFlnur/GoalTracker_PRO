package com.lightcore.goaltracker_pro.ui.home;

import static android.content.Context.MODE_PRIVATE;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
import androidx.cursoradapter.widget.SimpleCursorAdapter;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.lightcore.goaltracker_pro.ui.Model.DataModel;
import com.lightcore.goaltracker_pro.R;
import com.lightcore.goaltracker_pro.databinding.FragmentHomeBinding;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private FirebaseAuth mAuth;
    private final FirebaseFirestore fdb = FirebaseFirestore.getInstance();
    private SQLiteDatabase db;
    private ListView lv;
    long d = System.currentTimeMillis();
    ArrayList<DataModel> list2 = new ArrayList<>();
    SimpleCursorAdapter sca;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        homeViewModel.getText().observe(getViewLifecycleOwner(), dataModels -> {
            for(int i = 0;
            i<dataModels.size();
            i++) {
                Log.e("MVVM", dataModels.get(i).toString());
            }
        });
        mAuth = FirebaseAuth.getInstance();
        db = getContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS tasx (id INTEGER primary key autoincrement, name TEXT, complOn INTEGER, steps INTEGER, dates TEXT)");
//        db.execSQL("DROP TABLE tasx");
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        lv = root.findViewById(R.id.lv);
        ImageButton fab = root.findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            LayoutInflater li = LayoutInflater.from(getContext());
            View promptsView = li.inflate(R.layout.add_dialog, null);

            //Создаем AlertDialog
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
                            (dialog, id) -> {
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
                                                {Toast.makeText(getContext(), "Выполнено шагов не может быть больше общего количества", Toast.LENGTH_LONG).show();} else {
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
                                                            + inpuSteps.getText().toString(),
                                                            inputCompleted.getText().toString()+"     "+prgrs3.intValue()+"% ",
                                                            inputFormat.format(s), prgrs3.intValue()));
                                                }
                                            }
                                        }
                                    } else {
                                        Toast.makeText(getContext(), "Войдите чтобы использовать онлайн сервисы", Toast.LENGTH_LONG).show();}
                                    String a = ("'");
                                    Log.d("message",inputName.getText().toString() + inpuSteps.getText());
                                    db.execSQL("INSERT OR IGNORE INTO tasx VALUES (null, " +
                                            a + inputName.getText().toString() +a +", "+a +inpuSteps.getText() +a+", "+a+ inputCompleted.getText()+a+","+ String.valueOf(d) +");");
                                    updateDB();
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
        lv.setOnItemClickListener((parent, view, position, id) -> onCompliteTask((int) id));
        lv.setOnItemLongClickListener((parent, view, position, id) -> {
            Navigation.findNavController(root).navigate(R.id.action_nav_home_to_SecondFragment);
//            Bundle bundle = new Bundle();
//            ItemFragment f = new ItemFragment();
//            bundle.putInt("id", (int) id);
//            bundle.putInt("idd", (int) id);
//            f.setArguments(bundle);
//            Navigation.findNavController(view).navigate(R.layout.fragment_item_list, bundle);
//            FragmentManager fragmentManager = getFragmentManager();
//            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//            fragmentTransaction.replace(R.id.fragment_item_list, new ItemFragment());
            Log.d("OnLONGCLICK", "sda");
            return false;
        });
//        viewModel = new ViewModelProvider(this).get(DataViewModel.class);
//
//        // Наблюдайте за изменениями данных и обновляйте UI при необходимости
//        viewModel.getDataList().observe(getViewLifecycleOwner(), new Observer<List<DataModel>>() {
//            @Override
//            public void onChanged(List<DataModel> newData) {
//                list.clear();
//                list.addAll(newData);
//
//                // Обновите UI с новыми данными, используя ваш адаптер
//                adapter.notifyDataSetChanged();
//            }
//        });
        return root;
    }
    private void updateDB(){
        Log.w("updDb","StartInHomeFRAGMENT");
//        CollectionReference collection = fdb.collection("tasx");
//        if (mAuth.getCurrentUser()!=null){
//            Query fquery = collection.where(Filter.or(
//                    Filter.equalTo("uid", mAuth.getCurrentUser().getUid().toString()),
//                    Filter.greaterThanOrEqualTo("u2id", mAuth.getCurrentUser().getEmail().toString())
//            ));
//            fquery.get().addOnCompleteListener(task -> {
//                if (task.isSuccessful()) {
//                    list2.clear();
//                    ArrayList<String> arrayList = new ArrayList<>();
//                    for (QueryDocumentSnapshot document : task.getResult()) {
//                        Log.d(getTag(), document.getId() + " => " + document.get("TaskSteps"));
//                        Integer a = Integer.valueOf(document.get("TaskSteps").toString());
//                        Integer b = Integer.valueOf(document.get("TaskCompleted").toString());
//                        Float prgrs2 = (((float)a/b)*100);
//                        String last = document.get("CompleteLast").toString();
//                        String l = last.substring(last.lastIndexOf('_')+1);
//                        Calendar cal = Calendar.getInstance();
//                        cal.setTimeInMillis(Long.valueOf(l));
//                        Date d= cal.getTime();
//                        DateFormat inputFormat = new SimpleDateFormat("yyyy.MM.dd' 'HH:mm:ss.SSS");
//                        list2.add(new DataModel(document.get("TaskName").toString() ,"выполнено: "
//                                + document.get("TaskSteps").toString()+"из",
//                                document.get("TaskCompleted").toString()+"     "+prgrs2.intValue()+"% ",
//                                "последнее: "+inputFormat.format(d), prgrs2.intValue()));
//                        arrayList.add(document.get("TaskName").toString());
//                        Log.d("qwwe", String.valueOf(list2.size()));
//                    }
////                    new mlsdkWork().execute(arrayList);
//                    Log.d("qwwe3", String.valueOf(list3.size()));
//                    adapter.notifyDataSetChanged();
//                } else {
//                    Log.d(getTag(), "Error getting documents: ", task.getException());
//                }
//            });
//        }
        Cursor query = db.rawQuery("SELECT id AS _id, * FROM tasx;", null);
//        while(query.moveToNext()){
//            String dates = query.getString(4);
//            String last = dates.substring(dates.lastIndexOf('_') + 1);
//            Calendar cal = Calendar.getInstance();
//            cal.setTimeInMillis(Long.parseLong(last));
//            Date d= cal.getTime();
//            DateFormat inputFormat = new SimpleDateFormat("yyyy.MM.dd' 'HH:mm:ss.SSS");
//            String name = query.getString(1);
//            String age = String.valueOf(query.getInt(2));
//            String stp = String.valueOf(query.getInt(3));
//            Integer a = Integer.valueOf(age);
//            Integer b = Integer.valueOf(stp);
//            Float prgrs = (((float)a/b)*100);
//            Log.d("prgrs", String.valueOf(prgrs));
//            list.add(new DataModel(name, query.getInt(0)+"выполнено: "+age+"из", stp+"     "+prgrs.intValue()+"% ", "последнее: "+inputFormat.format(d), prgrs.intValue()));
//        }
//        list3.addAll(list);
//        SimpleCursorLoader loader = new SimpleCursorLoader(getContext(),
//                R.layout.list_item, query,
//                new String[]{"name", "complOn", "steps", "dates", "id"},
//                new int[]{R.id.tskName, R.id.tskPrc, R.id.tskSumm, R.id.tskview}, 0) {
//            @Override
//            public Cursor loadInBackground() {
//                return null;
//            }
//        };
        sca = new SimpleCursorAdapter(getContext(),
                R.layout.list_item, query,
                new String[]{"name", "complOn", "steps", "dates"},
                new int[]{R.id.tskName, R.id.tskPrc, R.id.tskSumm, R.id.tskview}, 0) {
        };
        lv.setAdapter(sca);
    }
//
    private void onCompliteTask(Integer i) {
        ContentValues values = new ContentValues();
        values.put("complOn =", "complOn+1");
//            db.update("tasx", values, "id = '"+ i+"'", null);
        String d = "'_" + System.currentTimeMillis() + "'";
        db.execSQL("UPDATE tasx SET complOn = complOn + 1, dates = (dates || " + d + ") WHERE id = " + i);
        Log.d("click", "successuful" + i);
//            db.delete("tasx", "id='" +i+ "' AND complOn >= steps", null);
        db.execSQL("DELETE FROM tasx WHERE id = " + i + " AND complOn >= steps");
        Log.d("click", "successuful" + i);
        sca.notifyDataSetChanged();
//        updateDB();
    }
//        updateDB();

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    public void onResume() {
        updateDB();
        super.onResume();
    }
//    private class mlsdkWork extends AsyncTask<ArrayList<String>, Void, Void>{
//
//        @Override
//        protected Void doInBackground(ArrayList<String>... lists) {
//            String result = TextUtils.join(", ", lists);
//
//            String res;
//            res = result.replaceAll(Pattern.quote("["), "");
//            res = res.replaceAll(Pattern.quote("]"), "");
////            res = result.replaceAll("[", "");
//            List<TextMessage> conversation = new ArrayList<>();
//            conversation.add(TextMessage.createForLocalUser(
//                    "I set myself the following tasks "+res+" , what other tasks can you recommend?", System.currentTimeMillis()));
//            Log.w("MlKit question", "I set myself the following tasks " + res + ", what other tasks can you recommend?");
//            SmartReplyGenerator smartReply = SmartReply.getClient();
//            smartReply.suggestReplies(conversation).addOnSuccessListener(new OnSuccessListener<SmartReplySuggestionResult>() {
//                @Override
//                public void onSuccess(SmartReplySuggestionResult smartReplySuggestionResult) {
//                    for (SmartReplySuggestion suggestion : smartReplySuggestionResult.getSuggestions()) {
//                        String replyText = suggestion.getText();
//                        Log.d("MlKit otvet", replyText);
//                    }
//                }
//            }).addOnFailureListener(new OnFailureListener() {
//                @Override
//                public void onFailure(@NonNull Exception e) {
//                    Log.e(getTag(), Objects.requireNonNull(e.getMessage()));
//                }
//            });
//            return null;
//        }
//    }
}