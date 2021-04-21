package com.example.discountme.view.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.discountme.model.User;
import com.example.discountme.model.UserRepository;
import com.example.discountme.data.remote.AuthFirebase;
import com.example.discountme.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;


public class SignUpActivity extends AppCompatActivity {

    EditText name_et;
    EditText email_et;
    EditText password_et;
    RadioGroup radioGroup;
    RadioButton radioButton;
    FirebaseAuth auth;
    AuthFirebase au;
    Button signUpBtn;
    Button movetologinBtn;
    ProgressBar progressBar;
    FirebaseFirestore firebaseFirestore;
    String userId;
    User user;
    int selectedCategory = 0;

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        name_et = findViewById(R.id.signup_fullname_et);
        email_et = findViewById(R.id.signup_email_ed);
        password_et = findViewById(R.id.signup_password_et);
        radioGroup = (RadioGroup) findViewById(R.id.radio);

        signUpBtn = findViewById(R.id.signup_signup_btn);
        movetologinBtn = findViewById(R.id.signup_alreadymember_tv);

        auth = FirebaseAuth.getInstance();

        progressBar = findViewById(R.id.signup_progressBar);

        movetologinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            }
        });

        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId){
                case R.id.clothes_rb:
                    selectedCategory = 1;
                    break;

                case R.id.electronics_rb:
                    selectedCategory = 2;
                    break;

                case R.id.sports_rb:
                    selectedCategory = 3;
                    break;

                case R.id.food_rb:
                    selectedCategory = 4;
                    break;
            }
        });


        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // trim remove spaces

                final String email = email_et.getText().toString().trim();
                String password = password_et.getText().toString().trim();
                final String name = name_et.getText().toString().trim();
              //  int selectedId = radioGroup.getCheckedRadioButtonId();


               // radioButton = (RadioButton) findViewById(selectedId);
                //String radioValue = radioButton.//.getText().toString();


                if (TextUtils.isEmpty(email)) {
                    email_et.setError("Email is required!");
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    password_et.setError("Password is required!");
                    return;
                }

                if (password.length() < 6) {
                    password_et.setError("Password must be up to 6 characters!");
                    return;
                }

                if (TextUtils.isEmpty(name)) {
                    name_et.setError("Enter your name!");
                    return;
                }


//                if (TextUtils.isEmpty(radioValue)) {
//                    radioButton.setError("Choose area of interest!");
//                    return;
//                }

                progressBar.setVisibility(View.VISIBLE);

                // Add the user to firebase

                auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d("TAG", "user is created successful");
                            Toast.makeText(SignUpActivity.this, "User Created" , Toast.LENGTH_SHORT).show();

                            userId = auth.getCurrentUser().getUid();

                            user = new User(userId,name,email, selectedCategory, "");

                            Log.d("TAG", "NEW USER SAVED!" + user);
                            // storage the user in firestore
                            AuthFirebase.addUser(user, new UserRepository.Listener<Boolean>() {
                                @Override
                                public void onComplete(Boolean data) {
                                    Log.d("TAG", "NEW USER SAVED!");
                                }
                            });
                            progressBar.setVisibility(View.GONE);
                            startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                        } else {
                            Log.d("TAG", "user is create failed");
                            Toast.makeText(SignUpActivity.this, "Error - User create failed! " +task.getException().getMessage() , Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);

                        }
                    }
                });

            }
        });

    }

}