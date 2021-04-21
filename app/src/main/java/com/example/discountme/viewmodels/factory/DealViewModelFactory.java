package com.example.discountme.viewmodels.factory;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.discountme.viewmodels.DealViewModel;


/**
 *
 * A ViewModel Factory for  @link{DisheViewModel}
 */
public class DealViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private String mDealId;

    public DealViewModelFactory(String dealId) {
        mDealId = dealId;
    }


    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new DealViewModel(mDealId);
    }
}
