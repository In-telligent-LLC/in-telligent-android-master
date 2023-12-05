package com.sca.in_telligent.ui.notificationdetail;

import com.sca.in_telligent.openapi.data.network.model.EditSaveMessageRequest;
import com.sca.in_telligent.openapi.data.network.model.UpdateSubscriberRequest;
import com.sca.in_telligent.ui.base.MvpPresenter;

public interface NotificationDetailMvpPresenter<V extends NotificationDetailMvpView> extends
    MvpPresenter<V> {

  void editSavedMessage(EditSaveMessageRequest editSaveMessageRequest);

  void listLanguages();

  void getTranslatedAlert(String id, String lang);
}
