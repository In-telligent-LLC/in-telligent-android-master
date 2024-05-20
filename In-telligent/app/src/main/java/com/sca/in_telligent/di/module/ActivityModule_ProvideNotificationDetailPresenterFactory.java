package com.sca.in_telligent.di.module;

import com.sca.in_telligent.ui.notificationdetail.NotificationDetailMvpPresenter;
import com.sca.in_telligent.ui.notificationdetail.NotificationDetailMvpView;
import com.sca.in_telligent.ui.notificationdetail.NotificationDetailPresenter;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
public final class ActivityModule_ProvideNotificationDetailPresenterFactory implements Factory<NotificationDetailMvpPresenter<NotificationDetailMvpView>> {
    private final ActivityModule module;
    private final Provider<NotificationDetailPresenter<NotificationDetailMvpView>> presenterProvider;

    public ActivityModule_ProvideNotificationDetailPresenterFactory(ActivityModule activityModule, Provider<NotificationDetailPresenter<NotificationDetailMvpView>> provider) {
        this.module = activityModule;
        this.presenterProvider = provider;
    }

    @Override // javax.inject.Provider
    public NotificationDetailMvpPresenter<NotificationDetailMvpView> get() {
        return provideNotificationDetailPresenter(this.module, this.presenterProvider.get());
    }

    public static ActivityModule_ProvideNotificationDetailPresenterFactory create(ActivityModule activityModule, Provider<NotificationDetailPresenter<NotificationDetailMvpView>> provider) {
        return new ActivityModule_ProvideNotificationDetailPresenterFactory(activityModule, provider);
    }

    public static NotificationDetailMvpPresenter<NotificationDetailMvpView> provideNotificationDetailPresenter(ActivityModule activityModule, NotificationDetailPresenter<NotificationDetailMvpView> notificationDetailPresenter) {
        return (NotificationDetailMvpPresenter) Preconditions.checkNotNullFromProvides(activityModule.provideNotificationDetailPresenter(notificationDetailPresenter));
    }
}
