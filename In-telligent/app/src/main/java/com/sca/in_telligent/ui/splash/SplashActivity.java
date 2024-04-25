
package com.sca.in_telligent.ui.splash;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.sca.in_telligent.R;
import com.sca.in_telligent.ui.auth.login.LoginActivity;
import com.sca.in_telligent.ui.base.BaseActivity;
import com.sca.in_telligent.ui.intro.IntroActivity;
import com.sca.in_telligent.ui.main.MainActivity;

import javax.inject.Inject;

import butterknife.ButterKnife;
import io.reactivex.rxjava3.annotations.NonNull;


public class SplashActivity extends BaseActivity implements SplashMvpView {


  private static final String TAG = "";
  @Inject
  SplashMvpPresenter<SplashMvpView> mPresenter;

  private final int SPLASH_DISPLAY_LENGTH = 1000;

  public static Intent getStartIntent(Context context) {
    Intent intent = new Intent(context, SplashActivity.class);
    return intent;
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setContentView(R.layout.activity_splash);

    getActivityComponent().inject(this);

    setUnBinder(ButterKnife.bind(this));

    new Handler().postDelayed(() -> mPresenter.onAttach(SplashActivity.this), SPLASH_DISPLAY_LENGTH);

  }

  @Override
  protected void setUp() {
    FirebaseMessaging.getInstance().getToken()
            .addOnCompleteListener(new OnCompleteListener<String>() {
              @Override
              public void onComplete(@NonNull Task<String> task) {
                if (!task.isSuccessful()) {
                  Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                  return;
                }

                // Get new FCM registration token
                String token = task.getResult();

                // Log and toast
                String msg = getString(R.string.fcm_fallback_notification_channel_label, token);
                Log.d(TAG, msg);
                Toast.makeText(SplashActivity.this, msg, Toast.LENGTH_SHORT).show();
              }
            });

  }

  @Override
  public void onDestroy() {
    mPresenter.onDetach();
    super.onDestroy();
  }


  @Override
  public void phonePermissionResult(boolean permission) {

  }


  @Override
  public void openLoginActivity() {
    Intent intent = LoginActivity.getStartIntent(SplashActivity.this);
    startActivity(intent);
    finish();
  }

  @Override
  public void openMainActivity() {
    Intent intent = MainActivity.getStartIntent(SplashActivity.this);
    startActivity(intent);
    finish();
  }

  @Override
  public void openIntroActivity() {
    Intent intent = IntroActivity.getStartIntent(SplashActivity.this);
    startActivity(intent);
    finish();
  }
}
