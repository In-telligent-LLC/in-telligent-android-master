package com.sca.in_telligent.ui.auth.logout;

import com.sca.in_telligent.data.DataManager;
import com.sca.in_telligent.ui.base.BasePresenter;
import com.sca.in_telligent.util.rx.SchedulerProvider;
import io.reactivex.disposables.CompositeDisposable;
import javax.inject.Inject;

public class LogoutPresenter<V extends LogoutMvpView> extends BasePresenter<V> implements
    LogoutMvpPresenter<V> {

  @Inject
  public LogoutPresenter(DataManager dataManager,
      SchedulerProvider schedulerProvider,
      CompositeDisposable compositeDisposable) {
    super(dataManager, schedulerProvider, compositeDisposable);
  }

  @Override
  public void logOut() {
    getDataManager().setUserAsLoggedOut();
    getMvpView().goToLogin();
  }
}
