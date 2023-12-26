
package com.sca.in_telligent.ui.splash;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import butterknife.ButterKnife;
import com.sca.in_telligent.R;
import com.sca.in_telligent.ui.base.BaseActivity;
import com.sca.in_telligent.ui.auth.login.LoginActivity;
import com.sca.in_telligent.ui.intro.IntroActivity;
import com.sca.in_telligent.ui.main.MainActivity;

import java.security.Permission;

import javax.inject.Inject;


public class SplashActivity extends BaseActivity implements SplashMvpView {

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

  }

  @Override
  public void onDestroy() {
    mPresenter.onDetach();
    super.onDestroy();
  }

  @Override
  public void phonePermissionResult(Permission permission) {

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
