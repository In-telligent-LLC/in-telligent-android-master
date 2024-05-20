package com.sca.in_telligent.ui.main;


import android.location.Location;

import com.sca.in_telligent.di.PerActivity;
import com.sca.in_telligent.ui.base.MvpPresenter;

@PerActivity
public interface MainMvpPresenter<V extends MainMvpView> extends
        MvpPresenter<V> {

    boolean isLoggedIn();

    void getSubscriber();

    void getSubscriber(boolean showLoading);

    void getSuggestedGroups();

    void requestLocationPermissions(boolean phone);

    void requestPhonePermission();

    void subscribeToCommunity(int buildingId);

    void subscribeToCommunity(int buildingId, int inviteId);

    void onAppOpened();

    void getCommunityInfo(int communityId, int inviteId);

    void refreshGeofences(Location location);

    void startFetchingAds();

    void onClickAd(int adId);
}
