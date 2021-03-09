package com.example.discountme.Model;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import com.example.discountme.MyApplication;


@Database(entities = {Deal.class}, version = 1)
abstract class AppLocalDbRepository extends RoomDatabase {
    public abstract DealDao dealDao();
}

public class AppLocalDb {
    static public AppLocalDbRepository db =
            Room.databaseBuilder(MyApplication.context,
                    AppLocalDbRepository.class,
                    "discount.db")
                    .fallbackToDestructiveMigration()
                    .build();
}
