package com.example.discountme.view.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
//import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.discountme.model.Deal;
import com.example.discountme.model.DealRepository;
import com.example.discountme.R;
import com.example.discountme.MyApplication;
import com.example.discountme.view.adapters.DealsAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;


public class DealsListFragment extends Fragment {

    protected RecyclerView postList;
    private String currentUserID="0";
    private ImageButton visitProfile;
    private ImageButton mapMode;
    protected TextView textMessage;
    protected FloatingActionButton addAPostBtn;
    protected ProgressBar PB_newsFeed;
    protected DealsAdapter adapter;
    protected SwipeRefreshLayout mSwipeRefreshLayout;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_deals_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SharedPreferences sp = MyApplication.instance.getApplicationContext().getSharedPreferences("Users", Context.MODE_PRIVATE);
        currentUserID=sp.getString("currentUserID","0");

        ((AppCompatActivity)getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu);

        PB_newsFeed = view.findViewById(R.id.PB_newsFeed);
        PB_newsFeed.setVisibility(View.VISIBLE);
        addAPostBtn= view.findViewById(R.id.add_post_btn);
        textMessage = view.findViewById(R.id.text_empty_list_message);
        mSwipeRefreshLayout = view.findViewById(R.id.swiperefresh_items);

        //postList

        addAPostBtn.setVisibility(View.GONE);
        postList=view.findViewById(R.id.feed_recyclerview);
        postList.hasFixedSize();
//
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        postList.setLayoutManager(layoutManager);

        adapter = new DealsAdapter(new ArrayList<Deal>(), this); //dealsListViewModel.getAllDeals() ,userListViewModel.getUserList());
        postList.setAdapter(adapter);


//        //Redirect to Add a post
        addAPostBtn.setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
            navController.navigate(R.id.addDealFragment);
        });
    }
}