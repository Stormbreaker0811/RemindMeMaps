package com.example.remindmemaps;


import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.remindmemaps.databinding.FragmentReminderBinding;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.FetchPlaceRequest;
import com.google.android.libraries.places.api.net.FetchPlaceResponse;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteFragment;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.FirebaseFirestore;

import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import io.radar.sdk.Radar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ReminderFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ReminderFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private Context context;
    View view;
    private MapsFragment mapsFragment;
    private FragmentReminderBinding binding;
    private static final String ARG_PARAM2 = "param2";
    private final int REQUEST_PERMISSION_COURSE_LOCATION = 1;
    private View root;
    private EditText place_name;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Button selectPlace;
    private TextInputLayout reminderName, reminderDescription, addItems;
    private EditText name, description, addItemsEditText;
    private String user, email, password;

    public ReminderFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ReminderFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ReminderFragment newInstance(String param1, String param2) {
        ReminderFragment fragment = new ReminderFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    private void getEditText() {
        reminderName.addOnEditTextAttachedListener(textInputLayout -> name = textInputLayout.getEditText());
        reminderDescription.addOnEditTextAttachedListener(textInputLayout -> description = textInputLayout.getEditText());
        addItems.addOnEditTextAttachedListener(textInputLayout -> addItemsEditText = textInputLayout.getEditText());
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentReminderBinding.inflate(inflater);
        root = binding.getRoot();
//        Bundle extras = getArguments();
//
//        String username = extras.getString("Name");

        /*mapsFragment = (MapsFragment) getChildFragmentManager().findFragmentById(R.id.map);*/
        reminderName = binding.enterReminderName;
        reminderDescription = binding.enterReminderDescription;
        addItems = binding.listAllItems;
        selectPlace = binding.selectPlace;
        getEditText();
        selectPlace.setOnClickListener(view1 -> {
            if (TextUtils.isEmpty(name.getText()) && TextUtils.isEmpty(description.getText()) && TextUtils.isEmpty(addItemsEditText.getText())) {
                Toast.makeText(context, "Please Fill all the fields...///", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getActivity(), "Select Place Called..//", Toast.LENGTH_SHORT).show();
                AlertDialog.Builder maps_alert = new AlertDialog.Builder(getContext());
                maps_alert.setCancelable(true);
                LayoutInflater inflater1 = LayoutInflater.from(context);
                view = inflater1.inflate(R.layout.popup_maps_fragment, null);
                maps_alert.setView(view);
                TextInputLayout placeName = view.findViewById(R.id.place_name);
                placeName.addOnEditTextAttachedListener(textInputLayout -> place_name = textInputLayout.getEditText());
                maps_alert.setPositiveButton("Add Place", (dialogInterface, i) -> {
                    Intent intent = new Intent(this.context, MapsActivity.class);
                    intent.putExtra("Place",place_name.getText().toString());
                    intent.putExtra("ReminderName",name.getText().toString());
                    intent.putExtra("ReminderDescription",description.getText().toString());
                    intent.putExtra("Items",addItemsEditText.getText().toString());
                    startActivity(intent);
                });
                AlertDialog maps_dialog = maps_alert.create();
                maps_dialog.show();

            }
        });
        return root;
    }

    public void setUserDetails(String name, String email, String password) {
        this.user = name;
        this.email = email;
        this.password = password;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_PERMISSION_COURSE_LOCATION) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(context, "Permission Granted!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Permission Denied!", Toast.LENGTH_SHORT).show();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }
}