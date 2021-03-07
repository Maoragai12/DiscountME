package com.example.discountme.Auth;

import android.annotation.SuppressLint;
import android.os.AsyncTask;

public class UserModel {

    public static final UserModel instance = new UserModel();

    @SuppressLint("StaticFieldLeak")
    public void updateUser(final User user, Listener<Boolean> listener) {
        AuthFirebase.updateUser(user,listener);
    }

    public interface Listener<T>{
        void onComplete(T data);
    }
    public interface CompListener{
        void onComplete();
    }
}
