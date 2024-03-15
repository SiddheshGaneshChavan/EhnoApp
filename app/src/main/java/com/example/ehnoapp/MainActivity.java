package com.example.ehnoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;

import com.airbnb.lottie.LottieAnimationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    FirebaseAuth mAuth;
    LottieAnimationView lottieAnimationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lottieAnimationView =findViewById(R.id.splashscreen);
        mAuth= FirebaseAuth.getInstance();
        Thread timer=new Thread(){
            @Override
            public void run() {
                try{
                    sleep(2000);
                    FirebaseUser user = mAuth.getCurrentUser();
                    if (user == null) {
                        Intent intent = new Intent(MainActivity.this,LoginScreen.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Intent mainIntent = new Intent(MainActivity.this, details.class);
                        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(mainIntent);
                        finish();
                    }
                    finish();
                    super.run();
                }catch(InterruptedException e){
                    e.printStackTrace();
                }

            }
        };
        timer.start();
    }

}