package com.sca.in_telligent.di.module;

import android.app.Service;
import com.sca.in_telligent.util.rx.AppSchedulerProvider;
import com.sca.in_telligent.util.rx.SchedulerProvider;
import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;

@Module
public class ServiceModule {

  private final Service mService;

  public ServiceModule(Service service) {
    mService = service;
  }

  @Provides
  CompositeDisposable provideCompositeDisposable() {
    return new CompositeDisposable();
  }

  @Provides
  SchedulerProvider provideSchedulerProvider() {
    return new AppSchedulerProvider();
  }

}
