package com.sca.in_telligent.ui.contact.message;

import com.sca.in_telligent.openapi.data.network.model.CreateNotificationRequest;
import com.sca.in_telligent.openapi.data.network.model.SuggestNotificationRequest;
import com.sca.in_telligent.ui.base.MvpPresenter;
import java.util.ArrayList;

public interface ContactMessageMvpPresenter<V extends ContactMessageMvpView> extends
    MvpPresenter<V> {

//  void getStoragePermission();

  void createNotification(String buildingId, String title, String body, String type,
      ArrayList<String> attachmentPaths, String sendToEmail,
      ArrayList<String> subscriberIds);

  void createNotificationNoAttachment(CreateNotificationRequest createNotificationRequest);

  void suggestNotification(String buildingId, String title, String body, ArrayList<String> attachmentPaths);

  void suggestNotificationNoAttachment(SuggestNotificationRequest suggestNotificationRequest);
}
