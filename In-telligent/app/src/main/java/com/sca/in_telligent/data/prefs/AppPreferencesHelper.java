package com.sca.in_telligent.data.prefs;

import android.content.Context;

import com.crashlytics.android.Crashlytics;
import com.sca.in_telligent.data.DataManager;
import com.sca.in_telligent.di.ApplicationContext;
import com.sca.in_telligent.di.PreferenceInfo;
import com.sca.in_telligent.openapi.Constants;
import com.sca.in_telligent.openapi.data.prefs.OpenApiPreferencesHelperImpl;
import com.sca.in_telligent.util.AppConstants;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class AppPreferencesHelper extends OpenApiPreferencesHelperImpl implements PreferencesHelper {

    @Inject
    public AppPreferencesHelper(@ApplicationContext Context context,
                                @PreferenceInfo String prefFileName) {
        super(context, prefFileName);
    }

    @Override
    public String getCurrentUserId() {
        String userId = mPrefs.getString(Constants.Preferences.PREF_KEY_CURRENT_USER_ID, AppConstants.NULL_INDEX);
        return userId.equals(AppConstants.NULL_INDEX) ? null : userId;
    }

    @Override
    public void setCurrentUserId(String userId) {
        Crashlytics.setUserIdentifier(userId);
        String id = userId == null ? AppConstants.NULL_INDEX : userId;
        mPrefs.edit().putString(Constants.Preferences.PREF_KEY_CURRENT_USER_ID, id).apply();
    }

    @Override
    public int getCurrentUserLoggedInMode() {
        return mPrefs.getInt(Constants.Preferences.PREF_KEY_USER_LOGGED_IN_MODE,
                DataManager.LoggedInMode.LOGGED_IN_MODE_LOGGED_OUT.getType());
    }

    @Override
    public void setCurrentUserLoggedInMode(DataManager.LoggedInMode mode) {
        mPrefs.edit().putInt(Constants.Preferences.PREF_KEY_USER_LOGGED_IN_MODE, mode.getType()).apply();
    }

    @Override
    public String getCurrentUserLanguage() {
        return mPrefs.getString(Constants.Preferences.PREF_KEY_CURRENT_USER_LOCALE, "tr");
    }

    @Override
    public void setCurrentUserLanguage(String locale) {
        mPrefs.edit().putString(Constants.Preferences.PREF_KEY_CURRENT_USER_LOCALE, locale).apply();
    }

    @Override
    public void clearAfterLogout() {
        mPrefs.edit().clear().apply();
//    mPrefs.edit().putBoolean(PREF_KEY_IS_FIRST_TIME, false).apply();
    }

    @Override
    public boolean isFirstTime() {
        return mPrefs.getBoolean(Constants.Preferences.PREF_KEY_IS_FIRST_TIME, true);
    }

    @Override
    public void setIsFirstTime(boolean isFirstTime) {
        mPrefs.edit().putBoolean(Constants.Preferences.PREF_KEY_IS_FIRST_TIME, isFirstTime).apply();
    }

    @Override
    public void setPushToken(String pushToken) {
        mPrefs.edit().putString(Constants.Preferences.PREF_KEY_PUSH_TOKEN, pushToken).apply();
    }

    @Override
    public String getPushToken() {
        return mPrefs.getString(Constants.Preferences.PREF_KEY_PUSH_TOKEN, null);
    }
}
