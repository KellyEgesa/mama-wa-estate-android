package com.mamawaestate.android;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.PlaceLikelihood;
import com.google.android.libraries.places.api.net.FindCurrentPlaceRequest;
import com.google.android.libraries.places.api.net.FindCurrentPlaceResponse;
import com.google.android.libraries.places.api.net.PlacesClient;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class MapsFragment {
    FindCurrentPlaceResponse userPlaces;
    private Location userLocation;
    private LocationManager locationManager;
    private LocationListener locationListener;

    public MapsFragment(Context context, Activity activity) throws IOException {
        Places.initialize(context, "AIzaSyB-5gt2UQsAXWRDzlTdBZlJiEavWf65CRc");

        PlacesClient placesClient = Places.createClient(context);

        List<Place.Field> placeFields = Collections.singletonList(Place.Field.ADDRESS);


        FindCurrentPlaceRequest request = FindCurrentPlaceRequest.newInstance(placeFields);


        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                userLocation = location;
            }
        };
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //ask for permission
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 2);
        } else {
            //we have permission
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 10, locationListener);
            userLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

            Geocoder geocoder = new Geocoder(context, Locale.getDefault());

            try {
                List<Address> listAddresses = geocoder.getFromLocation(-1.3827826,36.9368036
                        , 1);
                if (listAddresses != null && listAddresses.size() > 0) {
                    Log.i("PlaceInfo", listAddresses.get(0).toString());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            Task<FindCurrentPlaceResponse> placeResponse = placesClient.findCurrentPlace(request);
            placeResponse.addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    FindCurrentPlaceResponse response = task.getResult();
                    for (PlaceLikelihood placeLikelihood : response.getPlaceLikelihoods()) {
                        Log.i("Current Place", String.format("Place '%s' has likelihood: %f",
                                placeLikelihood.getPlace().getName(),
                                placeLikelihood.getLikelihood()));
                    }
                } else {
                    Exception exception = task.getException();
                    if (exception instanceof ApiException) {
                        ApiException apiException = (ApiException) exception;
                        Log.e("TAG", "Place not found: " + apiException.getStatusCode());
                    }
                }
            });
        }


    }

    public Location getUserLocation() {
        return userLocation;
    }

    public void setUserLocation(Location userLocation) {
        this.userLocation = userLocation;
    }

    public LocationManager getLocationManager() {
        return locationManager;
    }

    public void setLocationManager(LocationManager locationManager) {
        this.locationManager = locationManager;
    }

    public LocationListener getLocationListener() {
        return locationListener;
    }

    public void setLocationListener(LocationListener locationListener) {
        this.locationListener = locationListener;
    }

    public FindCurrentPlaceResponse getUserPlaces() {
        return userPlaces;
    }
}
