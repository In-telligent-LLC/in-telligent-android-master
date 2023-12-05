package com.sca.in_telligent.ui.group.alert.delivery;

import com.sca.in_telligent.openapi.data.network.model.DeliveryInfoResponse;
import com.sca.in_telligent.ui.base.MvpView;

public interface DeliveryInfoMvpView extends MvpView {

  void deliveryInfoFetched(DeliveryInfoResponse deliveryInfoResponse);
}
