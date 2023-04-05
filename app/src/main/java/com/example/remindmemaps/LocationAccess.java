package com.example.remindmemaps;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.CancellationSignal;
import android.telephony.CarrierConfigManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;

import io.radar.sdk.Radar;
import io.radar.sdk.model.RadarGeofence;
import io.radar.sdk.model.RadarPlace;

public class LocationAccess {
    private Location location;
    private String place;
    private Context context;

    public LocationAccess(Context context, String place) {
        this.context = context;
        this.place = place;
    }

    public boolean isNear(){

        return false;
    }
}
