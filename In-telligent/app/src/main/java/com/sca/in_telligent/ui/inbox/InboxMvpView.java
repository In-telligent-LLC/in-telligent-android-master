package com.sca.in_telligent.ui.inbox;

import com.sca.in_telligent.openapi.data.network.model.Notification;
import com.sca.in_telligent.ui.base.MvpView;
import java.util.List;

public interface InboxMvpView extends MvpView {

  void loadInbox(List<Notification> notificationList, int page);

  void loadSavedMessages(List<Notification> notificationList);

  void alertDeleted(int position);
}
