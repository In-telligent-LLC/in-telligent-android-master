package com.sca.in_telligent.ui.popup;

import com.sca.in_telligent.ui.base.MvpView;

public interface IncomingCallMvpView extends MvpView {

  void callDetail(boolean completed);

  void recordAudioPermissionResult(boolean granted);
}
