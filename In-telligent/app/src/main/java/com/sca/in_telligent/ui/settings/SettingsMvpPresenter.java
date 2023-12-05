package com.sca.in_telligent.ui.settings;

import com.sca.in_telligent.ui.base.MvpPresenter;

public interface SettingsMvpPresenter<V extends SettingsMvpView> extends MvpPresenter<V> {

  void logout();
}
