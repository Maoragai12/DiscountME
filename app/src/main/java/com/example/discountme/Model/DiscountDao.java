package com.example.discountme.Model;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.OnConflictStrategy;

import com.example.discountme.Model.Discount;

import java.util.List;

@Dao
public interface DiscountDao {
    @Query("select * from Discount")
    LiveData<List<Discount>> getAll();

    @Query("select * from Discount")
    List<Discount> getAllList();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Discount... discounts);

    @Delete
    void deleteJewelry(Discount... discounts);

}