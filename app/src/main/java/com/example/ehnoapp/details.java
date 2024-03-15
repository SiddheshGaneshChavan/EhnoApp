package com.example.ehnoapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;

public class details extends AppCompatActivity {
    Button b;
    EditText fname, lname, emphone,phone,city;
    TextView details;
    SharedPreferences sharedPreferences;
    AwesomeValidation awesomeValidation;
    private static final String SHARED_PREF_NAME="MYPREF";
    private static final String KEY_NAME="Firstname";
    private static final String KEY_LNAME="Lastname";
    private static final String KEY_EMPHONE="EMPhone";
    public static final String KEY_PHONENO="Phone";
    public static final String KEY_CITY="City";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            NotificationChannel channel=new NotificationChannel("Enho App","Enho App is running", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager=getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
        details=findViewById(R.id.details);
        fname=findViewById(R.id.Name_data);
        lname=findViewById(R.id.LastName_data);
        phone=findViewById(R.id.Phone_data);
        emphone=findViewById(R.id.EMPhone_data);
        city=findViewById(R.id.City);
        b=findViewById(R.id.ActionButton);
        sharedPreferences=getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);
        String Fname=sharedPreferences.getString(KEY_NAME,null);
        if(Fname!=null){
            Intent intent1=new Intent(details.this,LoopNotify.class);
            startActivity(intent1);
        }
        awesomeValidation=new AwesomeValidation(ValidationStyle.BASIC);
        awesomeValidation.addValidation(details.this,R.id.Name_data, RegexTemplate.NOT_EMPTY,R.string.invalid_name);
        awesomeValidation.addValidation(details.this,R.id.LastName_data,RegexTemplate.NOT_EMPTY,R.string.invalid_lastname);
        awesomeValidation.addValidation(details.this,R.id.City,RegexTemplate.NOT_EMPTY,R.string.invalid_city);
        awesomeValidation.addValidation(details.this,R.id.EMPhone_data,"[5-9]{1}[0-9]{9}$",R.string.invalid_mobile);
        awesomeValidation.addValidation(details.this,R.id.Phone_data,"[5-9]{1}[0-9]{9}$",R.string.invalid_emphone);
        b.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (awesomeValidation.validate()) {
                            Intent intent1 = new Intent(details.this, LoopNotify.class);
                            startActivity(intent1);
                            Toast.makeText(details.this, "Form Validate Successfully ", Toast.LENGTH_SHORT).show();
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString(KEY_NAME, fname.getText().toString());
                            editor.putString(KEY_LNAME, lname.getText().toString());
                            editor.putString(KEY_CITY,city.getText().toString());
                            editor.putString(KEY_PHONENO, phone.getText().toString());
                            editor.putString(KEY_EMPHONE, emphone.getText().toString());
                            editor.apply();
                            intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            PendingIntent pendingIntent = PendingIntent.getActivity(details.this, 0, intent1, 0);
                            startActivity(intent1);
                            NotificationCompat.Builder builder = new NotificationCompat.Builder(details.this, "Enho App")
                                    .setContentTitle("Enho App")
                                    .setAutoCancel(true)
                                    .setContentIntent(pendingIntent)
                                    .setContentText("Enho App is running")
                                    .setOngoing(true)
                                    .setSmallIcon(R.drawable.ic_baseline_notifications_24);
                            NotificationManagerCompat manager = NotificationManagerCompat.from(details.this);
                            manager.notify(1, builder.build());
                            Toast.makeText(details.this, "Data is Stored", Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(details.this, "Validate Failed ", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}