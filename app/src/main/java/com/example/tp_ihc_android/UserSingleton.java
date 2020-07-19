package com.example.tp_ihc_android;


import android.content.Context;

public class UserSingleton {
    private static UserSingleton instance;
    private static Context ctx;
    private String nome;
    private String email;

    private UserSingleton(Context context) {
        ctx = context;
        this.nome = "";
        this.email = "";
    }

    public static synchronized UserSingleton getInstance(Context context) {
        if (instance == null) {
            instance = new UserSingleton(context);
        }
        return instance;
    }
    public String getEmail(){
        return this.email;
    }
    public void setEmail(String email){
        this.email = email;
    }
    public String getNome(){
        return this.nome;
    }
    public void setNome(String nome){
        this.nome = nome;
    }

}