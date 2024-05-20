package com.sca.in_telligent.di.module;

import com.sca.in_telligent.ui.popup.IncomingCallMvpPresenter;
import com.sca.in_telligent.ui.popup.IncomingCallMvpView;
import com.sca.in_telligent.ui.popup.IncomingCallPresenter;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
public final class ActivityModule_ProvideIncomingCallPresenterFactory implements Factory<IncomingCallMvpPresenter<IncomingCallMvpView>> {
    private final ActivityModule module;
    private final Provider<IncomingCallPresenter<IncomingCallMvpView>> presenterProvider;

    public ActivityModule_ProvideIncomingCallPresenterFactory(ActivityModule activityModule, Provider<IncomingCallPresenter<IncomingCallMvpView>> provider) {
        this.module = activityModule;
        this.presenterProvider = provider;
    }

    @Override // javax.inject.Provider
    public IncomingCallMvpPresenter<IncomingCallMvpView> get() {
        return provideIncomingCallPresenter(this.module, this.presenterProvider.get());
    }

    public static ActivityModule_ProvideIncomingCallPresenterFactory create(ActivityModule activityModule, Provider<IncomingCallPresenter<IncomingCallMvpView>> provider) {
        return new ActivityModule_ProvideIncomingCallPresenterFactory(activityModule, provider);
    }

    public static IncomingCallMvpPresenter<IncomingCallMvpView> provideIncomingCallPresenter(ActivityModule activityModule, IncomingCallPresenter<IncomingCallMvpView> incomingCallPresenter) {
        return (IncomingCallMvpPresenter) Preconditions.checkNotNullFromProvides(activityModule.provideIncomingCallPresenter(incomingCallPresenter));
    }
}
