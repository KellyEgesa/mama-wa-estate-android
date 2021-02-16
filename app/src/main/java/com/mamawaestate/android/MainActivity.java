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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mamawaestate.android.Adapters.ListVendorsAdapter;
import com.mamawaestate.android.models.Vendors;
import com.mamawaestate.android.userLocation.UserLocation;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

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
    @BindView(R.id.listVendorsRecyclerView)
    RecyclerView mListVendorsRecyclerView;
    @BindView(R.id.textLocation)
    TextView location;
    @BindView(R.id.ChangeYourLocation) TextView mChangeYourLocation;
    private SharedPreferences preferences;
    private SharedPreferences.Editor mEditor;

    private List<Vendors> listVendors = new ArrayList();
    private ListVendorsAdapter listVendorsAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        Vendors vendors1 = new Vendors("Two bears", "Moi Avenue", "Clothing");
        Vendors vendors2 = new Vendors("Santamu Honey", "Harambee Avenue", "Food");
        Vendors vendors3 = new Vendors("Wacha Art", "Langata", "Services");
        Vendors vendors4 = new Vendors("Car Wash", "Nyayo Estate", "Services");
        Vendors vendors5 = new Vendors("Duka Yetu", "Moi Avenue", "Food");
        Vendors vendors6 = new Vendors("Knight Clothing", "South B", "Clothing");
        Vendors vendors7 = new Vendors("Greater Pharmaceuticals", "Waiyaki Way", "Pharmacy");
        Vendors vendors8 = new Vendors("Ibrahims Electronics", "Aga Khan Walk", "Electronics");
        Vendors vendors9 = new Vendors("Jumia", "BuruBuru", "Electronics");
        Vendors vendors10 = new Vendors("Mechanics", "Kileleshwa", "Services");
        Vendors vendors11 = new Vendors("KFC", "Moi Avenue", "Food");
        Vendors vendors12 = new Vendors("JADE Collection", "Moi Avenue", "Clothing");
        Vendors vendors13 = new Vendors("ABC Supermarket", "Uhuru Highway", "General");
        Vendors vendors14 = new Vendors("CDE Supermarket", "Kenyatta Avenue", "General");
        Vendors vendors15 = new Vendors("Laptops", "Ngara", "Electronics");

        listVendors.add(vendors1);
        listVendors.add(vendors2);
        listVendors.add(vendors3);
        listVendors.add(vendors4);
        listVendors.add(vendors5);
        listVendors.add(vendors6);
        listVendors.add(vendors7);
        listVendors.add(vendors8);
        listVendors.add(vendors9);
        listVendors.add(vendors10);
        listVendors.add(vendors11);
        listVendors.add(vendors12);
        listVendors.add(vendors13);
        listVendors.add(vendors14);
        listVendors.add(vendors15);

        listVendorsAdapter = new ListVendorsAdapter(MainActivity.this, listVendors);
        mListVendorsRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        mListVendorsRecyclerView.setAdapter(listVendorsAdapter);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        mEditor = preferences.edit();
        mEditor.putBoolean("IsLogIn", true).apply();
        boolean Islogin = preferences.getBoolean("Islogin", false);
        Log.i("AAAB", String.valueOf(Islogin));

        userLocation = Parcels.unwrap(getIntent().getParcelableExtra("userLocation"));

        if (userLocation.getAddress() != null) {
            location.setText(userLocation.getAddress());
        } else {
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

        mChangeYourLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MapActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();
    }

    public void filter(View v) {
        String categoryFilter = v.getTag().toString();
        List<Vendors> listVendorsFiltered = new ArrayList();


        if (categoryFilter.equals("All")) {
            listVendorsFiltered = listVendors;
        } else {
            for (Vendors vendors : listVendors) {
                if (vendors.getCategory().equals(categoryFilter)) {
                    listVendorsFiltered.add(vendors);
                }
            }
        }

        listVendorsAdapter = new ListVendorsAdapter(MainActivity.this, listVendorsFiltered);
        mListVendorsRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        mListVendorsRecyclerView.swapAdapter(listVendorsAdapter, false);

    }


};



