package com.example.discountme.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.discountme.data.local.AppLocalDb;
import com.example.discountme.data.local.DealDao;
import com.example.discountme.model.Deal;
import com.example.discountme.model.DealRepository;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;


public class DealsListViewModel extends ViewModel {
    private final DealRepository repository;

    public DealsListViewModel(){
        DealDao dealDao = AppLocalDb.db.dealDao();
        repository = DealRepository.getInstance(dealDao);
    }

    public LiveData<List<Deal>> getAllDeals() {
        return repository.getAllDeals();
    }

    public LiveData<List<Deal>> getAllDeals(int category) {
        return repository.getAllDeals(category);
    }

    public LiveData<List<Deal>> getCurrentUserDeals() {
        return repository.getUserDeals(FirebaseAuth.getInstance().getUid());
    }

    public LiveData<List<Deal>> getUserDeals(String userId) {
        return repository.getUserDeals(userId);
    }

    public void add(Deal deal, DealRepository.Listener listener) {
        repository.addDeal(deal, listener);
    }

    public void update(Deal deal, DealRepository.Listener listener) {
        repository.updateDeal(deal, listener);
    }

    public void delete(Deal deal, DealRepository.Listener listener) {
        repository.deleteDeal(deal, listener);
    }

    public void refreshFeed(DealRepository.CompListener listener) {
        repository.refreshDealList(listener);
    }

}
