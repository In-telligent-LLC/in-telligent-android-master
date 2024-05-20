package com.sca.in_telligent.util;

import com.sca.in_telligent.data.DataManager;
import com.sca.in_telligent.util.rx.SchedulerProvider;
import dagger.internal.Factory;
import io.reactivex.rxjava3.disposables.CompositeDisposable;

import javax.inject.Provider;

/* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
public final class AppLifecycleObserver_Factory implements Factory<AppLifecycleObserver> {
    private final Provider<CompositeDisposable> compositeDisposableProvider;
    private final Provider<DataManager> dataManagerProvider;
    private final Provider<SchedulerProvider> schedulerProvider;

    public AppLifecycleObserver_Factory(Provider<DataManager> provider, Provider<SchedulerProvider> provider2, Provider<CompositeDisposable> provider3) {
        this.dataManagerProvider = provider;
        this.schedulerProvider = provider2;
        this.compositeDisposableProvider = provider3;
    }

    @Override // javax.inject.Provider
    public AppLifecycleObserver get() {
        return newInstance(this.dataManagerProvider.get(), this.schedulerProvider.get(), this.compositeDisposableProvider.get());
    }

    public static AppLifecycleObserver_Factory create(Provider<DataManager> provider, Provider<SchedulerProvider> provider2, Provider<CompositeDisposable> provider3) {
        return new AppLifecycleObserver_Factory(provider, provider2, provider3);
    }

    public static AppLifecycleObserver newInstance(DataManager dataManager, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        return new AppLifecycleObserver(dataManager, schedulerProvider, compositeDisposable);
    }
}
