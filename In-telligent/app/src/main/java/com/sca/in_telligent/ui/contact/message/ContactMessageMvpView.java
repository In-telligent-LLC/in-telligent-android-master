package com.sca.in_telligent.ui.contact.message;

import com.sca.in_telligent.ui.base.MvpView;

public interface ContactMessageMvpView extends MvpView {

  void storagePermissionResult(boolean granted);

  void messageSendResult(boolean sent);
}
