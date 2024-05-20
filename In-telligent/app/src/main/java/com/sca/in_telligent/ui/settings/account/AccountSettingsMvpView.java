package com.sca.in_telligent.ui.settings.account;

import com.sca.in_telligent.openapi.data.network.model.NotificationLanguage;
import com.sca.in_telligent.openapi.data.network.model.Subscriber;
import com.sca.in_telligent.ui.base.MvpView;
import java.util.ArrayList;

public interface AccountSettingsMvpView extends MvpView {

  void loadLanguages(ArrayList<NotificationLanguage> notificationLanguages);

  void subscriberLanguageResult(Subscriber subscriber);
}
