package com.mamawaestate.android.userLocation;

import org.parceler.Parcel;

@Parcel
public class UserLocation {
    private Double latitude;
    private Double longitude;
    private String Address;
    private String name;

    public UserLocation() {
    }

    public UserLocation(Double latitude, Double longitude, String Address, String name) {
        super();
        this.latitude = latitude;
        this.longitude = longitude;
        this.Address = Address;
        this.name = name;
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

    public void setName(String name) {
        this.name = name;
    }
}
