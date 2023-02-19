package com.example.remindmemaps;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class LandingActivity extends AppCompatActivity {
    private TextView welcome;
    private String user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);
        Intent in = getIntent();
        user = in.getStringExtra("Name");
        Toast.makeText(this, user, Toast.LENGTH_SHORT).show();
        welcome = findViewById(R.id.welcome_landing);
        welcome.setText("Welcome "+user);
    }
}