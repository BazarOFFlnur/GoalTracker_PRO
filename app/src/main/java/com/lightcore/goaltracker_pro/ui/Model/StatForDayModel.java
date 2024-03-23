package com.lightcore.goaltracker_pro.ui.Model;

public class StatForDayModel {
    String day;
    String week;
    Integer progress;
    public StatForDayModel(String _day, String _week, Integer _progress){
        this.day = _day;
        this.week = _week;
        this.progress = _progress;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public Integer getProgress() {
        return progress;
    }

    public void setProgress(Integer progress) {
        this.progress = progress;
    }
}
