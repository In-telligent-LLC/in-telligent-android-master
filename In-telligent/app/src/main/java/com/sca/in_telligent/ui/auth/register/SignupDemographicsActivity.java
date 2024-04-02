package com.sca.in_telligent.ui.auth.register;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.CallbackManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.tasks.Task;
import com.sca.in_telligent.R;
import com.sca.in_telligent.openapi.data.network.model.CheckEmailResponse;
import com.sca.in_telligent.openapi.data.network.model.FacebookLoginRequest;
import com.sca.in_telligent.openapi.data.network.model.GoogleLoginRequest;
import com.sca.in_telligent.openapi.data.network.model.SignUpRequest;
import com.sca.in_telligent.ui.auth.login.LoginActivity;
import com.sca.in_telligent.ui.auth.logout.LogoutActivity;
import com.sca.in_telligent.ui.base.BaseActivity;
import com.sca.in_telligent.util.CommonUtils;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.annotations.Nullable;

public class SignupDemographicsActivity extends BaseActivity implements SignupDemographicsMvpView,
    OnConnectionFailedListener {

  private static final String TAG = "SignupDemographics";

  @Inject
  SignupDemographicsMvpPresenter<SignupDemographicsMvpView> mPresenter;

  @BindView(R.id.inputName)
  EditText inputName = null;

  @BindView(R.id.inputEmail)
  EditText inputEmail = null;

  @BindView(R.id.btnGoToSignupPassword)
  Button buttonGoToSignupPassword = null;

  @BindView(R.id.btnGoToLogin)
  TextView buttonGoToLogin = null;

  @BindView(R.id.btnLoginFacebookCustom)
  ImageView buttonFacebook = null;

  @BindView(R.id.btnLoginGooglePlus)
  ImageView buttonGooglePlus = null;

  CallbackManager callbackManager;

  private static final int RC_SIGN_IN = 12332;

  public static Intent getStartIntent(Context context) {
    Intent intent = new Intent(context, SignupDemographicsActivity.class);
    return intent;
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_signup_demographics);
    getActivityComponent().inject(this);

    setUnBinder(ButterKnife.bind(this));

    mPresenter.onAttach(SignupDemographicsActivity.this);

    callbackManager = CallbackManager.Factory.create();

    buttonFacebook = findViewById(R.id.btnLoginFacebookCustom);
    buttonFacebook.setOnClickListener(v -> facebookLoginClick(v));

    buttonGooglePlus = findViewById(R.id.btnLoginGooglePlus);
    buttonGooglePlus.setOnClickListener(v -> googleLoginClick(v));

    buttonGoToLogin = findViewById(R.id.btnGoToLogin);
    buttonGoToLogin.setOnClickListener(v -> goToLogin(v));

    buttonGoToSignupPassword = findViewById(R.id.btnGoToSignupPassword);
    buttonGoToSignupPassword.setOnClickListener(v -> signUpPasswordClick(v));

    inputName = findViewById(R.id.inputName);
    inputEmail = findViewById(R.id.inputEmail);


  }

  @OnClick(R.id.btnGoToSignupPassword)
  void signUpPasswordClick(View v) {
    if (!isNetworkConnected()) {
      showNetworkDialog();
    } else {
      String email = inputEmail.getText().toString();
      if (email.length() > 0) {
        mPresenter.checkEmailExists(email);
      }
    }
    hideKeyboard();
  }

  @Override
  public void proceedWithSignup(CheckEmailResponse checkEmailResponse) {
    if (checkEmailResponse.isValid() && inputEmail.length() > 0 && inputName.length() > 0) {
      SignUpRequest signUpRequest = new SignUpRequest();
      signUpRequest.setEmail(inputEmail.getText().toString());
      signUpRequest.setName(inputName.getText().toString());
      startActivityWithDeeplink(
          SignupPasswordActivity.getStartIntent(this).putExtra("signUpRequest", signUpRequest));
    }
  }

  @Override
  public void onFacebookConnected(LoginResult loginResult) {
    FacebookLoginRequest facebookLoginRequest = new FacebookLoginRequest();
    facebookLoginRequest.setFacebookAccessToken(loginResult.getAccessToken().getToken());
    facebookLoginRequest.setDeviceId(CommonUtils.getDeviceId(this));
    mPresenter.authFacebook(facebookLoginRequest);
  }

  @Override
  public void goToLogout() {
    startActivity(LogoutActivity.getStartIntent(this));
  }

  @OnClick(R.id.btnLoginFacebookCustom)
  void facebookLoginClick(View v) {
    mPresenter.loginFacebook(callbackManager);
  }

  @OnClick(R.id.btnLoginGooglePlus)
  void googleLoginClick(View v) {
    mPresenter.loginGoogle(RC_SIGN_IN);
  }

  @OnClick(R.id.btnGoToLogin)
  void goToLogin(View v) {
    startActivityWithDeeplink(LoginActivity.getStartIntent(this));
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (requestCode == RC_SIGN_IN) {
      // The Task returned from this call is always completed, no need to attach
      // a listener.
      Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
      try {
        GoogleSignInAccount account = task.getResult(ApiException.class);
        GoogleLoginRequest googleLoginRequest = new GoogleLoginRequest();
        googleLoginRequest.setDeviceId(CommonUtils.getDeviceId(this));
        googleLoginRequest.setGoogleAccessToken(account.getIdToken());
        mPresenter.authGoogle(googleLoginRequest);
      } catch (ApiException e) {
        Log.d(TAG, "Google Login: result unsuccessful");
        e.printStackTrace();
      }
    } else {
      callbackManager.onActivityResult(requestCode, resultCode, data);
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
  public void phonePermissionResult(boolean permission) {

  }


  @Override
  public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    Log.d(TAG, "Google Login: onConnectionFailed: " + connectionResult.getErrorMessage());
  }
}
