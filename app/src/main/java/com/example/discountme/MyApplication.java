package com.example.discountme;

import android.app.Application;
import android.content.Context;

import androidx.multidex.MultiDex;

import com.example.discountme.model.City;
import com.example.discountme.utilities.Utils;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collections;

public class MyApplication extends Application {
    public static MyApplication instance;
    public static ArrayList<City> cities;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        String jsonArrayString = Utils.readRawResource(R.raw.israel_cities, getApplicationContext());
        City[] _cities = new Gson().fromJson(jsonArrayString, City[].class);
        cities = new ArrayList<>();
        Collections.addAll(cities, _cities);
    }
}
