package com.mamawaestate.android;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.mamawaestate.android.userLocation.UserLocation;

import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;



public class MainActivity extends AppCompatActivity {
    ActionBarDrawerToggle actionBarDrawerToggle;
    UserLocation userLocation;
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    @BindView(R.id.cart)
    ImageView cart;
    @BindView(R.id.textLocation)
    TextView location;
    private SharedPreferences preferences;
    private SharedPreferences.Editor mEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        mEditor = preferences.edit();
        mEditor.putBoolean("IsLogIn", true).apply();
        boolean Islogin = preferences.getBoolean("Islogin", false);
        Log.i("AAAB", String.valueOf(Islogin));

        userLocation = Parcels.unwrap(getIntent().getParcelableExtra("userLocation"));

        if(userLocation.getAddress() != null){
            location.setText(userLocation.getAddress());
        }else {
            location.setText(userLocation.getName());
        }

        toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CatalogActivity.class);
                startActivity(intent);
            }
        });

        drawerLayout = findViewById(R.id.drawer_layout);

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.drawer_open, R.string.drawer_close);

        drawerLayout.addDrawerListener(actionBarDrawerToggle);


        SlideNavigation slideNavigation = new SlideNavigation(R.id.main_fragment_container);
        slideNavigation.initSlideMenu(MainActivity.this, getSupportFragmentManager(), drawerLayout);
    }

    public void changeLocation(){
        Intent intent = new Intent(MainActivity.this, MapActivity.class);
        startActivity(intent);
    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();
    }


};



