package com.example.discountme.model;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.discountme.data.local.AppLocalDb;
import com.example.discountme.data.local.DealDao;
import com.example.discountme.data.remote.DealFirebase;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DealRepository {

    private static DealRepository instance = null;



    public interface Listener<T>{
        void onComplete(T data);
    }
    public interface CompListener{
        void onComplete();
    }

    private DealDao dealDao;

    private DealRepository(DealDao dealDao){
        this.dealDao = dealDao;
        refreshDealList(null);
    }

    public static DealRepository getInstance(DealDao dealDao)
    {
        if (instance == null)
            instance = new DealRepository(dealDao);

        return instance;
    }


    public LiveData<List<Deal>> getAllDeals() {
        return dealDao.getAll();
    }

    public LiveData<List<Deal>> getAllDeals(int category) {
        return dealDao.getAll(category);
    }

    public LiveData<List<Deal>> getUserDeals(String userId){
        return dealDao.getAllUser(userId);
    }

    public LiveData<Deal> getDeal(String dealId){
        return dealDao.getItem(dealId);
    }


    public void refreshDealList(final CompListener listener){
        DealFirebase.getAllDeals(new Listener<List<Deal>>() {
            @Override
            // Thread
            public void onComplete(final List<Deal> data) {
                ExecutorService executor = Executors.newSingleThreadExecutor();
                Handler handler = new Handler(Looper.getMainLooper());

                executor.execute(() -> {
                    //Background work here
                    Log.d("TAG" , "Backgroud");
                    for(Deal Deal : data) {
                        //if (!Deal.isDeleted()) {
                            Log.d("TAG" , "Insert = " + Deal.title);
                            dealDao.insertAll(Deal);
                        //}
                    }

                    handler.post(() -> {
                        //UI Thread work here
                        if (listener != null)  {
                            listener.onComplete();
                        }
                    });
                });
            }
        }, null);
    }


    @SuppressLint("StaticFieldLeak")
    public void addDeal(final Deal deal, Listener<Boolean> listener) {
        new AsyncTask<String,String,String>(){
            @Override
            protected String doInBackground(String... strings) {
                dealDao.insertAll(deal);
                return "";
            }
        }.execute("");
        DealFirebase.addDeal(deal,listener);
    }

    @SuppressLint("StaticFieldLeak")
    public void addDealToFavs(final Deal deal, Listener<Boolean> listener) {
        DealFirebase.addDealToFavs(deal.id ,listener);
        deal.addUserToLiked(FirebaseAuth.getInstance().getUid());

        new AsyncTask<String,String,String>(){
            @Override
            protected String doInBackground(String... strings) {
                dealDao.insertAll(deal);
                return "";
            }
        }.execute("");
    }

    @SuppressLint("StaticFieldLeak")
    public void removeDealFromFavs(final Deal deal, Listener<Boolean> listener) {
        DealFirebase.removeDealFromFavs(deal.id, listener);
        deal.removeUserFromLiked(FirebaseAuth.getInstance().getUid());

        new AsyncTask<String,String,String>(){
            @SuppressLint("StaticFieldLeak")
            @Override
            protected String doInBackground(String... strings) {
                dealDao.insertAll(deal);
                return "";
            }
        }.execute("");
    }


    @SuppressLint("StaticFieldLeak")
    public void deleteDeal(final Deal deal, Listener<Boolean> listener) {
        new AsyncTask<String,String,String>(){
            @Override
            protected String doInBackground(String... strings) {
                dealDao.deleteDeal(deal);
                return "";
            }
        }.execute("");
        DealFirebase.deleteDeal(deal, listener);
       // refreshDealList(null);
    }

    @SuppressLint("StaticFieldLeak")
    public void updateDeal(final Deal deal, Listener<Boolean> listener) {
        new AsyncTask<String,String,String>(){
            @Override
            protected String doInBackground(String... strings) {
                dealDao.insertAll(deal);
                return "";
            }
        }.execute("");
        DealFirebase.updateDeal(deal,listener);
    }

}
