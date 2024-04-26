package com.sca.in_telligent.ui.findlocation;

import android.Manifest.permission;
import android.annotation.SuppressLint;

import androidx.fragment.app.Fragment;

import com.sca.in_telligent.data.DataManager;
import com.sca.in_telligent.data.DataManager.LoggedInMode;
import com.sca.in_telligent.openapi.data.network.model.LocationModel;
import com.sca.in_telligent.openapi.data.network.model.LoginResponse;
import com.sca.in_telligent.ui.base.BasePresenter;
import com.sca.in_telligent.util.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.functions.Consumer;

public class FindLocationPresenter<V extends FindLocationMvpView> extends
    BasePresenter<V> implements FindLocationMvpPresenter<V> {

  @Inject
  public FindLocationPresenter(DataManager dataManager,
      SchedulerProvider schedulerProvider,
      CompositeDisposable compositeDisposable) {
    super(dataManager, schedulerProvider, compositeDisposable);
  }

  @Override
  public void getUpdatedLocation(String subscriberId) {
    getCompositeDisposable().add(getDataManager().getUpdatedLocation(subscriberId)
        .subscribeOn(getSchedulerProvider().io()).
            observeOn(getSchedulerProvider().ui()).subscribe(locationModel -> {
              if (locationModel != null) {
                getMvpView().updatedLocationFetched(locationModel);
              }
            }, throwable -> getMvpView().hideLoading()));
  }

  @SuppressLint("CheckResult")
  @Override
  public void requestLocationPermission() {
    getRxPermissionsFragment((Fragment) getMvpView())
        .request(permission.ACCESS_FINE_LOCATION)
        .subscribe(granted -> getMvpView().locationPermissionResult(granted));
  }

}
