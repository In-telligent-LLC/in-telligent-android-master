package com.sca.in_telligent.ui.auth.login;


import com.facebook.login.LoginResult;
import com.sca.in_telligent.ui.base.MvpView;

public interface LoginMvpView extends MvpView {

  void openMainActivity();

  void onFacebookConnected(LoginResult loginResult);

  void goToLogout();
}
