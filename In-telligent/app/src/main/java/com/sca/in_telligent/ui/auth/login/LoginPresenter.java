
package com.sca.in_telligent.ui.auth.login;


import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.sca.in_telligent.R;
import com.sca.in_telligent.data.DataManager;
import com.sca.in_telligent.data.DataManager.LoggedInMode;
import com.sca.in_telligent.openapi.data.network.error.RetrofitException;
import com.sca.in_telligent.openapi.data.network.model.FacebookLoginRequest;
import com.sca.in_telligent.openapi.data.network.model.GoogleLoginRequest;
import com.sca.in_telligent.openapi.data.network.model.LoginRequest;
import com.sca.in_telligent.openapi.data.network.model.LoginResponse;
import com.sca.in_telligent.openapi.data.network.model.PushTokenRequest;
import com.sca.in_telligent.openapi.data.network.model.PushTokenSuccessResponse;
import com.sca.in_telligent.ui.base.BasePresenter;
import com.sca.in_telligent.util.rx.SchedulerProvider;

import java.util.Arrays;
import javax.inject.Inject;

import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.functions.Consumer;


public class LoginPresenter<V extends LoginMvpView> extends BasePresenter<V>
    implements LoginMvpPresenter<V> {

  private static final String TAG = "LoginPresenter";

  @Inject
  public LoginPresenter(DataManager dataManager,
      SchedulerProvider schedulerProvider,
      CompositeDisposable compositeDisposable) {
    super(dataManager, schedulerProvider, compositeDisposable);
  }

  @Override
  public void cleanAppData() {
    getDataManager().setUserAsLoggedOut();
  }

  @Override
  public void loginWithPassword(LoginRequest loginRequest) {
    getMvpView().showLoading();
    getCompositeDisposable().add(
        getDataManager().loginWithPassword(loginRequest).subscribeOn(getSchedulerProvider().io())
            .observeOn(getSchedulerProvider().ui()).subscribe(loginResponse -> {
                Log.d("Loginresponse:", loginResponse.toString());
              getMvpView().hideLoading();
              if (loginResponse.isSuccess()) {
                getDataManager()
                    .updateUserInfo(loginResponse.getToken(), LoggedInMode.LOGGED_IN_MODE_LOGGED_IN);
                sendRegistrationToServer();
              } else {
                getMvpView().showMessage(loginResponse.getError());
              }

            }, throwable -> {
              getMvpView().hideLoading();
              RetrofitException retrofitException = (RetrofitException) throwable;
              Log.e(TAG, "loginWithPassword: " + retrofitException.getKind());
            }));
  }

  @Override
  public void loginFacebook(CallbackManager callbackManager) {

    LoginManager.getInstance()
        .registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
          @Override
          public void onSuccess(LoginResult loginResult) {
            getMvpView().onFacebookConnected(loginResult);
          }

          @Override
          public void onCancel() {
            Log.d(TAG, "FACEBOOK: Login attempt canceled.");
          }

          @Override
          public void onError(FacebookException e) {
            Log.d(TAG, "FACEBOOK: Login attempt failed.");
          }
        });

    LoginManager.getInstance()
        .logInWithReadPermissions((Activity) getMvpView(),
            Arrays.asList("public_profile", "email"));
  }

  @Override
  public void authFacebook(FacebookLoginRequest facebookLoginRequest) {

    getMvpView().showLoading();
    getCompositeDisposable().add(getDataManager().loginFacebook(facebookLoginRequest)
        .subscribeOn(getSchedulerProvider().io()).
            observeOn(getSchedulerProvider().ui()).subscribe(loginResponse -> {
              getMvpView().hideLoading();
              getDataManager()
                  .updateUserInfo(loginResponse.getToken(), LoggedInMode.LOGGED_IN_MODE_LOGGED_IN);
              getMvpView().openMainActivity();
            }, throwable -> getMvpView().hideLoading()));
  }

  @Override
  public void loginGoogle(int rc_sign) {

    GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken(((Activity) getMvpView()).getString(R.string.server_client_id))
        .requestEmail()
        .build();

    GoogleApiClient mGoogleApiClient = new GoogleApiClient.Builder((Activity) getMvpView())
        .enableAutoManage((AppCompatActivity) getMvpView(),
            ((GoogleApiClient.OnConnectionFailedListener) (Activity) getMvpView()))
        .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
        .build();

    Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
    ((Activity) getMvpView()).startActivityForResult(signInIntent, rc_sign);
  }

  @Override
  public void authGoogle(GoogleLoginRequest googleLoginRequest) {
    getMvpView().showLoading();
    getCompositeDisposable().add(getDataManager().loginGoogle(googleLoginRequest).
        subscribeOn(getSchedulerProvider().io()).
        observeOn(getSchedulerProvider().ui()).subscribe(new Consumer<LoginResponse>() {
      @Override
      public void accept(LoginResponse loginResponse) throws Exception {
        getMvpView().hideLoading();
        getDataManager()
            .updateUserInfo(loginResponse.getToken(), LoggedInMode.LOGGED_IN_MODE_LOGGED_IN);
        getMvpView().openMainActivity();
      }
    }, throwable -> getMvpView().hideLoading()));
  }

  private void sendRegistrationToServer() {
    if (getDataManager().getPushToken() != null) {

      PushTokenRequest pushTokenRequest = new PushTokenRequest();
      pushTokenRequest.setEnvironment("fcm");
      pushTokenRequest.setPushToken(getDataManager().getPushToken());
      getCompositeDisposable()
          .add(getDataManager().registerPushToken(pushTokenRequest)
              .subscribeOn(getSchedulerProvider().io())
              .observeOn(getSchedulerProvider().ui())
              .subscribe(successResponse -> {
                if (successResponse.isSuccess()) {
                    getMvpView().openMainActivity();

                }

              }, throwable -> {
                Log.e(TAG, "sendRegistrationToServer: ", throwable);
              }));
    }
  }
}
