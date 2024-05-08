package com.sca.in_telligent.ui.group.detail.other;

import com.sca.in_telligent.openapi.data.network.model.UpdateSubscriptionRequest;
import com.sca.in_telligent.ui.base.MvpPresenter;
import com.sca.in_telligent.ui.group.detail.other.GroupDetailMvpView;

public interface GroupDetailMvpPresenter<V extends GroupDetailMvpView> extends MvpPresenter<V> {
    void connectGroup(UpdateSubscriptionRequest updateSubscriptionRequest);

    void disconnectGroup(UpdateSubscriptionRequest updateSubscriptionRequest);

    void loadNotifications(int buildingId);
}
