package com.sca.in_telligent.ui.auth.reset;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.rxjava3.annotations.Nullable;

import com.sca.in_telligent.R;
import com.sca.in_telligent.openapi.data.network.model.ResetPasswordRequest;
import com.sca.in_telligent.ui.auth.logout.LogoutActivity;
import com.sca.in_telligent.ui.base.BaseActivity;
import com.sca.in_telligent.ui.main.MainActivity;
import com.sca.in_telligent.util.CommonUtils;

import java.security.Permission;

import javax.inject.Inject;

public class ResetPasswordActivity extends BaseActivity implements ResetPasswordMvpView {

  @Inject
  ResetPasswordMvpPresenter<ResetPasswordMvpView> mPresenter;

  @BindView(R.id.buttonResetPasswordSubmit)
  Button resetPasswordSubmit;

  @BindView(R.id.editTextEnterPasswordFirst)
  EditText passwordEdittext;

  @BindView(R.id.editTextEnterPasswordSecond)
  EditText passwordConfirmEdittext;

  public static Intent getStartIntent(Context context) {
    Intent intent = new Intent(context, ResetPasswordActivity.class);
    return intent;
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_reset_password);
    getActivityComponent().inject(this);

    resetPasswordSubmit = findViewById(R.id.buttonResetPasswordSubmit);
    passwordEdittext = findViewById(R.id.editTextEnterPasswordFirst);
    passwordConfirmEdittext = findViewById(R.id.editTextEnterPasswordSecond);

    resetPasswordSubmit.setOnClickListener(v -> resetPassword(v));

    setUnBinder(ButterKnife.bind(this));

    mPresenter.onAttach(ResetPasswordActivity.this);

  }

  @OnClick(R.id.buttonResetPasswordSubmit)
  void resetPassword(View v) {
    String password = passwordEdittext.getText().toString();
    String passwordConfirm = passwordConfirmEdittext.getText().toString();

    if (!password.isEmpty() && !passwordConfirm.isEmpty() && password.equals(passwordConfirm)) {
      ResetPasswordRequest resetPasswordRequest = new ResetPasswordRequest();
      resetPasswordRequest.setDeviceId(CommonUtils.getDeviceId(this));
      resetPasswordRequest.setPassword(passwordEdittext.getText().toString());
      resetPasswordRequest.setPassword2(passwordConfirmEdittext.getText().toString());
      resetPasswordRequest.setResetCode(getIntent().getData().getLastPathSegment());
      mPresenter.submit(resetPasswordRequest);
    }
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
  public void passwordChanged() {
    Intent intent = MainActivity.getStartIntent(ResetPasswordActivity.this);
    startActivity(intent);
    finish();
  }


  @Override
  public void goToLogout() {
    startActivity(LogoutActivity.getStartIntent(this));
  }
}
