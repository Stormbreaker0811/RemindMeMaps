package com.example.remindmemaps;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.IBinder;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;

import java.util.Arrays;
import java.util.EnumSet;

import io.radar.sdk.Radar;
import io.radar.sdk.model.RadarPlace;
import io.radar.sdk.model.RadarRoutes;

public class LocationTracker extends Service implements LocationListener {
    private Location targetLocation;
    private LocationManager locationManager;
    private Context context;

    public LocationTracker(Context context){
        this.context = context;
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        double lat;
        double lon;
        targetLocation = new Location(LocationManager.GPS_PROVIDER);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return START_NOT_STICKY;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);

        return START_STICKY;
    }
    public void radar(){
        Radar.initialize(this.context,"prj_test_pk_cf9360fa0f845392d1c0bd2b640b220bfdf55e56");
        Radar.RadarSearchPlacesCallback radarPlaces = new Radar.RadarSearchPlacesCallback() {
            @Override
            public void onComplete(@NonNull Radar.RadarStatus radarStatus, @Nullable Location location, @Nullable RadarPlace[] radarPlaces) {

            }
        };
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public void onLocationChanged(@NonNull Location location) {
        float distance = location.distanceTo(targetLocation);
        if(distance <= 2000){
            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this,"reminder_channel");
            builder.setContentTitle("Hey you are near the reminder location.");
            builder.setContentText("Tap to view details");
            builder.setPriority(NotificationCompat.PRIORITY_HIGH);
            builder.setAutoCancel(true);
            Intent intent = new Intent(this, ReminderFragment.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
            builder.setContentIntent(pendingIntent);
            notificationManager.notify(1,builder.build());
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        locationManager.removeUpdates(this);
    }
}
