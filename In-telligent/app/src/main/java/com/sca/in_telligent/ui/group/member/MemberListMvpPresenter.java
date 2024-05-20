package com.sca.in_telligent.ui.group.member;

import com.sca.in_telligent.ui.base.MvpPresenter;

public interface MemberListMvpPresenter<V extends MemberListMvpView> extends MvpPresenter<V> {

    void getMembers(String buildingId, boolean showLoading);

    void getLastLocation(String subscriberId, String name);

    void removeMember(int inviteId, int position);

    void filterMembers(String name);
}
