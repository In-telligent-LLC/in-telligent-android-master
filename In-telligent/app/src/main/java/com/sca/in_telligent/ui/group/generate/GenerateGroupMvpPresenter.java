package com.sca.in_telligent.ui.group.generate;

import android.net.Uri;
import com.sca.in_telligent.openapi.data.network.model.CreateEditGroupRequest;
import com.sca.in_telligent.ui.base.MvpPresenter;

public interface GenerateGroupMvpPresenter<V extends GenerateGroupMvpView> extends MvpPresenter<V> {

    void requestPermissions();

    void upload(String buildingId, Uri uri, String name, String description);

    void createNoThumbnail(String buildingId, CreateEditGroupRequest createEditGroupRequest);
}
