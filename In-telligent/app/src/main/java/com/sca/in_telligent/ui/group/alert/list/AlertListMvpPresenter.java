package com.sca.in_telligent.ui.group.alert.list;

import com.sca.in_telligent.ui.base.MvpPresenter;
import com.sca.in_telligent.ui.group.alert.list.AlertListMvpView;

/* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
public interface AlertListMvpPresenter<V extends AlertListMvpView> extends MvpPresenter<V> {
    void deleteAlert(String str, int i);

    void getNotifications(String str, boolean z);
}
