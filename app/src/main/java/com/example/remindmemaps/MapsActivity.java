package com.example.remindmemaps;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.remindmemaps.databinding.ActivityMapsBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    boolean bound;
    LocationTracker tracker;
    private String placeName,name,reminderName,description,items;
    private ActivityMapsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        View root = binding.getRoot();
        setContentView(root);
        Intent intent = getIntent();
        placeName = intent.getStringExtra("Place");
        name = intent.getStringExtra("Username");
        reminderName = intent.getStringExtra("ReminderName");
        description = intent.getStringExtra("ReminderDescription");
        items = intent.getStringExtra("Items");
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        // Add a marker in Sydney and move the camera
        assert placeName != null;
        if(placeName.equals("MIT WPU")){
            LatLng sydney = new LatLng(18.518810002052458, 73.81498449181112);
            mMap.addMarker(new MarkerOptions().position(sydney).title("Marker "+placeName));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
            Map<String,Object> reminders = new HashMap<>();
            reminders.put("Name",name);
            reminders.put("Reminder name",reminderName);
            reminders.put("Reminder Description",description);
            reminders.put("Items",items);
            reminders.put("Latitude",Double.toString(sydney.latitude));
            reminders.put("Longitude",Double.toString(sydney.longitude));
            reminders.put("Place Name",placeName);
            FirebaseFirestore.getInstance().collection("reminders_collection").
                    add(reminders).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Toast.makeText(MapsActivity.this, "Location added Successfully..//", Toast.LENGTH_SHORT).show();
                            tracker = new LocationTracker(MapsActivity.this,reminderName);
                            Intent intent1 = new Intent(MapsActivity.this,LocationTracker.class);
                            bindService(intent1, new ServiceConnection() {
                                @Override
                                public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                                    LocationTracker.LocalBinder binder = (LocationTracker.LocalBinder) iBinder;
                                    tracker = binder.getService();
                                    bound = true;
                                }
                                @Override
                                public void onServiceDisconnected(ComponentName componentName) {
                                    tracker = null;
                                    bound = false;
                                }
                            }, Context.BIND_AUTO_CREATE);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            e.printStackTrace();
                        }
                    });
        }
    }

}