package com.sca.in_telligent.ui.popup;

import com.sca.in_telligent.ui.base.MvpPresenter;

public interface IncomingCallMvpPresenter<V extends IncomingCallMvpView> extends MvpPresenter<V> {

  void getCallDetail(String conferenceId);

  void requestRecordAudioPermission();
}
