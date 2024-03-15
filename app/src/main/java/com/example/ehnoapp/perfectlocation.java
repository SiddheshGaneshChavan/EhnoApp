package com.example.ehnoapp;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;


import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;

import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import android.provider.Settings;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;

import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import org.json.JSONArray;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class perfectlocation extends AppCompatActivity implements OnMapReadyCallback, View.OnClickListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener{
    boolean isPermissionGranted;
    GoogleMap mGooglemap;
    String sPhone="182";
    FloatingActionButton t1,t2,t3;
    float accur;
    double lat,longitude,alt;
    EditText Mess;
    MarkerOptions markerOptions;
    TextView police,hospital;
    private static double[] lati,longi;
    public static String[] name;
    private FusedLocationProviderClient mLocationClient;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    String location;
    SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME="MYPREF";
    private static final String KEY_NAME="Firstname";
    private static final String KEY_LNAME="Lastname";
    private static final String KEY_EMPHONE="EMPhone";
    private static final String KEY_PHONENO="Phone";
    public static final String KEY_CITY="City";
    LatLng start;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfectlocation);
        Mess=findViewById(R.id.Message_Box);
        t1 = findViewById(R.id.fab);
        t2=findViewById(R.id.fab2);
        police=findViewById(R.id.tx1);
        hospital=findViewById(R.id.tx2);
        t3=findViewById(R.id.fab3);
        t1.setTranslationY(800);
        t1.animate().translationY(0).setDuration(1000).setStartDelay(800).start();
        t2.setTranslationY(800);
        t2.animate().translationY(0).setDuration(1000).setStartDelay(800).start();
        t3.setTranslationY(800);
        t3.animate().translationY(0).setDuration(1000).setStartDelay(800).start();
        final MediaPlayer mediaPlayer=MediaPlayer.create(perfectlocation.this,R.raw.sample_voice);
        checkSelfPermission();
        initMap();
        police.setOnClickListener(this);
        hospital.setOnClickListener(this);

        mLocationClient = new FusedLocationProviderClient(this);
        t1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getCurr();
            }
        });

        t2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(perfectlocation.this, "Calling to  182", Toast.LENGTH_SHORT).show();
                if (ContextCompat.checkSelfPermission(perfectlocation.this,Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(perfectlocation.this,new String[]{Manifest.permission.CALL_PHONE},100);
                }
                Intent intent=new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:"+sPhone));
                startActivity(intent);
            }
        });

        t3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sharedPreferences=getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);
                String Emphone=sharedPreferences.getString(KEY_EMPHONE,null);
                Toast.makeText(perfectlocation.this, "Calling to  Emergency Phone Number", Toast.LENGTH_SHORT).show();
                if (ContextCompat.checkSelfPermission(perfectlocation.this,Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(perfectlocation.this,new String[]{Manifest.permission.CALL_PHONE},100);
                }
                Intent intent=new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:"+Emphone));
                startActivity(intent);
            }
        });
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tx1:
                getJson("http://192.168.0.7/project.php");
                break;
            case R.id.tx2:
                getJson("http://192.168.0.7/project2.php");
                break;
        }
    }
    private void getJson(String s) {
        class GetJSON extends AsyncTask<Void,Void,String>{
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                LatLng l;
                mGooglemap.clear();
                try {
                    JSONArray jsonarray = new JSONArray(s);
                    JSONObject jsonobject=null;
                    name=new String[jsonarray.length()];
                    lati=new double[jsonarray.length()];
                    longi=new double[jsonarray.length()];
                    for (int i = 0; i < jsonarray.length(); i++) {
                        jsonobject = jsonarray.getJSONObject(i);
                        name[i] = jsonobject.getString("Name");
                        lati[i] = jsonobject.getDouble("Latitude");
                        longi[i] = jsonobject.getDouble("Longitude");
                        l = new LatLng(lati[i], longi[i]);
                        markerOptions = new MarkerOptions();
                        markerOptions.title(name[i]);
                        markerOptions.position(l);
                        mGooglemap.addMarker(markerOptions);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(Void... voids) {
                try {
                    URL url=new URL(s);
                    HttpURLConnection con=(HttpURLConnection) url.openConnection();
                    StringBuilder sb=new StringBuilder();
                    BufferedReader b1=new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String json;
                    while ((json=b1.readLine())!=null){
                        sb.append(json+"\n");
                    }
                    return sb.toString().trim();
                }catch (Exception e){
                    return "";
                }
            }
        }
        GetJSON getJSON=new GetJSON();
        getJSON.execute();
    }

    @SuppressLint("MissingPermission")
    private void getCurr() {
        mLocationClient.getLastLocation().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Location location = task.getResult();
                lat=location.getLatitude();
                longitude=location.getLongitude();
                accur=location.getAccuracy();
                alt=location.getAltitude();
                gotoLocation(location.getLatitude(), location.getLongitude());
                sendSms(lat,longitude,accur,alt);
            }
        });
    }

    private void sendSms(double lat, double longitude,float accur,double alt) {
        sharedPreferences=getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);
        String EPhone=sharedPreferences.getString(KEY_EMPHONE,null);
        String Phone=sharedPreferences.getString(KEY_PHONENO,null);
        String fname=sharedPreferences.getString(KEY_NAME,null);
        String lname=sharedPreferences.getString(KEY_LNAME,null);
        String city=sharedPreferences.getString(KEY_CITY,null);
        String sMessage=Mess.getText().toString().trim();
        String Message=sMessage+"\n"+"https://www.google.com/maps/search/?api=1&query="+lat+","+longitude+"\nName:"+fname+lname;
        if (!sPhone.equals("")&&!sMessage.equals("")){
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(sPhone, null, Message, null, null);
            smsManager.sendTextMessage(EPhone,null,Message,null,null);
            Toast.makeText(getApplicationContext(), "Custom SMS sent Successfully!", Toast.LENGTH_LONG).show();
        }
        else {
            String Default="Help I'm in Trouble\n"+"https://www.google.com/maps/search/?api=1&query="+lat+","+longitude+"\nName:"+fname+lname;
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(sPhone, null, Default, null, null);
            smsManager.sendTextMessage(EPhone, null, Default, null, null);
            Toast.makeText(getApplicationContext(), "SMS sent Successfully", Toast.LENGTH_LONG).show();
        }
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference("Womens Details");
        UserHelperDatabase userHelperDatabase=new UserHelperDatabase(fname,lname,sMessage,EPhone,city,sPhone,lat,longitude,alt,accur);
        databaseReference.child(Phone).setValue(userHelperDatabase);
    }

    private void gotoLocation(double latitude, double longitude) {
        start = new LatLng(latitude, longitude);
        location="Location:"+start;
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(start, 30);
        mGooglemap.moveCamera(cameraUpdate);
    }
    private void initMap() {
        if (isPermissionGranted) {
            SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);
            supportMapFragment.getMapAsync(this);
        }
    }
    private void checkSelfPermission() {
        Dexter.withContext(this).withPermission(Manifest.permission.ACCESS_FINE_LOCATION).withListener(new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                Toast.makeText(perfectlocation.this, "Permission Granted", Toast.LENGTH_SHORT).show();
                isPermissionGranted = true;
            }
            @Override
            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                Intent intent = new Intent();
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

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mGooglemap = googleMap;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mGooglemap.setMyLocationEnabled(true);

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Toast.makeText(this, "Connected to API", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onConnectionSuspended(int i) {
        Toast.makeText(this, "Connection Suspended", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(this, "Connection Failed", Toast.LENGTH_SHORT).show();
    }
}