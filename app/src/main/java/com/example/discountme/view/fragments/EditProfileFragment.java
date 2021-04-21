package com.example.discountme.view.fragments;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.discountme.data.remote.AuthFirebase;
import com.example.discountme.model.User;
import com.example.discountme.model.UserRepository;
import com.example.discountme.R;
import com.example.discountme.viewmodels.UserViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import static android.app.Activity.RESULT_OK;

public class EditProfileFragment extends Fragment {
    private User user;
    UserViewModel viewModel;

    private ImageView profileImage;
    private TextView name;
    private TextView email;
    private RadioGroup radioGroup;

    private Button saveBTN;
    private Button changePhotoButton;
    private ProgressBar progressBar;
    private FirebaseAuth auth;
    private int userAio;


    private Uri imageUri;

    public EditProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        user = EditProfileFragmentArgs.fromBundle(getArguments()).getUser();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_edit_profile, container, false);

        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
//        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Edit My Profile");

        profileImage = view.findViewById(R.id.image_profile);
        name = view.findViewById(R.id.fullname_profile_edit_text);
        email = view.findViewById(R.id.email_profile_edit_text);
        radioGroup = view.findViewById(R.id.radio);
        changePhotoButton = view.findViewById(R.id.change_photo_button);

//        password = view.findViewById(R.id.password_profile_edit_text);

        saveBTN = view.findViewById(R.id.save_button_edit);


        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId){
                case R.id.clothes_rb:
                    userAio = 1;
                    break;

                case R.id.electronics_rb:
                    userAio = 2;
                    break;

                case R.id.sports_rb:
                    userAio = 3;
                    break;

                case R.id.food_rb:
                    userAio = 4;
                    break;
            }
        });

        viewModel = new ViewModelProvider(this).get(UserViewModel.class);


        viewModel.getData().observe(getViewLifecycleOwner(), new Observer<User>() {
            @Override
            public void onChanged(User user) {
                updateDisplay();
            }
        });

        viewModel.setData(user);


//        progressBar = view.findViewById(R.id.signup_progressBar2);

      //  if(user != null) updateDisplay();

        changePhotoButton.setOnClickListener(this::addPhoto);

        saveBTN.setOnClickListener(v -> {
            // trim remove spaces

            final String emailVal = email.getText().toString().trim();
//                String passwordVal = password.getText().toString().trim();
            final String nameVal = name.getText().toString().trim();

            if (TextUtils.isEmpty(emailVal)) {
                email.setError("Email is required!");
                return;
            }


//                if (TextUtils.isEmpty(passwordVal)) {
//                    password.setError("Password is required!");
//                    return;
//                }

//                if (password.length() < 6) {
//                    password.setError("Password must be up to 6 characters!");
//                    return;
//                }

            if (TextUtils.isEmpty(nameVal)) {
                name.setError("Enter your name!");
                return;
            }
            User userUpdate = new User(user.getUid(),nameVal,emailVal, userAio, user.getImage());

//            progressBar.setVisibility(View.VISIBLE);

//            // update the user to firebase
            viewModel.update(userUpdate, imageUri, new UserRepository.Listener() {
                @Override
                public void onComplete(Object data) {

                }
            });

        });


        return view;

    }

    private void updateDisplay() {
        name.setText(user.getName());
        email.setText(user.getEmail());

        ((RadioButton)radioGroup.getChildAt(user.aio-1)).setChecked(true);

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

    private void addPhoto(View v){
        if(ActivityCompat.checkSelfPermission(getActivity(),
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
        {
            requestPermissions(
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    2000);
        }
        else {
            openGallery();
        }
    }

    private void openGallery() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, 101);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK && requestCode == 101) {
            Uri returnUri = data.getData();
            if(returnUri == null)
                return;

            imageUri = returnUri;
            Picasso.get().load(imageUri).centerCrop().fit().into(profileImage);
        }
    }
}
