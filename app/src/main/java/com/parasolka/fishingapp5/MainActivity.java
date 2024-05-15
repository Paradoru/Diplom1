package com.parasolka.fishingapp5;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    EditText etEmail, etPassword;
    Button btnSignIn, btnSignUp;
    ProgressBar progressBar;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etEmail = findViewById(R.id.et_email);
        etPassword = findViewById(R.id.et_password);
        btnSignIn = findViewById(R.id.btn_sign_in);
        btnSignUp = findViewById(R.id.btn_sign_up);
        progressBar = findViewById(R.id.progressBar);
        firebaseAuth = FirebaseAuth.getInstance();
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                String email = etEmail.getText().toString().trim();
                String password = etPassword.getText().toString().trim();
                if (email.isEmpty()) {etEmail.setError("Email is required");
                    etEmail.requestFocus();
                    return;
                }
                if (password.isEmpty()) {etPassword.setError("Password is required");
                    etPassword.requestFocus();
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);
                firebaseAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {@Override
                            public void onComplete(@NonNull Task<AuthResult> task) {progressBar.setVisibility(View.GONE);
                                if (task.isSuccessful()) {
                                    FirebaseUser user = firebaseAuth.getCurrentUser();
                                    Toast.makeText(MainActivity.this, "Sign in successful.", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(MainActivity.this, videoimageplayer.class));
                                } else {Toast.makeText(MainActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
        btnSignUp.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, RegistrationActivity.class));
            }
        });
    }
}
