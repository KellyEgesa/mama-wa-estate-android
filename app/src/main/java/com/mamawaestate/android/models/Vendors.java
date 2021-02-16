package com.mamawaestate.android.models;

public class Vendors {
    private String name;
    private String location;
    private String category;

    public Vendors(String name, String location, String category) {
        this.name = name;
        this.location = location;
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public String getCategory() {
        return category;
    }
}
