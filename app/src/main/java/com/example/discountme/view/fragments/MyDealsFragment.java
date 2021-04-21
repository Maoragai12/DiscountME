package com.example.discountme.view.fragments;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.discountme.data.remote.DealFirebase;
import com.example.discountme.model.Deal;
import com.example.discountme.model.DealRepository;
import com.example.discountme.view.adapters.DealsAdapter;
import com.example.discountme.viewmodels.DealsListViewModel;
import com.example.discountme.viewmodels.UserViewModel;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class MyDealsFragment extends DealsListFragment {

    DealsListViewModel dealsListViewModel;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        addAPostBtn.setVisibility(View.VISIBLE);
        dealsListViewModel = new ViewModelProvider(getActivity()).get(DealsListViewModel.class);

        dealsListViewModel.getCurrentUserDeals().observe(getActivity(), new Observer<List<Deal>>() {
            @Override
            public void onChanged(List<Deal> data) {
                PB_newsFeed.setVisibility(View.INVISIBLE);

                textMessage.setVisibility(data.isEmpty() ? View.VISIBLE: View.GONE);
                textMessage.setText("You yet to publish a deal.\nDeals created by you will appear here!");
                adapter.setData(new ArrayList<>(data));
            }
        });

        mSwipeRefreshLayout.setEnabled(false);
    }
}
