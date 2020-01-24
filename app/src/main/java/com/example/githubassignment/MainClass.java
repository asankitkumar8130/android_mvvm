package com.example.githubassignment;

import android.app.Application;

public class MainClass extends Application {
    public static MainClass context;

    public static MainClass getInstance() {
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
    }
}
