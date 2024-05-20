package com.sca.in_telligent.ui.inbox;

import com.sca.in_telligent.openapi.data.network.model.AlertDeleteRequest;
import com.sca.in_telligent.ui.base.MvpPresenter;

public interface InboxMvpPresenter<V extends InboxMvpView> extends MvpPresenter<V> {


    void getInbox(String page, boolean showLoading);

    void getInbox(String page);

    void getSavedMessages();

    void deleteAlert(AlertDeleteRequest alertDeleteRequest, int position);
}
