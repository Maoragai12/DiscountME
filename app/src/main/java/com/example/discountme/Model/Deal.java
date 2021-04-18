package com.example.discountme.Model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.ServerTimestamp;

import java.io.Serializable;
import java.sql.Time;
import java.util.HashMap;
import java.util.Map;

@Entity
public class Deal implements Serializable {
    @PrimaryKey
    @NonNull
    public String  id;
    public String title;
    public String description;
    public String type;
    public String cost;
    public String imageUrl;
    public String userId;
    public boolean deleted;
//    private String dateUpdated;

    public Deal() {

    }

    public Deal(String id, String title, String description, String type, String cost, String imageUrl, Timestamp timestamp) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.type = type;
        this.cost = cost;
        this.imageUrl = imageUrl;
        this.userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        this.deleted = false;
//        this.dateUpdated = timestamp.toString();
    }

    public Deal(String id, String title, String type, String description, String cost, String imageUrl, Boolean deleted, Timestamp timestamp) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.type = type;
        this.cost = cost;
        this.imageUrl = imageUrl;
        this.userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        this.deleted = deleted;
//        this.dateUpdated = timestamp.toString();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

//    public String getLastUpdated() {
//        return dateUpdated;
//    }
//
//    public void setLastUpdated(String dateUpdated) {
//        this.dateUpdated = dateUpdated;
//    }
}
