package com.example.discountme.viewmodels;

import android.net.Uri;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.discountme.data.local.AppLocalDb;
import com.example.discountme.data.local.DealDao;
import com.example.discountme.data.remote.AuthFirebase;
import com.example.discountme.model.Deal;
import com.example.discountme.model.DealRepository;
import com.example.discountme.model.User;
import com.example.discountme.model.UserRepository;

import java.util.List;

public class UserViewModel extends ViewModel {

    private MutableLiveData<User> userLiveData;

    private final UserRepository repository;

    public void setData(User user){
        if(user != null){
            userLiveData.setValue(user);
        }
    }

    public LiveData<User> getData(){
        if(userLiveData == null){
            userLiveData = new MutableLiveData<>();
        }
        return userLiveData;
    }

    public UserViewModel(){
        repository = UserRepository.getInstance();
    }

    public void add(User user, UserRepository.Listener listener) {
        repository.addUser(user, listener);
    }

    public void update(User user, Uri imageUri, UserRepository.Listener listener) {
        if(imageUri == null)
            repository.updateUser(user, listener);
        else
            repository.updateUser(user, imageUri, listener);
       // setData(user);
    }

    // todo: replace
    public void readUser(String uid, UserRepository.Listener<User> listener) {
        repository.getUser(uid, listener);
    }

}
