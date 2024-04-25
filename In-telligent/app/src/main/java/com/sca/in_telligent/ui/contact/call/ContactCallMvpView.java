package com.sca.in_telligent.ui.contact.call;

import android.content.Context;

import com.sca.in_telligent.openapi.data.network.model.VoipTokenResponse;
import com.sca.in_telligent.ui.base.MvpView;

public interface ContactCallMvpView extends MvpView {
  void recordAudioPermissionResult(boolean granted);

  void onVoipTokenReceived(VoipTokenResponse voipTokenResponse);
}
