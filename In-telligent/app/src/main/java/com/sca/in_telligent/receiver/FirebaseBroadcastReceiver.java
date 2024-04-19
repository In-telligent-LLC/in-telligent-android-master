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
import com.sca.in_telligent.ui.main.MainActivity;

import javax.inject.Inject;



public class FirebaseBroadcastReceiver extends WakefulBroadcastReceiver  {
    MyFirebaseMessagingService myFirebaseMessagingService = new MyFirebaseMessagingService();

    private static final String TAG = FirebaseBroadcastReceiver.class.getName();

    @Inject
    AudioHelper audioHelper;

    private String state = "";

    private Context mContext;

    public void setContext(Context context) {
        this.mContext = context;
    }




    public void handleRingtone(String type) {
        Log.d(TAG, "handleRingtone: " + type);
        if (audioHelper == null) {
            Log.e("FirebaseBroadcastReceiver", "AudioHelper is null");
        } else {
            if (type != null) {
                if (type.equals("emergency")) {
                    audioHelper.startEmergencyRingtone();

                } else if (type.equals("life_safety")) {
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
        if (dataBundle != null) {
            if (dataBundle.containsKey("google.message_id")) {
                Log.d(TAG, "onReceive STATE: " + state);
                if(state.equals("start")) {
                    Log.d(TAG, "foreground STATE: " + state);
                    PushNotification pushNotification = new PushNotification();
                    pushNotification.setNotification_title(dataBundle.getString("title"));
                    pushNotification.setBody(dataBundle.getString("body"));
                    pushNotification.setAlertType(dataBundle.getString("alertType"));

                    if(mContext == null) {
                        Log.e("FirebaseBroadcastReceiver", "Context is null");

                    } else {
                        Intent refreshIntent = new Intent(mContext, MainActivity.class);
                        refreshIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        refreshIntent.putExtra("pushNotification", pushNotification);
                        refreshIntent.putExtra("from", "foreground");

                        refreshIntent.putExtra("show_popup", true);

                    }



//                    myFirebaseMessagingService.sendForeground(pushNotification);

                } else {
                    Log.d(TAG, "background STATE: " + state);
                    PushNotification pushNotification = new PushNotification();
                    pushNotification.setNotification_title(dataBundle.getString("title"));
                    pushNotification.setBody(dataBundle.getString("body"));
                    pushNotification.setAlertType(dataBundle.getString("alertType"));

                    Log.d(TAG, "push notification: " + pushNotification.getNotification_title() + " : " + pushNotification.getBody() + " : " + pushNotification.getAlertType());


                    if (audioHelper == null) {
                        Log.e("FirebaseBroadcastReceiver", "AudioHelper is null");
                        return;
                    } else {
                        handleRingtone(pushNotification.getAlertType());
                    }

                    myFirebaseMessagingService.sendNotification(pushNotification);




                }

//
//                for (String key : dataBundle.keySet()) {
//                    Object value = dataBundle.get(key);
//                    Log.d(TAG, "dataBundle: " + key + " : " + value);
//                }
            }
        }
    }

}
