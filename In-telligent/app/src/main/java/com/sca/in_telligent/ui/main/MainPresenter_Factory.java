package com.sca.in_telligent.ui.main;

import com.sca.in_telligent.data.DataManager;
import com.sca.in_telligent.ui.base.BasePresenter_MembersInjector;
import com.sca.in_telligent.ui.main.MainMvpView;
import com.sca.in_telligent.util.rx.SchedulerProvider;
import com.sca.in_telligent.util.twilio.TwilioUtil;
import com.tbruyelle.rxpermissions2.RxPermissions;
import dagger.internal.Factory;
import io.reactivex.disposables.CompositeDisposable;
import javax.inject.Provider;

/* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
public final class MainPresenter_Factory<V extends MainMvpView> implements Factory<MainPresenter<V>> {
    private final Provider<CompositeDisposable> compositeDisposableProvider;
    private final Provider<DataManager> dataManagerProvider;
    private final Provider<RxPermissions> rxPermissionsProvider;
    private final Provider<SchedulerProvider> schedulerProvider;
    private final Provider<TwilioUtil> twilioUtilProvider;

    public MainPresenter_Factory(Provider<DataManager> provider, Provider<SchedulerProvider> provider2, Provider<CompositeDisposable> provider3, Provider<TwilioUtil> provider4, Provider<RxPermissions> provider5) {
        this.dataManagerProvider = provider;
        this.schedulerProvider = provider2;
        this.compositeDisposableProvider = provider3;
        this.twilioUtilProvider = provider4;
        this.rxPermissionsProvider = provider5;
    }

    @Override // javax.inject.Provider
    public MainPresenter<V> get() {
        MainPresenter<V> newInstance = newInstance(this.dataManagerProvider.get(), this.schedulerProvider.get(), this.compositeDisposableProvider.get());
        BasePresenter_MembersInjector.injectTwilioUtil(newInstance, this.twilioUtilProvider.get());
        BasePresenter_MembersInjector.injectRxPermissions(newInstance, this.rxPermissionsProvider.get());
        return newInstance;
    }

    public static <V extends MainMvpView> MainPresenter_Factory<V> create(Provider<DataManager> provider, Provider<SchedulerProvider> provider2, Provider<CompositeDisposable> provider3, Provider<TwilioUtil> provider4, Provider<RxPermissions> provider5) {
        return new MainPresenter_Factory<>(provider, provider2, provider3, provider4, provider5);
    }

    public static <V extends MainMvpView> MainPresenter<V> newInstance(DataManager dataManager, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        return new MainPresenter<>(dataManager, schedulerProvider, compositeDisposable);
    }
}
