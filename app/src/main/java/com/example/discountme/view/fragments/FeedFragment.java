package com.example.discountme.view.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
//import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.view.View;

import com.example.discountme.model.Deal;
import com.example.discountme.model.DealRepository;
import com.example.discountme.viewmodels.DealsListViewModel;
import com.example.discountme.viewmodels.UserViewModel;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;


public class FeedFragment extends DealsListFragment {

    private UserViewModel userViewModel;
    private DealsListViewModel dealsListViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        userViewModel = new ViewModelProvider(getActivity()).get(UserViewModel.class);
        dealsListViewModel = new ViewModelProvider(getActivity()).get(DealsListViewModel.class);


        userViewModel.readUser(FirebaseAuth.getInstance().getUid(), user -> {

            dealsListViewModel.getAllDeals(user.aio).observe(getActivity(), new Observer<List<Deal>>() {
                @Override
                public void onChanged(List<Deal> data) {
                    PB_newsFeed.setVisibility(View.INVISIBLE);

                    textMessage.setVisibility(data.isEmpty() ? View.VISIBLE : View.GONE);
                    textMessage.setText("No Deals for now, Try later!");
                    adapter.setData(new ArrayList<>(data));

                }
            });
        });



        mSwipeRefreshLayout.setOnRefreshListener(() -> {

            PB_newsFeed.setVisibility(View.VISIBLE);
            postList.setVisibility(View.GONE);


            dealsListViewModel.refreshFeed(new DealRepository.CompListener() {
                @Override
                public void onComplete() {
                    if (mSwipeRefreshLayout.isRefreshing()) {
                        mSwipeRefreshLayout.setRefreshing(false);
                    }

                    PB_newsFeed.setVisibility(View.GONE);
                    postList.setVisibility(View.VISIBLE);
                }
            });
        });

    }
}