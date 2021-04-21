package com.example.discountme.data.local;

import androidx.room.Room;

import com.example.discountme.MyApplication;

public class AppLocalDb {
    static public AppLocalDbRepository db =
            Room.databaseBuilder(MyApplication.instance.getApplicationContext(),
                    AppLocalDbRepository.class,
                    "discount.db")
                    .fallbackToDestructiveMigration()
                    .build();
}
