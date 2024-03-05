package com.lightcore.goaltracker_pro.ui.Model;

public class SubTasks {

    private String name; // название
    private String progress;  // столица

    public SubTasks(String name, String progress){

        this.name=name;
        this.progress=progress;
    }

    public SubTasks(){}
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProgress() {
        return this.progress;
    }

    public void setProgress(String progress) {
        this.progress = progress;
    }

}