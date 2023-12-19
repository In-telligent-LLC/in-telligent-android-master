package com.sca.in_telligent.ui.splash;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import com.sca.in_telligent.R;
import com.sca.in_telligent.ui.auth.login.LoginActivity;
import com.sca.in_telligent.ui.base.BaseActivity;
import com.sca.in_telligent.ui.intro.IntroActivity;
import com.sca.in_telligent.ui.main.MainActivity;

import java.security.Permission;

import javax.inject.Inject;

public class SplashActivity extends BaseActivity implements SplashMvpView {
  private final int SPLASH_DISPLAY_LENGTH = 1000;
  @Inject
  SplashMvpPresenter<SplashMvpView> mPresenter;

  @Override
  protected void setUp() {
  }

  public static Intent getStartIntent(Context context) {
    return new Intent(context, SplashActivity.class);
  }


  @Override // com.sca.in_telligent.ui.base.BaseActivity
  public void onCreate(Bundle bundle) {
    super.onCreate(bundle);
    setContentView(R.layout.activity_splash);
    getActivityComponent().inject(this);
//    setUnBinder(ButterKnife.bind(this));
    new Handler().postDelayed(new Runnable() { // from class: com.sca.in_telligent.ui.splash.SplashActivity.1
      @Override // java.lang.Runnable
      public void run() {
        SplashActivity.this.mPresenter.onAttach(SplashActivity.this);
      }
    }, 1000L);
  }

  @Override // com.sca.in_telligent.ui.base.BaseActivity
  public void onDestroy() {
    this.mPresenter.onDetach();
    super.onDestroy();
  }

  @Override
  public void phonePermissionResult(Permission permission) {

  }

  @Override
  public void phonePermissionResult(boolean permission) {

  }

  @Override // com.sca.in_telligent.ui.splash.SplashMvpView
  public void openLoginActivity() {
    startActivity(LoginActivity.getStartIntent(this));
    finish();
  }

  @Override // com.sca.in_telligent.ui.splash.SplashMvpView
  public void openMainActivity() {
    startActivity(MainActivity.getStartIntent(this));
    finish();
  }

  /* JADX WARN: Multi-variable type inference failed */
  @Override // com.sca.in_telligent.ui.splash.SplashMvpView
  public void openIntroActivity() {
    startActivity(IntroActivity.getStartIntent(this));
    finish();
  }
}
