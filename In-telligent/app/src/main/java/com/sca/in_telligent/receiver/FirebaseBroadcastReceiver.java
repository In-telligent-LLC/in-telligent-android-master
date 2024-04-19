package com.sca.in_telligent.receiver;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.legacy.content.WakefulBroadcastReceiver;

import com.sca.in_telligent.ScaApplication;
import com.sca.in_telligent.openapi.data.network.model.PushNotification;
import com.sca.in_telligent.openapi.util.AudioHelper;
import com.sca.in_telligent.service.MyFirebaseMessagingService;

import javax.inject.Inject;

public class FirebaseBroadcastReceiver extends WakefulBroadcastReceiver  {
    MyFirebaseMessagingService myFirebaseMessagingService = new MyFirebaseMessagingService();

    private static final String TAG = FirebaseBroadcastReceiver.class.getName();

    @Inject
    AudioHelper audioHelper;



    @Override
    public void onReceive(Context context, Intent intent) {
        myFirebaseMessagingService.setContext(context);

        Bundle dataBundle = intent.getExtras();
        if (dataBundle != null) {
            if (dataBundle.containsKey("google.message_id")) {

                PushNotification pushNotification = new PushNotification();
                pushNotification.setNotification_title(dataBundle.getString("title"));
                pushNotification.setBody(dataBundle.getString("body"));
                pushNotification.setAlertType(dataBundle.getString("alertType"));

                Log.d(TAG, "push notification: " + pushNotification.getNotification_title() + " : " + pushNotification.getBody() + " : " + pushNotification.getAlertType());


                if (pushNotification.getAlertType() != null) {
                    if (pushNotification.getAlertType().equals("emergency")) {
                        getAudioHelper().startEmergencyRingtone();

                    } else if (pushNotification.getAlertType().equals("life_safety")) {
                        getAudioHelper().startLifeSafetyRingtone();

                    } else if (pushNotification.getAlertType().equals("critical")) {
                        getAudioHelper().startCriticalRingtone();

                    } else if (pushNotification.getAlertType().equals("ping")) {
                        getAudioHelper().startPingRingtone();
                    }

                    myFirebaseMessagingService.sendNotification(pushNotification);

                }
                else {
                    Log.d(TAG, "Alert type is null:");
                }


                for (String key : dataBundle.keySet()) {
                    Object value = dataBundle.get(key);
                    Log.d(TAG, "dataBundle: " + key + " : " + value);
                }
            }
        }
    }

    private AudioHelper getAudioHelper() {
        return this.audioHelper;
    }
}
