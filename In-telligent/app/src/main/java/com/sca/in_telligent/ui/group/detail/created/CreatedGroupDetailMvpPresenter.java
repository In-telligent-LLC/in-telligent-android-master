package com.sca.in_telligent.ui.group.detail.created;

import com.sca.in_telligent.ui.base.MvpPresenter;

public interface CreatedGroupDetailMvpPresenter<V extends CreatedGroupDetailMvpView> extends
    MvpPresenter<V> {

  void deletePersonalCommunity(String buildingId);
}
