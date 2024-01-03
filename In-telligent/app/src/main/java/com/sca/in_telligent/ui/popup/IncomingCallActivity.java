package com.sca.in_telligent.ui.popup;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.view.WindowManager;
import android.widget.Chronometer;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import com.sca.in_telligent.R;
import com.sca.in_telligent.ui.base.BaseActivity;
import com.sca.in_telligent.openapi.util.AudioHelper;
import com.sca.in_telligent.util.twilio.AppTwilioUtil.TwilioUtilListener;
import com.sca.in_telligent.util.twilio.TwilioUtil;
import com.twilio.voice.Call;

import java.security.Permission;
import java.util.Timer;
import java.util.TimerTask;
import javax.inject.Inject;

public class IncomingCallActivity extends BaseActivity implements TwilioUtilListener,
    IncomingCallMvpView {

  @Inject
  IncomingCallMvpPresenter<IncomingCallMvpView> mPresenter;

  @BindView(R.id.incoming_call_accept_call)
  ConstraintLayout acceptCall;

  @BindView(R.id.incoming_call_reject_call)
  ConstraintLayout rejectCall;

  @BindView(R.id.incoming_call_center_text)
  TextView centerText;

  @BindView(R.id.chronometer)
  Chronometer chronometer;

  private String conferenceId = "";
  private String remoteUserName = "";
  private String uuid = "";

  private boolean answered = false;

  @Inject
  TwilioUtil twilioUtil;

  @Inject
  AudioHelper audioHelper;

  public static Intent getStartIntent(Context context) {
    Intent intent = new Intent(context, IncomingCallActivity.class);
    return intent;
  }

  @Override
  public void onAttachedToWindow() {
    super.onAttachedToWindow();

    getWindow().addFlags(
        WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON |
            WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
            WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON |
            WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
    );
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setContentView(R.layout.activity_incoming_call_popup);

    getActivityComponent().inject(this);

    setUnBinder(ButterKnife.bind(this));

    setUp();
  }

  @Override
  protected void setUp() {
    runTimer();
    Intent i = getIntent();

    conferenceId = i.getExtras().getString("conferenceId");
    remoteUserName = i.getExtras().getString("remoteUserName");
    uuid = i.getExtras().getString("uuid");

    centerText.setText(getString(R.string.incoming_call_from) + " " + remoteUserName);
  }

  @OnClick(R.id.incoming_call_accept_call)
  void acceptClick(View v) {
    mPresenter.requestRecordAudioPermission();
  }

  @OnClick(R.id.incoming_call_reject_call)
  void rejectClick(View v) {
    audioHelper.stopRingtone();
    if (!answered) {
      twilioUtil.rejectCall();
    } else {
      twilioUtil.endCall();
    }
    finish();
  }

  @Override
  public void callDetail(boolean completed) {
    if (!completed) {
      centerText.setText(getString(R.string.call_active));

      twilioUtil.fillFields(null, uuid, remoteUserName, conferenceId);
      twilioUtil.setListener(this);
      twilioUtil.answerCall();

      chronometer.setBase(SystemClock.elapsedRealtime());
      chronometer.start();
    }
  }

  @Override
  public void recordAudioPermissionResult(boolean granted) {
    if (granted) {
      centerText.setText(getString(R.string.connecting));
      answered = true;

      audioHelper.stopRingtone();

      mPresenter.getCallDetail(conferenceId);
    } else {
      finish();
    }

  }

  private void runTimer() {
    Timer timer = new Timer();
    timer.schedule(new TimerTask() {
      @Override
      public void run() {
        if (!answered) {
          audioHelper.stopRingtone();
          finish();
        }
      }
    }, 15 * 1000);
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
  }

  @Override
  public void phonePermissionResult(Permission permission) {

  }

  @Override
  public void phonePermissionResult(boolean permission) {

  }

  @Override
  public void callDidFail() {

  }

  @Override
  public void callDidProgress(Call call) {

  }

  @Override
  public void callDidStart() {
    acceptCall.setActivated(false);
  }

  @Override
  public void callDidEnd() {

  }
}
