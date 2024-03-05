package com.lightcore.goaltracker_pro.DoMain;

import com.lightcore.goaltracker_pro.DataSource.SetSubsFirebase;

import java.util.Map;

public class SetSubTasx {
    SetSubsFirebase setSubsFirebase;
    public SetSubTasx(SetSubsFirebase setSubsFirebase){this.setSubsFirebase=setSubsFirebase;}
    public boolean exec(Map<String, Object> map){
        return setSubsFirebase.set(map);
    }
}
