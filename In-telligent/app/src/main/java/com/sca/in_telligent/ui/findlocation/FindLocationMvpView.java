package com.sca.in_telligent.ui.findlocation;

import com.sca.in_telligent.openapi.data.network.model.LocationModel;
import com.sca.in_telligent.ui.base.DialogMvpView;

public interface FindLocationMvpView extends DialogMvpView {

  void updatedLocationFetched(LocationModel locationModel);

  void locationPermissionResult(boolean granted);
}
