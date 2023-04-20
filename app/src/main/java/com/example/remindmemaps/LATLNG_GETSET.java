package com.example.remindmemaps;

import com.google.android.gms.maps.model.LatLng;

import org.checkerframework.checker.nullness.qual.NonNull;

public class LATLNG_GETSET {
    private LatLng latlng;
    public LATLNG_GETSET(){

    }
    public LATLNG_GETSET(@NonNull LatLng latLng){
        this.latlng = latLng;
    }
    public LatLng getLatLng(){
        return latlng;
    }
}
