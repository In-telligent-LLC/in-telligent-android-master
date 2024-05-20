package com.sca.in_telligent.ui.auth.forgot;

import com.sca.in_telligent.openapi.data.network.model.ForgotPasswordRequest;
import com.sca.in_telligent.ui.base.MvpPresenter;

public interface ForgotPasswordMvpPresenter<V extends ForgotPasswordMvpView> extends MvpPresenter<V> {

  void submitEmail(ForgotPasswordRequest forgotPasswordRequest);
}
