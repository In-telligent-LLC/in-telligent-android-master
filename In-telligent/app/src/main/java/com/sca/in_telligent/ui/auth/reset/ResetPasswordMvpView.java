package com.sca.in_telligent.ui.auth.reset;

import com.sca.in_telligent.ui.base.MvpView;

public interface ResetPasswordMvpView extends MvpView {

  void passwordChanged();

  void goToLogout();
}
