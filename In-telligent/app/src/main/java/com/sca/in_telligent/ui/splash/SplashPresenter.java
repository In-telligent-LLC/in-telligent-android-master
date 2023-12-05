package com.sca.in_telligent.ui.splash;


import com.sca.in_telligent.data.DataManager;
import com.sca.in_telligent.data.DataManager.LoggedInMode;
import com.sca.in_telligent.ui.base.BasePresenter;
import com.sca.in_telligent.util.rx.SchedulerProvider;
import io.reactivex.disposables.CompositeDisposable;
import javax.inject.Inject;


public class SplashPresenter<V extends SplashMvpView> extends BasePresenter<V>
    implements SplashMvpPresenter<V> {

  @Inject
  public SplashPresenter(DataManager dataManager,
      SchedulerProvider schedulerProvider,
      CompositeDisposable compositeDisposable) {
    super(dataManager, schedulerProvider, compositeDisposable);
  }

  @Override
  public void onAttach(V mvpView) {
    super.onAttach(mvpView);
    decideNextActivity();

  }

  private void decideNextActivity() {
    boolean isFirstTime = getDataManager().isFirstTime();
    if (isFirstTime) {
      getDataManager().setIsFirstTime(false);
    }

    int loggedInMode = getDataManager().getCurrentUserLoggedInMode();
    if (loggedInMode == DataManager.LoggedInMode.LOGGED_IN_MODE_LOGGED_OUT.getType()) {
      getMvpView().openIntroActivity();
    } else if (loggedInMode == LoggedInMode.LOGGED_IN_MODE_LOGGED_IN.getType()) {
      getMvpView().openMainActivity();
    }
  }
}
