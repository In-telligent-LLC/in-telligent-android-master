package com.sca.in_telligent.ui.auth.login;

import com.facebook.CallbackManager;
import com.sca.in_telligent.openapi.data.network.model.FacebookLoginRequest;
import com.sca.in_telligent.openapi.data.network.model.GoogleLoginRequest;
import com.sca.in_telligent.openapi.data.network.model.LoginRequest;
import com.sca.in_telligent.di.PerActivity;
import com.sca.in_telligent.ui.base.MvpPresenter;

@PerActivity
public interface LoginMvpPresenter<V extends LoginMvpView> extends MvpPresenter<V> {

  void cleanAppData();

  void loginWithPassword(LoginRequest loginRequest);

  void loginFacebook(CallbackManager callbackManager);

  void authFacebook(FacebookLoginRequest facebookLoginRequest);

  void loginGoogle(int rc_sign);

  void authGoogle(GoogleLoginRequest googleLoginRequest);
}
