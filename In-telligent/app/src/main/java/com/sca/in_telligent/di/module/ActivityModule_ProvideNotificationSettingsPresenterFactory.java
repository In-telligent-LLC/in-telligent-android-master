package com.sca.in_telligent.di.module;

import com.sca.in_telligent.ui.settings.notification.NotificationSettingsMvpPresenter;
import com.sca.in_telligent.ui.settings.notification.NotificationSettingsMvpView;
import com.sca.in_telligent.ui.settings.notification.NotificationSettingsPresenter;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
public final class ActivityModule_ProvideNotificationSettingsPresenterFactory implements Factory<NotificationSettingsMvpPresenter<NotificationSettingsMvpView>> {
    private final ActivityModule module;
    private final Provider<NotificationSettingsPresenter<NotificationSettingsMvpView>> presenterProvider;

    public ActivityModule_ProvideNotificationSettingsPresenterFactory(ActivityModule activityModule, Provider<NotificationSettingsPresenter<NotificationSettingsMvpView>> provider) {
        this.module = activityModule;
        this.presenterProvider = provider;
    }

    @Override // javax.inject.Provider
    public NotificationSettingsMvpPresenter<NotificationSettingsMvpView> get() {
        return provideNotificationSettingsPresenter(this.module, this.presenterProvider.get());
    }

    public static ActivityModule_ProvideNotificationSettingsPresenterFactory create(ActivityModule activityModule, Provider<NotificationSettingsPresenter<NotificationSettingsMvpView>> provider) {
        return new ActivityModule_ProvideNotificationSettingsPresenterFactory(activityModule, provider);
    }

    public static NotificationSettingsMvpPresenter<NotificationSettingsMvpView> provideNotificationSettingsPresenter(ActivityModule activityModule, NotificationSettingsPresenter<NotificationSettingsMvpView> notificationSettingsPresenter) {
        return (NotificationSettingsMvpPresenter) Preconditions.checkNotNullFromProvides(activityModule.provideNotificationSettingsPresenter(notificationSettingsPresenter));
    }
}
