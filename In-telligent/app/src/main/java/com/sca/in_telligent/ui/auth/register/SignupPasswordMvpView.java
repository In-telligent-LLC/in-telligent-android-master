package com.sca.in_telligent.ui.auth.register;

import android.support.annotation.StringRes;

import com.sca.in_telligent.ui.base.MvpView;

public interface SignupPasswordMvpView extends MvpView {

    void goToMain();
    void showError(@StringRes int stringResourceId);
}
