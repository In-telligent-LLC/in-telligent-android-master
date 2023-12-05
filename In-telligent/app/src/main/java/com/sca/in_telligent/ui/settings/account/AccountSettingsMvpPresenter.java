package com.sca.in_telligent.ui.settings.account;

import com.sca.in_telligent.openapi.data.network.model.UpdateSubscriberRequest;
import com.sca.in_telligent.ui.base.MvpPresenter;

public interface AccountSettingsMvpPresenter<V extends AccountSettingsMvpView> extends
    MvpPresenter<V> {

  void listLanguages();

  void syncMetadata(UpdateSubscriberRequest updateSubscriberRequest);
}
