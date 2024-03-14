package com.lightcore.goaltracker_pro.ui;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.lightcore.goaltracker_pro.R;
import com.lightcore.goaltracker_pro.ui.Adapt.RecycAdapter;
import com.lightcore.goaltracker_pro.ui.Model.SubTasks;
import com.lightcore.goaltracker_pro.ui.onlTasx.SlideshowViewModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
//import com.lightcore.goaltracker_pro.ui.Model.DataModel;


/**
 * A fragment representing a list of Items.
 */
@AndroidEntryPoint
public class ItemFragment extends Fragment implements RecycAdapter.OnItemCheckListener {

    SlideshowViewModel slideshowViewModel;

    RecycAdapter sca;
    private RecyclerView rv;
    private FirebaseAuth mAuth;
    private final FirebaseFirestore fdb = FirebaseFirestore.getInstance();
//    private SQLiteDatabase db;
//    ArrayList<DataModel> list = new ArrayList<>();

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_list, container, false);
        View root = view.getRootView();
        rv = root.findViewById(R.id.rv);
        slideshowViewModel = new ViewModelProvider(getActivity()).get(SlideshowViewModel.class);
        MutableLiveData<List<SubTasks>> subTasks = new MutableLiveData<>();
        subTasks = slideshowViewModel.getSubsVM();
        MutableLiveData<List<SubTasks>> finalSubTasks = subTasks;
        subTasks.observe(getViewLifecycleOwner(), subTasksList -> {
            sca = new RecycAdapter(finalSubTasks.getValue(), getContext());
            sca.setOnItemClickListener(this);
//            sca.setLocalDataSet(subTasksList);
            rv.setAdapter(sca);
            Log.d("DS", String.valueOf(subTasksList.size()));
//            Log.d("IF subs", subTasksList.get(0).getName().toString());
        });
//        slideshowViewModel.getSubTasks().observe(getViewLifecycleOwner(), subTasksList -> {
//
//        });
        mAuth = FirebaseAuth.getInstance();
//        db = getContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);
//        db.execSQL("CREATE TABLE IF NOT EXISTS subtasx (id INTEGER primary key autoincrement, name TEXT, progress INTEGER, taskID INTEGER)");
//        Cursor query = db.rawQuery("SELECT id AS _id, * FROM subtasx;", null);
//        sca = new SimpleCursorAdapter(getContext(),
//                R.layout.recyc_item, query,
//                new String[]{"name", "progress"},
//                new int[]{R.id.name, R.id.progress}, 0) {
//        };
//        lv.setAdapter(sca);
        ImageButton fab = root.findViewById(R.id.fab2);
        fab.setOnClickListener(v -> {
            Log.e("Clcikesd", "fab2");
            LayoutInflater li = LayoutInflater.from(getContext());
            View promptsView = li.inflate(R.layout.add_subtasx, null);

            //Создаем AlertDialog
            AlertDialog.Builder mDialogBuilder = new AlertDialog.Builder(getContext(), R.style.CustomAlertDialog);

            //Настраиваем prompt.xml для нашего AlertDialog:
            mDialogBuilder.setView(promptsView);

            final EditText inputName = (EditText) promptsView.findViewById(R.id.inpName);
            final EditText inputProgress = (EditText) promptsView.findViewById(R.id.inpProgress);
            mDialogBuilder
                    .setCancelable(false)
                    .setPositiveButton("OK",
                            (dialog, id) -> {
                                Map<String, Object> map = new HashMap<>();
                                map.put("name", inputName.getText().toString());
                                map.put("progress", inputProgress.getText().toString());
                                slideshowViewModel.setSubs(map);
                                slideshowViewModel.getSubsVM();
                            }).setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alertDialog = mDialogBuilder.create();

            //и отображаем его:
            alertDialog.show();
        });
        // Set the adapter
        return view;
    }

    @Override
    public void onItemCheck(int position) {
        slideshowViewModel.completeSubTask(position);
    }
}