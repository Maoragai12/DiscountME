package com.example.discountme;

import android.app.Application;
import android.content.Context;

import androidx.multidex.MultiDex;

public class MyApplication extends Application {
    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }
}
