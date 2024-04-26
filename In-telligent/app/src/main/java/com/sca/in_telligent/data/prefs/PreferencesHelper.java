package com.sca.in_telligent.data.prefs;

import com.sca.in_telligent.data.DataManager;
import com.sca.in_telligent.openapi.data.prefs.OpenApiPreferencesHelper;

public interface PreferencesHelper extends OpenApiPreferencesHelper {
    void clearAfterLogout();

    String getCurrentUserId();

    String getCurrentUserLanguage();

    int getCurrentUserLoggedInMode();

    String getPushToken();

    boolean isFirstTime();

    void setCurrentUserId(String userId);

    void setCurrentUserLanguage(String locale);

    void setCurrentUserLoggedInMode(DataManager.LoggedInMode mode);

    void setIsFirstTime(boolean isFirstTime);

    void setPushToken(String pushToken);
}
