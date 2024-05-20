package com.sca.in_telligent.data;

import com.sca.in_telligent.data.prefs.PreferencesHelper;
import com.sca.in_telligent.openapi.data.network.ApiHelper;

public interface DataManager extends PreferencesHelper, ApiHelper {

  void setUserAsLoggedOut();

  void updateUserInfo(String accessToken, LoggedInMode loggedInMode);

  void setLastFetchedGeofences(long timeInMillis);

  long getLastFetchedGeofences();

  enum LoggedInMode {

    LOGGED_IN_MODE_LOGGED_OUT(0),
    LOGGED_IN_MODE_LOGGED_IN(1);

    private final int mType;

    LoggedInMode(int type) {
      mType = type;
    }

    public int getType() {
      return mType;
    }
  }
}
