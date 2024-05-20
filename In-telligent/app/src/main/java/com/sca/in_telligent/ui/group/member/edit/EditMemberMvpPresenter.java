package com.sca.in_telligent.ui.group.member.edit;

import com.sca.in_telligent.openapi.data.network.model.EditCommunityInviteeRequest;
import com.sca.in_telligent.ui.base.MvpPresenter;

public interface EditMemberMvpPresenter<V extends EditMemberMvpView> extends MvpPresenter<V> {
  void saveMemberEdit(EditCommunityInviteeRequest editCommunityInviteeRequest);
}
