package com.lightcore.goaltracker_pro.ui.Model;

import java.util.List;

public class DataModel {

    String name;
    String type;
    String stp;
    String _id;
    Integer prgrs;
    List<SubTasks> subTasksList;


    public DataModel(String name, String type, String stp, String id, int prgrs, List<SubTasks> subTasksList) {
        this.name=name;
        this.type= type;
        this.stp = stp;
        this._id = id;
        this.prgrs = prgrs;
        this.subTasksList=subTasksList;
    }

    public DataModel(){}
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
    public List<SubTasks> getSubTasksList() {return subTasksList;}
}
