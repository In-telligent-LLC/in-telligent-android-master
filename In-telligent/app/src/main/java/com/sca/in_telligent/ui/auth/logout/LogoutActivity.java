package com.sca.in_telligent.ui.auth.logout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.sca.in_telligent.R;
import com.sca.in_telligent.ui.auth.login.LoginActivity;
import com.sca.in_telligent.ui.auth.register.SignupDemographicsActivity;
import com.sca.in_telligent.ui.base.BaseActivity;
import javax.inject.Inject;

public class LogoutActivity extends BaseActivity implements LogoutMvpView {

  @Inject
  LogoutMvpPresenter<LogoutMvpView> mPresenter;

  @BindView(R.id.testLogoutButton)
  Button logoutButton;

  public static Intent getStartIntent(Context context) {
    Intent intent = new Intent(context, LogoutActivity.class);
    return intent;
  }

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_logout);
    getActivityComponent().inject(this);

    setUnBinder(ButterKnife.bind(this));

    mPresenter.onAttach(LogoutActivity.this);

  }

  @OnClick(R.id.testLogoutButton)
  void logOut(View v) {
    mPresenter.logOut();
  }

  @Override
  protected void setUp() {

  }

  @Override
  public void goToLogin() {
    startActivityWithDeeplink(LoginActivity.getStartIntent(this));
  }
}
