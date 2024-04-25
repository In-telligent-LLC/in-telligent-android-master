package com.sca.in_telligent.ui.popup;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.sca.in_telligent.R;
import com.sca.in_telligent.ui.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LifeSafetyPopupActivity extends BaseActivity {

  @BindView(R.id.life_safety_alert_title)
  TextView titleText;

  @BindView(R.id.life_safety_alert_body)
  TextView bodyText;

  @BindView(R.id.life_safety_alert_button_ok)
  Button buttonOk;

  @BindView(R.id.life_safety_main)
  ConstraintLayout lifeSafetyMain;

  @Override
  public void onAttachedToWindow() {
    super.onAttachedToWindow();

    getWindow().addFlags(
        WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON |
            WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
            WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON |
            WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
    );

//    WindowManager.LayoutParams windowManager = getWindow().getAttributes();
//    windowManager.alpha = 0.75f;
//    getWindow().setAttributes(windowManager);
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setContentView(R.layout.activity_life_safety_popup);

    getActivityComponent().inject(this);

    setUnBinder(ButterKnife.bind(this));

//    lifeSafetyMain.getBackground().setAlpha(0);

    setUp();
  }


  @Override
  public void phonePermissionResult(boolean permission) {

  }

  @Override
  protected void setUp() {
    Intent i = getIntent();

    String title = i.getStringExtra("title");
    String body = i.getStringExtra("body");

    titleText.setText(title);
    bodyText.setText(body);
  }

  @OnClick(R.id.life_safety_alert_button_ok)
  void okClick(View v) {
    getAudioHelper().stopRingtone();
    finish();
  }
}
