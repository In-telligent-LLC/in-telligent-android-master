package com.sca.in_telligent.ui.group.generate;

import com.sca.in_telligent.ui.base.MvpView;

public interface GenerateGroupMvpView extends MvpView {
    void permissionResult(boolean granted);

    void groupCreateResult(boolean created);
}
