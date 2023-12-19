package com.sca.in_telligent.util;

import android.util.Log;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.OnLifecycleEvent;

import com.sca.in_telligent.data.DataManager;
import com.sca.in_telligent.util.rx.SchedulerProvider;
import javax.inject.Inject;

import io.reactivex.rxjava3.disposables.CompositeDisposable;

public class AppLifecycleObserver implements LifecycleInterface {

  private final DataManager mDataManager;
  private final SchedulerProvider mSchedulerProvider;
  private final CompositeDisposable mCompositeDisposable;
  private String state = "";

  @Inject
  public AppLifecycleObserver(DataManager dataManager,
      SchedulerProvider schedulerProvider,
      CompositeDisposable compositeDisposable) {
    this.mDataManager = dataManager;
    this.mSchedulerProvider = schedulerProvider;
    this.mCompositeDisposable = compositeDisposable;
  }


  @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
  @Override
  public void onCreated() {
    state = "create";
    Log.d("PROCESSLOG", "ON CREATE");
  }

  @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
  @Override
  public void onStopped() {
    state = "stop";
    Log.d("PROCESSLOG", "ON STOP");
    //mCompositeDisposable.dispose();
  }

  @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
  @Override
  public void onDestroyed() {
    state = "destroy";
  }

  @OnLifecycleEvent(Lifecycle.Event.ON_START)
  @Override
  public void onStarted() {
    state = "start";
    Log.d("PROCESSLOG", "ON START");
  }

  @Override
  public String getState() {
    return state;
  }

}