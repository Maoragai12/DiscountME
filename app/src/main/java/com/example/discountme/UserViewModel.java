package com.example.discountme;

import androidx.lifecycle.ViewModel;

import com.example.discountme.Auth.AuthFirebase;
import com.example.discountme.Auth.User;
import com.example.discountme.Auth.UserModel;

public class UserViewModel extends ViewModel {

    public void add(User user, UserModel.Listener listener) {
        AuthFirebase.addUser(user, listener);
    }

    public void update(User user, UserModel.Listener listener) {
        AuthFirebase.updateUser(user, listener);
    }

}
