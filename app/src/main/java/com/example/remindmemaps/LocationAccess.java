package com.example.remindmemaps;

import android.location.Location;

public class LocationAccess {
    private Location location;
    private String place;
    public LocationAccess(String place){
        this.place = place;
    }
    private void setLocation(){
        location = new Location("");

    }
}
