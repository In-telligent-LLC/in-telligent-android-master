package com.sca.in_telligent.ui.contact.call;

import android.content.Context;

import com.sca.in_telligent.ui.base.MvpView;

public interface ContactCallMvpView extends MvpView {
  void recordAudioPermissionResult(boolean granted);

    Context getBaseActivity();
}
