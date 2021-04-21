package com.example.discountme.view.activities;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.discountme.R;
import com.example.discountme.viewmodels.UserViewModel;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends AppCompatActivity {
    private DrawerLayout mDrawer;
    private Toolbar toolbar;
    private NavigationView nvDrawer;


    // Make sure to be using androidx.appcompat.app.ActionBarDrawerToggle version.
    private ActionBarDrawerToggle drawerToggle;
    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        // Find our drawer view
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        nvDrawer = (NavigationView) findViewById(R.id.nav_view);
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);

        setupDrawerContent(nvDrawer);



        UserViewModel userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        userViewModel.readUser(FirebaseAuth.getInstance().getUid(), user -> {
            ((TextView)(nvDrawer.findViewById(R.id.text_user_name))).setText(user.name);
            ((TextView)(nvDrawer.findViewById(R.id.text_user_email))).setText(user.email);
        });


        // select as default the first
        nvDrawer.getMenu().performIdentifierAction(R.id.nav_home, 0);



    }

    @SuppressLint("NonConstantResourceId")
    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                menuItem -> {
                    selectDrawerItem(menuItem);
                    return true;
                });

        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            String title = "";
            switch (destination.getId()){
                case R.id.feedFragment:
                    title = "Deals Feed";
                    break;


                case R.id.myDealsFragment:
                    title = "My Deals";
                    break;

                case R.id.profileFragment:
                    title = "Profile";
                    break;

                case R.id.settingsFragment:
                    title = "Settings";
                    break;

                case R.id.starredDealsFragment:
                    title = "Starred";
                    break;
            }
            if(!title.isEmpty()) {
               setTitle(title);
            }

        });
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                switch (navController.getCurrentDestination().getId()) {
                    case R.id.feedFragment:
                    case R.id.myDealsFragment:
                    case R.id.profileFragment:
                    case R.id.settingsFragment:
                        mDrawer.openDrawer(GravityCompat.START);
                        break;

                    default:
                        navController.popBackStack();
                        break;
                }
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @SuppressLint("NonConstantResourceId")
    public void selectDrawerItem(MenuItem menuItem) {
        int destination = -1;       // save the id of the screen to navigate
        switch(menuItem.getItemId()) {
            case R.id.nav_home:
                destination = R.id.feedFragment;
                break;
            case R.id.nav_my_deals:
                destination = R.id.myDealsFragment;
                break;
            case R.id.nav_profile:
                destination = R.id.profileFragment;
                break;
            case R.id.nav_settings:
                destination = R.id.settingsFragment;
                break;
        }

        // Navigate into the fragment suits the item that was clicked
        navController.navigate(destination);

        // Highlight the selected item has been done by NavigationView
        menuItem.setChecked(true);
        // Set action bar title
        setTitle(menuItem.getTitle());
        // Close the navigation drawer
        mDrawer.closeDrawers();
    }


    // every click on view will start the onOption function
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
//         getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
}
