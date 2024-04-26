package com.sca.in_telligent.ui.group.alert.list;

import com.sca.in_telligent.ui.base.MvpPresenter;
import com.sca.in_telligent.ui.group.alert.list.AlertListMvpView;

public interface AlertListMvpPresenter<V extends AlertListMvpView> extends MvpPresenter<V> {
    void deleteAlert(String str, int i);

    void getNotifications(String str, boolean z);
}
