package com.example.discountme.ui.my_deals;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.discountme.Model.Deal;
import com.example.discountme.R;

public class MyDealsFragment extends Fragment {

    private Deal deal;
    TextView dealName;
    TextView storeName;
    TextView city;
    TextView branchAddress;
    TextView description;

    public MyDealsFragment(){}


    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_deal, container, false);

        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        dealName = view.findViewById(R.id.dealName_text_deal);
        storeName = view.findViewById(R.id.store_text_deal);
        city = view.findViewById(R.id.city_text_deal);
        branchAddress = view.findViewById(R.id.address_text_deal);
        description = view.findViewById(R.id.description_text_deal);

        return view;
    }
}
