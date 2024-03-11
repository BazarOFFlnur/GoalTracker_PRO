package com.lightcore.goaltracker_pro.ui.Adapt;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.lightcore.goaltracker_pro.R;
import com.lightcore.goaltracker_pro.ui.Model.DataGetModelTasks;

import java.util.List;

public class CustomAdapter extends ArrayAdapter<DataGetModelTasks> implements View.OnClickListener{

    private List<DataGetModelTasks> dataSet;
    Context mContext;

    // View lookup cache
    private static class ViewHolder {
        TextView txtName;
        TextView txtType;
        TextView txtview;
        TextView txtVersion;
        ProgressBar progressBar;
    }

    public CustomAdapter(List<DataGetModelTasks> data, Context context) {
        super(context, R.layout.list_item, data);
        this.dataSet = data;
        this.mContext=context;

    }

    @Override
    public void onClick(View v) {

        int position=(Integer) v.getTag();
        Object object= getItem(position);
        DataGetModelTasks dataModel=(DataGetModelTasks)object;

//        switch (v.getId())
//        {
//            case R.id.plus:
//                Snackbar.make(v, "Release date " +dataModel.getFeature(), Snackbar.LENGTH_LONG)
//                        .setAction("No action", null).show();
//                break;
//        }
    }

    private int lastPosition = -1;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        DataGetModelTasks dataModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.list_item, parent, false);
            viewHolder.txtName = (TextView) convertView.findViewById(R.id.tskName);
            viewHolder.txtType = (TextView) convertView.findViewById(R.id.tskPrc);
            viewHolder.txtVersion = (TextView) convertView.findViewById(R.id.tskSumm);
            viewHolder.txtview = convertView.findViewById(R.id.tskview);
            viewHolder.progressBar = convertView.findViewById(R.id.progressBar);
            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }

//        Animation animation = AnimationUtils.loadAnimation(mContext, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
//        result.startAnimation(animation);
//        lastPosition = position;
//        viewHolder.info.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                assert dataModel != null;
//                Log.d("sd", dataModel.getName());
//            }
//        });
        assert dataModel != null;
        viewHolder.txtName.setText(dataModel.getName());
        viewHolder.txtType.setText(dataModel.getType());
        viewHolder.txtVersion.setText(dataModel.getStp());
        viewHolder.txtview.setText(dataModel.get_id());
        viewHolder.progressBar.setSecondaryProgress(dataModel.getPrgrs() != null ? dataModel.getPrgrs().intValue() : 0);
        // Return the completed view to render on screen
        return convertView;
    }
}
