package com.sca.in_telligent.ui.preview;

import com.sca.in_telligent.ui.base.MvpPresenter;

public interface MessageViewMvpPresenter<V extends MessageViewMvpView> extends MvpPresenter<V> {
    void getNotification(String notificationId);
}
