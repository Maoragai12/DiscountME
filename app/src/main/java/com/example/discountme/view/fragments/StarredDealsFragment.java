package com.example.discountme.view.fragments;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.discountme.R;
import com.example.discountme.data.remote.DealFirebase;
import com.example.discountme.model.Deal;
import com.example.discountme.model.DealRepository;
import com.example.discountme.view.adapters.DealsAdapter;
import com.example.discountme.viewmodels.DealsListViewModel;
import com.example.discountme.viewmodels.UserViewModel;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;


public class StarredDealsFragment extends DealsListFragment {

    private DealsListViewModel dealsListViewModel;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);

        dealsListViewModel = new ViewModelProvider(getActivity()).get(DealsListViewModel.class);

        dealsListViewModel.getAllDeals().observe(getActivity(), new Observer<List<Deal>>() {
            @Override
            public void onChanged(List<Deal> data) {
                PB_newsFeed.setVisibility(View.INVISIBLE);

                // filter only the starred ones
                ArrayList<Deal> starredDeals = new ArrayList<>();
                for (Deal deal : data) {
                    if (deal.liked.contains(FirebaseAuth.getInstance().getUid())) {
                        starredDeals.add(deal);
                    }
                }


                adapter.setData(starredDeals);
            }
        });

        mSwipeRefreshLayout.setEnabled(false);
    }
}