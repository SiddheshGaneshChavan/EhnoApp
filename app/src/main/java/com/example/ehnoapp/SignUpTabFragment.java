package com.example.ehnoapp;


import android.os.Bundle;
import android.text.TextUtils;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpTabFragment extends Fragment {
    EditText sign_email,sign_pass,sign_copass;
    Button SignButton;
    FirebaseAuth mAuth;
    float v=0;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        ViewGroup root=(ViewGroup) inflater.inflate(R.layout.signup_fragment,container,false);
        sign_email=root.findViewById(R.id.signemail);
        sign_pass=root.findViewById(R.id.signpassword);
        sign_copass=root.findViewById(R.id.copassword);
        mAuth=FirebaseAuth.getInstance();
        SignButton=root.findViewById(R.id.signUpButton);
        SignButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email_id = sign_email.getText().toString();
                String pass = sign_pass.getText().toString();
                String copass=sign_copass.getText().toString();
                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                if (!email_id.matches(emailPattern)){
                    sign_email.setError("Invalid Email Address");
                    sign_email.requestFocus();
                }
                if (TextUtils.isEmpty(email_id)){
                    sign_email.setError("E-Mail Cannot Be Empty");
                    sign_email.requestFocus();
                    return;
                }
                if(TextUtils.isEmpty(pass)){
                    sign_pass.setError("Password cannot be empty");
                    sign_pass.requestFocus();
                    return;
                }
                if(TextUtils.isEmpty((copass))){
                    sign_copass.setError("Confirm cannot be empty");
                    sign_copass.requestFocus();
                    return;
                }
                if(!(pass.equals(copass))){
                    sign_copass.setError("Password should be Same");
                    sign_copass.requestFocus();
                    return;
                }
                if(pass.length()<6){
                    sign_pass.setError("Password must be >=6 Character");
                    sign_pass.requestFocus();
                    return;
                }
                mAuth.createUserWithEmailAndPassword(email_id, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getActivity(), "User Registered Successfully", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getActivity(), "Registeration Error" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });
        sign_email.setTranslationY(800);
        sign_pass.setTranslationY(800);
        sign_copass.setTranslationY(800);
        SignButton.setTranslationY(800);
        sign_email.setAlpha(v);
        sign_pass.setAlpha(v);
        sign_copass.setAlpha(v);
        SignButton.setAlpha(v);
        sign_email.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(800).start();
        sign_pass.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(800).start();
        sign_copass.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(800).start();
        SignButton.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(800).start();
        return root;
    }
}
