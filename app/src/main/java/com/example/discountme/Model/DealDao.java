package com.example.discountme.Model;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.OnConflictStrategy;

import java.util.List;

@Dao
public interface DealDao {
    @Query("select * from Deal")
    LiveData<List<Deal>> getAll();

    @Query("select * from Deal")
    List<Deal> getAllList();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Deal... deals);

    @Delete
    void deleteDeal(Deal... deals);

}