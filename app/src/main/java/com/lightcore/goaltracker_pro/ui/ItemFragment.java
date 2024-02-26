package com.lightcore.goaltracker_pro.ui;

import static android.content.Context.MODE_PRIVATE;

import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.cursoradapter.widget.SimpleCursorAdapter;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.lightcore.goaltracker_pro.R;
import com.lightcore.goaltracker_pro.ui.Model.DataModel;

import java.util.ArrayList;

/**
 * A fragment representing a list of Items.
 */
public class ItemFragment extends Fragment {

    SimpleCursorAdapter sca;
    private ListView lv;
    private FirebaseAuth mAuth;
    private final FirebaseFirestore fdb = FirebaseFirestore.getInstance();
    private SQLiteDatabase db;
    ArrayList<DataModel> list = new ArrayList<>();

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_list, container, false);
        View root = view.getRootView();


        mAuth = FirebaseAuth.getInstance();
        db = getContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS subtasx (id INTEGER primary key autoincrement, name TEXT, progress INTEGER, taskID INTEGER)");
        lv= root.findViewById(R.id.lvv);
        Cursor query = db.rawQuery("SELECT id AS _id, * FROM subtasx;", null);
        sca = new SimpleCursorAdapter(getContext(),
                R.layout.recyc_item, query,
                new String[]{"name", "progress"},
                new int[]{R.id.name, R.id.progress}, 0) {
        };
        lv.setAdapter(sca);
        ImageButton fab = root.findViewById(R.id.fab2);
        fab.setOnClickListener(v -> {
            Log.e("Clcikesd", "fab2");
            LayoutInflater li = LayoutInflater.from(getContext());
            View promptsView = li.inflate(R.layout.add_subtasx, null);

            //Создаем AlertDialog
            AlertDialog.Builder mDialogBuilder = new AlertDialog.Builder(getContext());

            //Настраиваем prompt.xml для нашего AlertDialog:
            mDialogBuilder.setView(promptsView);

            final EditText inputName = (EditText) promptsView.findViewById(R.id.inpName);
            final EditText inputProgress = (EditText) promptsView.findViewById(R.id.inpProgress);
            mDialogBuilder
                    .setCancelable(false)
                    .setPositiveButton("OK",
                            (dialog, id) -> {
                        Log.e("Mdialog", "Created");
                                String a = ("'");
                                db.execSQL("INSERT OR IGNORE INTO subtasx VALUES (null, " +
                                        a + inputName.getText().toString() +a +", "+a +inputProgress.getText() +a+", "+a+"asd"+ a +");");
                                sca.notifyDataSetChanged();
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
        lv.setOnItemClickListener((parent, view1, position, id) -> {
            db.execSQL("DELETE FROM subtasx WHERE id = " + id);
            sca.notifyDataSetChanged();
        });
        // Set the adapter
        return view;
    }
}