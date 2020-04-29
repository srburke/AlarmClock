package com.example.scaryalarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import android.media.MediaPlayer;
import android.support.v4.app.NotificationCompat;

public class AlertReceiver extends BroadcastReceiver {
    private MediaPlayer mediaPlayer;


    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationHelper notificationHelper = new NotificationHelper(context);
        NotificationCompat.Builder nb = notificationHelper.getChannelNotification();
        notificationHelper.getManager().notify(1, nb.build());

    }
}
