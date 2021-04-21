package com.example.discountme.view.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.discountme.R;
import com.example.discountme.model.Deal;
import com.example.discountme.viewmodels.UserViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

public class DealDetailsFragment extends Fragment {


    TextView dealNameText;
    TextView storeNameText;
    TextView cityText;
    TextView descriptionText;
    TextView offeredByText;
    FloatingActionButton editButton;
    ImageView offeredByPhoto;

    private Deal deal;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        deal = DealDetailsFragmentArgs.fromBundle(getArguments()).getDeal();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_deal, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);


        dealNameText = view.findViewById(R.id.deal_name_text);
//        storeNameText = view.findViewById(R.id.store_name_text);
//        cityText = view.findViewById(R.id.city_text_deal);
        descriptionText = view.findViewById(R.id.description_text_deal);
        editButton = view.findViewById(R.id.edit_deal_button);
        offeredByText = view.findViewById(R.id.username_text_profile);
        offeredByPhoto = view.findViewById(R.id.image_offered_by);

        setupViews();

    }

    private void setupViews() {
        dealNameText.setText(deal.getTitle());

        descriptionText.setText(deal.getDescription().isEmpty() ? "No Description given.." : deal.getDescription());

        UserViewModel userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        userViewModel.readUser(deal.userId, user -> {
            offeredByText.setText(user.name);

            if(user.getImage() == null || user.getImage().isEmpty()){
                Picasso.get().load(R.drawable.selfprofile)
                        .centerCrop()
                        .fit()
                        .into(offeredByPhoto);
            }
            else{
                Picasso.get().load(user.getImage())
                        .placeholder(R.drawable.selfprofile)
                        .error(R.drawable.selfprofile)
                        .centerCrop()
                        .fit()
                        .into(offeredByPhoto);
            }
        });


        editButton.setVisibility(deal.isMy() ? View.VISIBLE : View.GONE);

        editButton.setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);

            Bundle args = new Bundle();
            args.putSerializable("deal", deal);

            navController.navigate(R.id.editDealFragment, args);
        });
    }
}

