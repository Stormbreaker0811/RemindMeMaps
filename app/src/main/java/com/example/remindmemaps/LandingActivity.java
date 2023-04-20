package com.example.remindmemaps;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class LandingActivity extends AppCompatActivity {
    private TextView welcome;
    private String user;
    private BottomNavigationView bottomNav;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);
        Intent intent = getIntent();
        user = intent.getStringExtra("Name");
        initialiseUI();
//        ReminderFragment fragment = (ReminderFragment) getSupportFragmentManager().findFragmentById(R.id.reminderFragment);
//        assert fragment != null;
//        fragment.setUserDetails(intent.getStringExtra("Name"), intent.getStringExtra("Email"),intent.getStringExtra("Password"));
        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.home_menu_item:
                        bottomNav.getMenu().findItem(R.id.home_menu_item).setChecked(true);
                        getSupportFragmentManager().
                                beginTransaction().
                                replace(R.id.container,new HomeFragment()).
                                setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).
                                commit();
                        break;
                    case R.id.reminder_menu_item:
                        Bundle bundle = new Bundle();
                        bundle.putString("Name",user);
                        ReminderFragment fragment = new ReminderFragment();
                        fragment.setArguments(bundle);
                        bottomNav.getMenu().findItem(R.id.reminder_menu_item).setChecked(true);
                        getSupportFragmentManager().
                                beginTransaction().
                                replace(R.id.container,new ReminderFragment()).
                                setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).
                                commit();
                        break;
                    case R.id.settings_menu_item:
                        bottomNav.getMenu().findItem(R.id.settings_menu_item).setChecked(true);
                        getSupportFragmentManager().
                                beginTransaction().
                                replace(R.id.container,new SettingsFragment()).
                                setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).
                                commit();
                        break;
                }
                return false;
            }
        });
    }

    private void initialiseUI(){
        welcome = findViewById(R.id.welcome_landing);
        bottomNav = findViewById(R.id.bottom_nav);
    }
}