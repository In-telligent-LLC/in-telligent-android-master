package com.sca.in_telligent.ui.contact.message.deliver;

import com.sca.in_telligent.ui.base.MvpPresenter;

public interface ContactDeliverMvpPresenter<V extends ContactDeliverMvpView> extends
        MvpPresenter<V> {

    void getInvitees(String buildingId);

    void getSubscribers(String buildingId);

    void filterNames(String name);
}
