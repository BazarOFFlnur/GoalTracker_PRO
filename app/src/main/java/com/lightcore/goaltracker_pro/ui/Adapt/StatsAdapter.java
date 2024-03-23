package com.lightcore.goaltracker_pro.ui.Adapt;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lightcore.goaltracker_pro.R;
import com.lightcore.goaltracker_pro.ui.Model.StatForDayModel;
import com.lightcore.goaltracker_pro.ui.Model.SubTasks;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

import javax.security.auth.callback.Callback;

public class StatsAdapter extends RecyclerView.Adapter<StatsAdapter.ViewHolder>{
    private List<StatForDayModel> localDataSet;
    private final LayoutInflater inflater;
    private Callback callback;
    private OnItemCheckListener listener;

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textView;
        private final TextView textView2;
        private ProgressBar progressBar;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View

            textView = (TextView) view.findViewById(R.id.day);
            textView2 = (TextView) view.findViewById(R.id.week);
            progressBar = (ProgressBar) view.findViewById(R.id.dayprgrs);
        }
    }

    public StatsAdapter(List<StatForDayModel> subTasksList, Context context){
        this.localDataSet=subTasksList;
        this.inflater= LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.stat_for_day_or_week, viewGroup, false);

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
        viewHolder.textView.setText(localDataSet.get(position).getDay());
        viewHolder.textView2.setText(localDataSet.get(position).getWeek());
        viewHolder.progressBar.setSecondaryProgress(localDataSet.get(position).getProgress() != null ? localDataSet.get(position).getProgress() : 0);
        int max = 0;
        List<Integer> ints = new ArrayList<>();
        for (int i = 0; i<localDataSet.size(); i++){
            ints.add(localDataSet.get(i).getProgress());
        }
        max = Collections.max(ints);
        viewHolder.progressBar.setMax(max);
//        viewHolder.progressBar.setMax();
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
