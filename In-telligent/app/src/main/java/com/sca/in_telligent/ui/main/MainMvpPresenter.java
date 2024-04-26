package com.sca.in_telligent.ui.main;

import android.location.Location;
import com.sca.in_telligent.di.PerActivity;
import com.sca.in_telligent.ui.base.MvpPresenter;
import com.sca.in_telligent.ui.main.MainMvpView;

@PerActivity
public interface MainMvpPresenter<V extends MainMvpView> extends MvpPresenter<V> {
    void getCommunityInfo(int communityId, int inviteId);

    void getSubscriber();

    void getSubscriber(boolean showLoading);

    void getSuggestedGroups();

    boolean isLoggedIn();

    void onAppOpened();

    void onClickAd(int i);

    void refreshGeofences(Location location);

    void requestLocationPermissions(boolean location);

    void requestPhonePermission();

    void startFetchingAds();

    void subscribeToCommunity(int buildingId);

    void subscribeToCommunity(int buildingId, int inviteId);
}
