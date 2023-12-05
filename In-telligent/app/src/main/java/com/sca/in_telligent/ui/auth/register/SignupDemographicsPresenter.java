package com.sca.in_telligent.ui.auth.register;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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
import com.sca.in_telligent.openapi.data.network.model.CheckEmailResponse;
import com.sca.in_telligent.openapi.data.network.model.FacebookLoginRequest;
import com.sca.in_telligent.openapi.data.network.model.GoogleLoginRequest;
import com.sca.in_telligent.openapi.data.network.model.LoginResponse;
import com.sca.in_telligent.ui.base.BasePresenter;
import com.sca.in_telligent.util.rx.SchedulerProvider;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import java.util.Arrays;
import javax.inject.Inject;

public class SignupDemographicsPresenter<V extends SignupDemographicsMvpView> extends
    BasePresenter<V> implements SignupDemographicsMvpPresenter<V> {

  private static final String TAG = "SignupDemPresenter";

  @Inject
  public SignupDemographicsPresenter(DataManager dataManager,
      SchedulerProvider schedulerProvider,
      CompositeDisposable compositeDisposable) {
    super(dataManager, schedulerProvider, compositeDisposable);
  }

  @Override
  public void checkEmailExists(String email) {
    getMvpView().showLoading();
    getCompositeDisposable().add(
        getDataManager().checkEmail(email).subscribeOn(getSchedulerProvider().io())
            .observeOn(getSchedulerProvider().ui()).subscribe(new Consumer<CheckEmailResponse>() {
          @Override
          public void accept(CheckEmailResponse checkEmailResponse) throws Exception {
            getMvpView().hideLoading();
            if (!checkEmailResponse.isValid()) {
              getMvpView().showMessage(R.string.this_email_is_already_registered);
            } else {
              getMvpView().proceedWithSignup(checkEmailResponse);
            }
          }
        }, new Consumer<Throwable>() {
          @Override
          public void accept(Throwable throwable) throws Exception {
            getMvpView().hideLoading();
            RetrofitException retrofitException = (RetrofitException) throwable;
          }
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
            observeOn(getSchedulerProvider().ui()).subscribe(new Consumer<LoginResponse>() {
          @Override
          public void accept(LoginResponse loginResponse) throws Exception {
            getMvpView().hideLoading();
            getDataManager()
                .updateUserInfo(loginResponse.getToken(), LoggedInMode.LOGGED_IN_MODE_LOGGED_IN);
            getMvpView().goToLogout();
          }
        }, new Consumer<Throwable>() {
          @Override
          public void accept(Throwable throwable) throws Exception {
            getMvpView().hideLoading();
          }
        }));
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
        getMvpView().goToLogout();
      }
    }, new Consumer<Throwable>() {
      @Override
      public void accept(Throwable throwable) throws Exception {
        getMvpView().hideLoading();
      }
    }));
  }
}
