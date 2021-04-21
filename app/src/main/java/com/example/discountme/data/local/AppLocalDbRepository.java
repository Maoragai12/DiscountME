package com.example.discountme.data.local;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.discountme.MyApplication;
import com.example.discountme.model.Deal;
import com.example.discountme.utilities.Converters;


@Database(entities = {Deal.class}, version = 1, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class AppLocalDbRepository extends RoomDatabase {
    public abstract DealDao dealDao();
}

