package com.sca.in_telligent.ui.group.alert.delivery;

import com.sca.in_telligent.ui.base.MvpPresenter;

public interface DeliveryInfoMvpPresenter<V extends DeliveryInfoMvpView> extends MvpPresenter<V> {

  void getDeliveryInfo(String buildingId, String notificationId);
}
