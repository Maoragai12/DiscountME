package com.example.discountme.data.remote;

import android.net.Uri;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class StorageFirebase {
    public static UploadTask uploadPhoto(Uri imageUri, String path) {
        StorageReference imageRef = FirebaseStorage.getInstance().getReference(path);
        return imageRef.putFile(imageUri);
    }
}
