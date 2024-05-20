package com.sca.in_telligent.ui.group.member.invite;

import com.sca.in_telligent.openapi.data.network.model.InviteOthersRequest;
import com.sca.in_telligent.ui.base.MvpPresenter;

public interface InviteMemberMvpPresenter<V extends InviteMemberMvpView> extends MvpPresenter<V> {

  void sendInvite(String buildingId, InviteOthersRequest inviteOthersRequest);
}
