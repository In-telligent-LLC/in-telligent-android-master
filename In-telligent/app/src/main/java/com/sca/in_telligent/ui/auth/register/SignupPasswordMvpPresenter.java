package com.sca.in_telligent.ui.auth.register;

import com.sca.in_telligent.di.PerActivity;
import com.sca.in_telligent.openapi.data.network.model.SignUpRequest;
import com.sca.in_telligent.ui.base.MvpPresenter;

@PerActivity
public interface SignupPasswordMvpPresenter<V extends SignupPasswordMvpView> extends
        MvpPresenter<V> {

    void signUp(String password, String passwordConfirm, String deviceId, SignUpRequest signUpRequest);

}
