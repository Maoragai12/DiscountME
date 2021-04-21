package com.example.discountme.data.local;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.OnConflictStrategy;

import com.example.discountme.model.Deal;

import java.util.List;

@Dao
public interface DealDao {
    @Query("select * from Deal")
    LiveData<List<Deal>> getAll();

    @Query("select * from Deal")
    List<Deal> getAllList();

    @Query("select * from Deal where type == :category")
    LiveData<List<Deal>> getAll(int category);

    @Query("select * from Deal where userId == :userId")
    LiveData<List<Deal>> getAllUser(String userId);

    @Query("select * from Deal where id == :dealId")
    LiveData<Deal> getItem(String dealId);


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Deal... deals);

    @Delete
    void deleteDeal(Deal... deals);

}