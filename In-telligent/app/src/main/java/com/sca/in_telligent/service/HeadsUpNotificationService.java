package com.sca.in_telligent.service;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import androidx.core.app.NotificationCompat;

import com.sca.in_telligent.R;
import com.sca.in_telligent.openapi.Constants;
import com.sca.in_telligent.openapi.data.network.model.PushNotification;
import com.sca.in_telligent.receiver.HeadsUpNotificationActionReceiver;

import java.util.Objects;

public class HeadsUpNotificationService extends Service {
    private final String CHANNEL_ID = "VoipChannel";
    private final String CHANNEL_NAME = "Voip Channel";
    private Context context;
    private PushNotification data;
    private Handler handler;
    private Runnable timerRunnable;

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override // android.app.Service
    public void onCreate() {
        super.onCreate();
        this.context = this;
        this.handler = new Handler();

        Runnable cancelCallRunnable = () -> {

            Intent cancelIntent = new Intent(HeadsUpNotificationService.this.context, HeadsUpNotificationActionReceiver.class);
            cancelIntent.putExtra(Constants.CALL_RESPONSE_ACTION_KEY, Constants.CALL_AUTO_CANCEL_ACTION);
            cancelIntent.putExtra(Constants.CALL_NOTIFICATION, HeadsUpNotificationService.this.data);
            cancelIntent.setAction("CANCEL_CALL");

            HeadsUpNotificationService.this.sendBroadcast(cancelIntent);
        };

        this.timerRunnable = cancelCallRunnable;
        this.handler.postDelayed(cancelCallRunnable, Constants.AUTO_REJECT_TIME);
    }

    @Override // android.app.Service
    public int onStartCommand(Intent intent, int i, int i2) {
        this.data = null;
        if (intent != null && intent.getExtras() != null) {
            this.data = (PushNotification) intent.getSerializableExtra(Constants.CALL_NOTIFICATION);
        }
        try {
            Intent intent2 = new Intent(this, HeadsUpNotificationActionReceiver.class);
            intent2.putExtra(Constants.CALL_RESPONSE_ACTION_KEY, Constants.CALL_RECEIVE_ACTION);
            intent2.putExtra(Constants.CALL_NOTIFICATION, this.data);
            intent2.setAction("RECEIVE_CALL");
            Intent intent3 = new Intent(this, HeadsUpNotificationActionReceiver.class);
            intent3.putExtra(Constants.CALL_RESPONSE_ACTION_KEY, Constants.CALL_CANCEL_ACTION);
            intent3.putExtra(Constants.CALL_NOTIFICATION, this.data);
            intent3.setAction("CANCEL_CALL");
            PendingIntent broadcast = PendingIntent.getBroadcast(this, 1200, intent2, PendingIntent.FLAG_UPDATE_CURRENT);
            PendingIntent broadcast2 = PendingIntent.getBroadcast(this, 1201, intent3, PendingIntent.FLAG_UPDATE_CURRENT);
            createChannel();
            NotificationCompat.Builder visibility = this.data != null ? new NotificationCompat.Builder(this, this.CHANNEL_ID).setContentText(this.data.getBody()).setContentTitle(this.data.getTitle()).setSmallIcon((int) R.drawable.ic_launcher).setPriority(1).setCategory("call").addAction((int) R.drawable.ic_launcher, getResources().getString(R.string.accept_call), broadcast).addAction((int) R.drawable.ic_launcher, getResources().getString(R.string.cancel), broadcast2).setAutoCancel(true).setOngoing(true).setVisibility(1) : null;
            startForeground(120, visibility != null ? visibility.build() : null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 1;
    }

    public void createChannel() {
        if (Build.VERSION.SDK_INT >= 26) {
            NotificationChannel notificationChannel = new NotificationChannel(this.CHANNEL_ID, this.CHANNEL_NAME, 4);
            notificationChannel.setDescription("Call Notifications");
            notificationChannel.setLockscreenVisibility(1);
            ((NotificationManager) Objects.requireNonNull((NotificationManager) getSystemService(NotificationManager.class))).createNotificationChannel(notificationChannel);
        }
    }

    @Override // android.app.Service
    public void onDestroy() {
        this.handler.removeCallbacks(this.timerRunnable);
        super.onDestroy();
    }
}
