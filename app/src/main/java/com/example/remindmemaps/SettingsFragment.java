package com.example.remindmemaps;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.example.remindmemaps.databinding.FragmentSettingsBinding;
import com.google.android.material.materialswitch.MaterialSwitch;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.lang.annotation.Native;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SettingsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SettingsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Button viewProfile,changePassword,notificationSetting,aboutUs;
    private SwitchCompat darkMode;
    private FragmentSettingsBinding binding;
    private Context context;

    public SettingsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SettingsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SettingsFragment newInstance(String param1, String param2) {
        SettingsFragment fragment = new SettingsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSettingsBinding.inflate(inflater);
        View root = binding.getRoot();
        View view = inflater.inflate(R.layout.activity_landing,null);
        viewProfile = binding.MyProfile;
        changePassword = binding.ChangePassword;
        notificationSetting = binding.NotificationSetting;
        aboutUs = binding.AboutUs;
        darkMode = binding.DarkModeSwitch;
        viewProfile.setOnClickListener(view1->{
            Intent profileIntent = new Intent(this.context,myprofile.class);
            startActivity(profileIntent);
        });
        changePassword.setOnClickListener(view1->{
           Intent changePasswordIntent = new Intent(this.context,changepassword.class);
           startActivity(changePasswordIntent);
        });
        notificationSetting.setOnClickListener(view1->{
            Intent notificationSettingIntent = new Intent(this.context,notificationsetting.class);
            startActivity(notificationSettingIntent);
        });
        aboutUs.setOnClickListener(view1->{
            Intent aboutUsIntent = new Intent(this.context,aboutus.class);
            startActivity(aboutUsIntent);
        });
        darkMode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(darkMode.isChecked()){
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                }
                else{
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                }
            }
        });

        boolean isNightModeOn = AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES;
        // Inflate the layout for this fragment
        return root;
    }

    @Override
    public void onAttach(@androidx.annotation.NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }
}