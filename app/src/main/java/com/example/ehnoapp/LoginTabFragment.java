package com.example.ehnoapp;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginTabFragment extends Fragment {
    EditText email,pass;
    TextView forgot;
    Button LoginButton;
    FirebaseAuth mAuth;
    float v=0;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        ViewGroup root=(ViewGroup) inflater.inflate(R.layout.login_fragment,container,false);
        email=root.findViewById(R.id.email);
        pass=root.findViewById(R.id.password);
        LoginButton=root.findViewById(R.id.LoginButton);
        forgot=root.findViewById(R.id.forgotview);
        mAuth=FirebaseAuth.getInstance();
        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUser();
            }
        });
        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(),ForgotPassword.class);
                startActivity(intent);
            }
        });
        email.setTranslationY(800);
        pass.setTranslationY(800);
        LoginButton.setTranslationY(800);
        forgot.setTranslationY(800);
        email.setAlpha(v);
        pass.setAlpha(v);
        LoginButton.setAlpha(v);
        forgot.setAlpha(v);
        email.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(800).start();
        pass.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(800).start();
        forgot.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(800).start();
        LoginButton.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(800).start();
        return root;
    }

    private void loginUser() {
        String email_id = email.getText().toString();
        String password = pass.getText().toString();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if (!email_id.matches(emailPattern)){
            email.setError("Invalid Email Address");
            email.requestFocus();
        }else if (TextUtils.isEmpty(email_id)) {
            email.setError("Username Cannot Be Empty");
            email.requestFocus();
        } else if (TextUtils.isEmpty(password)) {
            pass.setError("Password cannot be empty");
            pass.requestFocus();
        } else{
            mAuth.signInWithEmailAndPassword(email_id,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(getActivity(),"User Login in Successfully",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getActivity(),details.class));
                    }else{
                        Toast.makeText(getActivity(), "Login Error"+ task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}
