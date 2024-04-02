package com.sca.in_telligent.ui.main;

import com.sca.in_telligent.openapi.data.network.model.AdResponse;
import com.sca.in_telligent.openapi.data.network.model.Building;
import com.sca.in_telligent.openapi.data.network.model.BuildingIdItem;
import com.sca.in_telligent.openapi.data.network.model.Subscriber;
import com.sca.in_telligent.ui.base.MvpView;

import java.util.ArrayList;

public interface MainMvpView extends MvpView {
    void loadGroups(ArrayList<Building> arrayList, ArrayList<Building> arrayList2, ArrayList<BuildingIdItem> arrayList3);

    void loadImage(AdResponse.BannerAd bannerAd);

    void loadSubscriber(Subscriber subscriber);

    void loadSuggestedGroups(ArrayList<Building> arrayList);

    void locationPermissionResult(boolean granted, boolean phone);

    void phonePermissionResult(boolean granted);

    void showSubscribeToCommunityDialog(String name, int communityId, int inviteId);

    void showSubscriptionSuccessfulMessage();


}
