package com.mamawaestate.android;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.AutocompleteSessionToken;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.RectangularBounds;
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mamawaestate.android.userLocation.UserLocation;

import org.parceler.Parcels;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {
    @BindView(R.id.originalLocation)
    FloatingActionButton floatingActionButton;
    @BindView(R.id.proceedButton)
    Button proceedButton;
    LocationManager locationManager;
    LocationListener locationListener;
    private LatLng originalLocation;
    private Double latitude;
    private Double longitude;
    private String Address;
    private String name = "Unnamed";

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(MapActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 10, locationListener);

            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        ButterKnife.bind(this);


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        setUserLocation(googleMap);
        setNewUserLocation(googleMap);
        getLastLocationNewMethod(googleMap);

        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                latitude = location.getLatitude();
                longitude = location.getLongitude();
                originalLocation = new LatLng(location.getLatitude(), location.getLongitude());
                Geocoder geocoder = new Geocoder(MapActivity.this, Locale.getDefault());
                try {
                    List<android.location.Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 4);

                    Address = addresses.get(0).getAddressLine(0);

                    Log.i("PLACESS", geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 4).get(0).toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }

                LatLng user = new LatLng(location.getLatitude(), location.getLongitude());
                googleMap.clear();
                googleMap.addMarker(new MarkerOptions()
                        .position(user)
                        .title("Marker in Sydney"));
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(user, 18));
                Log.i("PlaceCurrent", location.toString());
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(@NonNull String provider) {

            }

            @Override
            public void onProviderDisabled(@NonNull String provider) {

            }
        };

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 2);
        } else {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 10, locationListener);
        }

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                returnToOriginalLocation(googleMap);
            }
        });
        proceedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserLocation userLocation = new UserLocation(latitude, longitude, Address, name);
                Intent intent = new Intent(MapActivity.this, MainActivity.class);
                intent.putExtra("userLocation", Parcels.wrap(userLocation));
                startActivity(intent);
            }
        });
    }

    private void setNewUserLocation(GoogleMap googleMap) {
        Places.initialize(getApplicationContext(), "AIzaSyB-5gt2UQsAXWRDzlTdBZlJiEavWf65CRc");

        PlacesClient placesClient = Places.createClient(this);

        List<Place.Field> placeFields = Collections.singletonList(Place.Field.ADDRESS);

        AutocompleteSessionToken token = AutocompleteSessionToken.newInstance();

        RectangularBounds bounds = RectangularBounds.newInstance(
                new LatLng(-33.880490, 151.184363),
                new LatLng(-33.858754, 151.229596));

        FindAutocompletePredictionsRequest request = FindAutocompletePredictionsRequest.builder()
                // Call either setLocationBias() OR setLocationRestriction().
                .setLocationBias(bounds)
                //.setLocationRestriction(bounds)
                .setOrigin(new LatLng(-33.8749937, 151.2041382))
                .setCountries("KE")
                .setSessionToken(token)
                .build();

        placesClient.findAutocompletePredictions(request);


    }

    private void setUserLocation(GoogleMap googleMap) {
        FusedLocationProviderClient mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 2);
        } else {
            Log.i("granted", mFusedLocationClient.toString());
            mFusedLocationClient.getLastLocation()
                    .addOnSuccessListener(new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            // GPS location can be null if GPS is switched off
                            if (location != null) {
                                latitude = location.getLatitude();
                                longitude = location.getLongitude();
                                originalLocation = new LatLng(location.getLatitude(), location.getLongitude());
                                Geocoder geocoder = new Geocoder(MapActivity.this, Locale.getDefault());
                                try {
                                    List<android.location.Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 4);

                                    Address = addresses.get(0).getAddressLine(0);

                                    Log.i("PLACESS", geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 4).get(0).toString());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                                LatLng user = new LatLng(location.getLatitude(), location.getLongitude());
                                googleMap.addMarker(new MarkerOptions()
                                        .position(user)
                                        .title("Marker in Sydney"));
                                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(user, 18));
                                Log.i("PlaceCurrent", location.toString());
                            } else {
                                Log.i("Place", "location.toString()");
                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d("MapDemoActivity", "Error trying to get last GPS location");
                            e.printStackTrace();
                        }
                    });
        }

    }

    private void getLastLocationNewMethod(GoogleMap googleMap) {
        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment) getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment);
        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG));
        autocompleteFragment.setCountry("KE");
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {
                latitude = place.getLatLng().latitude;
                longitude = place.getLatLng().longitude;
                Address = place.getAddress();
                name = place.getName();

                googleMap.clear();
                googleMap.addMarker(new MarkerOptions().position(place.getLatLng()).title("Your searched Location"));
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(place.getLatLng(), 18));
                Log.i("PlacesB", place.toString());

            }

            @Override
            public void onError(@NonNull Status status) {

            }
        });

    }

    private void returnToOriginalLocation(GoogleMap googleMap) {
        googleMap.clear();
        googleMap.addMarker(new MarkerOptions()
                .position(originalLocation)
                .title("Marker in Sydney"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(originalLocation, 18));
    }
}