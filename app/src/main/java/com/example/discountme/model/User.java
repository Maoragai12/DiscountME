package com.example.discountme.model;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.discountme.R;
import com.google.firebase.firestore.Exclude;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Entity
public class User implements Serializable {
    @PrimaryKey
    @NonNull
    public String uid;
    public String name;
    public String email;
    public int aio;
    public String image;

    public User() {}

    public User (@NonNull String uid, String name, String email, int aio, String image) {
        this.uid = uid;
        this.name = name;
        this.email = email;
        this.aio = aio;
        this.image = image;
    }


    @NonNull
    public String getUid() {
        return uid;
    }

    public void setUid(@NonNull String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAIO() { return aio; }

    public void setAIO(int aio) {
        this.aio = aio;
    }

    public String getImage() { return image; }

    public void setImage(String image) {
        this.image = image;
    }

    public String getInterestName(Context context) {
        String[] names = context.getResources().getStringArray(R.array.interests);
        return names[this.aio-1];
    }


    @Exclude
    public Map<String, Object> toMap(){
        Map<String, Object> map = new HashMap<>();
        map.put("name", this.name);
        map.put("email", this.email);
        map.put("aio", this.aio);
        map.put("image", this.image);

        return map;
    }
}


