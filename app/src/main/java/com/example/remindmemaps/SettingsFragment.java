package com.example.remindmemaps;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.remindmemaps.databinding.FragmentSettingsBinding;

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
    private Button viewProfile,changePassword;
    private FragmentSettingsBinding binding;

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
        viewProfile = binding.MyProfile;
        changePassword = binding.ChangePassword;
        viewProfile.setOnClickListener(view->{
            getChildFragmentManager().beginTransaction().
                    replace(R.id.settingsFragment,new fragment_myprofile()).
                    commit();
        });
        changePassword.setOnClickListener(view -> {
            getChildFragmentManager().beginTransaction().
                    replace(R.id.settingsFragment,new Fragment_ChangePassword()).
                    commit();
        });
        // Inflate the layout for this fragment
        return root;
    }
}