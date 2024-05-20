package com.sca.in_telligent.di.module;

import com.sca.in_telligent.ui.preview.MessageViewMvpPresenter;
import com.sca.in_telligent.ui.preview.MessageViewMvpView;
import com.sca.in_telligent.ui.preview.MessageViewPresenter;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
public final class ActivityModule_ProvideMessageViewPresenterFactory implements Factory<MessageViewMvpPresenter<MessageViewMvpView>> {
    private final ActivityModule module;
    private final Provider<MessageViewPresenter<MessageViewMvpView>> presenterProvider;

    public ActivityModule_ProvideMessageViewPresenterFactory(ActivityModule activityModule, Provider<MessageViewPresenter<MessageViewMvpView>> provider) {
        this.module = activityModule;
        this.presenterProvider = provider;
    }

    @Override // javax.inject.Provider
    public MessageViewMvpPresenter<MessageViewMvpView> get() {
        return provideMessageViewPresenter(this.module, this.presenterProvider.get());
    }

    public static ActivityModule_ProvideMessageViewPresenterFactory create(ActivityModule activityModule, Provider<MessageViewPresenter<MessageViewMvpView>> provider) {
        return new ActivityModule_ProvideMessageViewPresenterFactory(activityModule, provider);
    }

    public static MessageViewMvpPresenter<MessageViewMvpView> provideMessageViewPresenter(ActivityModule activityModule, MessageViewPresenter<MessageViewMvpView> messageViewPresenter) {
        return (MessageViewMvpPresenter) Preconditions.checkNotNullFromProvides(activityModule.provideMessageViewPresenter(messageViewPresenter));
    }
}
