package com.sca.in_telligent.ui.popup;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.sca.in_telligent.R;
import com.sca.in_telligent.openapi.data.network.model.AlertOpenedRequest;
import com.sca.in_telligent.ui.base.BaseActivity;
import com.sca.in_telligent.util.AppResponder.ResponderListener;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PersonalSafetyPopupActivity extends BaseActivity implements ResponderListener {

  @BindView(R.id.personal_safety_body)
  TextView bodyText;

  @BindView(R.id.personal_safety_title)
  TextView titleText;

  @BindView(R.id.personal_safety_no_button)
  Button noButton;

  @BindView(R.id.personal_safety_yes_button)
  Button yesButton;

  int notificationId;

  int buildingId;

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

    setContentView(R.layout.activity_personal_safety_popup);

    getActivityComponent().inject(this);

    setUnBinder(ButterKnife.bind(this));

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
    notificationId = i.getIntExtra("notificationId", 0);
    buildingId = i.getIntExtra("buildingId", 0);

    titleText.setText(title);
    bodyText.setText(body);
  }

  @OnClick(R.id.personal_safety_yes_button)
  void yesClick(View v) {
    if (notificationId != 0) {
      openedAlert(notificationId);
      respondToPersonalSafety(notificationId, 1);
    }
    finish();
  }

  @OnClick(R.id.personal_safety_no_button)
  void noClick(View v) {
    if (notificationId != 0) {
      openedAlert(notificationId);
      respondToPersonalSafety(notificationId, -1);
    }
    finish();
  }

  private void openedAlert(int notificationId) {
    AlertOpenedRequest alertOpenedRequest = new AlertOpenedRequest();
    alertOpenedRequest.setNotificationId(notificationId);

    getResponder().setResponderListener(this);
    getResponder().alertOpened(alertOpenedRequest);
  }

  private void respondToPersonalSafety(int notificationId, int response) {
    HashMap<String, Object> map = new HashMap<>();

    map.put("notification_id", notificationId);
    map.put("response", response);

    getResponder().respondToPersonalSafety(map);
  }

  @Override
  public void responderFinished(boolean finished) {

  }
}
