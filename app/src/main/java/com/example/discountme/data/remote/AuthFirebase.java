package com.example.discountme.data.remote;

import android.annotation.SuppressLint;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.discountme.model.User;
import com.example.discountme.model.UserRepository;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


import java.util.HashMap;
import java.util.Map;

public class AuthFirebase
{
    final static String USERS_COLLECTION = "users";
    private static String userId;
    private static Map<String, Object> user;
    private static User u;
    public static boolean flag = false;

    @SuppressLint("RestrictedApi")
    public static void addUser(User user , final UserRepository.Listener<Boolean> listener) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(USERS_COLLECTION).document(user.getUid()).set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (listener!=null){
                    listener.onComplete(task.isSuccessful());
                }
            }
        });
    }

    public static void updateUser(User user, final UserRepository.Listener<Boolean> listener) {
        Map<String, Object> map = user.toMap();

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference documentReference = db.collection(USERS_COLLECTION).document(user.getUid());
        documentReference.update(map)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (listener!=null){
                            Log.d("TAG", "DocumentSnapshot successfully update!");
                            listener.onComplete(task.isSuccessful());
                        }
                    }
                });
    }

    public static void getUserFromFirebase(String userId, final UserRepository.Listener<User> listener) {
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        DocumentReference documentReference = firebaseFirestore.collection(USERS_COLLECTION).document(userId);
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {

            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d("TAG", "User Data: " + document.getData());
                        user = document.getData();

                        u = document.toObject(User.class);

                        if (listener!=null){
                            listener.onComplete(u);
                        }

                    } else {
                        Log.d("TAG", "User not exists");
                    }
                } else {
                    Log.d("TAG", "User Get Failed ");
                }
            }
        });
//        return u;
    }

}
