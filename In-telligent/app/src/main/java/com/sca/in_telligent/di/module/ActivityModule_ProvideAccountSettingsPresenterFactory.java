package com.sca.in_telligent.di.module;

import com.sca.in_telligent.ui.settings.account.AccountSettingsMvpPresenter;
import com.sca.in_telligent.ui.settings.account.AccountSettingsMvpView;
import com.sca.in_telligent.ui.settings.account.AccountSettingsPresenter;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
public final class ActivityModule_ProvideAccountSettingsPresenterFactory implements Factory<AccountSettingsMvpPresenter<AccountSettingsMvpView>> {
    private final ActivityModule module;
    private final Provider<AccountSettingsPresenter<AccountSettingsMvpView>> presenterProvider;

    public ActivityModule_ProvideAccountSettingsPresenterFactory(ActivityModule activityModule, Provider<AccountSettingsPresenter<AccountSettingsMvpView>> provider) {
        this.module = activityModule;
        this.presenterProvider = provider;
    }

    @Override // javax.inject.Provider
    public AccountSettingsMvpPresenter<AccountSettingsMvpView> get() {
        return provideAccountSettingsPresenter(this.module, this.presenterProvider.get());
    }

    public static ActivityModule_ProvideAccountSettingsPresenterFactory create(ActivityModule activityModule, Provider<AccountSettingsPresenter<AccountSettingsMvpView>> provider) {
        return new ActivityModule_ProvideAccountSettingsPresenterFactory(activityModule, provider);
    }

    public static AccountSettingsMvpPresenter<AccountSettingsMvpView> provideAccountSettingsPresenter(ActivityModule activityModule, AccountSettingsPresenter<AccountSettingsMvpView> accountSettingsPresenter) {
        return (AccountSettingsMvpPresenter) Preconditions.checkNotNullFromProvides(activityModule.provideAccountSettingsPresenter(accountSettingsPresenter));
    }
}
