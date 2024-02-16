package com.sca.in_telligent.ui.group.alert.list;

import com.sca.in_telligent.openapi.data.network.model.Notification;
import com.sca.in_telligent.ui.base.MvpView;
import java.util.ArrayList;

public interface AlertListMvpView extends MvpView {
    void alertDeleted(int i);

    void loadNotifications(ArrayList<Notification> arrayList);
}
