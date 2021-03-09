package com.example.discountme.Model;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class DealFirebase {
    final static String DEAL_COLLECTION = "deals";

    List<Deal> deals;

    public static void getAllDeals(final DealModel.Listener<List<Deal>> listener) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(DEAL_COLLECTION).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                List<Deal> dealList = null;
                if (task.isSuccessful()){
                    dealList = new LinkedList<Deal>();
                    for(QueryDocumentSnapshot doc : task.getResult()){
                        Deal deal = doc.toObject(Deal.class);
                        if (!deal.isDeleted()) {
                            dealList.add(deal);
                        }
                    }
                }
                listener.onComplete(dealList);
            }
        });
    }

    public static void getUsersDeals(final DealModel.Listener<List<Deal>> listener) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        db.collection(DEAL_COLLECTION).whereEqualTo("userId", userId).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                List<Deal> dealList = null;
                if (task.isSuccessful()){
                    dealList = new LinkedList<Deal>();
                    for(QueryDocumentSnapshot doc : task.getResult()){
                        Deal deal = doc.toObject(Deal.class);
                        if (!deal.isDeleted()) {
                            dealList.add(deal);
                        }
                    }
                }
                listener.onComplete(dealList);
            }
        });
    }

    public static void addDeal(Deal deal, final DealModel.Listener<Boolean> listener) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference documentReference = db.collection(DEAL_COLLECTION).document();

        deal.setId(documentReference.getId());

        Log.d("TAG" , "add = " + deal.id + " " + documentReference.getId());

        documentReference.set(deal)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (listener!=null){
                            listener.onComplete(task.isSuccessful());
                        }
                    }
                });
    }

    public static void updateDeal(Deal deal, final DealModel.Listener<Boolean> listener) {
        Map<String, Object> map = new HashMap<>();
        map.put("title", deal.title);
        map.put("description", deal.description);
        map.put("cost", deal.cost);
        map.put("type", deal.type);
        map.put("imageUrl", deal.imageUrl);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference documentReference = db.collection(DEAL_COLLECTION).document(deal.id);
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

    public static void deleteDeal(Deal deal, final DealModel.Listener<Boolean> listener) {
        updateDealDeleted(deal);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Log.d("TAG" , "to delete firebase = " + deal.id + " " + deal.title);
        db.collection(DEAL_COLLECTION).document(deal.id)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("TAG", "DocumentSnapshot successfully deleted!");
                        listener.onComplete(true);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("TAG", "Error deleting document", e);
                    }
                });

    }

    public static void updateDealDeleted(Deal deal) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(DEAL_COLLECTION).document(deal.id)
                .update("deleted", true)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("TAG", "DocumentSnapshot successfully deleted!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("TAG", "Error deleting document", e);
                    }
                });

    }
}
