package com.sca.in_telligent.ui.auth.reset;

import com.sca.in_telligent.openapi.data.network.model.ResetPasswordRequest;
import com.sca.in_telligent.ui.base.MvpPresenter;

public interface ResetPasswordMvpPresenter<V extends ResetPasswordMvpView> extends MvpPresenter<V> {

  void submit(ResetPasswordRequest resetPasswordRequest);

}
