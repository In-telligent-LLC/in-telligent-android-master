package com.sca.in_telligent.data;

import com.sca.in_telligent.data.prefs.PreferencesHelper;
import com.sca.in_telligent.openapi.data.network.ApiHelper;

public interface DataManager extends PreferencesHelper, ApiHelper {
  @Override // com.sca.in_telligent.openapi.data.prefs.OpenApiPreferencesHelper
  long getLastFetchedGeofences();

    // com.sca.in_telligent.openapi.data.prefs.OpenApiPreferencesHelper
    String getAccessToken();

    @Override // com.sca.in_telligent.openapi.data.prefs.OpenApiPreferencesHelper
  void setLastFetchedGeofences(long j);

  void setUserAsLoggedOut();

  void updateUserInfo(String str, LoggedInMode loggedInMode);

  /* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
  public enum LoggedInMode {
    LOGGED_IN_MODE_LOGGED_OUT(0),
    LOGGED_IN_MODE_LOGGED_IN(1);

    private final int mType;

    LoggedInMode(int i) {
      this.mType = i;
    }

    public int getType() {
      return this.mType;
    }
  }
}
