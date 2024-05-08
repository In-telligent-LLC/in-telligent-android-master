package com.sca.in_telligent.ui.group.detail.other;

import com.sca.in_telligent.openapi.data.network.model.Notification;
import com.sca.in_telligent.ui.base.MvpView;

import java.util.ArrayList;

public interface GroupDetailMvpView extends MvpView {
    void subscribed(String str);

    void unsubscribed(String str);

    void loadNotifications(ArrayList<Notification> notifications);

}
