package com.sca.in_telligent.ui.group.alert.detail;

import com.sca.in_telligent.data.DataManager;
import com.sca.in_telligent.ui.base.BasePresenter;
import com.sca.in_telligent.util.rx.SchedulerProvider;
import io.reactivex.disposables.CompositeDisposable;
import javax.inject.Inject;

public class AlertDetailPresenter<V extends AlertDetailMvpView> extends BasePresenter<V> implements
    AlertDetailMvpPresenter<V> {

  @Inject
  public AlertDetailPresenter(DataManager dataManager,
      SchedulerProvider schedulerProvider,
      CompositeDisposable compositeDisposable) {
    super(dataManager, schedulerProvider, compositeDisposable);
  }
}
