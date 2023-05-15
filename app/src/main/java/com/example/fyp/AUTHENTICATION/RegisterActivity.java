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
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {

    FirebaseAuth firebaseAuth;

    FirebaseFirestore firebaseFirestore;
    TextInputEditText etRegName;
    TextInputEditText etRegEmail;
    TextInputEditText etRegPassword;
    TextView tvLoginHere;
    Button btnRegister;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etRegName = findViewById(R.id.nameEt);
        etRegEmail = findViewById(R.id.emailEt);
        etRegPassword = findViewById(R.id.passET);
        tvLoginHere = findViewById(R.id.signInIntent);
        btnRegister = findViewById(R.id.btnSignUp);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        btnRegister.setOnClickListener(view -> createUser());
        tvLoginHere.setOnClickListener(view -> startActivity(new Intent(RegisterActivity.this, LoginActivity.class)));
    }

    private void createUser() {
        String name = Objects.requireNonNull(etRegName.getText()).toString();
        String email = Objects.requireNonNull(etRegEmail.getText()).toString();
        String password = Objects.requireNonNull(etRegPassword.getText()).toString();

        if (TextUtils.isEmpty(name)) {
            etRegName.setError("Name cannot be empty");
            etRegName.requestFocus();
        } else if (TextUtils.isEmpty(email)) {
            etRegEmail.setError("Email cannot be empty");
            etRegEmail.requestFocus();
        } else if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Password cannot be empty", Toast.LENGTH_SHORT).show();
        } else {
            firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(this, "User registered successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(RegisterActivity.this, MainActivity.class));

                    firebaseFirestore.collection("User")
                            .document(Objects.requireNonNull(FirebaseAuth.getInstance().getUid()))
                            .set(new UserModel(name, email));
                }else {
                    Toast.makeText(this, "Registration Error: " + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}