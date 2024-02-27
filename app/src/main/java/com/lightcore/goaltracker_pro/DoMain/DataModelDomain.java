package com.lightcore.goaltracker_pro.DoMain;

public class DataModelDomain {

    String name;
    String type;
    String stp;
    String _id;
    Integer prgrs;

    public DataModelDomain(String name, String type, String stp, String id, int prgrs) {
        this.name=name;
        this.type= type;
        this.stp = stp;
        this._id = id;
        this.prgrs = prgrs;
    }

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
