package com.sca.in_telligent.openapi;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Vibrator;
import android.util.Log;
import java.io.IOException;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
public class AudioManager {
    private Context context;
    private MediaPlayer mediaPlayer;
    private Vibrator vibrator;

    /* JADX INFO: Access modifiers changed from: package-private */
    public AudioManager(Context context) {
        this.context = context;
    }

    /* renamed from: com.sca.in_telligent.openapi.AudioManager$2  reason: invalid class name */
    /* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
    static /* synthetic */ class AnonymousClass2 {
        static final /* synthetic */ int[] $SwitchMap$com$sca$in_telligent$openapi$Ringtone;

        static {
            int[] iArr = new int[Ringtone.values().length];
            $SwitchMap$com$sca$in_telligent$openapi$Ringtone = iArr;
            try {
                iArr[Ringtone.LIFE_SAFETY.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$sca$in_telligent$openapi$Ringtone[Ringtone.CRITICAL.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$sca$in_telligent$openapi$Ringtone[Ringtone.VOIP.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$sca$in_telligent$openapi$Ringtone[Ringtone.PING.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$sca$in_telligent$openapi$Ringtone[Ringtone.WEATHER.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$sca$in_telligent$openapi$Ringtone[Ringtone.LIGHTNING.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$com$sca$in_telligent$openapi$Ringtone[Ringtone.URGENT.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$com$sca$in_telligent$openapi$Ringtone[Ringtone.EMERGENCY.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
        }
    }

    void startRingtone(Ringtone ringtone, BooleanCallback booleanCallback) {
        int i;
        switch (AnonymousClass2.$SwitchMap$com$sca$in_telligent$openapi$Ringtone[ringtone.ordinal()]) {
            case 1:
                i = R.raw.alarm;
                break;
            case 2:
                i = R.raw.critical;
                break;
            case 3:
                i = R.raw.voip;
                break;
            case 4:
                i = R.raw.ping;
                break;
            case 5:
                i = R.raw.weather;
                break;
            case 6:
                i = R.raw.lightning_sound;
                break;
            case 7:
                i = R.raw.personal_community_urgent;
                break;
            case 8:
                i = R.raw.personal_community_emergency;
                break;
            default:
                return;
        }
        Uri parse = Uri.parse("android.resource://" + this.context.getPackageName() + "/" + i);
        Log.d("SinchWrapper", "Start Ringtone");
        MediaPlayer mediaPlayer = this.mediaPlayer;
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
        android.media.AudioManager audioManager = (android.media.AudioManager) this.context.getSystemService("audio");
        int streamVolume = audioManager.getStreamVolume(4);
        Log.d("GcmIntentService", "volume was: " + streamVolume);
        if (streamVolume == 0) {
            streamVolume = audioManager.getStreamMaxVolume(4);
        }
        audioManager.setStreamVolume(4, streamVolume, 8);
        try {
            MediaPlayer mediaPlayer2 = new MediaPlayer();
            this.mediaPlayer = mediaPlayer2;
            mediaPlayer2.setDataSource(this.context, parse);
            this.mediaPlayer.setLooping(true);
            this.mediaPlayer.setAudioStreamType(4);
            this.mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() { // from class: com.sca.in_telligent.openapi.AudioManager.1
                @Override // android.media.MediaPlayer.OnPreparedListener
                public void onPrepared(MediaPlayer mediaPlayer3) {
                    AudioManager.this.mediaPlayer.start();
                }
            });
            this.mediaPlayer.prepareAsync();
            if (this.vibrator == null) {
                this.vibrator = (Vibrator) this.context.getSystemService("vibrator");
            }
            this.vibrator.vibrate(new long[]{0, 1000, 1000}, 0);
            booleanCallback.callback(true);
        } catch (IOException e) {
            e.printStackTrace();
            booleanCallback.callback(false);
        }
    }

    void stopRingtone() {
        MediaPlayer mediaPlayer = this.mediaPlayer;
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            this.mediaPlayer = null;
        }
        Vibrator vibrator = this.vibrator;
        if (vibrator != null) {
            vibrator.cancel();
            this.vibrator = null;
        }
    }
}
