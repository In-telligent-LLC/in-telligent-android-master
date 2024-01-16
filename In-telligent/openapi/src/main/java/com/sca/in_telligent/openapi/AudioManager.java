package com.sca.in_telligent.openapi;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Vibrator;
import android.util.Log;

import com.openapi.R;

import java.io.IOException;


public class AudioManager {
    private final Context context;
    private MediaPlayer mediaPlayer;
    private Vibrator vibrator;

    public AudioManager(Context context) {
        this.context = context;
    }

    @SuppressLint("MissingPermission")
    void startRingtone(Ringtone type, BooleanCallback callback) {

        int ringtoneId;
        switch (type) {
            case LIFE_SAFETY:
                ringtoneId = R.raw.alarm;
                break;
            case CRITICAL:
                ringtoneId = R.raw.critical;
                break;
            case VOIP:
                ringtoneId = R.raw.voip;
                break;
            case PING:
                ringtoneId = R.raw.ping;
                break;
            case WEATHER:
                ringtoneId = R.raw.weather;
                break;
            case LIGHTNING:
                ringtoneId = R.raw.lightning_sound;
                break;
            case URGENT:
                ringtoneId = R.raw.personal_community_urgent;
                break;
            case EMERGENCY:
                ringtoneId = R.raw.personal_community_emergency;
                break;
            default:
                return;
        }

        Uri ringtoneUri = Uri.parse("android.resource://" + context.getPackageName() + "/" + ringtoneId);

        Log.d("SinchWrapper", "Start Ringtone");

        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }


        android.media.AudioManager audioManager = (android.media.AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        int volume = audioManager.getStreamVolume(android.media.AudioManager.STREAM_ALARM);
        Log.d("GcmIntentService", "volume was: " + volume);
        if (volume == 0)
            volume = audioManager.getStreamMaxVolume(android.media.AudioManager.STREAM_ALARM);
        audioManager.setStreamVolume(android.media.AudioManager.STREAM_ALARM, volume, android.media.AudioManager.FLAG_REMOVE_SOUND_AND_VIBRATE);

        try {
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setDataSource(context, ringtoneUri);
            mediaPlayer.setLooping(true);
            mediaPlayer.setAudioStreamType(android.media.AudioManager.STREAM_ALARM);
            mediaPlayer.setOnPreparedListener(mp -> mediaPlayer.start());
            mediaPlayer.prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
            callback.callback(false);
            return;
        }

        if (vibrator == null) {
            vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        }
        vibrator.vibrate(new long[]{0, 1000, 1000}, 0);

        callback.callback(true);
    }

    void stopRingtone() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer = null;
        }


        if (vibrator != null) {
            vibrator.cancel();
            vibrator = null;
        }
    }

}
