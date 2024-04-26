package com.sca.in_telligent.ui.base;

import android.content.Intent;

import androidx.annotation.StringRes;

public interface MvpView {
    void showLoading();

    void hideLoading();

    void onError(@StringRes int resId);

    void onError(String message);

    void showMessage(String message);

    void showMessage(@StringRes int resId);

    void showMessageSnack(String message);

    boolean isNetworkConnected();

    void hideKeyboard();

    void startActivityWithDeeplink(Intent intent);

    void showPopup(String message);

    void showNetworkDialog();
}
