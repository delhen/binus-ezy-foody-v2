package com.example.binusezyfoody2;

import android.location.Geocoder;
import java.util.Locale;

public class Restaurant {
    private String name;
    private int imageResourceId;
    private double latitude;
    private double longitude;

    public static Restaurant[] restaurants = {
            new Restaurant("Sinar Mulia", R.drawable.restaurant1, -6.1778137599635174, 106.63107145328445),
            new Restaurant("Semoga Jaya", R.drawable.restaurant2, -6.1245522912402475, 106.81238015962725),
            new Restaurant("Modernisasi Resto", R.drawable.restaurant3, -6.582032811365941, 106.81983236780079),
            new Restaurant("Bojo Negeri Senseng", R.drawable.restaurant4, -6.091683819243678, 106.65145746232717)
    };

    public Restaurant(String name, int imageResourceId, double latitude, double longitude) {
        this.name = name;
        this.imageResourceId = imageResourceId;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Restaurant(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }

    public void setImageResourceId(int imageResourceId) {
        this.imageResourceId = imageResourceId;
    }
}
