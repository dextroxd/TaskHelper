package com.dextroxd.taskhelper.activity;

import android.app.Fragment;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.Toast;

import com.dextroxd.taskhelper.R;
import com.dextroxd.taskhelper.adapter.GridAdapter;
import com.dextroxd.taskhelper.fragments.BookingFragment;
import com.dextroxd.taskhelper.fragments.HomeFragment;
import com.dextroxd.taskhelper.fragments.ProfileFragment;
import com.dextroxd.taskhelper.login.SignupActivity;
import com.dextroxd.taskhelper.model.ServicesModel;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        auth = FirebaseAuth.getInstance();
        BottomNavigationView bottomNavigationView = findViewById(R.id.nav_bar);
        bottomNavigationView.setOnNavigationItemSelectedListener(navigationItemSelectedListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new HomeFragment()).commit();


    }
    @Override
    protected void onStart() {
        super.onStart();
        auth = FirebaseAuth.getInstance();

    }

    private BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    android.support.v4.app.Fragment selectedFragment = null;
                    switch (item.getItemId())
                    {
                        case R.id.nav_home:
                            selectedFragment = new HomeFragment();
                            break;
                        case R.id.nav_booking:
                            if(auth.getCurrentUser()!=null)
                            selectedFragment = new BookingFragment();
                            else
                            {
                                Toast.makeText(MainActivity.this,"You have to login to unlock this",Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(MainActivity.this, SignupActivity.class));
                            }
                            break;
                        case R.id.nav_profile:
                            if(auth.getCurrentUser()!=null)
                            selectedFragment = new ProfileFragment();
                            else
                            {
                                Toast.makeText(MainActivity.this,"You have to login to unlock this",Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(MainActivity.this, SignupActivity.class));
                            }
                            break;
                    }
                    if(auth.getCurrentUser()!=null)
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,selectedFragment).commit();
                    return true;
                }
            };

}


