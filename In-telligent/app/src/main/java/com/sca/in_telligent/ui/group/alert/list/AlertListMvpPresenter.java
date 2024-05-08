package com.sca.in_telligent.ui.group.alert.list;

import com.sca.in_telligent.ui.base.MvpPresenter;
import com.sca.in_telligent.ui.group.alert.list.AlertListMvpView;


public interface AlertListMvpPresenter<V extends AlertListMvpView> extends MvpPresenter<V> {

    void loadNotifications(String buildingId, boolean showLoading);

    void deleteAlert(String notificationId, int position);
}
