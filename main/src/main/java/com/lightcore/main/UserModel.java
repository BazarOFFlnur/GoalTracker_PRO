package com.lightcore.main;

public class UserModel {
    private String uid;
    private String email;
    public UserModel(String uid, String email){
        this.uid = uid;
        this.email = email;
    }

    public String getUid() {
        return uid;
    }
    public String getEmail(){
        return email;
    }
}
