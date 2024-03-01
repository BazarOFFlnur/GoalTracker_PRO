package com.lightcore.goaltracker_pro.ui.testr;

import javax.inject.Inject;

public class Tsd {
    Sec sec;

    public Tsd (Sec sec){
        this.sec=sec;
    }
    public String exec(){
        return sec.sdec();
    }
}
