package com.in_telligent.openapi;

import android.content.Context;
import android.os.Bundle;

/**
 * Created by zacharyzeno on 3/15/18.
 */

public class OpenAPI {

    private static Context context;
    private static AudioManager audioManager;

    public static void init(Context context) {
        OpenAPI.context = context;
        IntelligentNetworking.initialize(context);
    }

    private static void checkInitialized() {
        if(context == null) {
            throw new IllegalStateException("You must initialize OpenAPI before using any methods");
        }
    }

    private static AudioManager getAudioManager() {
        checkInitialized();

        if(audioManager == null) {
            return audioManager = new AudioManager(context);
        }

        return audioManager;
    }


    public static void reportPushToken(String partnerToken, String token, BooleanCallback callback) {
        checkInitialized();

        IntelligentNetworking.post("api/partner/reportPushToken")


        callback.callback(true);
    }

    public static boolean relayPushNotification(String partnerToken, Bundle notification) {
        checkInitialized();


        return false;
    }

    public static void startLifeSafetyNoise(Ringtone ringtone, BooleanCallback callback) {

        getAudioManager().startRingtone(ringtone, callback);
    }

    public static void stopLifeSafetyNoise() {

        getAudioManager().stopRingtone();
    }

}
