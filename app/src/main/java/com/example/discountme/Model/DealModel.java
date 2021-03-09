package com.example.discountme.Model;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;

import java.util.List;

public class DealModel {
    public static final DealModel instance = new DealModel();

    public interface Listener<T>{
        void onComplete(T data);
    }
    public interface CompListener{
        void onComplete();
    }
    public DealModel(){

    }

    public void refreshDealList(final CompListener listener){
        DealFirebase.getAllDeals(new Listener<List<Deal>>() {
            @SuppressLint("StaticFieldLeak")
            @Override
            // Thread
            public void onComplete(final List<Deal> data) {
                new AsyncTask<String,String,String>(){
                    @Override
                    protected String doInBackground(String... strings) {
                        Log.d("TAG" , "Backgroud");
                        for(Deal Deal : data) {
                            if (!Deal.isDeleted()) {
                                Log.d("TAG" , "Insert = " + Deal.title);
                                AppLocalDb.db.dealDao().insertAll(Deal);
                            }
                        }
                        return "";
                    }

                    @Override
                    protected void onPostExecute(String s) {
                        super.onPostExecute(s);
                        if (listener != null)  {
                            listener.onComplete();
                        }
                    }
                }.execute("");
            }
        });
    }

    @SuppressLint("StaticFieldLeak")
    public void addDeal(final Deal deal, Listener<Boolean> listener) {
        new AsyncTask<String,String,String>(){
            @Override
            protected String doInBackground(String... strings) {
                AppLocalDb.db.dealDao().insertAll(deal);
                return "";
            }
        }.execute("");
        DealFirebase.addDeal(deal,listener);
    }


    @SuppressLint("StaticFieldLeak")
    public void deleteDeal(final Deal deal, Listener<Boolean> listener) {
        new AsyncTask<String,String,String>(){
            @Override
            protected String doInBackground(String... strings) {
                AppLocalDb.db.dealDao().deleteDeal(deal);
                return "";
            }
        }.execute("");
        DealFirebase.deleteDeal(deal, listener);
        refreshDealList(null);
    }

    @SuppressLint("StaticFieldLeak")
    public void updateDeal(final Deal deal, Listener<Boolean> listener) {
        new AsyncTask<String,String,String>(){
            @Override
            protected String doInBackground(String... strings) {
                AppLocalDb.db.dealDao().insertAll(deal);
                return "";
            }
        }.execute("");
        DealFirebase.updateDeal(deal,listener);
    }

    public LiveData<List<Deal>> getAllDeals(){
        LiveData<List<Deal>> liveData = null;
        liveData = AppLocalDb.db.dealDao().getAll();
        refreshDealList(null);
        return liveData;
    }



}
