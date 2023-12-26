package com.sca.in_telligent.ui.intro;

import com.sca.in_telligent.data.DataManager;
import com.sca.in_telligent.ui.base.BasePresenter;
import com.sca.in_telligent.util.rx.SchedulerProvider;
import javax.inject.Inject;

import io.reactivex.rxjava3.disposables.CompositeDisposable;

public class IntroPresenter<V extends IntroMvpView> extends BasePresenter<V> implements
    IntroMvpPresenter<V> {

  @Inject
  public IntroPresenter(DataManager dataManager,
      SchedulerProvider schedulerProvider,
      CompositeDisposable compositeDisposable) {
    super(dataManager, schedulerProvider, compositeDisposable);
  }
}
