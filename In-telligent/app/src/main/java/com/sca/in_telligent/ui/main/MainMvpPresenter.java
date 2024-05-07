package com.sca.in_telligent.ui.main;

import android.content.Context;
import android.location.Location;
import com.sca.in_telligent.di.PerActivity;
import com.sca.in_telligent.ui.base.MvpPresenter;
import com.sca.in_telligent.ui.main.MainMvpView;

@PerActivity
public interface MainMvpPresenter<V extends MainMvpView> extends MvpPresenter<V> {
    void getCommunityInfo(int i, int i2);

    void getSubscriber();

    void getSubscriber(boolean z);

    void getSuggestedGroups();

    boolean isLoggedIn();

    void onAppOpened();

    void onClickAd(int i);

    void refreshGeofences(Location location);

    void requestDNDPermission(Context context);

    void requestLocationPermissions(boolean phone);

    void requestPhonePermission();

    void startFetchingAds();

    void subscribeToCommunity(int i);

    void subscribeToCommunity(int i, int i2);
}
