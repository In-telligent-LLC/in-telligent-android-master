package com.sca.in_telligent.ui.auth.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;


import androidx.annotation.NonNull;

import com.facebook.CallbackManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.tasks.Task;
import com.sca.in_telligent.R;
import com.sca.in_telligent.databinding.ActivityLoginBinding;
import com.sca.in_telligent.openapi.data.network.model.FacebookLoginRequest;
import com.sca.in_telligent.openapi.data.network.model.GoogleLoginRequest;
import com.sca.in_telligent.openapi.data.network.model.LoginRequest;
import com.sca.in_telligent.ui.auth.forgot.ForgotPasswordDialog;
import com.sca.in_telligent.ui.auth.logout.LogoutActivity;
import com.sca.in_telligent.ui.auth.register.SignupDemographicsActivity;
import com.sca.in_telligent.ui.base.BaseActivity;
import com.sca.in_telligent.ui.main.MainActivity;
import com.sca.in_telligent.util.CommonUtils;
import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;


public class LoginActivity extends BaseActivity implements LoginMvpView,
    OnConnectionFailedListener {

  private static final String TAG = "LoginActivity";

  @Inject
  LoginMvpPresenter<LoginMvpView> mPresenter;


  EditText inputUserName = findViewById(R.id.inputUsername);

  EditText inputPasword = findViewById(R.id.inputPassword);

  Button buttonLogin = findViewById(R.id.btnLogin);

//  @BindView(R.id.btnGoToSignup)
//  TextView buttonGoToSignup;
//
//  @BindView(R.id.btnForgotPassword)
//  TextView buttonForgotPassword;
//
  ImageView buttonFacebook = findViewById(R.id.btnLoginFacebookCustom);

//
//  @BindView(R.id.btnLoginGooglePlus)
//  ImageView buttonGooglePlus;

  CallbackManager callbackManager;


  private static final int RC_SIGN_IN = 12332;

  public static Intent getStartIntent(Context context) {
    Intent intent = new Intent(context, LoginActivity.class);
    return intent;
  }
  private ActivityLoginBinding binding;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);

    binding = ActivityLoginBinding.inflate(getLayoutInflater());
    View view = binding.getRoot();
    setContentView(view);


    getActivityComponent().inject(this);

    setUnBinder(ButterKnife.bind(this));

    mPresenter.onAttach(LoginActivity.this);

    callbackManager = CallbackManager.Factory.create();

    hideKeyboard();

    setUp();
  }



  void loginWithPassword(View v) {
    if (!isNetworkConnected()) {
      showNetworkDialog();
    } else {
      if (inputUserName.getText().toString().length() > 3
          && inputPasword.getText().toString().length() > 3) {
        String android_id = CommonUtils.getDeviceId(this);
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setDeviceId(android_id);
        loginRequest.setEmail(inputUserName.getText().toString().trim());
        loginRequest.setPassword(inputPasword.getText().toString().trim());
        mPresenter.loginWithPassword(loginRequest);
      }
    }
    hideKeyboard();
  }


//  binding.buttonFacebook.setOnClickListener(new View.OnClickListener(){
//    onClick
//  })

  void facebookLoginClick(View v) {
    if (!isNetworkConnected()) {
      showNetworkDialog();
    } else {
      mPresenter.loginFacebook(callbackManager);
    }
  }

//  @OnClick(R.id.btnLoginGooglePlus)
  void googleLoginClick(View v) {
    if (!isNetworkConnected()) {
      showNetworkDialog();
    } else {
      mPresenter.loginGoogle(RC_SIGN_IN);
    }
  }

//  @OnClick(R.id.btnGoToSignup)
  void goToSignup(View v) {
    startActivityWithDeeplink(SignupDemographicsActivity.getStartIntent(this));
  }

//  @OnClick(R.id.btnForgotPassword)
  void forgotPassword(View v) {
    showForgotPasswordDialog();
  }

  @Override
  public void openMainActivity() {
    Intent intent = MainActivity.getStartIntent(LoginActivity.this);
    startActivity(intent);
    finish();
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

  @Override
  protected void onDestroy() {
    mPresenter.onDetach();
    super.onDestroy();
  }

  @Override
  protected void setUp() {
    inputUserName.setText("");
    inputPasword.setText("");
  }

  @Override
  protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
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

  public void showForgotPasswordDialog() {
    ForgotPasswordDialog.newInstance().show(getSupportFragmentManager());
  }

  @Override
  public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    Log.d(TAG, "Google Login: onConnectionFailed: " + connectionResult.getErrorMessage());
  }
}