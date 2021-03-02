package com.example.discountme.Model;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import com.example.discountme.MyApplication;


@Database(entities = {Discount.class}, version = 1)
abstract class AppLocalDbRepository extends RoomDatabase {
    public abstract DiscountDao discountDao();
}

public class AppLocalDb {
    static public AppLocalDbRepository db =
            Room.databaseBuilder(MyApplication.context,
                    AppLocalDbRepository.class,
                    "discount.db")
                    .fallbackToDestructiveMigration()
                    .build();
}
