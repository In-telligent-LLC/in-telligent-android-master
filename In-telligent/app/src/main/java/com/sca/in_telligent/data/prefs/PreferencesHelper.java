package com.sca.in_telligent.data.prefs;

import com.sca.in_telligent.data.DataManager;
import com.sca.in_telligent.openapi.data.prefs.OpenApiPreferencesHelper;

/* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
public interface PreferencesHelper extends OpenApiPreferencesHelper {
    void clearAfterLogout();

    String getCurrentUserId();

    String getCurrentUserLanguage();

    int getCurrentUserLoggedInMode();

    String getPushToken();

    boolean isFirstTime();

    void setCurrentUserId(String str);

    void setCurrentUserLanguage(String str);

    void setCurrentUserLoggedInMode(DataManager.LoggedInMode loggedInMode);

    void setIsFirstTime(boolean z);

    void setPushToken(String str);
}
