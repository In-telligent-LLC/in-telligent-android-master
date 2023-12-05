package com.sca.in_telligent.di.module;

import com.sca.in_telligent.ui.contact.message.ContactMessageMvpPresenter;
import com.sca.in_telligent.ui.contact.message.ContactMessageMvpView;
import com.sca.in_telligent.ui.contact.message.ContactMessagePresenter;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
public final class ActivityModule_ProvideContactMessagePresenterFactory implements Factory<ContactMessageMvpPresenter<ContactMessageMvpView>> {
    private final ActivityModule module;
    private final Provider<ContactMessagePresenter<ContactMessageMvpView>> presenterProvider;

    public ActivityModule_ProvideContactMessagePresenterFactory(ActivityModule activityModule, Provider<ContactMessagePresenter<ContactMessageMvpView>> provider) {
        this.module = activityModule;
        this.presenterProvider = provider;
    }

    @Override // javax.inject.Provider
    public ContactMessageMvpPresenter<ContactMessageMvpView> get() {
        return provideContactMessagePresenter(this.module, this.presenterProvider.get());
    }

    public static ActivityModule_ProvideContactMessagePresenterFactory create(ActivityModule activityModule, Provider<ContactMessagePresenter<ContactMessageMvpView>> provider) {
        return new ActivityModule_ProvideContactMessagePresenterFactory(activityModule, provider);
    }

    public static ContactMessageMvpPresenter<ContactMessageMvpView> provideContactMessagePresenter(ActivityModule activityModule, ContactMessagePresenter<ContactMessageMvpView> contactMessagePresenter) {
        return (ContactMessageMvpPresenter) Preconditions.checkNotNullFromProvides(activityModule.provideContactMessagePresenter(contactMessagePresenter));
    }
}
