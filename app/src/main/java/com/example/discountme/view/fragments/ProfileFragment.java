package com.example.discountme.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.example.discountme.data.remote.AuthFirebase;
import com.example.discountme.model.User;
import com.example.discountme.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;


public class ProfileFragment extends Fragment {

    private User user;
    private TextView nameText;
    private TextView emailText;
    private TextView interestText;
    private Button starredDealsButton;

//    private TextView password;
    private FloatingActionButton editBTN;
    private NavController navController;
    private ImageView profileImage;

    public ProfileFragment() {
        // Required empty public constructor
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu);

        profileImage = view.findViewById(R.id.image_profile);
        nameText = view.findViewById(R.id.fullname_text_profile);
        emailText = view.findViewById(R.id.email_text_profile);
        interestText = view.findViewById(R.id.interest_text);
//        password = view.findViewById(R.id.password_text_profile);
        editBTN = (FloatingActionButton) view.findViewById(R.id.edit_profile_button);

        starredDealsButton = view.findViewById(R.id.starred_deals_button);

        AuthFirebase.getUserFromFirebase(FirebaseAuth.getInstance().getCurrentUser().getUid(), data -> {
            user = data;
            if(user != null) {
                updateDisplay();
            }
        });


        navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);

        setClickListeners();
    }

    private void setClickListeners(){
        editBTN.setOnClickListener(v -> {
            NavDirections action = ProfileFragmentDirections.editProfileAction(user);
            Navigation.findNavController(v).navigate(action);
        });

        starredDealsButton.setOnClickListener(v -> {
            navController.navigate(R.id.starredDealsFragment);

        });
    }

    private void updateDisplay() {
        nameText.setText(user.getName());
        emailText.setText(user.getEmail());
        interestText.setText(user.getInterestName(getContext()));

        if(user.getImage() == null || user.getImage().isEmpty()){
            Picasso.get().load(R.drawable.selfprofile)
                    .centerCrop()
                    .fit()
                    .into(profileImage);
        }
        else{
            Picasso.get().load(user.getImage())
                    .placeholder(R.drawable.selfprofile)
                    .error(R.drawable.selfprofile)
                    .centerCrop()
                    .fit()
                    .into(profileImage);
        }

    }
}