package com.example.discountme.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
public class Deal implements Serializable {
    @PrimaryKey
    @NonNull
    public String  id;
    public String title;
    public String description;
    public int/*String*/ type;
    public String cost;
    public String imageUrl;
    public String userId;
    public boolean deleted;
    public ArrayList<String> liked;

//    private String dateUpdated;

    public Deal() {

    }

    public Deal(String id, String title, String description, int type, String cost, String imageUrl, Timestamp timestamp, ArrayList<String> liked) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.type = type;
        this.cost = cost;
        this.imageUrl = imageUrl;
        this.userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        this.deleted = false;
        this.liked = liked;
//        this.dateUpdated = timestamp.toString();
    }

    public Deal(String id, String title, int type, String description, String cost, String imageUrl, Boolean deleted, Timestamp timestamp, ArrayList<String> liked) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.type = type;
        this.cost = cost;
        this.imageUrl = imageUrl;
        this.userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        this.deleted = deleted;
        this.liked = liked;
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

    public int getType() {
        return type;
    }

    public void setType(int type) {
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


    public ArrayList<String> getLiked() {
        return liked;
    }

    public void setLiked(ArrayList<String> liked) {
        this.liked = liked;
    }


    public Map<String, Object> toMap(){
        Map<String, Object> map = new HashMap<>();
        map.put("title", title);
        map.put("description", description);
        map.put("cost", cost);
        map.put("type", type);
        map.put("imageUrl", imageUrl);

        return map;
    }

    public void addUserToLiked(String userId) {
        this.liked.add(userId);
    }

    public void removeUserFromLiked(String userId) {
        this.liked.remove(userId);
    }

    public boolean likedByUser(String userId){
        return liked.contains(userId);
    }

    public boolean isMy(){
        return userId.equals(FirebaseAuth.getInstance().getUid());
    }

//    public String getLastUpdated() {
//        return dateUpdated;
//    }
//
//    public void setLastUpdated(String dateUpdated) {
//        this.dateUpdated = dateUpdated;
//    }
}
