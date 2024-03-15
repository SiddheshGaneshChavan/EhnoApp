package com.example.ehnoapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword extends AppCompatActivity {
    EditText forgot;
    Button b1;
    TextView t1;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        forgot=findViewById(R.id.forgortPasswordfield);
        t1=findViewById(R.id.forgortPassword);
        b1=findViewById(R.id.signUpButton);
        mAuth= FirebaseAuth.getInstance();
        forgot.setTranslationY(800);
        b1.setTranslationY(800);
        forgot.animate().translationY(0).setDuration(1000).setStartDelay(800).start();
        b1.animate().translationY(0).setDuration(1000).setStartDelay(800).start();
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email=forgot.getText().toString().trim();
                if (email.isEmpty()){
                    forgot.setError("Email is required");
                    forgot.requestFocus();
                    return;
                }
                mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(ForgotPassword.this, "Check your email to reset your password", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(ForgotPassword.this, "Try again! Something wrong happened!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}