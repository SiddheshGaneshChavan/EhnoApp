package com.example.ehnoapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.widget.Button;

public class InternetConn extends BroadcastReceiver {
Button b1;
    @Override
    public void onReceive(Context context, Intent intent) {
        if (!isNetworkConnected(context)){
           Dialog dialog=new Dialog(context);
           dialog.setContentView(R.layout.no_internet_conn);
           b1=dialog.findViewById(R.id.button4);
           b1.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   if(isNetworkConnected(context)){
                       dialog.dismiss();
                   }
               }
           });
           dialog.show();
        }
    }
    private boolean isNetworkConnected(Context context){
        try{
            ConnectivityManager connectivityManager= (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();
            return networkInfo!= null  && networkInfo.isConnected();
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
