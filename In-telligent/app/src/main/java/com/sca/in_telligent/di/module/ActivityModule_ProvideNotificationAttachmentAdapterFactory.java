package com.sca.in_telligent.di.module;

import androidx.appcompat.app.AppCompatActivity;
import com.sca.in_telligent.ui.notificationdetail.NotificationAttachmentAdapter;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
public final class ActivityModule_ProvideNotificationAttachmentAdapterFactory implements Factory<NotificationAttachmentAdapter> {
    private final Provider<AppCompatActivity> appCompatActivityProvider;
    private final ActivityModule module;

    public ActivityModule_ProvideNotificationAttachmentAdapterFactory(ActivityModule activityModule, Provider<AppCompatActivity> provider) {
        this.module = activityModule;
        this.appCompatActivityProvider = provider;
    }

    @Override // javax.inject.Provider
    public NotificationAttachmentAdapter get() {
        return provideNotificationAttachmentAdapter(this.module, this.appCompatActivityProvider.get());
    }

    public static ActivityModule_ProvideNotificationAttachmentAdapterFactory create(ActivityModule activityModule, Provider<AppCompatActivity> provider) {
        return new ActivityModule_ProvideNotificationAttachmentAdapterFactory(activityModule, provider);
    }

    public static NotificationAttachmentAdapter provideNotificationAttachmentAdapter(ActivityModule activityModule, AppCompatActivity appCompatActivity) {
        return (NotificationAttachmentAdapter) Preconditions.checkNotNullFromProvides(activityModule.provideNotificationAttachmentAdapter(appCompatActivity));
    }
}
