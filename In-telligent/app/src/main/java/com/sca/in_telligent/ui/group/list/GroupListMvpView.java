package com.sca.in_telligent.ui.group.list;

import com.sca.in_telligent.openapi.data.network.model.Building;
import com.sca.in_telligent.ui.base.MvpView;
import java.util.ArrayList;

/* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
public interface GroupListMvpView extends MvpView {
    void onOptOutFromCommunitySuccess();

    void subscribed(String str, boolean z);

    void unsubscribed(String str);

    void updateGroupList(ArrayList<Building> arrayList);

    void updateNextSuggested(Building building, int i);

    void updateSuggested(ArrayList<Building> arrayList);
}
