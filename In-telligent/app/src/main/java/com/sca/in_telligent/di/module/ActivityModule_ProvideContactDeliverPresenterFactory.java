package com.sca.in_telligent.di.module;

import com.sca.in_telligent.ui.contact.message.deliver.ContactDeliverMvpPresenter;
import com.sca.in_telligent.ui.contact.message.deliver.ContactDeliverMvpView;
import com.sca.in_telligent.ui.contact.message.deliver.ContactDeliverPresenter;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
public final class ActivityModule_ProvideContactDeliverPresenterFactory implements Factory<ContactDeliverMvpPresenter<ContactDeliverMvpView>> {
    private final ActivityModule module;
    private final Provider<ContactDeliverPresenter<ContactDeliverMvpView>> presenterProvider;

    public ActivityModule_ProvideContactDeliverPresenterFactory(ActivityModule activityModule, Provider<ContactDeliverPresenter<ContactDeliverMvpView>> provider) {
        this.module = activityModule;
        this.presenterProvider = provider;
    }

    @Override // javax.inject.Provider
    public ContactDeliverMvpPresenter<ContactDeliverMvpView> get() {
        return provideContactDeliverPresenter(this.module, this.presenterProvider.get());
    }

    public static ActivityModule_ProvideContactDeliverPresenterFactory create(ActivityModule activityModule, Provider<ContactDeliverPresenter<ContactDeliverMvpView>> provider) {
        return new ActivityModule_ProvideContactDeliverPresenterFactory(activityModule, provider);
    }

    public static ContactDeliverMvpPresenter<ContactDeliverMvpView> provideContactDeliverPresenter(ActivityModule activityModule, ContactDeliverPresenter<ContactDeliverMvpView> contactDeliverPresenter) {
        return (ContactDeliverMvpPresenter) Preconditions.checkNotNullFromProvides(activityModule.provideContactDeliverPresenter(contactDeliverPresenter));
    }
}
