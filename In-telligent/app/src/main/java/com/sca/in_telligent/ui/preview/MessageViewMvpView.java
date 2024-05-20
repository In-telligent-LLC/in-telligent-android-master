package com.sca.in_telligent.ui.preview;

import com.sca.in_telligent.openapi.data.network.model.Notification;
import com.sca.in_telligent.ui.base.DialogMvpView;

public interface MessageViewMvpView extends DialogMvpView {
    void notifyNotificationDetailsFetched(Notification notification);
}
