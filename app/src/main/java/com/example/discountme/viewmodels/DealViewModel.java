package com.example.discountme.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.example.discountme.data.local.AppLocalDb;
import com.example.discountme.data.local.DealDao;
import com.example.discountme.model.Deal;
import com.example.discountme.model.DealRepository;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;


public class DealViewModel extends ViewModel {
    private MutableLiveData<Deal> dealLiveData;
    private final DealRepository repository;
    private final String id;

    public DealViewModel(String dealId){
        DealDao dealDao = AppLocalDb.db.dealDao();
        repository = DealRepository.getInstance(dealDao);
        this.id = dealId;
    }

//    public LiveData<Deal> getData() {
//        if (dealLiveData == null) {
//            dealLiveData = new MutableLiveData<>();
//            loadData();
//        }
//        return dealLiveData;
//    }

    public void setData(Deal deal){
        if (dealLiveData == null)
            dealLiveData = new MutableLiveData<>();

        dealLiveData.setValue(deal);
    }

    private void loadData() {
        dealLiveData.setValue(
            repository.getDeal(this.id).getValue()
        );
    }

    public void addToMyFav(DealRepository.Listener<Boolean> listener){
        try {
            repository.addDealToFavs(dealLiveData.getValue(), new DealRepository.Listener<Boolean>() {
                @Override
                public void onComplete(Boolean data) {
                 //   loadData();
                }
            });

        }
        catch (Exception ignored){

        }
    }

    public void removeFromMyFav(DealRepository.Listener<Boolean> listener){
        try {
            repository.removeDealFromFavs(dealLiveData.getValue(), new DealRepository.Listener<Boolean>() {
                @Override
                public void onComplete(Boolean data) {
                  //  loadData();
                }
            });

        }
        catch (Exception ignored){

        }
    }

}

