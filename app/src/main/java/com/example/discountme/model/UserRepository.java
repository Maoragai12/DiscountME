package com.example.discountme.model;

import android.annotation.SuppressLint;
import android.net.Uri;

import androidx.annotation.NonNull;

import com.example.discountme.data.local.DealDao;
import com.example.discountme.data.remote.AuthFirebase;
import com.example.discountme.data.remote.StorageFirebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class UserRepository {
    private static UserRepository instance = null;



    public interface Listener<T>{
        void onComplete(T data);
    }
    public interface CompListener{
        void onComplete();
    }

    private UserRepository(){
    }

    public static UserRepository getInstance()
    {
        if (instance == null)
            instance = new UserRepository();

        return instance;
    }

    public void updateUser(final User user, Listener<Boolean> listener) {
        AuthFirebase.updateUser(user,listener);
    }

    public void updateUser(User user, Uri imageUri, Listener<Boolean> listener) {
        final String imagePath = "users/"+user.uid+".png";

        StorageFirebase.uploadPhoto(imageUri, imagePath)
                .addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                StorageReference imageStorageRef =
                        FirebaseStorage.getInstance().getReference(imagePath);
                imageStorageRef.getDownloadUrl().addOnCompleteListener(taskUrl -> {
                    if(taskUrl.isSuccessful() && taskUrl.getResult()!=null){
                        user.image = taskUrl.getResult().toString();
                    }
                   // else{
                        AuthFirebase.updateUser(user, listener);
                   // }
                });
            }
            else {
                AuthFirebase.updateUser(user, listener);
            }
        });


    }

    public void getUser(String uid, Listener<User> listener) {
        AuthFirebase.getUserFromFirebase(uid, listener);
    }

    public void addUser(User user, Listener listener) {
        AuthFirebase.addUser(user, listener);
    }

}
