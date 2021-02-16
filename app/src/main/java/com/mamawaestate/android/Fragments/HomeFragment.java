package com.mamawaestate.android.Fragments;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

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
import com.mamawaestate.android.R;
import com.mamawaestate.android.userLocation.UserLocation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class HomeFragment extends Fragment {

    String Address;
    String name;
    Double latitude;
    Double longitude;
    UserLocation userLocation;
    TextView location;
    LinearLayout mainHome;
    private ProgressDialog progressDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadingScreen();


        Places.initialize(getContext(), "AIzaSyB-5gt2UQsAXWRDzlTdBZlJiEavWf65CRc");

        PlacesClient placesClient = Places.createClient(getContext());

        List<Place.Field> placeFields = Collections.singletonList(Place.Field.ADDRESS);

        FindCurrentPlaceRequest request = FindCurrentPlaceRequest.newInstance(placeFields);

        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 2);
        } else {
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
                    location.setText(Address);
                    progressDialog.dismiss();
                } else {
                    Exception exception = task.getException();
                    if (exception instanceof ApiException) {
                        ApiException apiException = (ApiException) exception;
                        Log.e("TAG", "Place not found: " + apiException.getMessage());
                    }
                }
            });
        }

        getLastLocationNewMethod();
        userLocation = new UserLocation(latitude, longitude, Address, name);

    }

    private void getLastLocationNewMethod() {
        FusedLocationProviderClient mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 2);
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_home, container, false);
        location = (TextView) rootview.findViewById(R.id.textLocation);
        mainHome = (LinearLayout) rootview.findViewById(R.id.mainHome);
        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return rootview;
    }

    private void loadingScreen() {
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Getting Your Location");
        progressDialog.setMessage("Searching....");
        progressDialog.setCancelable(false);

    }

    @Override
    public void onPause() {
        super.onPause();
    }
}
