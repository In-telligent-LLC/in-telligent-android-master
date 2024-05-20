package com.sca.in_telligent.ui.group.member;


import com.sca.in_telligent.openapi.data.network.model.Invitee;
import com.sca.in_telligent.openapi.data.network.model.LocationModel;
import com.sca.in_telligent.ui.base.MvpView;

import java.util.ArrayList;

public interface MemberListMvpView extends MvpView {

    void memberListFetched(ArrayList<Invitee> invitees);

    void lastLocationFetched(LocationModel locationModel, String subscriberId, String name);

    void onMemberInvitationRemoved(int position);
}
