package com.sca.in_telligent.ui.settings.help;

import com.sca.in_telligent.ui.base.MvpView;

public interface HelpMvpView extends MvpView {

  void phonePermissionResult(boolean granted);

  void messageSent(boolean success);
}
