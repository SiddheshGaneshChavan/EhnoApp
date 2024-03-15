package com.example.ehnoapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class LoopNotify extends AppCompatActivity {
    ImageView b1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loop_notify);
        b1=findViewById(R.id.button);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoopNotify.this, MainPrem.class);
                startActivity(intent);
                Intent intent1 = new Intent(LoopNotify.this, LoopNotify.class);
                intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                PendingIntent pendingIntent = PendingIntent.getActivity(LoopNotify.this, 0, intent1, 0);
                NotificationCompat.Builder builder = new NotificationCompat.Builder(LoopNotify.this, "Enho App")
                        .setContentTitle("Enho App")
                        .setAutoCancel(true)
                        .setContentIntent(pendingIntent)
                        .setContentText("Enho App is running")
                        .setOngoing(true)
                        .setSmallIcon(R.drawable.ic_baseline_notifications_24);
                NotificationManagerCompat manager = NotificationManagerCompat.from(LoopNotify.this);
                manager.notify(1, builder.build());
            }
        });
    }
}