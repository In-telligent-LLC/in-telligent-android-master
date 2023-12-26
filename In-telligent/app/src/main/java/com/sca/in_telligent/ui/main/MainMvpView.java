package com.sca.in_telligent.ui.main;

import com.sca.in_telligent.openapi.data.network.model.AdResponse;
import com.sca.in_telligent.openapi.data.network.model.Building;
import com.sca.in_telligent.openapi.data.network.model.BuildingIdItem;
import com.sca.in_telligent.openapi.data.network.model.Subscriber;
import com.sca.in_telligent.ui.base.MvpView;

import java.security.Permission;
import java.util.ArrayList;

/* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
public interface MainMvpView extends MvpView {
    void loadGroups(ArrayList<Building> arrayList, ArrayList<Building> arrayList2, ArrayList<BuildingIdItem> arrayList3);

    void loadImage(AdResponse.BannerAd bannerAd);

    void loadSubscriber(Subscriber subscriber);

    void loadSuggestedGroups(ArrayList<Building> arrayList);

    void locationPermissionResult(boolean z, boolean z2);

    void phonePermissionResult(Permission permission);

    void showSubscribeToCommunityDialog(String str, int i, int i2);

    void showSubscriptionSuccessfulMessage();
}
