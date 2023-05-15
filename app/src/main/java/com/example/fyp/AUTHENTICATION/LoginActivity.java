package com.example.fyp.AUTHENTICATION;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fyp.MAIN_ACTIVITIES.MainActivity;
import com.example.fyp.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    TextInputEditText etRegEmail;
    TextInputEditText etRegPassword;
    TextView tvRegisterHere;
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
//
        etRegEmail = findViewById(R.id.emailEt);
        etRegPassword = findViewById(R.id.passET);
        tvRegisterHere = findViewById(R.id.signUpIntent);
        btnLogin = findViewById(R.id.btnSignIn);

        firebaseAuth = FirebaseAuth.getInstance();

        btnLogin.setOnClickListener(view -> loginUser());
        tvRegisterHere.setOnClickListener(view -> startActivity(new Intent(LoginActivity.this, RegisterActivity.class)));

        TextView forgotPass = findViewById(R.id.forgotPass);
        forgotPass.setOnClickListener(view -> {
            String email = Objects.requireNonNull(etRegEmail.getText()).toString();
            firebaseAuth.sendPasswordResetEmail(email)
                    .addOnSuccessListener(unused -> Toast.makeText(LoginActivity.this, "Email Sent", Toast.LENGTH_SHORT).show())
                    .addOnFailureListener(e -> Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show());
        });
    }

    private void loginUser() {
        String email = Objects.requireNonNull(etRegEmail.getText()).toString();
        String password = Objects.requireNonNull(etRegPassword.getText()).toString();

        if (TextUtils.isEmpty(email)) {
            etRegEmail.setError("Email cannot be empty");
            etRegEmail.requestFocus();
        } else if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Password cannot be empty", Toast.LENGTH_SHORT).show();
        } else {
            firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(this, "User logged in successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                } else {
                    Toast.makeText(this, "Login Error: " + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}

