package com.example.discountme.ui.feed;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import com.example.discountme.Auth.User;
import com.example.discountme.Model.Deal;
import com.example.discountme.Model.DealModel;
import com.example.discountme.dealsListViewModel;
import com.example.discountme.R;
import com.example.discountme.UserListViewModel;
import com.example.discountme.MyApplication;

import java.util.List;


public class FeedFragment extends Fragment {

//    RecyclerView postList;
//    UserListViewModel userListViewModel;
//    dealsListViewModel postListViewModel;
//    String currentUserID="0";
//    ImageButton visitProfile;
//    ImageButton mapMode;
//    Button addAPostBtn;
//    ProgressBar PB_newsFeed;



//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        View view =inflater.inflate(R.layout.fragment_feed, container, false);
//
//        DealModel.instance.refreshDealList(null);
//
//        postListViewModel=new ViewModelProvider(this).get(dealsListViewModel.class);
//        userListViewModel=new ViewModelProvider(this).get(UserListViewModel.class);
//        SharedPreferences sp = MyApplication.context.getSharedPreferences("Users", Context.MODE_PRIVATE);
//        currentUserID=sp.getString("currentUserID","0");
//        PB_newsFeed = view.findViewById(R.id.signup_progressBar);
//        PB_newsFeed.setVisibility(View.VISIBLE);
//        addAPostBtn= view.findViewById(R.id.addApost_newsFeed);
//
//        //postList
//
//        postList=view.findViewById(R.id.postList_newFeed);
//        postList.hasFixedSize();
//
//        LinearLayoutManager layoutmaneger = new LinearLayoutManager(this.getContext());
//        postList.setLayoutManager(layoutmaneger);
//
//        postListAdapter adapter = new postListAdapter(postListViewModel.getPostList(),userListViewModel.getUserList());
//        postList.setAdapter(adapter);
//
//
//
//        postListViewModel.getPostList().observe(getViewLifecycleOwner(), new Observer<List<Deal>>() {
//            @Override
//            public void onChanged(List<Post> posts) {
//                PB_newsFeed.setVisibility(View.INVISIBLE);
//                adapter.notifyDataSetChanged();
//            }
//        });
//
//        userListViewModel.getUserList().observe(getViewLifecycleOwner(), new Observer<List<User>>() {
//            @Override
//            public void onChanged(List<User> users) {
//                adapter.notifyDataSetChanged();
//            }
//        });
//
//
//        //Redirect to Add a post
//        addAPostBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                newsFeedFragmentDirections.ActionNewsFeedFragmentToAddPostFragment actionAdd =
//                        newsFeedFragmentDirections.actionNewsFeedFragmentToAddPostFragment(currentUserID);
//                Navigation.findNavController(view).navigate(actionAdd);
//            }
//        });
//
//        //Redirect to My Profile
//        visitProfile.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                newsFeedFragmentDirections.ActionNewsFeedFragmentToProfileFragment actionProfile =
//                        newsFeedFragmentDirections.actionNewsFeedFragmentToProfileFragment(currentUserID);
//                Navigation.findNavController(view).navigate(actionProfile);
//            }
//        });
//
//        //Redirect to map mode
//        mapMode.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_newsFeed_to_maps));
//
//
//        SwipeRefreshLayout swipeRefreshLayout=view.findViewById(R.id.newsFeedSwipe);
//        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                swipeRefreshLayout.setRefreshing(true);
//                Model.instance.refreshData(new Model.refreshListener() {
//                    @Override
//                    public void onComplete() {
//                        swipeRefreshLayout.setRefreshing(false);
//                    }
//                });
//            }
//        });
//        adapter.setOnItemClickListener(new postListAdapter.onItemClickListener() {
//            @Override
//            public void onClick(int position) {
//                int size= postListViewModel.getPostList().getValue().size();
//                String postId = postListViewModel.getPostList().getValue().get(size-position-1).getPostID() ;
//                newsFeedFragmentDirections.ActionNewsFeedFragmentToSinglePostFragment actionToSinglePost =
//                        newsFeedFragmentDirections.actionNewsFeedFragmentToSinglePostFragment(postId);
//
//                Navigation.findNavController(view).navigate(actionToSinglePost);
//            }
//        });
//        return view;
//    }
}