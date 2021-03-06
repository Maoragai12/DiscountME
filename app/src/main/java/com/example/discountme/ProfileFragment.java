package com.example.discountme;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.discountme.Auth.AuthFirebase;
import com.example.discountme.Auth.SignUpActivity;
import com.example.discountme.Auth.User;
import com.example.discountme.Auth.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class ProfileFragment extends Fragment {

    private User user;
    TextView name;
    TextView email;
    TextView password;
    RadioGroup radioGroup;
    RadioButton radioButton;
    String userId;
    String userName;
    String userEmail;
    String userPassword;
    String userAio;
    ImageView imageView;
    UserViewModel viewModel;
    Button editBTN;


    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        name = view.findViewById(R.id.fullname_profile_text);
        email = view.findViewById(R.id.email_profile_text);
        password = view.findViewById(R.id.password_profile_text);
        radioGroup = (RadioGroup) view.findViewById(R.id.radio);
        imageView = view.findViewById(R.id.photo_profile_text);

        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        userName = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
        userEmail = FirebaseAuth.getInstance().getCurrentUser().getEmail();

        update_display();

        editBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


//        user = ProfileFragmentArgs.fromBundle(getArguments()).getJewelry();
//
//        if (jewelry != null) {
//            update_display();
//        }
//
//        View closeButton = view.findViewById(R.id.jewelry_details_close_btn);

//        closeButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                NavController navController = Navigation.findNavController(getView());
//                NavDirections direction = JewelryListFragmentDirections.actionGlobalJewelryListFragment();
//                navController.navigate(direction);
//            }
//        });
        return view;

    }

    private void update_display() {
        name.setText(userName);
        email.setText(userEmail);
    }

    // Connect the activity to the fragment
    // context = activity
    // Call one time
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        viewModel = new ViewModelProvider(this).get(UserViewModel.class);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

//        if (jewelry.getUserId().equals(userId)) {
//            inflater.inflate(R.menu.jewelry_deatlis_menu, menu);
//        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.edit_profile_button:
                onEditClicked(new EditProfileFragment());
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onEditClicked(Fragment fragment) {
//        NavController navController = Navigation.findNavController(getView());
//        NavDirections direction = EditJewelryFragmentDirections.actionGlobalEditJewelryFragment(jewelry);
//        navController.navigate(direction);

        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container_view_tag, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }


}

