package com.sca.in_telligent.openapi.util;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Vibrator;
import android.util.Log;

import com.sca.in_telligent.openapi.R;

import java.io.IOException;


public class OpenApiAudioHelper implements AudioHelper {

    private final AudioManager mAudioManager;
    private final Vibrator mVibrator;
    private MediaPlayer mediaPlayer;
    private final Context mContext;
    private FlashHelper mFlashHelper;
    Uri ringtoneUri;


    private OpenApiAudioHelper(Context context, AudioManager audioManager,
                               Vibrator vibrator, FlashHelper flashHelper) {
        mContext = context;
        mAudioManager = audioManager;
        mVibrator = vibrator;
        mFlashHelper = flashHelper;
    }

    public static OpenApiAudioHelper newInstance(Context context, AudioManager audioManager,
                                                 Vibrator vibrator, FlashHelper flashHelper) {
        return new OpenApiAudioHelper(context, audioManager, vibrator, flashHelper);
    }

    public void startFlash() {
        mFlashHelper.startFlashTask();
    }

    public void stopFlash() {
        mFlashHelper.stopFlashTask();
    }

    @Override
    public void startLifeSafetyRingtone() {
        ringtoneUri = Uri.parse("android.resource://com.sca.in_telligent/" + R.raw.alarm);
        startRingtone();
    }

    @Override
    public void startCriticalRingtone() {
        ringtoneUri = Uri.parse("android.resource://com.sca.in_telligent/" + R.raw.critical);
        startRingtone();
    }

    @Override
    public void startVoipRingtone() {
        ringtoneUri = Uri.parse("android.resource://com.sca.in_telligent/" + R.raw.voip);
        startRingtone();
    }

    @Override
    public void startPingRingtone() {
        ringtoneUri = Uri.parse("android.resource://com.sca.in_telligent/" + R.raw.ping);
        startRingtone();
    }

    @Override
    public void startWeatherRingtone() {
        ringtoneUri = Uri.parse("android.resource://com.sca.in_telligent/" + R.raw.weather);
        startRingtone();
    }

    @Override
    public void startUrgentRingtone() {
        ringtoneUri = Uri
                .parse("android.resource://com.sca.in_telligent/" + R.raw.personal_community_urgent);
        startRingtone();
    }

    @Override
    public void startEmergencyRingtone() {
        ringtoneUri = Uri
                .parse("android.resource://com.sca.in_telligent/" + R.raw.personal_community_emergency);
        startRingtone();
    }

    @Override
    public void startLightningRingtone() {
        ringtoneUri = Uri.parse("android.resource://com.sca.in_telligent/" + R.raw.lightning_sound);
        startRingtone();
    }

    private void startRingtone() {

        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
        int volume = mAudioManager.getStreamVolume(AudioManager.STREAM_ALARM);
        Log.d("OpenApiAudioHelper: ", "volume was: " + volume);
        if (volume == 0) {
            volume = mAudioManager.getStreamMaxVolume(AudioManager.STREAM_ALARM);
        }
        mAudioManager.setStreamVolume(AudioManager.STREAM_ALARM, volume,
                AudioManager.FLAG_REMOVE_SOUND_AND_VIBRATE);

        try {
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setDataSource(mContext, ringtoneUri);
            mediaPlayer.setLooping(true);
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_ALARM);
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mediaPlayer.start();
                }
            });
            mediaPlayer.prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
        }

        mVibrator.vibrate(new long[]{0, 1000, 1000}, 0);
        startFlash();
    }

    public void stopRingtone() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }

        if (mVibrator != null) {
            mVibrator.cancel();
        }

        stopFlash();
    }
}
