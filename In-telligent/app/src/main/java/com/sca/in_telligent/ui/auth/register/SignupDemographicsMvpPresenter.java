package com.sca.in_telligent.ui.auth.register;

import com.facebook.CallbackManager;
import com.sca.in_telligent.openapi.data.network.model.FacebookLoginRequest;
import com.sca.in_telligent.openapi.data.network.model.GoogleLoginRequest;
import com.sca.in_telligent.di.PerActivity;
import com.sca.in_telligent.ui.base.MvpPresenter;

@PerActivity
public interface SignupDemographicsMvpPresenter<V extends SignupDemographicsMvpView> extends
    MvpPresenter<V> {

  void checkEmailExists(String email);

  void loginFacebook(CallbackManager callbackManager);

  void authFacebook(FacebookLoginRequest facebookLoginRequest);

  void loginGoogle(int rc_sign);

  void authGoogle(GoogleLoginRequest googleLoginRequest);

}
