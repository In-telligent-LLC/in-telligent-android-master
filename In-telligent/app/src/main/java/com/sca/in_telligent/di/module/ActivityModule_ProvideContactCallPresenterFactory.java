package com.sca.in_telligent.di.module;

import com.sca.in_telligent.ui.contact.call.ContactCallMvpPresenter;
import com.sca.in_telligent.ui.contact.call.ContactCallMvpView;
import com.sca.in_telligent.ui.contact.call.ContactCallPresenter;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
public final class ActivityModule_ProvideContactCallPresenterFactory implements Factory<ContactCallMvpPresenter<ContactCallMvpView>> {
    private final ActivityModule module;
    private final Provider<ContactCallPresenter<ContactCallMvpView>> presenterProvider;

    public ActivityModule_ProvideContactCallPresenterFactory(ActivityModule activityModule, Provider<ContactCallPresenter<ContactCallMvpView>> provider) {
        this.module = activityModule;
        this.presenterProvider = provider;
    }

    @Override // javax.inject.Provider
    public ContactCallMvpPresenter<ContactCallMvpView> get() {
        return provideContactCallPresenter(this.module, this.presenterProvider.get());
    }

    public static ActivityModule_ProvideContactCallPresenterFactory create(ActivityModule activityModule, Provider<ContactCallPresenter<ContactCallMvpView>> provider) {
        return new ActivityModule_ProvideContactCallPresenterFactory(activityModule, provider);
    }

    public static ContactCallMvpPresenter<ContactCallMvpView> provideContactCallPresenter(ActivityModule activityModule, ContactCallPresenter<ContactCallMvpView> contactCallPresenter) {
        return (ContactCallMvpPresenter) Preconditions.checkNotNullFromProvides(activityModule.provideContactCallPresenter(contactCallPresenter));
    }
}
