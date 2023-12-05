package com.sca.in_telligent.ui.settings.help;

import com.sca.in_telligent.openapi.data.network.model.SupportRequest;
import com.sca.in_telligent.ui.base.MvpPresenter;

public interface HelpMvpPresenter<V extends HelpMvpView> extends MvpPresenter<V> {
  void requestPhonePermission();
  void sendSupportMail(SupportRequest supportRequest);
}
