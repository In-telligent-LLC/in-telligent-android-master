package com.sca.in_telligent.ui.base;

import com.sca.in_telligent.ui.base.MvpView;

/* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
public interface MvpPresenter<V extends MvpView> {
    void onAttach(V v);

    void onDetach();
}
