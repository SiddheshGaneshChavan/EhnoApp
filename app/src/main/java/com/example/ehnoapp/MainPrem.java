package com.example.ehnoapp;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import androidx.core.content.ContextCompat;

import android.Manifest;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;

import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainPrem extends AppCompatActivity {
    ImageButton shield,Camera,live;
    ActivityResultLauncher<String[]> mPermission;
    private boolean isLocationPermission=false;
    private boolean isSmsPermission=false;
    private boolean isCameraPermission=false;
    private boolean isStoragewrite=false;
    private boolean isPermissionGranted=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_prem);
        shield = findViewById(R.id.shield_button);
        Camera = findViewById(R.id.camera_button);
        live=findViewById(R.id.location_button);
        checkSelfPermissionofback();
        mPermission=registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), new ActivityResultCallback<Map<String, Boolean>>() {
            @Override
            public void onActivityResult(Map<String, Boolean> result) {
                if (result.get(Manifest.permission.ACCESS_FINE_LOCATION)!=null){
                    isLocationPermission=result.get(Manifest.permission.ACCESS_FINE_LOCATION);
                }
                if (result.get(Manifest.permission.SEND_SMS)!=null){
                    isSmsPermission=result.get(Manifest.permission.SEND_SMS);
                }
                if (result.get(Manifest.permission.CAMERA)!=null){
                    isCameraPermission=result.get(Manifest.permission.CAMERA);
                }
                if (result.get(Manifest.permission.WRITE_EXTERNAL_STORAGE)!=null){
                    isStoragewrite=result.get(Manifest.permission.WRITE_EXTERNAL_STORAGE);
                }

            }
        });
        requestPermission();
        live.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2=new Intent(MainPrem.this,Livelocation.class);
                startActivity(intent2);
            }
        });
        shield.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainPrem.this,perfectlocation.class);
                startActivity(intent);
                if (ContextCompat.checkSelfPermission(MainPrem.this,Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(MainPrem.this,new String[]{Manifest.permission.CALL_PHONE},100);
                }
            }
        });
        Camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2=new Intent(MainPrem.this,Camera_Act.class);
                startActivity(intent2);
            }
        });
    }

    private void checkSelfPermissionofback() {
        Dexter.withContext(this).withPermission(Manifest.permission.ACCESS_BACKGROUND_LOCATION).withListener(new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                Toast.makeText(getApplicationContext(), "Permission Granted", Toast.LENGTH_SHORT).show();
                isPermissionGranted = true;
            }

            @Override
            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                Intent intent = new Intent();
                Toast.makeText(getApplicationContext(), "Go to Permission and change to all location permission to all the time", Toast.LENGTH_LONG).show();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", getPackageName(), "");
                intent.setData(uri);
                startActivity(intent);
            }

            @Override
            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                permissionToken.continuePermissionRequest();
            }
        }).check();
    }

    private void requestPermission() {
        isLocationPermission= ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED;
        isSmsPermission=ContextCompat.checkSelfPermission(this,Manifest.permission.SEND_SMS)==PackageManager.PERMISSION_GRANTED;
        List<String> permissionRequest=new ArrayList<>();
        if (!isLocationPermission){
            permissionRequest.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (!isSmsPermission){
            permissionRequest.add(Manifest.permission.SEND_SMS);
        }
        if (!isCameraPermission){
            permissionRequest.add(Manifest.permission.CAMERA);
        }
        if (!isStoragewrite){
            permissionRequest.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (!permissionRequest.isEmpty()){
            mPermission.launch(permissionRequest.toArray(new String[0]));
        }

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode==1){
            if (grantResults[0]==PackageManager.PERMISSION_GRANTED){
                Toast.makeText(MainPrem.this,"Location permisson is Granted",Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(MainPrem.this, "Location permission is Denied!", Toast.LENGTH_SHORT).show();
            }
            if (grantResults[1]==PackageManager.PERMISSION_GRANTED){
                Toast.makeText(MainPrem.this,"Sending SMS permisson is Granted",Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(MainPrem.this, "Sending SMS permission is Denied!", Toast.LENGTH_SHORT).show();
            }
            if (grantResults[2]==PackageManager.PERMISSION_GRANTED){
                Toast.makeText(MainPrem.this,"Camera permisson is Granted",Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(MainPrem.this, "Camera permission is Denied!", Toast.LENGTH_SHORT).show();
            }
            if (grantResults[3]==PackageManager.PERMISSION_GRANTED){
                Toast.makeText(MainPrem.this,"Storage permission is Granted",Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(MainPrem.this, "Storage permission is Denied!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}