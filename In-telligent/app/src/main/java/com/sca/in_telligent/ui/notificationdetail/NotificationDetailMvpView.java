package com.sca.in_telligent.ui.notificationdetail;

import com.sca.in_telligent.openapi.data.network.model.NotificationLanguage;
import com.sca.in_telligent.openapi.data.network.model.TranslationResponse;
import com.sca.in_telligent.ui.base.MvpView;
import java.util.ArrayList;

public interface NotificationDetailMvpView extends MvpView {

  void saveButtonChange(String operation);

  void loadLanguages(ArrayList<NotificationLanguage> notificationLanguages);

  void translatedAlert(TranslationResponse translationResponse);
}
