package com.lightcore.goaltracker_pro.ui.Model;

public class DataGetModelTasks {
    String name;
    String type;
    String stp;
    String _id;
    Integer prgrs;
    SubTasks subTasksList;

    public DataGetModelTasks(String name, String type, String stp, String id, int prgrs, SubTasks subTasksList) {
        this.name=name;
        this.type= type;
        this.stp = stp;
        this._id = id;
        this.prgrs = prgrs;
        this.subTasksList=subTasksList;
    }

    public DataGetModelTasks(){}
    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }
    public String getStp() {return stp;}
    public String get_id() {
        return _id;
    }
    public Integer getPrgrs(){return prgrs;}
}
