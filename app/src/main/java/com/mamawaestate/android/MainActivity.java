package com.mamawaestate.android;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.mamawaestate.android.Fragments.HomeFragment;
import com.mamawaestate.android.userLocation.UserLocation;

import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;

//import androidx.fragment.app.FragmentManager;
//import androidx.fragment.app.FragmentTransaction;


public class MainActivity extends AppCompatActivity {
    ActionBarDrawerToggle actionBarDrawerToggle;
    UserLocation userLocation;
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    @BindView(R.id.cart)
    ImageView cart;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        userLocation = Parcels.unwrap(getIntent().getParcelableExtra("userLocation"));
//
//        location.setText(userLocation.getAddress());

        toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ShoppingCartActivity.class);
                startActivity(intent);
            }
        });

        drawerLayout = findViewById(R.id.drawer_layout);

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.drawer_open, R.string.drawer_close);

        drawerLayout.addDrawerListener(actionBarDrawerToggle);


        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.main_fragment_container, new HomeFragment(), "HomeFragment")
                .commit();


        SlideNavigation slideNavigation = new SlideNavigation(R.id.main_fragment_container);
        slideNavigation.initSlideMenu(MainActivity.this, getSupportFragmentManager(), drawerLayout);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();
    }


};



