package com.sca.in_telligent.ui.group.list;

import com.sca.in_telligent.openapi.data.network.model.UpdateSubscriptionRequest;
import com.sca.in_telligent.ui.base.MvpPresenter;
import com.sca.in_telligent.ui.group.list.GroupListMvpView;

/* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
public interface GroupListMvpPresenter<V extends GroupListMvpView> extends MvpPresenter<V> {
    void getSuggestedGroups();

    void onIgnoreCommunityClicked(String str, int i);

    void optOutFromCommunity(int i, boolean z);

    void searchCommunities(String str);

    void subscribe(UpdateSubscriptionRequest updateSubscriptionRequest, boolean z);

    void unsubscribe(UpdateSubscriptionRequest updateSubscriptionRequest);
}
