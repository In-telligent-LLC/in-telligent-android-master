package com.sca.in_telligent.ui.findlocation;

import android.Manifest.permission;
import android.support.v4.app.Fragment;
import com.sca.in_telligent.data.DataManager;
import com.sca.in_telligent.data.DataManager.LoggedInMode;
import com.sca.in_telligent.openapi.data.network.model.LocationModel;
import com.sca.in_telligent.openapi.data.network.model.LoginResponse;
import com.sca.in_telligent.ui.base.BasePresenter;
import com.sca.in_telligent.util.rx.SchedulerProvider;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import javax.inject.Inject;

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
            observeOn(getSchedulerProvider().ui()).subscribe(new Consumer<LocationModel>() {
          @Override
          public void accept(LocationModel locationModel) throws Exception {
            if (locationModel != null) {
              getMvpView().updatedLocationFetched(locationModel);
            }
          }
        }, new Consumer<Throwable>() {
          @Override
          public void accept(Throwable throwable) throws Exception {
            getMvpView().hideLoading();
          }
        }));
  }

  @Override
  public void requestLocationPermission() {
    getRxPermissionsFragment((Fragment) getMvpView())
        .request(permission.ACCESS_FINE_LOCATION)
        .subscribe(granted -> getMvpView().locationPermissionResult(granted));
  }

}
