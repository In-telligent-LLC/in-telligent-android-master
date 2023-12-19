package com.sca.in_telligent.ui.auth.register;

import android.text.TextUtils;

import com.sca.in_telligent.R;
import com.sca.in_telligent.data.DataManager;
import com.sca.in_telligent.data.DataManager.LoggedInMode;
import com.sca.in_telligent.openapi.data.network.model.SignUpRequest;
import com.sca.in_telligent.ui.base.BasePresenter;
import com.sca.in_telligent.util.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.rxjava3.disposables.CompositeDisposable;

public class SignupPasswordPresenter<V extends SignupPasswordMvpView> extends
        BasePresenter<V> implements SignupPasswordMvpPresenter<V> {

    private static final int MIN_PASSWORD_LENGTH = 3;

    @Inject
    public SignupPasswordPresenter(DataManager dataManager,
                                   SchedulerProvider schedulerProvider,
                                   CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void signUp(String password, String passwordConfirm, String deviceId, SignUpRequest signUpRequest) {

        if (TextUtils.isEmpty(password) || password.length() < MIN_PASSWORD_LENGTH) {
            getMvpView().showError(R.string.invalid_password);
            return;
        }

        if (!password.equals(passwordConfirm)) {
            getMvpView().showError(R.string.passwords_dont_match);
            return;
        }

        signUpRequest.setPassword(password);
        signUpRequest.setPassword2(passwordConfirm);
        signUpRequest.setDeviceId(deviceId);
        getMvpView().showLoading();
        getCompositeDisposable().add(
                getDataManager().signUp(signUpRequest).subscribeOn(getSchedulerProvider().io())
                        .observeOn(getSchedulerProvider().ui()).subscribe(loginResponse -> {
                            getMvpView().hideLoading();
                            if (loginResponse.isSuccess()) {
                                getDataManager()
                                        .updateUserInfo(loginResponse.getToken(), LoggedInMode.LOGGED_IN_MODE_LOGGED_IN);
                                getMvpView().goToMain();
                            } else {
                                if (loginResponse.getError() != null) {
                                    getMvpView().showMessage(loginResponse.getError());
                                }
                            }
                        }, throwable -> {
                    getMvpView().hideLoading();
                    getMvpView().showError(R.string.there_was_error_sending_request);
                }));
    }
}
