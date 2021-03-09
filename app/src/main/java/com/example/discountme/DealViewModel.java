package com.example.discountme;

import androidx.lifecycle.ViewModel;

import com.example.discountme.Model.Deal;
import com.example.discountme.Model.DealModel;


public class DealViewModel extends ViewModel {

    public void add(Deal deal, DealModel.Listener listener) {
        DealModel.instance.addDeal(deal, listener);
    }

    public void update(Deal deal, DealModel.Listener listener) {
        DealModel.instance.updateDeal(deal, listener);
    }

    public void delete(Deal deal, DealModel.Listener listener) {
        DealModel.instance.deleteDeal(deal, listener);
    }
}
