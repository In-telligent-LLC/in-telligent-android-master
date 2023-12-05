package com.sca.in_telligent.di.module;

import com.sca.in_telligent.ui.settings.SettingsMvpPresenter;
import com.sca.in_telligent.ui.settings.SettingsMvpView;
import com.sca.in_telligent.ui.settings.SettingsPresenter;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
public final class ActivityModule_ProvideSettingsPresenterFactory implements Factory<SettingsMvpPresenter<SettingsMvpView>> {
    private final ActivityModule module;
    private final Provider<SettingsPresenter<SettingsMvpView>> presenterProvider;

    public ActivityModule_ProvideSettingsPresenterFactory(ActivityModule activityModule, Provider<SettingsPresenter<SettingsMvpView>> provider) {
        this.module = activityModule;
        this.presenterProvider = provider;
    }

    @Override // javax.inject.Provider
    public SettingsMvpPresenter<SettingsMvpView> get() {
        return provideSettingsPresenter(this.module, this.presenterProvider.get());
    }

    public static ActivityModule_ProvideSettingsPresenterFactory create(ActivityModule activityModule, Provider<SettingsPresenter<SettingsMvpView>> provider) {
        return new ActivityModule_ProvideSettingsPresenterFactory(activityModule, provider);
    }

    public static SettingsMvpPresenter<SettingsMvpView> provideSettingsPresenter(ActivityModule activityModule, SettingsPresenter<SettingsMvpView> settingsPresenter) {
        return (SettingsMvpPresenter) Preconditions.checkNotNullFromProvides(activityModule.provideSettingsPresenter(settingsPresenter));
    }
}
