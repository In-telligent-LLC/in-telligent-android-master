package com.sca.in_telligent.ui.settings.notification;

import com.sca.in_telligent.openapi.data.network.model.Subscriber;
import com.sca.in_telligent.ui.base.MvpView;

public interface NotificationSettingsMvpView extends MvpView {
  void subscriptonUpdated(String buildingId, String subscription);

  void subscriberUpdateResult(Subscriber subscriber);
}
