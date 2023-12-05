package com.sca.in_telligent.di.module;

import com.sca.in_telligent.ui.contact.list.ContactListMvpPresenter;
import com.sca.in_telligent.ui.contact.list.ContactListMvpView;
import com.sca.in_telligent.ui.contact.list.ContactListPresenter;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
public final class ActivityModule_ProvideContactListPresenterFactory implements Factory<ContactListMvpPresenter<ContactListMvpView>> {
    private final ActivityModule module;
    private final Provider<ContactListPresenter<ContactListMvpView>> presenterProvider;

    public ActivityModule_ProvideContactListPresenterFactory(ActivityModule activityModule, Provider<ContactListPresenter<ContactListMvpView>> provider) {
        this.module = activityModule;
        this.presenterProvider = provider;
    }

    @Override // javax.inject.Provider
    public ContactListMvpPresenter<ContactListMvpView> get() {
        return provideContactListPresenter(this.module, this.presenterProvider.get());
    }

    public static ActivityModule_ProvideContactListPresenterFactory create(ActivityModule activityModule, Provider<ContactListPresenter<ContactListMvpView>> provider) {
        return new ActivityModule_ProvideContactListPresenterFactory(activityModule, provider);
    }

    public static ContactListMvpPresenter<ContactListMvpView> provideContactListPresenter(ActivityModule activityModule, ContactListPresenter<ContactListMvpView> contactListPresenter) {
        return (ContactListMvpPresenter) Preconditions.checkNotNullFromProvides(activityModule.provideContactListPresenter(contactListPresenter));
    }
}
