package com.sca.in_telligent.ui.contact.call;

import com.sca.in_telligent.ui.base.MvpView;

public interface ContactCallMvpView extends MvpView {
  void recordAudioPermissionResult(boolean granted);
}
