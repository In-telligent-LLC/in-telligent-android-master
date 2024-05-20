package com.sca.in_telligent.ui.group.alert.list;

import com.sca.in_telligent.ui.base.MvpPresenter;

public interface AlertListMvpPresenter<V extends AlertListMvpView> extends MvpPresenter<V> {

    void getNotifications(String buildingId, boolean showLoading);

    void deleteAlert(String notificationId, int position);
}
