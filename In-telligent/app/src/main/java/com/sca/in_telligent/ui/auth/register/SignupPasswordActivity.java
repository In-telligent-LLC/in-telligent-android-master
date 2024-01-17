package com.sca.in_telligent.ui.auth.register;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.StringRes;

import com.sca.in_telligent.R;
import com.sca.in_telligent.openapi.data.network.model.SignUpRequest;
import com.sca.in_telligent.ui.auth.login.LoginActivity;
import com.sca.in_telligent.ui.base.BaseActivity;
import com.sca.in_telligent.ui.main.MainActivity;
import com.sca.in_telligent.util.CommonUtils;

import java.security.Permission;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.rxjava3.annotations.Nullable;

public class SignupPasswordActivity extends BaseActivity implements SignupPasswordMvpView {

    @Inject
    SignupPasswordMvpPresenter<SignupPasswordMvpView> mPresenter;

    @BindView(R.id.inputPasswordFirst)
    EditText inputPasswordFirst;

    @BindView(R.id.inputPasswordSecond)
    EditText inputPasswordSecond;

    @BindView(R.id.btnSignup)
    Button buttonSignup;

    @BindView(R.id.btnGoToLogin)
    TextView buttonGoToLogin;

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, SignupPasswordActivity.class);
        return intent;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_password);
        getActivityComponent().inject(this);
        inputPasswordFirst = findViewById(R.id.inputPasswordFirst);
        inputPasswordSecond = findViewById(R.id.inputPasswordSecond);
        buttonSignup = findViewById(R.id.btnSignup);
        buttonGoToLogin = findViewById(R.id.btnGoToLogin);

        setUnBinder(ButterKnife.bind(this));

        mPresenter.onAttach(SignupPasswordActivity.this);

        buttonGoToLogin.setOnClickListener(v -> goToLogin(v));
        buttonSignup.setOnClickListener(v -> signUpClick(v));

    }

    @OnClick(R.id.btnGoToLogin)
    void goToLogin(View v) {
        startActivityWithDeeplink(LoginActivity.getStartIntent(this));
    }

    @OnClick(R.id.btnSignup)
    void signUpClick(View v) {
        String passwordFirst = inputPasswordFirst.getText().toString();
        String passwordSecond = inputPasswordSecond.getText().toString();
        String android_id = CommonUtils.getDeviceId(this);
        inputPasswordFirst.setError(null);
        mPresenter.signUp(passwordFirst, passwordSecond, android_id, getSignUpRequest());
    }

    private SignUpRequest getSignUpRequest() {
        SignUpRequest signUpRequest = (SignUpRequest) getIntent().getExtras()
                .getSerializable("signUpRequest");
        return signUpRequest;
    }

    @Override
    protected void setUp() {

    }

    @Override
    public void showLoading() {
        hideKeyboard();
        super.showLoading();
    }

    @Override
    public void phonePermissionResult(Permission permission) {

    }

    @Override
    public void phonePermissionResult(boolean permission) {

    }

    @Override
    public void onDestroy() {
        mPresenter.onDetach();
        super.onDestroy();
    }

    @Override
    public void showError(@StringRes int stringResourceId) {
        inputPasswordFirst.setError(getString(stringResourceId));
    }

    @Override
    public void goToMain() {
        Intent intent = MainActivity.getStartIntent(SignupPasswordActivity.this);
        startActivity(intent);
        finish();
    }
}
