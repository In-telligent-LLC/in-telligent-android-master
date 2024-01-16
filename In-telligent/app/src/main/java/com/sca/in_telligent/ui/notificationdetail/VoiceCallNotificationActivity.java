package com.sca.in_telligent.ui.notificationdetail;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.sca.in_telligent.R;
import com.sca.in_telligent.openapi.Constants;
import com.sca.in_telligent.openapi.data.network.model.PushNotification;
import com.sca.in_telligent.openapi.util.AudioHelper;
import com.sca.in_telligent.ui.main.MainActivity;
import com.sca.in_telligent.util.CommonUtils;
import java.util.Locale;
import java.util.Random;
import javax.inject.Inject;

/* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
public class VoiceCallNotificationActivity extends AppCompatActivity {
    public static final int ACTIVITY_REQUEST_CODE = 123;
    @Inject
    AudioHelper audioHelper;
    private FloatingActionButton call_accept_view;
    private FloatingActionButton call_cancel_view;
    private Handler handler;
    private boolean initFlag = false;
    private String mostRecentUtteranceID;
    private TextView notification_title;
    private PushNotification pushNotification;
    private TextToSpeech textToSpeech;
    private Runnable timerRunnable;

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getWindow().addFlags(2359296);
        setContentView(R.layout.activity_incoming_call_popup);
        this.pushNotification = (PushNotification) getIntent().getSerializableExtra("notification");
        init();
        this.textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() { // from class: com.sca.in_telligent.ui.notificationdetail.VoiceCallNotificationActivity.1
            @Override // android.speech.tts.TextToSpeech.OnInitListener
            public void onInit(int i) {
                if (VoiceCallNotificationActivity.this.pushNotification.getNotification_lang() == null || VoiceCallNotificationActivity.this.pushNotification.getNotification_lang().matches("")) {
                    VoiceCallNotificationActivity.this.textToSpeech.setLanguage(Locale.ENGLISH);
                } else {
                    VoiceCallNotificationActivity.this.textToSpeech.setLanguage(new Locale(VoiceCallNotificationActivity.this.pushNotification.getNotification_lang()));
                }
                VoiceCallNotificationActivity.this.textToSpeech.setSpeechRate(0.9f);
                if (i == 0) {
                    VoiceCallNotificationActivity.this.initFlag = true;
                    VoiceCallNotificationActivity.this.ttsInitialized();
                    if (VoiceCallNotificationActivity.this.getIntent().getBooleanExtra("accept", false)) {
                        VoiceCallNotificationActivity.this.acceptCall();
                    }
                }
            }
        });
    }

    private void init() {
//        DaggerActivityComponent.builder().activityModule(new ActivityModule(this)).applicationComponent(((ScaApplication) getApplication()).getComponent()).build().inject(this);
        this.notification_title = (TextView) findViewById(R.id.notification_title_text);
        this.call_cancel_view = (FloatingActionButton) findViewById(R.id.incoming_call_reject_call);
        this.call_accept_view = (FloatingActionButton) findViewById(R.id.incoming_call_accept_call);
        this.notification_title.setText(this.pushNotification.getBuilding_name());
        this.handler = new Handler();
        Runnable runnable = new Runnable() { // from class: com.sca.in_telligent.ui.notificationdetail.VoiceCallNotificationActivity.2
            @Override // java.lang.Runnable
            public void run() {
                VoiceCallNotificationActivity.this.autoRejectCall();
            }
        };
        this.timerRunnable = runnable;
        this.handler.postDelayed(runnable, Constants.AUTO_REJECT_TIME);
        this.call_accept_view.setOnClickListener(new View.OnClickListener() { // from class: com.sca.in_telligent.ui.notificationdetail.VoiceCallNotificationActivity.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (VoiceCallNotificationActivity.this.initFlag) {
                    new Handler().postDelayed(new Runnable() { // from class: com.sca.in_telligent.ui.notificationdetail.VoiceCallNotificationActivity.3.1
                        @Override // java.lang.Runnable
                        public void run() {
                            VoiceCallNotificationActivity.this.acceptCall();
                        }
                    }, 1000L);
                } else {
                    VoiceCallNotificationActivity.this.getIntent().putExtra("accept", true);
                }
            }
        });
        this.call_cancel_view.setOnClickListener(new View.OnClickListener() { // from class: com.sca.in_telligent.ui.notificationdetail.VoiceCallNotificationActivity.4
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                VoiceCallNotificationActivity.this.handler.removeCallbacks(VoiceCallNotificationActivity.this.timerRunnable);
                if (!VoiceCallNotificationActivity.this.getIntent().getBooleanExtra("accept", false)) {
                    VoiceCallNotificationActivity.this.rejectCall();
                    return;
                }
                VoiceCallNotificationActivity voiceCallNotificationActivity = VoiceCallNotificationActivity.this;
                voiceCallNotificationActivity.acceptNavigationToScreen(voiceCallNotificationActivity.pushNotification);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public void acceptCall() {
        getIntent().putExtra("accept", true);
        this.handler.removeCallbacks(this.timerRunnable);
        this.audioHelper.stopRingtone();
        setToSpeak(this.pushNotification.getNotification_title() + ".", this.pushNotification.getMessage_suffix());
//        this.call_accept_view.setVisibility(View.GONE);
        findViewById(R.id.incoming_call_accept_call).setVisibility(View.GONE);
        CommonUtils.clearNotification(this, Integer.parseInt(this.pushNotification.getNotificationId()));
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public void autoRejectCall() {
        if (getIntent().getBooleanExtra("isAppFourGround", false)) {
            startActivity(getMainActivityIntent("fourGround"));
        } else {
            PendingIntent activity = PendingIntent.getActivity(this, Integer.valueOf(this.pushNotification.getNotificationId()).intValue(), getMainActivityIntent("background"), PendingIntent.FLAG_UPDATE_CURRENT);
            PushNotification pushNotification = this.pushNotification;
            CommonUtils.createNotification(this, pushNotification, activity, false, pushNotification.getTitle(), this.pushNotification.getBody(), true);
        }
        finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void acceptNavigationToScreen(PushNotification pushNotification) {
        this.audioHelper.stopRingtone();
        startActivity(getMainActivityIntent("background"));
        finish();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @SuppressLint("WrongConstant")
    private Intent getMainActivityIntent(String str) {
        Intent intent = new Intent((Context) this, (Class<?>) MainActivity.class);
        intent.addFlags(603979776);
        Bundle bundle = new Bundle();
        bundle.putSerializable("pushNotification", this.pushNotification);
        bundle.putString("from", str);
        bundle.putBoolean("show_popup", true);
        bundle.putBoolean("details_screen", true);
        bundle.putInt("buildingId", Integer.valueOf(this.pushNotification.getBuildingId()).intValue());
        bundle.putInt("notificationId", Integer.valueOf(this.pushNotification.getNotificationId()).intValue());
        intent.putExtra("bundle", bundle);
        return intent;
    }

    public void onPause() {
        super.onPause();
    }

    protected void onResume() {
        super.onResume();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void ttsInitialized() {
        this.textToSpeech.setOnUtteranceProgressListener(new UtteranceProgressListener() { // from class: com.sca.in_telligent.ui.notificationdetail.VoiceCallNotificationActivity.5
            @Override // android.speech.tts.UtteranceProgressListener
            public void onError(String str) {
            }

            @Override // android.speech.tts.UtteranceProgressListener
            public void onStart(String str) {
            }

            @Override // android.speech.tts.UtteranceProgressListener
            public void onDone(String str) {
                if (!str.equals(VoiceCallNotificationActivity.this.mostRecentUtteranceID + "done")) {
                    Log.i("XXX", "onDone() blocked: utterance ID mismatch.");
                    return;
                }
                Log.i("XXX", "was onDone() called on a background thread? : " + (Thread.currentThread().getId() != 1));
                Log.i("XXX", "onDone working.");
                VoiceCallNotificationActivity.this.runOnUiThread(new Runnable() { // from class: com.sca.in_telligent.ui.notificationdetail.VoiceCallNotificationActivity.5.1
                    @Override // java.lang.Runnable
                    public void run() {
                        VoiceCallNotificationActivity.this.acceptNavigationToScreen(VoiceCallNotificationActivity.this.pushNotification);
                    }
                });
            }
        });
    }

    private void setToSpeak(String str, String str2) {
        this.mostRecentUtteranceID = (new Random().nextInt() % 9999999) + "";
        Bundle bundle = new Bundle();
        bundle.putString("utteranceId", this.mostRecentUtteranceID);
        this.textToSpeech.speak(str, 0, bundle, this.mostRecentUtteranceID + "");
        this.textToSpeech.speak(str2, 1, bundle, this.mostRecentUtteranceID + "done");
        this.textToSpeech.playSilentUtterance(750L, 1, null);
    }

    protected void onDestroy() {
        TextToSpeech textToSpeech = this.textToSpeech;
        if (textToSpeech != null) {
            textToSpeech.stop();
            this.textToSpeech.shutdown();
        }
        this.handler.removeCallbacks(this.timerRunnable);
        super.onDestroy();
    }

    public void onBackPressed() {
        this.audioHelper.stopRingtone();
        if (getIntent().getBooleanExtra("accept", false)) {
            acceptNavigationToScreen(this.pushNotification);
        } else {
            rejectCall();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void rejectCall() {
        this.audioHelper.stopRingtone();
        startActivity(getMainActivityIntent("background"));
    }
}
