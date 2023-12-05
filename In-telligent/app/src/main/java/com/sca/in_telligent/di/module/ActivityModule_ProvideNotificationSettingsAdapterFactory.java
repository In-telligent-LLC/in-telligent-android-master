package com.sca.in_telligent.di.module;

import androidx.appcompat.app.AppCompatActivity;
import com.sca.in_telligent.ui.settings.notification.NotificationSettingAdapter;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
public final class ActivityModule_ProvideNotificationSettingsAdapterFactory implements Factory<NotificationSettingAdapter> {
    private final Provider<AppCompatActivity> appCompatActivityProvider;
    private final ActivityModule module;

    public ActivityModule_ProvideNotificationSettingsAdapterFactory(ActivityModule activityModule, Provider<AppCompatActivity> provider) {
        this.module = activityModule;
        this.appCompatActivityProvider = provider;
    }

    @Override // javax.inject.Provider
    public NotificationSettingAdapter get() {
        return provideNotificationSettingsAdapter(this.module, this.appCompatActivityProvider.get());
    }

    public static ActivityModule_ProvideNotificationSettingsAdapterFactory create(ActivityModule activityModule, Provider<AppCompatActivity> provider) {
        return new ActivityModule_ProvideNotificationSettingsAdapterFactory(activityModule, provider);
    }

    public static NotificationSettingAdapter provideNotificationSettingsAdapter(ActivityModule activityModule, AppCompatActivity appCompatActivity) {
        return (NotificationSettingAdapter) Preconditions.checkNotNullFromProvides(activityModule.provideNotificationSettingsAdapter(appCompatActivity));
    }
}
