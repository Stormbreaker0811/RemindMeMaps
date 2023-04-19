package com.example.remindmemaps;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class LoginActivity extends AppCompatActivity {
    private TextInputLayout username,password;
    private EditText user,pass;
    private Button login;
    private FirebaseFirestore user_records;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initialiseUI();
        username.addOnEditTextAttachedListener(textInputLayout -> user = textInputLayout.getEditText());
        password.addOnEditTextAttachedListener(textInputLayout ->  pass = textInputLayout.getEditText());
        login.setOnClickListener(view->{
            initialiseDB();
        });

    }

    private void initialiseDB() {
        user_records = FirebaseFirestore.getInstance();
        user_records.collection("user_records").whereEqualTo("Password",pass.getText().toString())
                .get().addOnSuccessListener(queryDocumentSnapshots -> {
                    String name = "";
                    String email = "";
                    String password = "";
                    for(QueryDocumentSnapshot doc : queryDocumentSnapshots){
                        name = doc.getString("Name");
                        email = doc.getString("Email");
                        password = doc.getString("Password");
                    }
                    Intent intent = new Intent(LoginActivity.this,LandingActivity.class);
                    intent.putExtra("Name",name);
                    intent.putExtra("Email",email);
                    intent.putExtra("Password",password);
                    startActivity(intent);
                }).addOnFailureListener(e -> Toast.makeText(LoginActivity.this, "Password Incorrect. No user found", Toast.LENGTH_SHORT).show());
    }

    private void initialiseUI() {
        username = findViewById(R.id.name_text_layout);
        password = findViewById(R.id.password_text_layout);
        login = findViewById(R.id.login_btn);
    }
}