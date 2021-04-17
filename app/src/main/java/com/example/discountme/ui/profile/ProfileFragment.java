package com.example.discountme.ui.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.discountme.Auth.AuthFirebase;
import com.example.discountme.Auth.User;
import com.example.discountme.EditProfileFragment;
import com.example.discountme.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class ProfileFragment extends Fragment {

    private User user;
    TextView name;
    TextView email;
    TextView password;
    FloatingActionButton editBTN;

    public ProfileFragment() {
        // Required empty public constructor
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);


        name = view.findViewById(R.id.fullname_text_profile);
        email = view.findViewById(R.id.email_text_profile);
        password = view.findViewById(R.id.password_text_profile);
        editBTN = (FloatingActionButton) view.findViewById(R.id.edit_profile_button);
        user = AuthFirebase.getUserFromFirebase();

//        Log.d("TAG", "USER:" + user.getName());

        if(user != null) update_display();

        editBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = null;
                Class fragmentClass = EditProfileFragment.class;
                try {
                    fragment = (Fragment) fragmentClass.newInstance();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (java.lang.InstantiationException e) {
                    e.printStackTrace();
                }

                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();
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