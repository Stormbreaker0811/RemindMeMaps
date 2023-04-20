package com.example.remindmemaps;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import io.radar.sdk.Radar;
import io.radar.sdk.model.RadarAddress;
import io.radar.sdk.model.RadarPlace;

public class LocationTracker extends Service {
    private Location targetLocation;
    private String reminderName;
    List<RadarAddress> radar;

    LatLng latlng;
    double lat;
    double lon;
    private LocationManager locationManager;
    private Context context;
    private Location location1;
    private String place;

    public LocationTracker(Context context, String name) {
        this.context = context;
        this.reminderName = name;
    }

    public class LocalBinder extends Binder {
        LocationTracker getService(){
            return LocationTracker.this;
        }
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(context, "Location Tracker has started..//", Toast.LENGTH_SHORT).show();
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return START_NOT_STICKY;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                location1 = location;
            }
        });
        FirebaseFirestore.getInstance().collection("reminders_collection").whereEqualTo("Reminder Name", reminderName).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot doc : task.getResult()) {
                                targetLocation = new Location(LocationManager.GPS_PROVIDER);
                                targetLocation.setLatitude(Double.parseDouble(Objects.requireNonNull(doc.getString("Latitude"))));
                                targetLocation.setLongitude(Double.parseDouble(Objects.requireNonNull(doc.getString("Longitude"))));
                                Toast.makeText(context, "Target location set", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        e.printStackTrace();
                    }
                });
        if (location1.distanceTo(targetLocation) <= 2000) {
            // remind the user
            /*NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "reminder_channel")
                    .setContentTitle("You're near the reminder location!")
                    .setContentText("Tap to view details")
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setAutoCancel(true);
            Intent intent1 = new Intent(context, MapsActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent1, PendingIntent.FLAG_UPDATE_CURRENT);
            builder.setContentIntent(pendingIntent);
            notificationManager.notify(1, builder.build());*/
            Toast.makeText(context, "You are near the location..//", Toast.LENGTH_SHORT).show();
        }
        return START_STICKY;
    }

    public LatLng radar(String place) {
        this.place = place;
        Radar.initialize(this.context, "prj_live_pk_9078c5798c882871cd310e30f257caf2a21dda5c");
        Radar.searchPlaces(targetLocation, 2000, new String[]{place}, null, null, 10, new Radar.RadarSearchPlacesCallback() {
            @Override
            public void onComplete(@NonNull Radar.RadarStatus radarStatus, @Nullable Location location, @Nullable RadarPlace[] radarPlaces) {
                assert location != null;
                latlng = new LatLng(location.getLatitude(), location.getLongitude());
            }
        });
        return latlng;
    }

    public List<RadarAddress> autocomplete(String placeName) {
        Radar.autocomplete(placeName, location1 , 10, new Radar.RadarGeocodeCallback() {
            @Override
            public void onComplete(@NonNull Radar.RadarStatus radarStatus, @Nullable RadarAddress[] radarAddresses) {
                if (radarStatus == Radar.RadarStatus.SUCCESS && radarAddresses != null) {
                    radar = new ArrayList<>(Arrays.asList(radarAddresses));
                }
            }
        });
        return radar;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new LocalBinder();
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}
