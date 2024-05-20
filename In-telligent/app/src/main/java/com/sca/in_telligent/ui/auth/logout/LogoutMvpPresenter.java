package com.sca.in_telligent.ui.auth.logout;

import com.sca.in_telligent.di.PerActivity;
import com.sca.in_telligent.ui.base.MvpPresenter;

@PerActivity
public interface LogoutMvpPresenter<V extends LogoutMvpView> extends MvpPresenter<V> {

  void logOut();
}
