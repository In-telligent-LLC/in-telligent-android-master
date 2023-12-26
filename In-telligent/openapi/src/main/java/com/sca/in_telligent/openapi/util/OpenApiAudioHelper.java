package com.sca.in_telligent.openapi.util;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Vibrator;
import com.sca.in_telligent.openapi.R;
import java.io.IOException;

/* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
public class OpenApiAudioHelper implements AudioHelper {
    private final AudioManager mAudioManager;
    private final Context mContext;
    private FlashHelper mFlashHelper;
    private final Vibrator mVibrator;
    private MediaPlayer mediaPlayer;
    Uri ringtoneUri;

    private String getPackage() {
        return "android.resource://com.sca.in_telligent/";
    }

    private OpenApiAudioHelper(Context context, AudioManager audioManager, Vibrator vibrator, FlashHelper flashHelper) {
        this.mContext = context;
        this.mAudioManager = audioManager;
        this.mVibrator = vibrator;
        this.mFlashHelper = flashHelper;
    }

    public static OpenApiAudioHelper newInstance(Context context, AudioManager audioManager, Vibrator vibrator, FlashHelper flashHelper) {
        return new OpenApiAudioHelper(context, audioManager, vibrator, flashHelper);
    }

    public void startFlash() {
        this.mFlashHelper.startFlashTask();
    }

    public void stopFlash() {
        this.mFlashHelper.stopFlashTask();
    }

    @Override // com.sca.in_telligent.openapi.util.AudioHelper
    public void startLifeSafetyRingtone() {
        this.ringtoneUri = Uri.parse(getPackage() + R.raw.alarm);
        startRingtone();
    }

    @Override // com.sca.in_telligent.openapi.util.AudioHelper
    public void startCriticalRingtone() {
        this.ringtoneUri = Uri.parse(getPackage() + R.raw.critical);
        startRingtone();
    }

    @Override // com.sca.in_telligent.openapi.util.AudioHelper
    public void startVoipRingtone() {
        this.ringtoneUri = Uri.parse(getPackage() + R.raw.voip);
        startRingtone();
    }

    @Override // com.sca.in_telligent.openapi.util.AudioHelper
    public void startPingRingtone() {
        this.ringtoneUri = Uri.parse(getPackage() + R.raw.ping);
        startRingtone();
    }

    @Override // com.sca.in_telligent.openapi.util.AudioHelper
    public void startWeatherRingtone() {
        this.ringtoneUri = Uri.parse(getPackage() + R.raw.weather);
        startRingtone();
    }

    @Override // com.sca.in_telligent.openapi.util.AudioHelper
    public void startUrgentRingtone() {
        this.ringtoneUri = Uri.parse(getPackage() + R.raw.personal_community_urgent);
        startRingtone();
    }

    @Override // com.sca.in_telligent.openapi.util.AudioHelper
    public void startEmergencyRingtone() {
        this.ringtoneUri = Uri.parse(getPackage() + R.raw.personal_community_emergency);
        startRingtone();
    }

    @Override // com.sca.in_telligent.openapi.util.AudioHelper
    public void startLightningRingtone() {
        this.ringtoneUri = Uri.parse(getPackage() + R.raw.lightning_sound);
        startRingtone();
    }

    private void startRingtone() {
        MediaPlayer mediaPlayer = this.mediaPlayer;
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
        this.mAudioManager.setStreamVolume(4, this.mAudioManager.getStreamMaxVolume(4), 8);
        try {
            MediaPlayer mediaPlayer2 = new MediaPlayer();
            this.mediaPlayer = mediaPlayer2;
            mediaPlayer2.setDataSource(this.mContext, this.ringtoneUri);
            this.mediaPlayer.setLooping(true);
            this.mediaPlayer.setAudioStreamType(4);
            this.mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() { // from class: com.sca.in_telligent.openapi.util.OpenApiAudioHelper.1
                @Override // android.media.MediaPlayer.OnPreparedListener
                public void onPrepared(MediaPlayer mediaPlayer3) {
                    OpenApiAudioHelper.this.mediaPlayer.start();
                }
            });
            this.mediaPlayer.prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.mVibrator.vibrate(new long[]{0, 1000, 1000}, 0);
        startFlash();
    }

    @Override // com.sca.in_telligent.openapi.util.AudioHelper
    public void stopRingtone() {
        MediaPlayer mediaPlayer = this.mediaPlayer;
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
        Vibrator vibrator = this.mVibrator;
        if (vibrator != null) {
            vibrator.cancel();
        }
        stopFlash();
    }
}
