package com.sca.in_telligent.ui.group.list;

import com.sca.in_telligent.openapi.data.network.model.UpdateSubscriptionRequest;
import com.sca.in_telligent.ui.base.MvpPresenter;

public interface GroupListMvpPresenter<V extends GroupListMvpView> extends MvpPresenter<V> {

    void searchCommunities(String groupName);

    void subscribe(UpdateSubscriptionRequest updateSubscriptionRequest, boolean suggested);

    void unsubscribe(UpdateSubscriptionRequest updateSubscriptionRequest);

    void getSuggestedGroups();

    void onIgnoreCommunityClicked(String buildingId, int ignoredPosition);

    void optOutFromCommunity(int buildingId, boolean optOut);
}
