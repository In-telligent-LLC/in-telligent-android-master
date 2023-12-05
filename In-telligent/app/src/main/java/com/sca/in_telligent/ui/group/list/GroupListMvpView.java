package com.sca.in_telligent.ui.group.list;

import com.sca.in_telligent.openapi.data.network.model.Building;
import com.sca.in_telligent.ui.base.MvpView;

import java.util.ArrayList;

public interface GroupListMvpView extends MvpView {

    void updateGroupList(ArrayList<Building> otherBuildings);

    void subscribed(String buildingId, boolean suggested);

    void unsubscribed(String buildingId);

    void updateSuggested(ArrayList<Building> suggestedGroups);

    void updateNextSuggested(Building building, int ignoredPosition);

    void onOptOutFromCommunitySuccess();
}
