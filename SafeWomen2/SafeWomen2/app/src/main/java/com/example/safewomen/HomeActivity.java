package com.example.safewomen;

import android.content.Intent;
import android.os.Bundle;

import com.example.safewomen.ui.contacts.ContactFragment;
import com.example.safewomen.ui.contacts.ContactViewModel;
import com.example.safewomen.ui.contactus.ContactusViewModel;
import com.example.safewomen.ui.feedback.FeedbackViewModel;
import com.example.safewomen.ui.home.HomeFragment;
import com.example.safewomen.ui.home.HomefileViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.Toast;

import java.util.Map;

public class HomeActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    ActionBarDrawerToggle t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this,MapsActivity.class));
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
         mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_profile, R.id.nav_contact,
                R.id.nav_contactus, R.id.nav_feedback)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        navController.addOnDestinationChangedListener( new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {
                if(destination.getId()==R.id.nav_profile){
                    Toast.makeText(HomeActivity.this, "Home Screen", Toast.LENGTH_SHORT).show();
                }
                if(destination.getId()==R.id.nav_contact){
                    Toast.makeText(HomeActivity.this, "Profile Screen", Toast.LENGTH_SHORT).show();
                }
                if(destination.getId()==R.id.nav_contactus){
                    Toast.makeText(HomeActivity.this, "Contacts Screen", Toast.LENGTH_SHORT).show();
                }
                if(destination.getId()==R.id.nav_feedback){
                    Toast.makeText(HomeActivity.this, "Feedback Screen", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.profile,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_profile:
                startActivity(new Intent(this,ProfilesActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }


}