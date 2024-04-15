package com.sca.in_telligent.receiver;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.legacy.content.WakefulBroadcastReceiver;

import com.sca.in_telligent.openapi.data.network.model.PushNotification;
import com.sca.in_telligent.service.MyFirebaseMessagingService;

public class FirebaseBroadcastReceiver extends WakefulBroadcastReceiver  {
    MyFirebaseMessagingService myFirebaseMessagingService = new MyFirebaseMessagingService();

    private static final String TAG = FirebaseBroadcastReceiver.class.getName();


    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle dataBundle = intent.getExtras();
        if (dataBundle != null) {
            if(dataBundle.containsKey("google.message_id")) {
                PushNotification pushNotification = new PushNotification();
                pushNotification.setNotification_title(dataBundle.getString("title"));
                pushNotification.setBody(dataBundle.getString("body"));
                pushNotification.setAlertType(dataBundle.getString("alertType"));
                myFirebaseMessagingService.sendNotification(context, pushNotification);

                Log.d(TAG, "google.message_id: "
                        + dataBundle.getString("google.message_id"));
            }


            for (String key : dataBundle.keySet()) {
                Object value = dataBundle.get(key);
                Log.d(TAG, "dataBundle: " + key + " : " + value);
            }
        }

    }
}
