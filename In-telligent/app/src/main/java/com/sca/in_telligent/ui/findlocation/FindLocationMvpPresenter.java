package com.sca.in_telligent.ui.findlocation;

import com.sca.in_telligent.ui.base.MvpPresenter;

public interface FindLocationMvpPresenter<V extends FindLocationMvpView> extends MvpPresenter<V> {

  void getUpdatedLocation(String subscriberId);

  void requestLocationPermission();
}
