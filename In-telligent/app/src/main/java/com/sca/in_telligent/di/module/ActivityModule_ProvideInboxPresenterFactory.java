package com.sca.in_telligent.di.module;

import com.sca.in_telligent.ui.inbox.InboxMvpPresenter;
import com.sca.in_telligent.ui.inbox.InboxMvpView;
import com.sca.in_telligent.ui.inbox.InboxPresenter;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
public final class ActivityModule_ProvideInboxPresenterFactory implements Factory<InboxMvpPresenter<InboxMvpView>> {
    private final ActivityModule module;
    private final Provider<InboxPresenter<InboxMvpView>> presenterProvider;

    public ActivityModule_ProvideInboxPresenterFactory(ActivityModule activityModule, Provider<InboxPresenter<InboxMvpView>> provider) {
        this.module = activityModule;
        this.presenterProvider = provider;
    }

    @Override // javax.inject.Provider
    public InboxMvpPresenter<InboxMvpView> get() {
        return provideInboxPresenter(this.module, this.presenterProvider.get());
    }

    public static ActivityModule_ProvideInboxPresenterFactory create(ActivityModule activityModule, Provider<InboxPresenter<InboxMvpView>> provider) {
        return new ActivityModule_ProvideInboxPresenterFactory(activityModule, provider);
    }

    public static InboxMvpPresenter<InboxMvpView> provideInboxPresenter(ActivityModule activityModule, InboxPresenter<InboxMvpView> inboxPresenter) {
        return (InboxMvpPresenter) Preconditions.checkNotNullFromProvides(activityModule.provideInboxPresenter(inboxPresenter));
    }
}
