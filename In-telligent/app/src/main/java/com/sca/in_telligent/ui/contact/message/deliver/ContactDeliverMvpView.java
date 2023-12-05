package com.sca.in_telligent.ui.contact.message.deliver;

import com.sca.in_telligent.openapi.data.network.model.BuildingMember;
import com.sca.in_telligent.openapi.data.network.model.Invitee;
import com.sca.in_telligent.ui.base.DialogMvpView;
import java.util.ArrayList;

public interface ContactDeliverMvpView extends DialogMvpView {

  void inviteeFetched(ArrayList<Invitee> invitees);

  void memberFetched(ArrayList<BuildingMember> members);
}
