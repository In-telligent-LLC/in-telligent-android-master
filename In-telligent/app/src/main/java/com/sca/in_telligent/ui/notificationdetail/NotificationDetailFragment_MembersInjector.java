package com.sca.in_telligent.ui.notificationdetail;

import androidx.recyclerview.widget.LinearLayoutManager;
import dagger.MembersInjector;
import javax.inject.Provider;

public final class NotificationDetailFragment_MembersInjector implements MembersInjector<NotificationDetailFragment> {
    private final Provider<NotificationAttachmentAdapter> attachmentAdapterProvider;
    private final Provider<LinearLayoutManager> mLayoutManagerProvider;
    private final Provider<NotificationDetailMvpPresenter<NotificationDetailMvpView>> mPresenterProvider;

    public NotificationDetailFragment_MembersInjector(Provider<NotificationDetailMvpPresenter<NotificationDetailMvpView>> provider, Provider<LinearLayoutManager> provider2, Provider<NotificationAttachmentAdapter> provider3) {
        this.mPresenterProvider = provider;
        this.mLayoutManagerProvider = provider2;
        this.attachmentAdapterProvider = provider3;
    }

    public static MembersInjector<NotificationDetailFragment> create(Provider<NotificationDetailMvpPresenter<NotificationDetailMvpView>> provider, Provider<LinearLayoutManager> provider2, Provider<NotificationAttachmentAdapter> provider3) {
        return new NotificationDetailFragment_MembersInjector(provider, provider2, provider3);
    }

    @Override // dagger.MembersInjector
    public void injectMembers(NotificationDetailFragment notificationDetailFragment) {
        injectMPresenter(notificationDetailFragment, this.mPresenterProvider.get());
        injectMLayoutManager(notificationDetailFragment, this.mLayoutManagerProvider.get());
        injectAttachmentAdapter(notificationDetailFragment, this.attachmentAdapterProvider.get());
    }

    public static void injectMPresenter(NotificationDetailFragment notificationDetailFragment, NotificationDetailMvpPresenter<NotificationDetailMvpView> notificationDetailMvpPresenter) {
        notificationDetailFragment.mPresenter = notificationDetailMvpPresenter;
    }

    public static void injectMLayoutManager(NotificationDetailFragment notificationDetailFragment, LinearLayoutManager linearLayoutManager) {
        notificationDetailFragment.mLayoutManager = linearLayoutManager;
    }

    public static void injectAttachmentAdapter(NotificationDetailFragment notificationDetailFragment, NotificationAttachmentAdapter notificationAttachmentAdapter) {
        notificationDetailFragment.attachmentAdapter = notificationAttachmentAdapter;
    }
}
