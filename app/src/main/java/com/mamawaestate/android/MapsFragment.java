package com.mamawaestate.android;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.PlaceLikelihood;
import com.google.android.libraries.places.api.net.FindCurrentPlaceRequest;
import com.google.android.libraries.places.api.net.FindCurrentPlaceResponse;
import com.google.android.libraries.places.api.net.PlacesClient;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MapsFragment {
    Context mContext;
    Activity mActivity;
    Double latitude;
    Double longitude;
    String Address;
    String name;
    private FindCurrentPlaceResponse userPlaces;
    private Location userLocation;
    private LocationManager locationManager;
    private FusedLocationProviderClient fusedLocationClient;
    private LocationListener locationListener;

    public MapsFragment(Context context, Activity activity) {
        mActivity = activity;
        mContext = context;

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
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 2);
        } else {
            //we have permission
//            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 10, locationListener);
//            userLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);


//            PlacesSearchResponse requestPlaceSearch = new PlacesSearchResponse();
//            GeoApiContext contextPlaceSearch = new GeoApiContext.Builder()
//                    .apiKey("AIzaSyB-5gt2UQsAXWRDzlTdBZlJiEavWf65CRc")
//                    .build();
//
//            LatLng location = new LatLng(userLocation.getLatitude(), userLocation.getLongitude());

//            try {
//                requestPlaceSearch = PlacesApi.nearbySearchQuery(contextPlaceSearch, location)
//                        .radius(5000)
//                        .rankby(RankBy.DISTANCE)
//                        .keyword("estate")
//                        .type(TypeFilter.ADDRESS)
//            }

//            Geocoder geocoder = new Geocoder(context, Locale.getDefault());
//
//            try {
//                List<Address> listAddresses = geocoder.getFromLocation(-1.3827826,36.9368036
//                        , 1);
//                if (listAddresses != null && listAddresses.size() > 0) {
//                    Log.i("PlaceInfo", listAddresses.get(0).toString());
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
            Task<FindCurrentPlaceResponse> placeResponse = placesClient.findCurrentPlace(request);
            placeResponse.addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    FindCurrentPlaceResponse response = task.getResult();
                    List<PlaceLikelihood> places = new ArrayList();
                    for (PlaceLikelihood placeLikelihood : response.getPlaceLikelihoods()) {
                        Log.i("PlacesCurrent", String.format("Place '%s' has likelihood: %f",
                                placeLikelihood.getPlace().toString(),
                                placeLikelihood.getLikelihood()));
                        places.add(placeLikelihood);

                    }
                    double likeHood = places.get(0).getLikelihood();
                    PlaceLikelihood placeLikelihoodFinal = places.get(0);
                    for (PlaceLikelihood placeLikelihood : places) {
                        if (placeLikelihood.getLikelihood() < likeHood) {
                            likeHood = placeLikelihood.getLikelihood();
                            placeLikelihoodFinal = placeLikelihood;
                        }
                    }
                    Address = placeLikelihoodFinal.getPlace().getAddress();
                    if (placeLikelihoodFinal.getPlace().getName() != null) {
                        name = placeLikelihoodFinal.getPlace().getName();
                    } else {
                        name = "Unnamed";
                    }
                    Log.i("PlacesAddress", Address);
                } else {
                    Exception exception = task.getException();
                    if (exception instanceof ApiException) {
                        ApiException apiException = (ApiException) exception;
                        Log.e("TAG", "Place not found: " + apiException.getStatusCode());
                    }
                }
            });
        }

        getLastLocationNewMethod();


    }

    private void getLastLocationNewMethod() {
        FusedLocationProviderClient mFusedLocationClient = LocationServices.getFusedLocationProviderClient(mContext);
        if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(mActivity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 2);
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
                                Log.i("Place", location.toString());
                                Log.i("PlaceLatitude", latitude.toString());
                                Log.i("Place", longitude.toString());
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

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public String getAddress() {
        return Address;
    }

    public String getName() {
        return name;
    }

    public Context getmContext() {
        return mContext;
    }

    public Activity getmActivity() {
        return mActivity;
    }
}
