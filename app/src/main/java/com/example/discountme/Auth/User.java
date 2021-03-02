package com.example.discountme.Auth;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class User implements Serializable {
    @PrimaryKey
    @NonNull
    public String uid;
    public String name;
    public String email;
    public String password;
    public String aio;


    public User() {}

    public User (@NonNull String uid, String name, String email, String password, String aio) {
        this.uid = uid;
        this.name = name;
        this.email = email;
        this.password = password;
        this.aio = aio;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAIO() { return aio; }

    public void setAIO(String aio) {
        this.aio = aio;
    }
}
