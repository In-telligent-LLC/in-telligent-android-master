package com.sca.in_telligent.receiver;



import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.app.PendingIntent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.legacy.content.WakefulBroadcastReceiver;

import com.sca.in_telligent.R;
import com.sca.in_telligent.ScaApplication;
import com.sca.in_telligent.openapi.data.network.model.PushNotification;
import com.sca.in_telligent.openapi.util.AudioHelper;
import com.sca.in_telligent.service.MyFirebaseMessagingService;
import com.sca.in_telligent.ui.main.MainActivity;

import javax.inject.Inject;



public class FirebaseBroadcastReceiver extends WakefulBroadcastReceiver  {

    private static final String TAG = FirebaseBroadcastReceiver.class.getName();

    @Inject
    AudioHelper audioHelper;

    private String state = "";


    public void handleRingtone(String type) {
        Log.d(TAG, "handleRingtone: " + type);
        if (audioHelper == null) {
            Log.e("FirebaseBroadcastReceiver", "AudioHelper is null");
        } else {
            if (type != null) {
                if (type.equals("emergency")) {
                    audioHelper.startEmergencyRingtone();

                } else if (type.equals("life-safety")) {
                    audioHelper.startLifeSafetyRingtone();

                } else if (type.equals("critical")) {
                    audioHelper.startCriticalRingtone();

                } else if (type.equals("ping")) {
                    audioHelper.startPingRingtone();
                }

            }
        }
    }



    @Override
    public void onReceive(Context context, Intent intent) {
        ((ScaApplication) context.getApplicationContext()).getComponent().inject(this);
        state = ((ScaApplication) context.getApplicationContext()).getCurrentState();

        Bundle dataBundle = intent.getExtras();
        if (dataBundle != null && dataBundle.containsKey("google.message_id")) {
            if(state.equals("start")) {
                PushNotification pushNotification = new PushNotification();
                pushNotification.setNotification_title(dataBundle.getString("title"));
                pushNotification.setBody(dataBundle.getString("body"));
                pushNotification.setAlertType(dataBundle.getString("alertType"));
                pushNotification.setNotificationId(dataBundle.getString("google.message_id"));


                Intent refreshIntent = new Intent(context, MainActivity.class);
                refreshIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                refreshIntent.putExtra("pushNotification", pushNotification);
                refreshIntent.putExtra("from", "");
                refreshIntent.putExtra("show_popup", true);

                context.startActivity(refreshIntent);
            }

            else {
                    PushNotification pushNotification = new PushNotification();
                    pushNotification.setNotification_title(dataBundle.getString("title"));
                    pushNotification.setBody(dataBundle.getString("body"));
                    pushNotification.setAlertType(dataBundle.getString("alertType"));
                    pushNotification.setNotificationId(dataBundle.getString("buildindId"));


                    if (audioHelper == null) {
                        Log.e("FirebaseBroadcastReceiver", "AudioHelper is null");
                    } else {
                        handleRingtone(pushNotification.getAlertType());
                        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, new Intent(context, MainActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);
                        NotificationCompat.Builder notificationBuilder =
                                new NotificationCompat.Builder(context, "default")
                                        .setSmallIcon(R.drawable.ic_launcher)
                                        .setContentTitle(pushNotification.getTitle())
                                        .setContentText(pushNotification.getBody())
                                        .setAutoCancel(true)
                                        .setContentIntent(pendingIntent);

                        NotificationManager notificationManager =
                                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            NotificationChannel channel = new NotificationChannel("default",
                                    "Channel human readable title",
                                    NotificationManager.IMPORTANCE_DEFAULT);
                            notificationManager.createNotificationChannel(channel);
                        }

                        if (pushNotification.getNotificationId() != null) {
                            notificationManager.notify(Integer.parseInt(pushNotification.getNotificationId()), notificationBuilder.build());
                        }

                    }




            }
        }
    }

}
