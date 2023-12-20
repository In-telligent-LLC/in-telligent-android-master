package com.sca.in_telligent.ui.base;

import android.content.Intent;

public interface MvpView {
    void hideKeyboard();

    void hideLoading();

    boolean isNetworkConnected();

    void onError(int i);

    void onError(String str);

    void showLoading();

    void showMessage(int i);

    void showMessage(String str);

    void showMessageSnack(String str);

    void showNetworkDialog();

    void showPopup(String str);

    void startActivityWithDeeplink(Intent intent);
}
