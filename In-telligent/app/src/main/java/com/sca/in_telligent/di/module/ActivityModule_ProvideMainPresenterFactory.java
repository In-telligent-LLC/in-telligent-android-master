package com.sca.in_telligent.di.module;

import com.sca.in_telligent.ui.main.MainMvpPresenter;
import com.sca.in_telligent.ui.main.MainMvpView;
import com.sca.in_telligent.ui.main.MainPresenter;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
public final class ActivityModule_ProvideMainPresenterFactory implements Factory<MainMvpPresenter<MainMvpView>> {
    private final ActivityModule module;
    private final Provider<MainPresenter<MainMvpView>> presenterProvider;

    public ActivityModule_ProvideMainPresenterFactory(ActivityModule activityModule, Provider<MainPresenter<MainMvpView>> provider) {
        this.module = activityModule;
        this.presenterProvider = provider;
    }

    @Override // javax.inject.Provider
    public MainMvpPresenter<MainMvpView> get() {
        return provideMainPresenter(this.module, this.presenterProvider.get());
    }

    public static ActivityModule_ProvideMainPresenterFactory create(ActivityModule activityModule, Provider<MainPresenter<MainMvpView>> provider) {
        return new ActivityModule_ProvideMainPresenterFactory(activityModule, provider);
    }

    public static MainMvpPresenter<MainMvpView> provideMainPresenter(ActivityModule activityModule, MainPresenter<MainMvpView> mainPresenter) {
        return (MainMvpPresenter) Preconditions.checkNotNullFromProvides(activityModule.provideMainPresenter(mainPresenter));
    }
}
