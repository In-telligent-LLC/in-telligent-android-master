package com.sca.in_telligent.ui.group.detail.other;

import com.sca.in_telligent.openapi.data.network.model.UpdateSubscriberRequest;
import com.sca.in_telligent.openapi.data.network.model.UpdateSubscriptionRequest;
import com.sca.in_telligent.ui.base.MvpPresenter;

public interface GroupDetailMvpPresenter<V extends GroupDetailMvpView> extends MvpPresenter<V> {

  void disconnectGroup(UpdateSubscriptionRequest updateSubscriptionRequest);

  void connectGroup(UpdateSubscriptionRequest updateSubscriptionRequest);

}
