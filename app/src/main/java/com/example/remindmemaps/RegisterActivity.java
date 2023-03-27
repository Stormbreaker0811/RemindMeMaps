package com.example.remindmemaps;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {
    private TextInputLayout username, password, email, confirm;
    boolean isRegistered;
    private EditText user, pass, email_text, conf;
    private Button register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initialiseUI();
        getEditText();
        register.setOnClickListener(view -> {
            if (TextUtils.isEmpty(user.getText()) && TextUtils.isEmpty(pass.getText()) &&
                    TextUtils.isEmpty(conf.getText()) && TextUtils.isEmpty(email_text.getText())) {
                Toast.makeText(this, "Please fill all feilds.", Toast.LENGTH_SHORT).show();
            } else if (!Patterns.EMAIL_ADDRESS.matcher(email_text.getText().toString()).matches()) {
                email.setError("Email Address not valid!");
                email.setErrorEnabled(true);
            } else {
                if (pass.getText().toString().equals(conf.getText().toString())) {
                    if (isRegistered()) {
                        //Toast.makeText(this, "User Has Already Been Registered.", Toast.LENGTH_SHORT).show();
                        AlertDialog.Builder registered = new AlertDialog.Builder(this);
                        registered.setTitle("Existing User Found.");
                        registered.setMessage("User Has Already Been Registered. Do you want to login?");
                        registered.setPositiveButton("Yes",(dialogInterface, i) -> {
                            dialogInterface.dismiss();
                            Intent in = new Intent(RegisterActivity.this,LoginActivity.class);
                            startActivity(in);
                        }).setNegativeButton("No",(dialogInterface, i) -> {
                            dialogInterface.dismiss();
                            Intent in = new Intent(RegisterActivity.this,MainActivity.class);
                            startActivity(in);
                        });
                    } else {
                        email.setErrorEnabled(false);
                        Map<String, String> users = new HashMap<>();
                        users.put("Username", user.getText().toString());
                        users.put("Email", email_text.getText().toString());
                        users.put("Password", pass.getText().toString());
                        FirebaseFirestore userRecords = FirebaseFirestore.getInstance();
                        userRecords.collection("user_records").
                                document("User" + user.getText().toString()).set(users).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        AlertDialog.Builder register = new AlertDialog.Builder(RegisterActivity.this);
                                        register.setTitle("Registration Complete.");
                                        register.setMessage("Congrats!!You have been registered.Do you want to login?");
                                        register.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                Intent intent = new Intent(RegisterActivity.this, EmailConfigActivity.class);
                                                startActivity(intent);
                                                finish();
                                            }
                                        }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                dialogInterface.dismiss();
                                                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                                                startActivity(intent);
                                                finish();
                                            }
                                        }).show();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        e.printStackTrace();
                                    }
                                });
                    }
                } else {
                    password.setError("Passwords don't match.");
                    confirm.setError("Passwords don't match.");
                }
            }
        });
    }

    private boolean isRegistered() {
        FirebaseFirestore registered = FirebaseFirestore.getInstance();
        registered.collection("users").whereEqualTo("Email",email_text.getText().toString())
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for(QueryDocumentSnapshot doc : queryDocumentSnapshots){
                            if(Objects.equals(doc.getString("Email"),email_text.getText().toString())){
                                isRegistered = true;
                            }
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        isRegistered = false;
                    }
                });
        return isRegistered;
    }

    private void getEditText() {
        user = username.getEditText();
        pass = password.getEditText();
        email_text = email.getEditText();
        conf = confirm.getEditText();
    }

    private void initialiseUI() {
        username = (TextInputLayout) findViewById(R.id.username_register);
        password = (TextInputLayout) findViewById(R.id.password_register);
        email = (TextInputLayout) findViewById(R.id.email_register);
        confirm = (TextInputLayout) findViewById(R.id.confirm_pass_register);
        register = (Button) findViewById(R.id.register_btn);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        AlertDialog.Builder logout_alert = new AlertDialog.Builder(this);
        logout_alert.setTitle("Logout??");
        logout_alert.setMessage("Do you want to logout?");
        logout_alert.setPositiveButton("Yes", (dialogInterface, i) -> {
            dialogInterface.dismiss();
            System.exit(0);
        }).setNegativeButton("No", (dialogInterface, i) -> {
            dialogInterface.dismiss();
        }).show();
    }
}