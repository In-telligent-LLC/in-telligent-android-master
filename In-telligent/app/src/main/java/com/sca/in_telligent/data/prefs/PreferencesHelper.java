package com.sca.in_telligent.data.prefs;


import com.sca.in_telligent.data.DataManager;
import com.sca.in_telligent.openapi.data.prefs.OpenApiPreferencesHelper;

public interface PreferencesHelper extends OpenApiPreferencesHelper {

    String getCurrentUserId();

    void setCurrentUserId(String userId);

    int getCurrentUserLoggedInMode();

    void setCurrentUserLoggedInMode(DataManager.LoggedInMode mode);

    String getCurrentUserLanguage();

    void setCurrentUserLanguage(String locale);

    void clearAfterLogout();

    boolean isFirstTime();

    void setIsFirstTime(boolean isFirstTime);

    void setPushToken(String pushToken);

    String getPushToken();

}
