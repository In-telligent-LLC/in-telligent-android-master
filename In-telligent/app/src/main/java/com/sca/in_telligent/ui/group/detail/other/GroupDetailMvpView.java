package com.sca.in_telligent.ui.group.detail.other;

import com.sca.in_telligent.ui.base.MvpView;

public interface GroupDetailMvpView extends MvpView {
  void unsubscribed(String buildingId);
  void subscribed(String buildingId);
}
