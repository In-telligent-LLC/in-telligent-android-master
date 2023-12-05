package com.sca.in_telligent.ui.settings.notification;

import com.sca.in_telligent.openapi.data.network.model.AlertSubscriptionRequest;
import com.sca.in_telligent.openapi.data.network.model.UpdateSubscriberRequest;
import com.sca.in_telligent.ui.base.MvpPresenter;

public interface NotificationSettingsMvpPresenter<V extends NotificationSettingsMvpView> extends
    MvpPresenter<V> {
  void syncAlertSubscription(AlertSubscriptionRequest alertSubscriptionRequest);

  void syncMetadata(UpdateSubscriberRequest updateSubscriberRequest);
}
