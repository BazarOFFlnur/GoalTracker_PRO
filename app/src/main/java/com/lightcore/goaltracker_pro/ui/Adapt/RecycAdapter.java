package com.lightcore.goaltracker_pro.ui.Adapt;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lightcore.goaltracker_pro.R;
import com.lightcore.goaltracker_pro.ui.Model.SubTasks;
import com.lightcore.goaltracker_pro.ui.onlTasx.SlideshowViewModel;

import java.util.List;

import javax.security.auth.callback.Callback;

public class RecycAdapter extends RecyclerView.Adapter<RecycAdapter.ViewHolder>{
    private List<SubTasks> localDataSet;
    private final LayoutInflater inflater;
    private Callback callback;
    private OnItemCheckListener listener;

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textView;
        private final TextView textView2;
        private CheckBox cb;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View

            textView = (TextView) view.findViewById(R.id.name);
            textView2 = (TextView) view.findViewById(R.id.progress);
            cb = (CheckBox) view.findViewById(R.id.cb);
        }
    }

    public RecycAdapter(List<SubTasks> subTasksList, Context context){
        this.localDataSet=subTasksList;
        this.inflater= LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.recyc_item, viewGroup, false);

        return new ViewHolder(view);
    }
    public interface OnItemCheckListener {
        void onItemCheck(int position);
    }
    public void setOnItemClickListener(OnItemCheckListener listener) {
        this.listener = listener;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.textView.setText(localDataSet.get(position).getName());
        viewHolder.textView2.setText(localDataSet.get(position).getProgress());
        viewHolder.cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (listener != null) {
                    listener.onItemCheck(position);
                }
            }
        });
    }
//    public void setLocalDataSet(List<SubTasks> subTasksList) {
//        this.localDataSet = subTasksList;
//    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return localDataSet.size();
    }
}
