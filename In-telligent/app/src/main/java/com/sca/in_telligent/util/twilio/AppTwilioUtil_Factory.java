package com.sca.in_telligent.util.twilio;

import android.content.Context;
import com.sca.in_telligent.data.DataManager;
import com.sca.in_telligent.data.prefs.PreferencesHelper;
import com.sca.in_telligent.util.Responder;
import com.sca.in_telligent.util.rx.SchedulerProvider;
import dagger.internal.Factory;
import io.reactivex.rxjava3.disposables.CompositeDisposable;

import javax.inject.Provider;

/* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
public final class AppTwilioUtil_Factory implements Factory<AppTwilioUtil> {
    private final Provider<CompositeDisposable> compositeDisposableProvider;
    private final Provider<Context> contextProvider;
    private final Provider<DataManager> dataManagerProvider;
    private final Provider<PreferencesHelper> preferencesHelperProvider;
    private final Provider<Responder> responderProvider;
    private final Provider<SchedulerProvider> schedulerProvider;

    public AppTwilioUtil_Factory(Provider<Context> provider, Provider<DataManager> provider2, Provider<SchedulerProvider> provider3, Provider<CompositeDisposable> provider4, Provider<Responder> provider5, Provider<PreferencesHelper> provider6) {
        this.contextProvider = provider;
        this.dataManagerProvider = provider2;
        this.schedulerProvider = provider3;
        this.compositeDisposableProvider = provider4;
        this.responderProvider = provider5;
        this.preferencesHelperProvider = provider6;
    }

    @Override // javax.inject.Provider
    public AppTwilioUtil get() {
        return newInstance(this.contextProvider.get(), this.dataManagerProvider.get(), this.schedulerProvider.get(), this.compositeDisposableProvider.get(), this.responderProvider.get(), this.preferencesHelperProvider.get());
    }

    public static AppTwilioUtil_Factory create(Provider<Context> provider, Provider<DataManager> provider2, Provider<SchedulerProvider> provider3, Provider<CompositeDisposable> provider4, Provider<Responder> provider5, Provider<PreferencesHelper> provider6) {
        return new AppTwilioUtil_Factory(provider, provider2, provider3, provider4, provider5, provider6);
    }

    public static AppTwilioUtil newInstance(Context context, DataManager dataManager, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable, Responder responder, PreferencesHelper preferencesHelper) {
        return new AppTwilioUtil(context, dataManager, schedulerProvider, compositeDisposable, responder, preferencesHelper);
    }
}
