package com.example.smilingfacedemo;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignInActivity extends AppCompatActivity {
    EditText etEmail, etPass;
    Button btnLogin;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        etEmail = findViewById(R.id.etEmail_login);
        etPass = findViewById(R.id.etPass_login);
        btnLogin = findViewById(R.id.btnLogin);

        auth = FirebaseAuth.getInstance();
        btnLogin.setOnClickListener(v-> {
            String email = etEmail.getText().toString();
            String password = etPass.getText().toString();

            if(TextUtils.isEmpty(email)){
                Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(password)) {
                Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                return;
            }

            auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(SignInActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (!task.isSuccessful()) {
                                // there was an error
                                if (password.length() < 6) {
                                    etPass.setError("Mat khau khong duoi 6 ki tu");
                                } else {
                                    Toast.makeText(SignInActivity.this, "Fail", Toast.LENGTH_LONG).show();
                                }
                            } else {
                                Intent intent = new Intent(SignInActivity.this, FaceActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }
                    });
        });

    }
}
