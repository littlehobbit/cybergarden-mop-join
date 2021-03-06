package com.example.appmobile;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.widget.Toast;

public class Restarter extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context.getApplicationContext(), "restarted", Toast.LENGTH_SHORT);
        context.startForegroundService(new Intent(context, NewsDaemon.class));
    }
}
