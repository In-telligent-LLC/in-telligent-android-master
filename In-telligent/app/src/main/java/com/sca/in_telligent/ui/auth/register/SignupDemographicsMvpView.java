package com.sca.in_telligent.ui.auth.register;

import com.facebook.login.LoginResult;
import com.sca.in_telligent.openapi.data.network.model.CheckEmailResponse;
import com.sca.in_telligent.ui.base.MvpView;

public interface SignupDemographicsMvpView extends MvpView {

  void proceedWithSignup(CheckEmailResponse checkEmailResponse);

  void onFacebookConnected(LoginResult loginResult);

  void goToLogout();
}
