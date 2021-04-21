package com.example.discountme.view.fragments;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.example.discountme.MyApplication;
import com.example.discountme.R;
import com.example.discountme.model.Deal;
import com.example.discountme.model.DealRepository;
import com.example.discountme.utilities.Utils;
import com.example.discountme.viewmodels.DealViewModel;
import com.example.discountme.viewmodels.DealsListViewModel;
import com.google.firebase.Timestamp;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AddDealFragment extends Fragment {
    Button saveButton;
    private DealsListViewModel dealsListViewModel;

    private EditText dealNameEt;
    private EditText dealDescriptionEt;
    private RadioGroup radioGroup;
    private Spinner citySpinner;

    private int dealType;


    public AddDealFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_deal, container, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @SuppressLint("NonConstantResourceId")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);

        dealsListViewModel = new ViewModelProvider(this).get(DealsListViewModel.class);

        radioGroup = view.findViewById(R.id.radio);
        dealNameEt = view.findViewById(R.id.deal_name_edit_text);
        dealDescriptionEt = view.findViewById(R.id.description_edit_text);
        citySpinner = view.findViewById(R.id.cities_spinner);

        saveButton = view.findViewById(R.id.btn_save);

        setupViews();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void setupViews() {
        saveButton.setOnClickListener(this::addDealToDb);

        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId){
                case R.id.clothes_rb:
                    dealType = 1;
                    break;

                case R.id.electronics_rb:
                    dealType = 2;
                    break;

                case R.id.sports_rb:
                    dealType = 3;
                    break;

                case R.id.food_rb:
                    dealType = 4;
                    break;
            }
        });


        List<String> citiesNames =  MyApplication.cities.stream()
                .map(city -> city.cityName)
                .collect(Collectors.toList());


        Utils.setItems(citySpinner, citiesNames.toArray(new String[0]), R.layout.spinner_item_layout, R.layout.spinner_item_dropdown_layout);
    }

    private void addDealToDb(View view) {

        dealsListViewModel.add(new Deal(
                "M"+System.currentTimeMillis() / 1000,
                dealNameEt.getText().toString().trim(),
                dealDescriptionEt.getText().toString().trim(),
                dealType,
                "0",
                null,
                 Timestamp.now(),
                new ArrayList<>()
                ), new DealRepository.Listener() {
            @Override
            public void onComplete(Object data) {
                Log.d("TAG", "data entered");
            }
        });
    }
}