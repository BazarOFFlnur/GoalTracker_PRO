package com.lightcore.goaltracker_pro.DoMain;

import com.lightcore.goaltracker_pro.DataSource.UpdateFirebase;

public class CompleteTask {
    UpdateFirebase updateFirebase;
    public CompleteTask(UpdateFirebase updateFirebase){
        this.updateFirebase=updateFirebase;
    }
    public boolean execute(int i){

        return true;
    }
}
