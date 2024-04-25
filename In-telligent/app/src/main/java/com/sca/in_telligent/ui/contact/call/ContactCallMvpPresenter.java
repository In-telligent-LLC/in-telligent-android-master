package com.sca.in_telligent.ui.contact.call;

import com.sca.in_telligent.ui.base.MvpPresenter;

public interface ContactCallMvpPresenter<V extends ContactCallMvpView> extends MvpPresenter<V> {

  void requestRecordAudioPermission();


}
