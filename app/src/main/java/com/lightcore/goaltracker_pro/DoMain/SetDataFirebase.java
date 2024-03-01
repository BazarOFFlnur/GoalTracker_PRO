package com.lightcore.goaltracker_pro.DoMain;

import com.lightcore.goaltracker_pro.DataSource.GetFirebase;
import com.lightcore.goaltracker_pro.DataSource.SetFirebase;
import com.lightcore.goaltracker_pro.ui.Model.DataModel;

import java.util.Map;

public class SetDataFirebase {
    SetFirebase setFirebase;
    public SetDataFirebase(SetFirebase setFirebase){
        this.setFirebase = setFirebase;
    }
    public boolean execute(Map<String, Object> map){
        return setFirebase.setData(map);
    }
}
