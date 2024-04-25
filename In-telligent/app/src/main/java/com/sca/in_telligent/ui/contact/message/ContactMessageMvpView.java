package com.sca.in_telligent.ui.contact.message;

import android.content.Context;

import com.sca.in_telligent.ui.base.MvpView;

public interface ContactMessageMvpView extends MvpView {

  void storagePermissionResult(boolean granted);

  void messageSendResult(boolean sent);

  Context getContext();
}
