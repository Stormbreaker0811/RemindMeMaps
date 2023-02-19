package com.example.remindmemaps;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import org.w3c.dom.Text;

public class RegisterActivity extends AppCompatActivity {
    private TextInputLayout username,password,email,confirm;
    private EditText user,pass,email_text,conf;
    private Button register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initialiseUI();
        getEditText();
        register.setOnClickListener(view -> {
            if(TextUtils.isEmpty(user.getText()) && TextUtils.isEmpty(pass.getText()) &&
                    TextUtils.isEmpty(conf.getText()) && TextUtils.isEmpty(email_text.getText())){
                Toast.makeText(this, "Please fill all feilds.", Toast.LENGTH_SHORT).show();
            }
            else if(!Patterns.EMAIL_ADDRESS.matcher(email_text.getText().toString()).matches()){
                email.setError("Email Address not valid!");
                email.setErrorEnabled(true);
            }
        });
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
}