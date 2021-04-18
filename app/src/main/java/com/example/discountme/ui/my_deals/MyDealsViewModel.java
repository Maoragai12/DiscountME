package com.example.discountme.ui.my_deals;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MyDealsViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public MyDealsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is my deals fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}