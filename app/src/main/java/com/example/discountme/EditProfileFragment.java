package com.example.discountme;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.discountme.Auth.AuthFirebase;
import com.example.discountme.Auth.SignUpActivity;
import com.example.discountme.Auth.User;
import com.example.discountme.Auth.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class EditProfileFragment extends Fragment {
    private User user;
    UserViewModel viewModel;
    TextView name;
    TextView email;
    TextView password;
    Button saveBTN;
    ProgressBar progressBar;
    FirebaseAuth auth;

    public EditProfileFragment() {
        // Required empty public constructor
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_edit_profile, container, false);

        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Edit My Profile");

        name = view.findViewById(R.id.fullname_profile_edit_text);
        email = view.findViewById(R.id.email_profile_edit_text);
        password = view.findViewById(R.id.password_profile_edit_text);
        user = AuthFirebase.getUserFromFirebase();



        saveBTN = view.findViewById(R.id.save_button_edit);

        viewModel = new ViewModelProvider(this).get(UserViewModel.class);

        progressBar = view.findViewById(R.id.signup_progressBar2);

        if(user != null) update_display();

        saveBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // trim remove spaces

                final String emailVal = email.getText().toString().trim();
                String passwordVal = password.getText().toString().trim();
                final String nameVal = name.getText().toString().trim();

                if (TextUtils.isEmpty(emailVal)) {
                    email.setError("Email is required!");
                    return;
                }

                if (TextUtils.isEmpty(passwordVal)) {
                    password.setError("Password is required!");
                    return;
                }

                if (password.length() < 6) {
                    password.setError("Password must be up to 6 characters!");
                    return;
                }

                if (TextUtils.isEmpty(nameVal)) {
                    name.setError("Enter your name!");
                    return;
                }
                User userUpdate = new User(user.getUid(),nameVal,emailVal,passwordVal,user.getAIO());

                progressBar.setVisibility(View.VISIBLE);

                // Add the user to firebase
                viewModel.update(userUpdate, new UserModel.Listener() {
                    @Override
                    public void onComplete(Object data) {
                        Log.d("TAG", "update new jewelry success");
                    }
                });

            }
        });


        return view;

    }

    private void update_display() {
        name.setText(user.getName());
        email.setText(user.getEmail());
        password.setText(user.getPassword());
    }


}
